package com.portfolio

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.em
import org.jetbrains.compose.resources.Font
import portfolio.composeapp.generated.resources.Res
import portfolio.composeapp.generated.resources.ibm_plex_mono_400
import portfolio.composeapp.generated.resources.ibm_plex_mono_500
import portfolio.composeapp.generated.resources.ibm_plex_mono_600
import portfolio.composeapp.generated.resources.manrope_400
import portfolio.composeapp.generated.resources.manrope_600
import portfolio.composeapp.generated.resources.manrope_700
import portfolio.composeapp.generated.resources.manrope_800

/** Manrope carries every display/heading line of the sheet. */
@Composable
fun manropeFamily(): FontFamily = FontFamily(
    Font(Res.font.manrope_400, FontWeight.Normal),
    Font(Res.font.manrope_600, FontWeight.SemiBold),
    Font(Res.font.manrope_700, FontWeight.Bold),
    Font(Res.font.manrope_800, FontWeight.ExtraBold)
)

/** IBM Plex Mono carries every label, index number and link caption. */
@Composable
fun plexMonoFamily(): FontFamily = FontFamily(
    Font(Res.font.ibm_plex_mono_400, FontWeight.Normal),
    Font(Res.font.ibm_plex_mono_500, FontWeight.Medium),
    Font(Res.font.ibm_plex_mono_600, FontWeight.SemiBold)
)

val LocalDisplayFamily = compositionLocalOf<FontFamily> { FontFamily.SansSerif }
val LocalMonoFamily = compositionLocalOf<FontFamily> { FontFamily.Monospace }

/** Oversized name lockup: Manrope 800, tight leading, tight tracking. */
@Composable
fun displayStyle(): TextStyle = TextStyle(
    fontFamily = LocalDisplayFamily.current,
    fontWeight = FontWeight.ExtraBold,
    fontSize = LocalMetrics.current.display,
    lineHeight = 0.95.em,
    letterSpacing = (-0.05).em
)

/** Numbered row headings in "What I do". */
@Composable
fun rowTitleStyle(): TextStyle = TextStyle(
    fontFamily = LocalDisplayFamily.current,
    fontWeight = FontWeight.Bold,
    fontSize = LocalMetrics.current.rowTitle,
    lineHeight = 1.1.em,
    letterSpacing = (-0.035).em
)

@Composable
fun workTitleStyle(): TextStyle = TextStyle(
    fontFamily = LocalDisplayFamily.current,
    fontWeight = FontWeight.Bold,
    fontSize = LocalMetrics.current.workTitle,
    lineHeight = 1.1.em,
    letterSpacing = (-0.03).em
)

/** The one prose sentence on the sheet, in the contact block. */
@Composable
fun contactStyle(): TextStyle = TextStyle(
    fontFamily = LocalDisplayFamily.current,
    fontWeight = FontWeight.SemiBold,
    fontSize = LocalMetrics.current.contact,
    lineHeight = 1.35.em,
    letterSpacing = (-0.02).em
)

/** Prose inside an expanded "What I do" row. */
@Composable
fun bodyStyle(): TextStyle = TextStyle(
    fontFamily = LocalDisplayFamily.current,
    fontWeight = FontWeight.Normal,
    fontSize = LocalMetrics.current.body,
    lineHeight = 1.6.em,
    letterSpacing = (-0.005).em
)

/** Wide-tracked mono caption. Every small line on the sheet is one of these. */
@Composable
fun monoStyle(
    size: TextUnit,
    tracking: Float,
    weight: FontWeight = FontWeight.Normal,
    lineHeight: TextUnit = 1.9.em
): TextStyle = TextStyle(
    fontFamily = LocalMonoFamily.current,
    fontWeight = weight,
    fontSize = size,
    lineHeight = lineHeight,
    letterSpacing = tracking.em
)
