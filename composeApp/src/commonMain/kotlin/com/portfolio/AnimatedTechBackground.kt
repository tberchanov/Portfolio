package com.portfolio

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import kotlin.math.PI
import kotlin.math.floor
import kotlin.math.sin
import kotlin.random.Random

private const val BACKGROUND_ALPHA = 0.25f
private const val MIN_NODE_DISTANCE = 0.12f
private const val MAX_NODE_ATTEMPTS = 16
private val NODE_X_RANGE = 0.08f..0.92f
private val NODE_Y_RANGE = 0.18f..0.72f
private val NODE_RADIUS_RANGE = 0.012f..0.02f
private val NODE_ALPHA_RANGE = 0.42f..0.65f
private const val MIN_NODE_COUNT = 8
private const val MAX_NODE_COUNT = 12

private data class Node(val position: Offset, val radius: Float, val baseAlpha: Float)
private data class Connection(val from: Int, val to: Int, val phaseOffset: Float)
private data class Network(val nodes: List<Node>, val connections: List<Connection>)

private fun Random.nextFloatIn(range: ClosedFloatingPointRange<Float>): Float =
    range.start + nextFloat() * (range.endInclusive - range.start)

private fun Float.wrapToUnit(): Float {
    val fraction = this - floor(this.toDouble())
    return (if (fraction < 0.0) fraction + 1.0 else fraction).toFloat()
}

private fun Random.generateNetwork(): Network {
    val nodeCount = nextInt(MIN_NODE_COUNT, MAX_NODE_COUNT + 1)
    val minDistanceSquared = MIN_NODE_DISTANCE * MIN_NODE_DISTANCE
    val nodes = mutableListOf<Node>()

    repeat(nodeCount) {
        var attempts = 0
        var requiredDistanceSquared = minDistanceSquared
        var position: Offset
        var isFarEnough: Boolean
        do {
            position = Offset(
                nextFloatIn(NODE_X_RANGE),
                nextFloatIn(NODE_Y_RANGE)
            )
            isFarEnough = nodes.none { existing ->
                val dx = position.x - existing.position.x
                val dy = position.y - existing.position.y
                dx * dx + dy * dy < requiredDistanceSquared
            }
            attempts++
            if (!isFarEnough && attempts % 4 == 0) {
                requiredDistanceSquared *= 0.85f
            }
        } while (!isFarEnough && attempts < MAX_NODE_ATTEMPTS)

        val radius = nextFloatIn(NODE_RADIUS_RANGE)
        val alpha = nextFloatIn(NODE_ALPHA_RANGE)
        nodes += Node(position, radius, alpha)
    }

    if (nodes.size < 2) {
        return Network(nodes, emptyList())
    }

    val connectionPairs = mutableSetOf<Pair<Int, Int>>()

    nodes.forEachIndexed { index, node ->
        val linkCount = minOf(nextInt(2, 4), nodes.size - 1)
        val nearest = nodes.indices
            .filter { it != index }
            .sortedBy { other ->
                val otherNode = nodes[other]
                val dx = node.position.x - otherNode.position.x
                val dy = node.position.y - otherNode.position.y
                dx * dx + dy * dy
            }
            .take(linkCount)

        nearest.forEach { neighbor ->
            val pair = if (index < neighbor) index to neighbor else neighbor to index
            connectionPairs += pair
        }
    }

    repeat(nextInt(1, 4)) {
        val a = nextInt(nodes.size)
        val b = nextInt(nodes.size)
        if (a != b) {
            val pair = if (a < b) a to b else b to a
            connectionPairs += pair
        }
    }

    val connections = connectionPairs.map { (from, to) ->
        Connection(from, to, nextFloat())
    }

    return Network(nodes, connections)
}

@Composable
fun AnimatedTechBackground(modifier: Modifier = Modifier) {
    val colorScheme = MaterialTheme.colorScheme

    val network = remember(colorScheme) {
        val seededRandom = Random(Random.nextLong())
        seededRandom.generateNetwork()
    }

    val transition = rememberInfiniteTransition(label = "networkAnimation")

    val gridShift by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 24_000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "gridShift"
    )

    val connectionProgress = network.connections.mapIndexed { index, _ ->
        transition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 5_200 + index * 430,
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Restart
            ),
            label = "connectionPulse$index"
        )
    }

    val nodePulse = network.nodes.mapIndexed { index, _ ->
        transition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 3_600 + index * 320,
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Reverse
            ),
            label = "nodePulse$index"
        )
    }

    Canvas(modifier = modifier.graphicsLayer(alpha = BACKGROUND_ALPHA)) {
        val gradient = Brush.linearGradient(
            colors = listOf(
                colorScheme.primary.copy(alpha = 0.12f),
                colorScheme.secondary.copy(alpha = 0.08f),
                Color.Transparent
            ),
            start = Offset.Zero,
            end = Offset(size.width, size.height)
        )
        drawRect(brush = gradient)

        val gridColor = colorScheme.primary.copy(alpha = 0.08f)
        val verticalSpacing = size.width / 10f
        val horizontalSpacing = size.height / 7f
        val shiftX = gridShift.wrapToUnit() * verticalSpacing
        val shiftY = (gridShift * 0.7f).wrapToUnit() * horizontalSpacing

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

        network.connections.forEachIndexed { index, connection ->
            val startNode = network.nodes[connection.from]
            val endNode = network.nodes[connection.to]
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

            val progress = (connectionProgress[index].value + connection.phaseOffset).wrapToUnit()
            val pulsePoint = Offset(
                start.x + (end.x - start.x) * progress,
                start.y + (end.y - start.y) * progress
            )

            drawCircle(
                color = connectorHighlight,
                radius = size.minDimension * 0.01f,
                center = pulsePoint,
                alpha = 0.7f
            )
        }

        network.nodes.forEachIndexed { index, node ->
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
