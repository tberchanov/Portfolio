package com.portfolio

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random
import org.jetbrains.compose.resources.painterResource
import portfolio.composeapp.generated.resources.Res
import portfolio.composeapp.generated.resources.linkedin

private const val SECTION_TITLE_FONT_SIZE_SP = 36
private const val CARD_DESC_FONT_SIZE_SP = 18
private const val ICON_SIZE_DP = 20

@Composable
fun ContactMe(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.02f))
    ) {
        GeometricShapesBackground(
            modifier = Modifier.matchParentSize(),
            baseColor = MaterialTheme.colorScheme.primary,
            accentColor = MaterialTheme.colorScheme.tertiary
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = getResponsiveHorizontalPadding(),
                    vertical = 124.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Contact Me",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize = SECTION_TITLE_FONT_SIZE_SP.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "I'd be happy to discuss projects, creative ideas or opportunities to collaborate.",
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = CARD_DESC_FONT_SIZE_SP.sp),
                color = MaterialTheme.colorScheme.onBackground,
                lineHeight = 26.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))

            val uriHandler = LocalUriHandler.current
            Button(
                onClick = {
                    uriHandler.openUri("https://www.linkedin.com/in/anatolii-berchanov/")
                },
                shape = RoundedCornerShape(8.dp),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        painter = painterResource(Res.drawable.linkedin),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.background),
                        modifier = Modifier.size(ICON_SIZE_DP.dp)
                    )
                    Text(
                        text = "LinkedIn",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        ),
                        color = MaterialTheme.colorScheme.background,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun GeometricShapesBackground(
    modifier: Modifier = Modifier,
    baseColor: Color,
    accentColor: Color
) {
    val shapes = remember(baseColor, accentColor) {
        generateGeometricShapes(
            count = 12,
            palette = listOf(
                baseColor.copy(alpha = 0.08f),
                baseColor.copy(alpha = 0.05f),
                accentColor.copy(alpha = 0.06f)
            )
        )
    }

    Canvas(modifier = modifier) {
        shapes.forEach { shape ->
            val center = Offset(
                x = shape.position.x * size.width,
                y = shape.position.y * size.height
            )
            val shapeSizePx = shape.sizeDp.dp.toPx()
            when (shape.type) {
                GeometricShapeType.Circle -> {
                    drawCircle(
                        color = shape.color,
                        radius = shapeSizePx / 2f,
                        center = center
                    )
                }

                GeometricShapeType.Square -> {
                    val halfSize = shapeSizePx / 2f
                    rotate(degrees = shape.rotation, pivot = center) {
                        drawRoundRect(
                            color = shape.color,
                            topLeft = Offset(center.x - halfSize, center.y - halfSize),
                            size = Size(shapeSizePx, shapeSizePx),
                            cornerRadius = CornerRadius(shapeSizePx * 0.2f)
                        )
                    }
                }

                GeometricShapeType.Triangle -> {
                    val halfSize = shapeSizePx / 2f
                    val path = Path().apply {
                        moveTo(center.x, center.y - halfSize)
                        lineTo(center.x + halfSize, center.y + halfSize)
                        lineTo(center.x - halfSize, center.y + halfSize)
                        close()
                    }
                    rotate(degrees = shape.rotation, pivot = center) {
                        drawPath(path = path, color = shape.color)
                    }
                }
            }
        }
    }
}

private fun generateGeometricShapes(
    count: Int,
    palette: List<Color>
): List<GeometricShape> {
    val random = kotlin.random.Random(42)
    return List(count) {
        GeometricShape(
            type = GeometricShapeType.entries[random.nextInt(GeometricShapeType.entries.size)],
            position = Offset(random.nextPositionCoordinate(), random.nextPositionCoordinate()),
            sizeDp = 24f + random.nextFloat() * 32f,
            rotation = random.nextFloat() * 360f,
            color = palette[random.nextInt(palette.size)]
        )
    }
}

/**
 * Samples a coordinate that skips the outer 3% margins and the central 20% band.
 */
private fun Random.nextPositionCoordinate(): Float {
    val edgeMargin = 0.03f
    val centerMarginFraction = 0.20f
    val centerHalfWidth = centerMarginFraction / 2f
    val leftBound = edgeMargin
    val leftEnd = 0.5f - centerHalfWidth
    val rightStart = 0.5f + centerHalfWidth
    val rightEnd = 1f - edgeMargin
    val leftSpan = leftEnd - leftBound
    val rightSpan = rightEnd - rightStart
    val totalSpan = leftSpan + rightSpan
    val sample = nextFloat() * totalSpan
    return if (sample < leftSpan) {
        leftBound + sample
    } else {
        rightStart + (sample - leftSpan)
    }
}

private data class GeometricShape(
    val type: GeometricShapeType,
    val position: Offset,
    val sizeDp: Float,
    val rotation: Float,
    val color: Color
)

private enum class GeometricShapeType {
    Circle,
    Square,
    Triangle
}
