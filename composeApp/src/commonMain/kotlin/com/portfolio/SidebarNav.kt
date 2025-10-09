package com.portfolio

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.unit.dp
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.ExperimentalComposeUiApi

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SidebarNav(
    items: List<String>,
    activeIndex: Int,
    onClick: (index: Int) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(10.dp)
) {
    Surface(
        color = Color.Transparent,
        tonalElevation = 0.dp,
        modifier = modifier.fillMaxHeight()
    ) {
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            var hoverIndex by remember { mutableStateOf<Int?>(null) }
            items.forEachIndexed { index, label ->
                val isActive = index == activeIndex
                val isHover = hoverIndex == index
                val isEmphasized = isActive || isHover
                
                // Animated values
                val animatedLineWidth by animateDpAsState(
                    targetValue = 24.dp * (if (isEmphasized) 1.15f else 1f),
                    animationSpec = tween(durationMillis = 200),
                    label = "lineWidth"
                )
                val animatedFontSize by animateFloatAsState(
                    targetValue = if (isEmphasized) 1.15f else 1f,
                    animationSpec = tween(durationMillis = 200),
                    label = "fontSize"
                )
                
                val color = if (isEmphasized) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                val lineColor = if (isEmphasized) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                val baseTextStyle = MaterialTheme.typography.titleMedium
                val textStyle = baseTextStyle.copy(fontSize = (baseTextStyle.fontSize.value * animatedFontSize).sp)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { onClick(index) }
                        .onPointerEvent(PointerEventType.Enter) { hoverIndex = index }
                        .onPointerEvent(PointerEventType.Exit) { hoverIndex = null },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Leading accent line
                    Spacer(
                        modifier = Modifier
                            .width(animatedLineWidth)
                            .height(1.dp)
                            .drawBehind {
                                drawRect(
                                    color = lineColor
                                )
                            }
                    )
                    Text(
                        text = label,
                        color = color,
                        style = textStyle,
                        modifier = Modifier.padding(start = 12.dp)
                    )
                }
                Spacer(Modifier.height(4.dp))
            }
        }
    }
}
