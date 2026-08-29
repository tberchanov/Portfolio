package com.portfolio

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

/** Palette of the spec-sheet redesign. */
val SheetBlack = Color(0xFF0B0C0D)
val PageBlack = Color(0xFF0E0F11)
val BoneWhite = Color(0xFFE8EAE6)
val AshGrey = Color(0xFF787D7A)
val SmokeGrey = Color(0xFFA6ABA3)
val AcidLime = Color(0xFFC6F24E)

val Hairline = BoneWhite.copy(alpha = 0.10f)
val HairlineStrong = BoneWhite.copy(alpha = 0.20f)

private val SpecSheetColors: ColorScheme = darkColorScheme(
    primary = AcidLime,
    onPrimary = SheetBlack,
    secondary = SmokeGrey,
    onSecondary = SheetBlack,
    background = SheetBlack,
    onBackground = BoneWhite,
    surface = SheetBlack,
    onSurface = BoneWhite,
    surfaceVariant = PageBlack,
    onSurfaceVariant = AshGrey,
    outline = Hairline
)

@Composable
fun PortfolioTheme(content: @Composable () -> Unit) {
    val screenSize = rememberScreenSize()
    MaterialTheme(colorScheme = SpecSheetColors) {
        CompositionLocalProvider(
            LocalScreenSize provides screenSize,
            LocalMetrics provides metricsFor(screenSize),
            LocalDisplayFamily provides manropeFamily(),
            LocalMonoFamily provides plexMonoFamily(),
            content = content
        )
    }
}
