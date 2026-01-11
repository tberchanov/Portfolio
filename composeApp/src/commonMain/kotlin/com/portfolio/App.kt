package com.portfolio

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

private const val SIDEBAR_WIDTH_DP = 300
private const val MIN_CONTENT_WIDTH_DP = 400
private const val SECTION_SPACING_DP = 0
private const val SCROLL_THRESHOLD = 0.5f

private val RESPONSIVE_BREAKPOINT_DP = SIDEBAR_WIDTH_DP + MIN_CONTENT_WIDTH_DP

sealed class Section(val name: String) {
    data object Welcome : Section("Welcome")
    data object AboutMe : Section("About Me")
    data object Services : Section("Services")
    data object SideProjects : Section("Side Projects")
    data object Contact : Section("Contact")
}

@Composable
@Preview
fun App() {
    DeepTechTheme {
        val screenWidthDp = getScreenWidthDp()
        val showSidebar = screenWidthDp >= RESPONSIVE_BREAKPOINT_DP

        val sections = listOf(
            Section.Welcome,
            Section.AboutMe,
            Section.Services,
            Section.SideProjects,
            Section.Contact,
        )
        val listState = rememberLazyListState()
        var activeIndex by remember { mutableStateOf(0) }
        val scope = rememberCoroutineScope()
        val bringIntoViewRequesters = remember { sections.map { BringIntoViewRequester() } }
        val sectionOffsets = remember { MutableList(sections.size) { 0 } }

        Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(SECTION_SPACING_DP.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillParentMaxHeight()
                            .onGloballyPositioned { coordinates ->
                                sectionOffsets[0] = coordinates.positionInRoot().y.toInt()
                            }
                            .bringIntoViewRequester(bringIntoViewRequesters[0])
                    ) {
                        Welcome(
                            modifier = Modifier.fillMaxWidth(),
                            onScrollToAboutMe = {
                                scope.launch {
                                    listState.animateScrollToItem(1)
                                }
                            }
                        )
                    }
                }

                itemsIndexed(sections.drop(1)) { index, section ->
                    when (section) {
                        Section.Welcome -> {
                            // Welcome is already added as a separate item
                        }
                        Section.AboutMe -> AnimatedSection {
                            AboutMe(
                                modifier = Modifier
                                    .onGloballyPositioned { coordinates ->
                                        sectionOffsets[index + 1] =
                                            coordinates.positionInRoot().y.toInt()
                                    }
                                    .bringIntoViewRequester(bringIntoViewRequesters[index + 1])
                            )
                        }

                        Section.Services -> AnimatedSection {
                            Services(
                                modifier = Modifier
                                    .onGloballyPositioned { coordinates ->
                                        sectionOffsets[index + 1] =
                                            coordinates.positionInRoot().y.toInt()
                                    }
                                    .bringIntoViewRequester(bringIntoViewRequesters[index + 1])
                            )
                        }

                        Section.SideProjects -> AnimatedSection {
                            Projects(
                                modifier = Modifier
                                    .onGloballyPositioned { coordinates ->
                                        sectionOffsets[index + 1] =
                                            coordinates.positionInRoot().y.toInt()
                                    }
                                    .bringIntoViewRequester(bringIntoViewRequesters[index + 1])
                            )
                        }

                        Section.Contact -> AnimatedSection {
                            ContactMe(
                                modifier = Modifier
                                    .onGloballyPositioned { coordinates ->
                                        sectionOffsets[index + 1] =
                                            coordinates.positionInRoot().y.toInt()
                                    }
                                    .bringIntoViewRequester(bringIntoViewRequesters[index + 1])
                            )
                        }
                    }
                }

                item {
                    Footer()
                }
            }

            if (showSidebar) {
                Box(modifier = Modifier.fillMaxHeight().width(SIDEBAR_WIDTH_DP.dp)) {
                    SidebarNav(
                        items = sections.map { it.name },
                        activeIndex = activeIndex,
                        contentPadding = PaddingValues(horizontal = 38.dp, vertical = 24.dp),
                        onClick = { index ->
                            activeIndex = index
                            scope.launch {
                                listState.animateScrollToItem(index)
                            }
                        }
                    )
                }
            }
        }

        LaunchedEffect(listState.layoutInfo) {
            val layoutInfo = listState.layoutInfo
            val firstVisibleIndex = listState.firstVisibleItemIndex
            val firstVisibleOffset = listState.firstVisibleItemScrollOffset
            val contactItemIndex = sections.size - 1
            val isFooterVisible = layoutInfo.visibleItemsInfo.any { it.index >= sections.size }

            activeIndex = when {
                isFooterVisible -> sections.size - 1
                firstVisibleIndex == contactItemIndex -> contactItemIndex
                firstVisibleIndex >= sections.size -> sections.size - 1
                else -> {
                    val firstVisibleItemInfo = layoutInfo.visibleItemsInfo.firstOrNull()
                    val firstVisibleItemHeight = firstVisibleItemInfo?.size ?: 0
                    val scrolledPastRatio = if (firstVisibleItemHeight > 0) {
                        firstVisibleOffset.toFloat() / firstVisibleItemHeight.toFloat()
                    } else 0f

                    if (scrolledPastRatio > SCROLL_THRESHOLD && firstVisibleIndex < sections.size - 1) {
                        firstVisibleIndex + 1
                    } else {
                        firstVisibleIndex
                    }
                }
            }.coerceIn(0, sections.size - 1)
        }
    }
}
