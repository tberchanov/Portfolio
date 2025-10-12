package com.portfolio

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random
import org.jetbrains.compose.resources.painterResource
import portfolio.composeapp.generated.resources.Res
import portfolio.composeapp.generated.resources.github
import portfolio.composeapp.generated.resources.linkedin

private const val NAME_FONT_SIZE_SP = 85
private const val TITLE_FONT_SIZE_SP = 30
private const val ICON_SIZE_DP = 24
private const val ICON_ALPHA = 1f
private const val ICON_SPACING_DP = 24
private const val ARROW_SIZE_DP = 24
private const val ARROW_STROKE_WIDTH_DP = 3
private const val BOUNCE_OFFSET_DP = -9.2f
private const val ANIMATION_DURATION_MS = 1260
private const val ANIMATION_DELAY_MS = 630
private const val ARROW_CONTAINER_SIZE_DP = 60
private const val ARROW_CONTAINER_ALPHA = 0.8f
private const val ARROW_BOTTOM_PADDING_DP = 24
private const val ARROW_SIZE_RATIO = 0.3f
private const val ARROW_HEIGHT_RATIO = 0.5f
private const val BACKGROUND_SHAPE_COUNT = 5
private const val BACKGROUND_ALPHA = 0.25f

@Composable
fun Welcome(
    modifier: Modifier = Modifier,
    onScrollToAboutMe: () -> Unit = {}
) {
    val uriHandler = LocalUriHandler.current
    Box(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        val colorScheme = MaterialTheme.colorScheme

        AnimatedBackground(
            modifier = Modifier.matchParentSize()
        )
        
        // Main content centered
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .align(Alignment.Center)
                .padding(vertical = 64.dp)
        ) {
            Text(
                text = "Anatolii Berchanov",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize = NAME_FONT_SIZE_SP.sp,
                    fontWeight = FontWeight.Bold,
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            colorScheme.primary,
                            colorScheme.secondary
                        )
                    )
                ),
                textAlign = TextAlign.Center,
            )

            Text(
                text = "Mobile Applications Architect",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = TITLE_FONT_SIZE_SP.sp,
                ),
                color = colorScheme.onBackground,
                textAlign = TextAlign.Center,
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(ICON_SPACING_DP.dp),
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Icon(
                    painter = painterResource(Res.drawable.linkedin),
                    contentDescription = "LinkedIn",
                    modifier = Modifier
                        .size(ICON_SIZE_DP.dp)
                        .alpha(ICON_ALPHA)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            uriHandler.openUri("https://www.linkedin.com/in/anatolii-berchanov/")
                        },
                    tint = colorScheme.primary
                )
                Icon(
                    painter = painterResource(Res.drawable.github),
                    contentDescription = "GitHub",
                    modifier = Modifier
                        .size(ICON_SIZE_DP.dp)
                        .alpha(ICON_ALPHA)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            uriHandler.openUri("https://github.com/tberchanov")
                        },
                    tint = colorScheme.primary
                )
            }
        }
        
        val infiniteTransition = rememberInfiniteTransition(label = "arrowAnimation")
        val bounceOffset by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = BOUNCE_OFFSET_DP,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = ANIMATION_DURATION_MS, delayMillis = ANIMATION_DELAY_MS),
                repeatMode = RepeatMode.Reverse
            ),
            label = "bounceOffset"
        )
        
        Box(
            modifier = Modifier
                .alpha(ARROW_CONTAINER_ALPHA)
                .align(Alignment.BottomCenter)
                .padding(bottom = ARROW_BOTTOM_PADDING_DP.dp)
                .size(ARROW_CONTAINER_SIZE_DP.dp)
                .offset(y = bounceOffset.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { onScrollToAboutMe() },
            contentAlignment = Alignment.Center
        ) {
            Canvas(
                modifier = Modifier.size(ARROW_SIZE_DP.dp)
            ) {
                drawDownArrow(
                    color = colorScheme.primary,
                    strokeWidth = ARROW_STROKE_WIDTH_DP.dp.toPx()
                )
            }
        }
    }
}

private data class FloatingShape(
    val base: Offset,
    val amplitude: Offset,
    val radiusFraction: Float,
    val alpha: Float,
    val color: Color,
    val durationMillis: Int,
    val phaseOffset: Float
)

@Composable
private fun AnimatedBackground(modifier: Modifier = Modifier) {
    val colorScheme = MaterialTheme.colorScheme
    val shapes = remember(colorScheme.primary, colorScheme.secondary) {
        val random = Random(0xA5CE)
        List(BACKGROUND_SHAPE_COUNT) { index ->
            FloatingShape(
                base = Offset(random.nextFloat(), random.nextFloat()),
                amplitude = Offset(
                    0.06f + random.nextFloat() * 0.12f,
                    0.08f + random.nextFloat() * 0.18f
                ),
                radiusFraction = 0.12f + random.nextFloat() * 0.2f,
                alpha = 0.10f + random.nextFloat() * 0.18f,
                color = when (index % 3) {
                    0 -> colorScheme.primary
                    1 -> colorScheme.secondary
                    else -> colorScheme.primary.copy(alpha = 0.65f)
                },
                durationMillis = 11000 + random.nextInt(7000),
                phaseOffset = random.nextFloat()
            )
        }
    }
    val transition = rememberInfiniteTransition(label = "welcomeBackground")
    val motions = shapes.mapIndexed { index, shape ->
        transition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = shape.durationMillis,
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Reverse
            ),
            label = "shape$index"
        )
    }

    Canvas(
        modifier = modifier
            .graphicsLayer(alpha = BACKGROUND_ALPHA)
    ) {
        shapes.forEachIndexed { index, shape ->
            val motion = motions[index].value
            val angle = (motion + shape.phaseOffset) * 2f * PI
            val x = (shape.base.x + shape.amplitude.x * sin(angle)).toFloat()
            val y = (shape.base.y + shape.amplitude.y * cos(angle)).toFloat()
            val center = Offset(
                x.coerceIn(0f, 1f) * size.width,
                y.coerceIn(0f, 1f) * size.height
            )

            drawCircle(
                color = shape.color,
                radius = size.minDimension * shape.radiusFraction,
                center = center,
                alpha = shape.alpha
            )
        }

        val waveProgress = motions.firstOrNull()?.value ?: 0f
        val wavePhase = waveProgress * 2f * PI
        val waveHeight = size.height * 0.18f
        val segments = 12
        val path = Path().apply {
            moveTo(0f, size.height)
            for (i in 0..segments) {
                val fraction = i / segments.toFloat()
                val x = fraction * size.width
                val y =
                    (size.height * 0.62f +
                        sin(wavePhase + fraction * 2f * PI) * waveHeight * 0.45f).toFloat()
                lineTo(x, y)
            }
            lineTo(size.width, size.height)
            close()
        }

        drawPath(
            path = path,
            brush = Brush.verticalGradient(
                colors = listOf(
                    colorScheme.primary.copy(alpha = 0.16f),
                    Color.Transparent
                ),
                startY = size.height * 0.4f,
                endY = size.height
            )
        )
    }
}

private fun DrawScope.drawDownArrow(
    color: Color,
    strokeWidth: Float
) {
    val path = Path().apply {
        val centerX = size.width / 2f
        val centerY = size.height / 2f
        val arrowSize = size.width * ARROW_SIZE_RATIO
        
        moveTo(centerX - arrowSize, centerY - arrowSize * ARROW_HEIGHT_RATIO)
        lineTo(centerX, centerY + arrowSize * ARROW_HEIGHT_RATIO)
        lineTo(centerX + arrowSize, centerY - arrowSize * ARROW_HEIGHT_RATIO)
    }
    
    drawPath(
        path = path,
        color = color,
        style = Stroke(
            width = strokeWidth,
            cap = androidx.compose.ui.graphics.StrokeCap.Round,
            join = androidx.compose.ui.graphics.StrokeJoin.Round
        )
    )
}
