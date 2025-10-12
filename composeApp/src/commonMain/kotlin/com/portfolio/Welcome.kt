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

@Composable
private fun AnimatedBackground(modifier: Modifier = Modifier) {
    val colorScheme = MaterialTheme.colorScheme

    data class Node(val position: Offset, val radius: Float, val baseAlpha: Float)
    data class Connection(val from: Int, val to: Int, val phaseOffset: Float)
    data class Network(val nodes: List<Node>, val connections: List<Connection>)

    fun Random.nextFloatIn(range: ClosedFloatingPointRange<Float>): Float =
        range.start + this.nextFloat() * (range.endInclusive - range.start)

    val network = remember(colorScheme.primary, colorScheme.secondary) {
        val seed = Random.nextLong()
        val random = Random(seed)

        val nodeCount = random.nextInt(10, 20)
        val minDistanceSquared = 0.12f * 0.12f
        val maxAttemptsPerNode = 16

        val nodes = mutableListOf<Node>()
        repeat(nodeCount) {
            var attempts = 0
            var position: Offset
            var isFarEnough: Boolean
            do {
                position = Offset(
                    random.nextFloatIn(0.08f..0.92f),
                    random.nextFloatIn(0.18f..0.72f)
                )
                isFarEnough = nodes.none { existing ->
                    val dx = position.x - existing.position.x
                    val dy = position.y - existing.position.y
                    dx * dx + dy * dy < minDistanceSquared
                }
                attempts++
            } while (!isFarEnough && attempts < maxAttemptsPerNode)

            val radius = random.nextFloatIn(0.012f..0.02f)
            val alpha = random.nextFloatIn(0.42f..0.65f)
            nodes += Node(position, radius, alpha)
        }

        val connectionPairs = mutableSetOf<Pair<Int, Int>>()
        if (nodes.size >= 2) {
            nodes.forEachIndexed { index, node ->
                val nearest = nodes.indices
                    .filter { it != index }
                    .sortedBy { other ->
                        val otherNode = nodes[other]
                        val dx = node.position.x - otherNode.position.x
                        val dy = node.position.y - otherNode.position.y
                        dx * dx + dy * dy
                    }
                    .take(random.nextInt(2, minOf(4, nodes.size)))

                nearest.forEach { neighbor ->
                    val pair = if (index < neighbor) index to neighbor else neighbor to index
                    connectionPairs += pair
                }
            }

            repeat(random.nextInt(1, 4)) {
                val a = random.nextInt(nodes.size)
                val b = random.nextInt(nodes.size)
                if (a != b) {
                    val pair = if (a < b) a to b else b to a
                    connectionPairs += pair
                }
            }
        }

        val connections = connectionPairs.map { (from, to) ->
            Connection(from, to, random.nextFloat())
        }

        Network(nodes.toList(), connections)
    }

    val nodes = network.nodes
    val connections = network.connections

    val transition = rememberInfiniteTransition(label = "welcomeBackground")

    val gridShift by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 24000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "gridShift"
    )

    val pulseProgress = connections.mapIndexed { index, connection ->
        transition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 5200 + index * 430,
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Restart
            ),
            label = "connectionPulse$index"
        )
    }

    val nodePulse = nodes.mapIndexed { index, _ ->
        transition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 3600 + index * 320,
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Reverse
            ),
            label = "nodePulse$index"
        )
    }

    Canvas(modifier = modifier.graphicsLayer(alpha = BACKGROUND_ALPHA)) {
        val backgroundGradient = Brush.linearGradient(
            colors = listOf(
                colorScheme.primary.copy(alpha = 0.12f),
                colorScheme.secondary.copy(alpha = 0.08f),
                Color.Transparent
            ),
            start = Offset.Zero,
            end = Offset(size.width, size.height)
        )
        drawRect(brush = backgroundGradient)

        val verticalSpacing = size.width / 10f
        val horizontalSpacing = size.height / 7f
        val shiftX = (gridShift % 1f) * verticalSpacing
        val shiftY = (gridShift % 1f) * horizontalSpacing
        val gridColor = colorScheme.primary.copy(alpha = 0.08f)

        for (i in -1..11) {
            val x = i * verticalSpacing + shiftX
            drawLine(
                color = gridColor,
                start = Offset(x, 0f),
                end = Offset(x, size.height),
                strokeWidth = 1f
            )
        }
        for (j in -1..9) {
            val y = j * horizontalSpacing + shiftY
            drawLine(
                color = gridColor,
                start = Offset(0f, y),
                end = Offset(size.width, y),
                strokeWidth = 1f
            )
        }

        val connectorColor = colorScheme.primary.copy(alpha = 0.35f)
        val connectorHighlight = colorScheme.secondary.copy(alpha = 0.6f)

        connections.forEachIndexed { index, connection ->
            val startNode = nodes[connection.from]
            val endNode = nodes[connection.to]
            val start = Offset(
                startNode.position.x * size.width,
                startNode.position.y * size.height
            )
            val end = Offset(
                endNode.position.x * size.width,
                endNode.position.y * size.height
            )

            drawLine(
                color = connectorColor,
                start = start,
                end = end,
                strokeWidth = size.minDimension * 0.003f
            )

            val progress = pulseProgress[index].value
            val pulsePosition = (progress + connection.phaseOffset) % 1f
            val pulsePoint = Offset(
                start.x + (end.x - start.x) * pulsePosition,
                start.y + (end.y - start.y) * pulsePosition
            )
            drawCircle(
                color = connectorHighlight,
                radius = size.minDimension * 0.01f,
                center = pulsePoint,
                alpha = 0.7f
            )
        }

        nodes.forEachIndexed { index, node ->
            val pulse = nodePulse[index].value
            val center = Offset(
                node.position.x * size.width,
                node.position.y * size.height
            )
            val baseRadius = size.minDimension * node.radius
            drawCircle(
                color = colorScheme.primary.copy(alpha = node.baseAlpha),
                radius = baseRadius * (1f + pulse * 0.4f),
                center = center
            )
            drawCircle(
                color = colorScheme.secondary.copy(alpha = 0.4f),
                radius = (baseRadius * (1.8f + sin(pulse * 2f * PI) * 0.2f)).toFloat(),
                center = center,
                style = Stroke(width = baseRadius * 0.25f)
            )
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
