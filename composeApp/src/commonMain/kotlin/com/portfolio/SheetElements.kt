package com.portfolio

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

private const val HOVER_ALPHA = 0.7f
private const val HOVER_DURATION_MS = 160

/** A hairline rule; the sheet separates every row with one. */
@Composable
fun Rule(modifier: Modifier = Modifier, color: Color = Hairline) {
    Box(modifier.fillMaxWidth().height(1.dp).background(color))
}

/** Lime section label, e.g. "WHAT I DO". */
@Composable
fun SectionLabel(text: String) {
    Text(
        text = text,
        style = monoStyle(LocalMetrics.current.micro, tracking = 0.18f),
        color = AcidLime
    )
}

/** Wide-tracked mono caption, the sheet's default small text. */
@Composable
fun Caption(
    text: String,
    color: Color,
    modifier: Modifier = Modifier,
    tracking: Float = 0.12f,
    weight: FontWeight = FontWeight.Normal
) {
    Text(
        text = text,
        style = monoStyle(LocalMetrics.current.label, tracking = tracking, weight = weight),
        color = color,
        modifier = modifier
    )
}

/** The "01", "02" index in the numbered lists. */
@Composable
fun IndexNumber(index: Int, modifier: Modifier = Modifier) {
    val metrics = LocalMetrics.current
    Text(
        text = index.toString().padStart(2, '0'),
        style = monoStyle(metrics.micro, tracking = 0.12f),
        color = AshGrey,
        modifier = modifier.width(metrics.indexColumn)
    )
}

/** The ↗ that terminates every outbound link, drawn so no font has to carry it. */
@Composable
fun ArrowOutward(tint: Color, modifier: Modifier = Modifier) {
    Canvas(modifier.size(11.dp)) {
        val inset = size.minDimension * 0.12f
        val start = Offset(inset, size.height - inset)
        val end = Offset(size.width - inset, inset)
        val head = size.minDimension * 0.42f
        val stroke = Stroke(width = size.minDimension * 0.11f, cap = StrokeCap.Square)
        drawLine(tint, start, end, strokeWidth = stroke.width, cap = stroke.cap)
        drawLine(tint, Offset(end.x - head, end.y), end, strokeWidth = stroke.width, cap = stroke.cap)
        drawLine(tint, Offset(end.x, end.y + head), end, strokeWidth = stroke.width, cap = stroke.cap)
    }
}

/**
 * Click + hover behaviour shared by every link on the sheet:
 * fades to 70% on hover, exactly like the canvas `a:hover`.
 */
@Composable
fun Modifier.sheetLink(onClick: () -> Unit): Modifier {
    val interaction = remember { MutableInteractionSource() }
    val hovered by interaction.collectIsHoveredAsState()
    val linkAlpha by animateFloatAsState(
        targetValue = if (hovered) HOVER_ALPHA else 1f,
        animationSpec = tween(HOVER_DURATION_MS),
        label = "linkAlpha"
    )
    return this
        .clickable(interactionSource = interaction, indication = null, onClick = onClick)
        .pointerHoverIcon(PointerIcon.Hand)
        .alpha(linkAlpha)
}

/**
 * One row of a numbered list.
 *
 * The index, the title and the trailing caption share a baseline, so the top
 * line of every row reads as one line of type. Only single-line text lives in
 * that Row — `meta` and `below` sit beneath it, indented to the title column —
 * because anything that changes height inside a baseline-aligned Row drags the
 * row's alignment line with it.
 */
@Composable
fun IndexedRow(
    index: Int,
    title: String,
    titleStyle: TextStyle,
    modifier: Modifier = Modifier,
    meta: String? = null,
    trailing: @Composable (() -> Unit)? = null,
    below: @Composable (() -> Unit)? = null
) {
    val metrics = LocalMetrics.current
    val mobile = LocalScreenSize.current.isMobile
    val indent = metrics.indexColumn + metrics.columnGap
    Column(modifier.fillMaxWidth()) {
        Rule()
        Column(Modifier.fillMaxWidth().padding(vertical = metrics.rowPadding)) {
            Row(Modifier.fillMaxWidth()) {
                IndexNumber(index, Modifier.alignByBaseline())
                Spacer(Modifier.width(metrics.columnGap))
                Text(
                    text = title,
                    style = titleStyle,
                    color = BoneWhite,
                    modifier = Modifier.weight(1f).alignByBaseline()
                )
                if (!mobile && trailing != null) {
                    Spacer(Modifier.width(metrics.columnGap))
                    Box(Modifier.alignByBaseline()) { trailing() }
                }
            }
            if (meta != null) {
                Spacer(Modifier.height(12.dp))
                Caption(meta, AshGrey, Modifier.padding(start = indent), tracking = 0.1f)
            }
            if (mobile && trailing != null) {
                Spacer(Modifier.height(12.dp))
                Box(Modifier.padding(start = indent)) { trailing() }
            }
            if (below != null) {
                Box(Modifier.padding(start = indent)) { below() }
            }
        }
    }
}
