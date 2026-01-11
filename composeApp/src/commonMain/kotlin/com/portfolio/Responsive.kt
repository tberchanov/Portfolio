package com.portfolio

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Breakpoints for responsive design
 */
object Breakpoints {
    const val MOBILE_MAX_DP = 600
    const val TABLET_MAX_DP = 900
    const val DESKTOP_MIN_DP = 901
}

/**
 * Screen size categories
 */
enum class ScreenSize {
    Mobile,    // 0-600dp
    Tablet,    // 601-900dp
    Desktop    // 901+ dp
}

/**
 * Determines the current screen size category based on width.
 * 
 * Breakpoints:
 * - Mobile: 0-600dp (inclusive)
 * - Tablet: 601-900dp (inclusive)
 * - Desktop: 901+ dp (inclusive)
 * 
 * @return The current screen size category
 */
@Composable
fun getScreenSize(): ScreenSize {
    val widthDp = getScreenWidthDp()
    return when {
        widthDp <= Breakpoints.MOBILE_MAX_DP -> ScreenSize.Mobile
        widthDp <= Breakpoints.TABLET_MAX_DP -> ScreenSize.Tablet
        else -> ScreenSize.Desktop
    }
}

/**
 * Returns responsive content width with proper padding.
 * 
 * - Mobile: Full width minus padding (16dp per side = 32dp total)
 * - Tablet: 90% of screen width
 * - Desktop: Fixed 900dp max width
 * 
 * @return The maximum content width in dp
 */
@Composable
fun getResponsiveContentWidth(): Dp {
    val screenSize = getScreenSize()
    val screenWidthDp = getScreenWidthDp()
    
    return when (screenSize) {
        ScreenSize.Mobile -> {
            // On mobile, use full width minus padding (16dp per side)
            (screenWidthDp - 32).dp
        }
        ScreenSize.Tablet -> {
            // On tablet, use 90% of width
            (screenWidthDp * 0.9f).dp
        }
        ScreenSize.Desktop -> {
            // On desktop, use fixed max width
            900.dp
        }
    }
}

/**
 * Returns responsive horizontal padding based on screen size.
 * 
 * - Mobile: 16dp
 * - Tablet: 24dp
 * - Desktop: 32dp
 * 
 * @return The horizontal padding in dp
 */
@Composable
fun getResponsiveHorizontalPadding(): Dp {
    val screenSize = getScreenSize()
    return when (screenSize) {
        ScreenSize.Mobile -> 16.dp
        ScreenSize.Tablet -> 24.dp
        ScreenSize.Desktop -> 32.dp
    }
}

/**
 * Returns responsive vertical padding based on screen size.
 * 
 * - Mobile: 32dp
 * - Tablet: 48dp
 * - Desktop: 64dp
 * 
 * @return The vertical padding in dp
 */
@Composable
fun getResponsiveVerticalPadding(): Dp {
    val screenSize = getScreenSize()
    return when (screenSize) {
        ScreenSize.Mobile -> 32.dp
        ScreenSize.Tablet -> 48.dp
        ScreenSize.Desktop -> 64.dp
    }
}

/**
 * Returns responsive font scale multiplier based on screen size.
 * 
 * - Mobile: 0.75 (25% smaller)
 * - Tablet: 0.875 (12.5% smaller)
 * - Desktop: 1.0 (full size)
 * 
 * @return The font scale multiplier (Float)
 */
@Composable
fun getResponsiveFontScale(): Float {
    val screenSize = getScreenSize()
    return when (screenSize) {
        ScreenSize.Mobile -> 0.75f
        ScreenSize.Tablet -> 0.875f
        ScreenSize.Desktop -> 1.0f
    }
}

/**
 * Returns responsive icon size based on screen size.
 * Provides better touch targets on mobile devices.
 * 
 * - Mobile: 22dp (better touch targets)
 * - Tablet: 23dp
 * - Desktop: 24dp
 * 
 * @return The icon size in dp
 */
@Composable
fun getResponsiveIconSize(): Dp {
    val screenSize = getScreenSize()
    return when (screenSize) {
        ScreenSize.Mobile -> 22.dp
        ScreenSize.Tablet -> 23.dp
        ScreenSize.Desktop -> 24.dp
    }
}

/**
 * Returns responsive portrait/image size for AboutMe section.
 * 
 * - Mobile: 150dp
 * - Tablet: 200dp
 * - Desktop: 250dp
 * 
 * @return The portrait size in dp
 */
@Composable
fun getResponsivePortraitSize(): Dp {
    val screenSize = getScreenSize()
    return when (screenSize) {
        ScreenSize.Mobile -> 150.dp
        ScreenSize.Tablet -> 200.dp
        ScreenSize.Desktop -> 250.dp
    }
}

/**
 * Returns responsive section title font size.
 * Convenience function for the standard 36sp section title.
 * 
 * - Mobile: 27sp (36 * 0.75)
 * - Tablet: 31.5sp (36 * 0.875)
 * - Desktop: 36sp (full size)
 * 
 * @return The section title font size in sp (as Float)
 */
@Composable
fun getResponsiveSectionTitleSize(): Float {
    val baseSize = 36f
    return baseSize * getResponsiveFontScale()
}

/**
 * Helper function to check if current screen size is Mobile.
 * Makes conditionals cleaner: `if (isMobile()) Column else Row`
 * 
 * @return true if screen size is Mobile
 */
@Composable
fun isMobile(): Boolean {
    return getScreenSize() == ScreenSize.Mobile
}

/**
 * Helper function to check if current screen size is Tablet.
 * Makes conditionals cleaner: `if (isTablet()) ...`
 * 
 * @return true if screen size is Tablet
 */
@Composable
fun isTablet(): Boolean {
    return getScreenSize() == ScreenSize.Tablet
}

/**
 * Helper function to check if current screen size is Desktop.
 * Makes conditionals cleaner: `if (isDesktop()) ...`
 * 
 * @return true if screen size is Desktop
 */
@Composable
fun isDesktop(): Boolean {
    return getScreenSize() == ScreenSize.Desktop
}

