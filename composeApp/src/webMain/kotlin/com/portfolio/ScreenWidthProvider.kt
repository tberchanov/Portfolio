package com.portfolio

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalDensity
import kotlinx.browser.window

/**
 * Tracks `window.innerWidth`, subscribing to the browser's own resize event
 * rather than polling for it.
 *
 * The handler is installed through `window.onresize` because `addEventListener`
 * takes an `EventListener` on Kotlin/JS and a plain function on Kotlin/Wasm —
 * this source set has to compile for both.
 */
@Composable
actual fun rememberScreenWidthDp(): Int {
    var widthPx by remember { mutableStateOf(window.innerWidth) }

    DisposableEffect(Unit) {
        window.onresize = { widthPx = window.innerWidth }
        onDispose { window.onresize = null }
    }

    return with(LocalDensity.current) { widthPx.toDp().value.toInt() }
}
