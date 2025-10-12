package com.portfolio

import androidx.compose.runtime.Composable

/**
 * Provides the current screen width in dp units across all platforms.
 * 
 * This function uses platform-specific implementations to get the screen width:
 * - Web (WASM): Uses window.innerWidth with polling for resize events
 * - Android/iOS/Desktop: Uses LocalConfiguration.current.screenWidthDp
 * 
 * @return The current screen width in dp units
 */
@Composable
expect fun getScreenWidthDp(): Int
