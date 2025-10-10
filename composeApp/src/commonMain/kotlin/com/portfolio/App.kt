package com.portfolio

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import portfolio.composeapp.generated.resources.Res
import portfolio.composeapp.generated.resources.compose_multiplatform

@Composable
@Preview
fun App() {
    DeepTechTheme {
        val sectionTitles = listOf("Welcome", "About Me", "Services", "Resume", "Speaking", "Contact")
        val listState = rememberLazyListState()
        var activeIndex by remember { mutableStateOf(0) }
        val scope = rememberCoroutineScope()
        val bringIntoViewRequesters = remember { sectionTitles.map { BringIntoViewRequester() } }
        val sectionOffsets = remember { MutableList(sectionTitles.size) { 0 } }

        Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {

            // Content
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 16.dp),
                verticalArrangement = Arrangement.spacedBy(0.dp),
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
                        Welcome(modifier = Modifier.fillMaxWidth())
                    }
                }

                itemsIndexed(sectionTitles.drop(1)) { index, title ->
                    when (title) {
                        "About Me" -> AboutMe(
                            modifier = Modifier
                                .onGloballyPositioned { coordinates ->
                                    sectionOffsets[index + 1] = coordinates.positionInRoot().y.toInt()
                                }
                                .bringIntoViewRequester(bringIntoViewRequesters[index + 1])
                        )
                        "Services" -> Services(
                            modifier = Modifier
                                .onGloballyPositioned { coordinates ->
                                    sectionOffsets[index + 1] = coordinates.positionInRoot().y.toInt()
                                }
                                .bringIntoViewRequester(bringIntoViewRequesters[index + 1])
                        )
                        else -> Section(
                            title = title,
                            modifier = Modifier
                                .onGloballyPositioned { coordinates ->
                                    sectionOffsets[index + 1] = coordinates.positionInRoot().y.toInt()
                                }
                                .bringIntoViewRequester(bringIntoViewRequesters[index + 1])
                        )
                    }
                }
            }

            Box(modifier = Modifier.fillMaxHeight().width(220.dp)) {
                SidebarNav(
                    items = sectionTitles,
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

        // Update active index based on section whose top is closest to the viewport top
        LaunchedEffect(listState.firstVisibleItemIndex, listState.firstVisibleItemScrollOffset) {
            activeIndex = listState.firstVisibleItemIndex
        }
    }
}

@Composable
private fun Section(title: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 64.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painterResource(Res.drawable.compose_multiplatform), null)
        Text(title, style = MaterialTheme.typography.headlineLarge)
        Text("Lorem ipsum dolor sit amet, consectetur adipiscing elit.")
    }
}