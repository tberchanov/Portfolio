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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun Welcome(
    modifier: Modifier = Modifier,
    onScrollToAboutMe: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        val colorScheme = MaterialTheme.colorScheme
        
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
                    fontSize = 85.sp,
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

            // Title text
            Text(
                text = "Mobile Applications Architect",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 30.sp,
                ),
                color = colorScheme.onBackground,
                textAlign = TextAlign.Center,
            )
        }
        
        val infiniteTransition = rememberInfiniteTransition(label = "arrowAnimation")
        val bounceOffset by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = -9.2f, // 8 * 1.15 = 9.2 (15% increase)
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 1260, delayMillis = 630), // 10% faster
                repeatMode = RepeatMode.Reverse
            ),
            label = "bounceOffset"
        )
        
        Box(
            modifier = Modifier
                .alpha(0.8f)
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp) // Moved lower (reduced from 48dp to 24dp)
                .size(60.dp)
                .offset(y = bounceOffset.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { onScrollToAboutMe() },
            contentAlignment = Alignment.Center
        ) {
            Canvas(
                modifier = Modifier.size(24.dp)
            ) {
                drawDownArrow(
                    color = colorScheme.primary,
                    strokeWidth = 3.dp.toPx()
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
        val arrowSize = size.width * 0.3f
        
        // Draw downward arrow (V shape)
        moveTo(centerX - arrowSize, centerY - arrowSize * 0.5f)
        lineTo(centerX, centerY + arrowSize * 0.5f)
        lineTo(centerX + arrowSize, centerY - arrowSize * 0.5f)
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
