package com.portfolio

import androidx.compose.runtime.Composable

/**
 * The width of the window the app is drawn into, in dp, kept up to date as that
 * window is resized.
 *
 * Android, iOS and desktop read it from the Compose window; the browser targets
 * read it from `window.innerWidth`, which needs its own resize subscription.
 *
 * Call this once per composition — prefer [LocalScreenSize] elsewhere.
 */
@Composable
expect fun rememberScreenWidthDp(): Int
