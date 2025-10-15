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

            // Content
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(SECTION_SPACING_DP.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Welcome full-height item
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
                                    listState.animateScrollToItem(1) // Scroll to "About Me" section
                                }
                            }
                        )
                    }
                }

                itemsIndexed(sections.drop(1)) { index, section ->
                    when (section) {
                        Section.Welcome -> {
                            // none, as it is already added as a separate item.
                        }
                        Section.AboutMe -> AboutMe(
                            modifier = Modifier
                                .onGloballyPositioned { coordinates ->
                                    sectionOffsets[index + 1] =
                                        coordinates.positionInRoot().y.toInt()
                                }
                                .bringIntoViewRequester(bringIntoViewRequesters[index + 1])
                        )

                        Section.Services -> Services(
                            modifier = Modifier
                                .onGloballyPositioned { coordinates ->
                                    sectionOffsets[index + 1] =
                                        coordinates.positionInRoot().y.toInt()
                                }
                                .bringIntoViewRequester(bringIntoViewRequesters[index + 1])
                        )

                        Section.SideProjects -> Projects(
                            modifier = Modifier
                                .onGloballyPositioned { coordinates ->
                                    sectionOffsets[index + 1] =
                                        coordinates.positionInRoot().y.toInt()
                                }
                                .bringIntoViewRequester(bringIntoViewRequesters[index + 1])
                        )

                        Section.Contact -> ContactMe(
                            modifier = Modifier
                                .onGloballyPositioned { coordinates ->
                                    sectionOffsets[index + 1] =
                                        coordinates.positionInRoot().y.toInt()
                                }
                                .bringIntoViewRequester(bringIntoViewRequesters[index + 1])
                        )
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

        LaunchedEffect(listState.firstVisibleItemIndex, listState.firstVisibleItemScrollOffset) {
            val firstVisibleIndex = listState.firstVisibleItemIndex
            val firstVisibleOffset = listState.firstVisibleItemScrollOffset

            val firstVisibleItemInfo = listState.layoutInfo.visibleItemsInfo.firstOrNull()
            val firstVisibleItemHeight = firstVisibleItemInfo?.size ?: 0

            val scrolledPastRatio = if (firstVisibleItemHeight > 0) {
                firstVisibleOffset.toFloat() / firstVisibleItemHeight.toFloat()
            } else 0f

            activeIndex =
                if (scrolledPastRatio > SCROLL_THRESHOLD && firstVisibleIndex < sections.size - 1) {
                    firstVisibleIndex + 1
                } else {
                    firstVisibleIndex
                }
        }
    }
}
