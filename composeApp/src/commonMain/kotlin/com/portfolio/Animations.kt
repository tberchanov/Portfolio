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

private const val INITIAL_ANIMATION_DURATION = 1500
private const val REGULAR_ANIMATION_DURATION = 300

@Composable
fun AnimatedSection(
    block: @Composable () -> Unit
) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isVisible = true
    }

    val animationDuration = if (isVisible) {
        INITIAL_ANIMATION_DURATION
    } else {
        REGULAR_ANIMATION_DURATION
    }

    val alpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(durationMillis = animationDuration)
    )

    val offsetY by animateDpAsState(
        targetValue = if (isVisible) 0.dp else 12.dp,
        animationSpec = tween(durationMillis = animationDuration)
    )

    Box(
        modifier = Modifier
            .alpha(alpha)
            .offset(y = offsetY)
    ) {
        block()
    }
}


