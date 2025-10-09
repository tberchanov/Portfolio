package com.portfolio

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Deep Tech palette
val DeepCharcoal = Color(0xFF1D2024)      // background
val TextOffWhite = Color(0xFFF5F5F5)      // on background
val ElectricCyan = Color(0xFF00FFFF)      // primary accent
val MutedTeal = Color(0xFF4A9090)         // secondary accent

private val DeepTechDarkColors: ColorScheme = darkColorScheme(
    primary = ElectricCyan,
    onPrimary = DeepCharcoal,
    secondary = MutedTeal,
    onSecondary = DeepCharcoal,
    background = DeepCharcoal,
    onBackground = TextOffWhite,
    surface = Color(0xFF22262B),
    onSurface = TextOffWhite,
    surfaceVariant = Color(0xFF292E34),
    onSurfaceVariant = TextOffWhite.copy(alpha = 0.8f),
    outline = MutedTeal.copy(alpha = 0.6f)
)

@Composable
fun DeepTechTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DeepTechDarkColors,
        content = content
    )
}
