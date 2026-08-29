package com.portfolio

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import portfolio.composeapp.generated.resources.Res
import portfolio.composeapp.generated.resources.github
import portfolio.composeapp.generated.resources.linkedin
import portfolio.composeapp.generated.resources.portrait

private const val GLYPH_DURATION_MS = 200
private const val OPEN_GLYPH_ANGLE = 45f

/** The name lockup and the two fact lines under it. */
@Composable
fun Hero(modifier: Modifier = Modifier) {
    val metrics = LocalMetrics.current
    Column(modifier.fillMaxWidth().padding(top = metrics.heroTopPadding)) {
        Text(
            text = "Anatolii\nBerchanov",
            style = displayStyle(),
            color = BoneWhite
        )
        Spacer(Modifier.height(44.dp))
        Text(
            text = "MOBILE APPLICATIONS ARCHITECT · TEAM LEAD",
            style = monoStyle(metrics.caption, tracking = 0.14f, lineHeight = 1.5.em),
            color = BoneWhite
        )
        Spacer(Modifier.height(12.dp))
        Text(
            text = "SINCE 2017",
            style = monoStyle(metrics.caption, tracking = 0.14f, lineHeight = 1.5.em),
            color = AshGrey
        )
    }
}

private data class Discipline(val title: String, val body: String)

private val Disciplines = listOf(
    Discipline(
        "Mobile architecture",
        "Designing and implementing scalable mobile solutions. " +
                "Participated in presales activities and project discovery phases " +
                "to align technical solutions with business goals."
    ),
    Discipline(
        "Cross-platform delivery",
        "Building cross-platform solutions that deliver native-like experiences " +
                "across iOS and Android platforms using Kotlin Multiplatform, Flutter."
    ),
    Discipline(
        "Team leadership",
        "Leading development teams, establishing best practices, planning and organizing" +
                " work and mentoring developers to ensure consistent, predictable delivery."
    ),
    Discipline(
        "R&D",
        "Research and development of advanced mobile functionality, including video " +
                "editing, cryptography and SDK development. Exploring cross-domain innovations " +
                "across AOSP, robotics, computer vision and machine learning."
    )
)

/**
 * Four facts, no descriptions until asked for: each row opens to its own
 * paragraph. Rows toggle independently, so several can stand open at once.
 */
@Composable
fun WhatIDo(modifier: Modifier = Modifier) {
    val metrics = LocalMetrics.current
    var openRows by remember { mutableStateOf(emptySet<Int>()) }
    Column(modifier.fillMaxWidth()) {
        SectionLabel("WHAT I DO")
        Spacer(Modifier.height(metrics.labelGap))
        Disciplines.forEachIndexed { index, discipline ->
            val open = index in openRows
            IndexedRow(
                index = index + 1,
                title = discipline.title,
                titleStyle = rowTitleStyle(),
                modifier = Modifier.sheetLink {
                    openRows = if (open) openRows - index else openRows + index
                },
                trailing = { ExpandGlyph(open) },
                below = {
                    AnimatedVisibility(
                        visible = open,
                        enter = expandVertically(expandFrom = Alignment.Top) + fadeIn(),
                        exit = shrinkVertically(shrinkTowards = Alignment.Top) + fadeOut()
                    ) {
                        Column {
                            Spacer(Modifier.height(20.dp))
                            Text(
                                text = discipline.body,
                                style = bodyStyle(),
                                color = SmokeGrey,
                                modifier = Modifier.widthIn(max = 620.dp)
                            )
                        }
                    }
                }
            )
        }
        Rule()
        Spacer(Modifier.height(32.dp))
        Text(
            text = "ANDROID · KOTLIN · KOTLIN MULTIPLATFORM · COMPOSE · FLUTTER · REACT NATIVE",
            style = monoStyle(metrics.label, tracking = 0.1f),
            color = AshGrey
        )
    }
}

/**
 * The open/closed affordance: a mono "+" that turns a quarter-turn into an "×".
 * Rotation is a draw transform, so the glyph keeps the row's shared baseline.
 */
@Composable
private fun ExpandGlyph(open: Boolean) {
    val angle by animateFloatAsState(
        targetValue = if (open) OPEN_GLYPH_ANGLE else 0f,
        animationSpec = tween(GLYPH_DURATION_MS),
        label = "expandGlyphAngle"
    )
    val color by animateColorAsState(
        targetValue = if (open) AcidLime else AshGrey,
        animationSpec = tween(GLYPH_DURATION_MS),
        label = "expandGlyphColor"
    )
    Caption(
        text = "+",
        color = color,
        modifier = Modifier.rotate(angle),
        weight = FontWeight.SemiBold
    )
}

private data class Work(
    val title: String,
    val kind: String,
    val linkLabel: String,
    val url: String
)

private val SelectedWorks = listOf(
    Work("SlowLock", "MOBILE APP", "GITHUB", "https://github.com/tberchanov/SlowLock"),
    Work(
        "IR image codec PHD research",
        "RESEARCH",
        "GITHUB",
        "https://github.com/tberchanov/IR-codec-PHD-research"
    ),
    Work("StrictPro", "ANDROID LIBRARY", "GITHUB", "https://github.com/tberchanov/StrictPro"),
    Work("Donatta", "MOBILE APP", "WEBSITE", "https://donatta.app/"),
    Work("RelaxFin", "KOTLIN MULTIPLATFORM", "GITHUB", "https://github.com/tberchanov/RelaxFin"),
    Work(
        "Portfolio Website",
        "KOTLIN MULTIPLATFORM",
        "GITHUB",
        "https://github.com/tberchanov/Portfolio"
    )
)

/** The project list: each row is one line of what it is and where it goes. */
@Composable
fun SelectedWork(modifier: Modifier = Modifier) {
    val metrics = LocalMetrics.current
    val uriHandler = LocalUriHandler.current
    Column(modifier.fillMaxWidth()) {
        SectionLabel("PERSONAL PROJECTS")
        Spacer(Modifier.height(metrics.labelGap))
        SelectedWorks.forEachIndexed { index, work ->
            IndexedRow(
                index = index + 1,
                title = work.title,
                titleStyle = workTitleStyle(),
                modifier = Modifier.sheetLink { uriHandler.openUri(work.url) },
                meta = work.kind,
                trailing = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Caption(work.linkLabel, AcidLime)
                        Spacer(Modifier.width(6.dp))
                        ArrowOutward(AcidLime)
                    }
                }
            )
        }
        Rule()
    }
}

/** Portrait, one sentence, two ways to reach me. */
@Composable
fun ContactBlock(modifier: Modifier = Modifier) {
    if (LocalScreenSize.current.isMobile) {
        Column(modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(28.dp)) {
            ContactPortrait()
            ContactDetails()
        }
    } else {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(40.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ContactPortrait()
            ContactDetails()
        }
    }
}

@Composable
private fun ContactPortrait() {
    Image(
        painter = painterResource(Res.drawable.portrait),
        contentDescription = "Anatolii Berchanov",
        contentScale = ContentScale.Crop,
        colorFilter = ColorFilter.colorMatrix(GrayscaleMatrix),
        modifier = Modifier.size(LocalMetrics.current.avatar).clip(CircleShape)
    )
}

@Composable
private fun ContactDetails() {
    Column {
        Text(
            text = "I'd be happy to discuss projects, creative ideas or " +
                "opportunities to collaborate.",
            style = contactStyle(),
            color = BoneWhite,
            modifier = Modifier.widthIn(max = 460.dp)
        )
        Spacer(Modifier.height(28.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(32.dp)) {
            ContactLink(
                icon = Res.drawable.linkedin,
                label = "LINKEDIN",
                color = AcidLime,
                underline = AcidLime,
                url = "https://www.linkedin.com/in/anatolii-berchanov/"
            )
            ContactLink(
                icon = Res.drawable.github,
                label = "GITHUB",
                color = SmokeGrey,
                underline = HairlineStrong,
                url = "https://github.com/tberchanov"
            )
        }
    }
}

@Composable
private fun ContactLink(
    icon: DrawableResource,
    label: String,
    color: Color,
    underline: Color,
    url: String
) {
    val uriHandler = LocalUriHandler.current
    Column(
        modifier = Modifier
            .width(IntrinsicSize.Max)
            .sheetLink { uriHandler.openUri(url) }
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(13.dp)
            )
            Spacer(Modifier.width(9.dp))
            Caption(label, color)
            Spacer(Modifier.width(6.dp))
            ArrowOutward(color)
        }
        Spacer(Modifier.height(4.dp))
        Rule(color = underline)
    }
}

/** Desaturate, nudge contrast up and brightness down — the canvas portrait filter. */
private val GrayscaleMatrix = ColorMatrix().apply {
    setToSaturation(0f)
    timesAssign(
        ColorMatrix(
            floatArrayOf(
                1.045f, 0f, 0f, 0f, -12.11f,
                0f, 1.045f, 0f, 0f, -12.11f,
                0f, 0f, 1.045f, 0f, -12.11f,
                0f, 0f, 0f, 1f, 0f
            )
        )
    )
}
