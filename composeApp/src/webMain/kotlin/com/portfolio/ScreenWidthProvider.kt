package com.portfolio

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalDensity
import kotlinx.browser.window
import kotlinx.coroutines.delay

// Constants for screen width polling
private const val POLLING_INTERVAL_MS = 200L

/**
 * Provides the current screen width in dp units.
 * Uses polling to detect window resize events for WASM compatibility.
 */
@Composable
actual fun getScreenWidthDp(): Int {
    val density = LocalDensity.current
    var windowWidth by remember { mutableStateOf(window.innerWidth) }
    
    // Poll for window size changes every 200ms
    // This approach is WASM-compatible and efficient
    LaunchedEffect(Unit) {
        while (true) {
            delay(POLLING_INTERVAL_MS)
            val newWidth = window.innerWidth
            if (newWidth != windowWidth) {
                windowWidth = newWidth
            }
        }
    }
    
    // Convert pixels to dp using the current density
    return with(density) { windowWidth.toDp().value.toInt() }
}
