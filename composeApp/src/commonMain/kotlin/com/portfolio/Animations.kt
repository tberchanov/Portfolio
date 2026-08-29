package com.portfolio

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp

private const val ENTRY_DURATION_MS = 1500
private val EntryOffset = 12.dp

/** Fades and lifts [content] into place once, the first time it is composed. */
@Composable
fun AnimatedSection(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visible = true
    }

    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(ENTRY_DURATION_MS),
        label = "sectionAlpha"
    )

    val offsetY by animateDpAsState(
        targetValue = if (visible) 0.dp else EntryOffset,
        animationSpec = tween(ENTRY_DURATION_MS),
        label = "sectionOffset"
    )

    Box(
        modifier = modifier
            .alpha(alpha)
            .offset(y = offsetY)
    ) {
        content()
    }
}
