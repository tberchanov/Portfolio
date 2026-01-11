package com.portfolio

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
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.PI
import kotlin.math.sin
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

        AnimatedTechBackground(
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.7f)
        )

        val isMobileScreen = isMobile()
        val fontScale = getResponsiveFontScale()
        val horizontalPadding = getResponsiveHorizontalPadding()
        val verticalPadding = getResponsiveVerticalPadding()
        
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .align(Alignment.Center)
                .padding(
                    horizontal = horizontalPadding,
                    vertical = verticalPadding
                )
        ) {
            Text(
                text = "Anatolii Berchanov",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize = (NAME_FONT_SIZE_SP * fontScale).sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = if (isMobileScreen) {
                        (NAME_FONT_SIZE_SP * fontScale * 1.2).sp
                    } else {
                        (NAME_FONT_SIZE_SP * fontScale * 1.0).sp
                    },
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            colorScheme.primary,
                            colorScheme.secondary
                        )
                    )
                ),
                textAlign = TextAlign.Center,
                maxLines = 2,
            )

            Text(
                text = "Mobile Applications Architect",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = (TITLE_FONT_SIZE_SP * fontScale).sp,
                ),
                color = colorScheme.onBackground,
                textAlign = TextAlign.Center,
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(
                    if (isMobileScreen) 20.dp else ICON_SPACING_DP.dp
                ),
                modifier = Modifier.padding(
                    top = if (isMobileScreen) 16.dp else 8.dp,
                    bottom = if (isMobileScreen) 8.dp else 0.dp
                )
            ) {
                val iconSize = if (isMobileScreen) 28.dp else getResponsiveIconSize()
                val touchTargetSize = if (isMobileScreen) 48.dp else iconSize

                Box(
                    modifier = Modifier
                        .size(touchTargetSize)
                        .alpha(ICON_ALPHA)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            uriHandler.openUri("https://www.linkedin.com/in/anatolii-berchanov/")
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.linkedin),
                        contentDescription = "LinkedIn",
                        modifier = Modifier.size(iconSize),
                        tint = colorScheme.primary
                    )
                }
                
                Box(
                    modifier = Modifier
                        .size(touchTargetSize)
                        .alpha(ICON_ALPHA)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            uriHandler.openUri("https://github.com/tberchanov")
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.github),
                        contentDescription = "GitHub",
                        modifier = Modifier.size(iconSize),
                        tint = colorScheme.primary
                    )
                }
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
        
        val arrowBottomPadding = if (isMobileScreen) 24.dp else ARROW_BOTTOM_PADDING_DP.dp
        val arrowContainerSize = maxOf(
            (ARROW_CONTAINER_SIZE_DP * fontScale).dp,
            48.dp
        )
        val arrowSize = maxOf(
            (ARROW_SIZE_DP * fontScale).dp,
            20.dp
        )
        val arrowStrokeWidth = (ARROW_STROKE_WIDTH_DP * fontScale).dp
        
        Box(
            modifier = Modifier
                .alpha(ARROW_CONTAINER_ALPHA)
                .align(Alignment.BottomCenter)
                .padding(bottom = arrowBottomPadding)
                .size(arrowContainerSize)
                .offset(y = bounceOffset.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { onScrollToAboutMe() },
            contentAlignment = Alignment.Center
        ) {
            Canvas(
                modifier = Modifier.size(arrowSize)
            ) {
                drawDownArrow(
                    color = colorScheme.primary,
                    strokeWidth = arrowStrokeWidth.toPx()
                )
            }
        }
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
