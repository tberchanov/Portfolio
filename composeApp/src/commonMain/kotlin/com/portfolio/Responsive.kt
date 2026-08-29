package com.portfolio

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/** Width in dp at which the sheet stops growing. Matches the canvas artboard. */
val SheetMaxWidth: Dp = 1100.dp

private const val MOBILE_MAX_DP = 600
private const val TABLET_MAX_DP = 900

enum class ScreenSize {
    Mobile,
    Tablet,
    Desktop;

    val isMobile: Boolean get() = this == Mobile
}

/**
 * The current breakpoint. Resolved once, in [PortfolioTheme], and handed to the
 * tree through [LocalScreenSize] — the platform width lookup it builds on is not
 * free (on the web it observes window resizes), so the sheet reads it only here.
 */
@Composable
fun rememberScreenSize(): ScreenSize {
    val widthDp = rememberScreenWidthDp()
    return when {
        widthDp <= MOBILE_MAX_DP -> ScreenSize.Mobile
        widthDp <= TABLET_MAX_DP -> ScreenSize.Tablet
        else -> ScreenSize.Desktop
    }
}

/**
 * Every dimension the sheet uses, resolved once per breakpoint.
 *
 * The desktop column is the canvas 1:1; tablet and mobile keep the same
 * proportions with the type and the generous vertical rhythm scaled down.
 */
@Immutable
data class Metrics(
    val sheetPadding: Dp,
    val heroTopPadding: Dp,
    val blockGap: Dp,
    val labelGap: Dp,
    val rowPadding: Dp,
    val indexColumn: Dp,
    val columnGap: Dp,
    val avatar: Dp,
    val display: TextUnit,
    val rowTitle: TextUnit,
    val workTitle: TextUnit,
    val contact: TextUnit,
    val body: TextUnit,
    val caption: TextUnit,
    val label: TextUnit,
    val micro: TextUnit
)

private val DesktopMetrics = Metrics(
    sheetPadding = 112.dp,
    heroTopPadding = 200.dp,
    blockGap = 180.dp,
    labelGap = 44.dp,
    rowPadding = 30.dp,
    indexColumn = 56.dp,
    columnGap = 32.dp,
    avatar = 88.dp,
    display = 88.sp,
    rowTitle = 38.sp,
    workTitle = 30.sp,
    contact = 26.sp,
    body = 17.sp,
    caption = 13.sp,
    label = 12.sp,
    micro = 11.sp
)

private val TabletMetrics = Metrics(
    sheetPadding = 56.dp,
    heroTopPadding = 140.dp,
    blockGap = 120.dp,
    labelGap = 36.dp,
    rowPadding = 26.dp,
    indexColumn = 44.dp,
    columnGap = 24.dp,
    avatar = 76.dp,
    display = 64.sp,
    rowTitle = 30.sp,
    workTitle = 26.sp,
    contact = 22.sp,
    body = 16.sp,
    caption = 12.sp,
    label = 11.sp,
    micro = 11.sp
)

private val MobileMetrics = Metrics(
    sheetPadding = 24.dp,
    heroTopPadding = 96.dp,
    blockGap = 80.dp,
    labelGap = 28.dp,
    rowPadding = 22.dp,
    indexColumn = 32.dp,
    columnGap = 16.dp,
    avatar = 64.dp,
    display = 42.sp,
    rowTitle = 24.sp,
    workTitle = 21.sp,
    contact = 19.sp,
    body = 15.sp,
    caption = 11.sp,
    label = 11.sp,
    micro = 10.sp
)

fun metricsFor(screenSize: ScreenSize): Metrics = when (screenSize) {
    ScreenSize.Mobile -> MobileMetrics
    ScreenSize.Tablet -> TabletMetrics
    ScreenSize.Desktop -> DesktopMetrics
}

val LocalScreenSize = staticCompositionLocalOf { ScreenSize.Desktop }
val LocalMetrics = staticCompositionLocalOf { DesktopMetrics }
