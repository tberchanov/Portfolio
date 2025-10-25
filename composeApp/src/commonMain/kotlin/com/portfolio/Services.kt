package com.portfolio

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import portfolio.composeapp.generated.resources.Res
import portfolio.composeapp.generated.resources.cross_platform
import portfolio.composeapp.generated.resources.diagram
import portfolio.composeapp.generated.resources.group
import portfolio.composeapp.generated.resources.research_and_development

private const val CONTENT_WIDTH_DP = 900
private const val SECTION_TITLE_FONT_SIZE_SP = 36
private const val CARD_TITLE_FONT_SIZE_SP = 20
private const val CARD_DESCRIPTION_FONT_SIZE_SP = 16
private const val CARD_PADDING_DP = 24
private const val ICON_RIGHT_PADDING_DP = 12
private const val SECTION_SPACING_DP = 24
private const val ROW_SPACING_DP = 24
private const val CARD_SPACING_DP = 12
private const val BORDER_WIDTH_DP = 1
private const val BORDER_ALPHA = 0.3f
private const val ICON_SIZE_DP = 24
private const val ICON_CIRCLE_SIZE_DP = 40
private const val HOVER_SCALE_FACTOR = 1.05f
private const val HOVER_ELEVATION_DP = 8
private const val ANIMATION_DURATION_MS = 200

@Composable
fun Services(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(CONTENT_WIDTH_DP.dp)
            .padding(horizontal = 32.dp, vertical = 64.dp)
    ) {
        Text(
            text = "Services",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontSize = SECTION_TITLE_FONT_SIZE_SP.sp,
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(SECTION_SPACING_DP.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(ROW_SPACING_DP.dp)
            ) {
                ExperienceCard(
                    title = "Mobile Architecture",
                    description = "Designing and implementing scalable mobile solutions. Actively involved in presales activities and project discovery phases to align technical solutions with business goals.",
                    iconPainter = painterResource(Res.drawable.diagram),
                    modifier = Modifier.weight(1f)
                )
                ExperienceCard(
                    title = "Cross-Platform Development",
                    description = "Building cross-platform solutions that deliver native-like experiences across iOS and Android platforms using Kotlin Multiplatform, Flutter and React Native.",
                    iconPainter = painterResource(Res.drawable.cross_platform),
                    modifier = Modifier.weight(1f)
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(ROW_SPACING_DP.dp)
            ) {
                ExperienceCard(
                    title = "Team Leadership",
                    description = "Leading development teams, establishing best practices, planning and organizing work and mentoring developers to ensure consistent, predictable delivery.",
                    iconPainter = painterResource(Res.drawable.group),
                    modifier = Modifier.weight(1f)
                )
                ExperienceCard(
                    title = "R&D",
                    description = "Research and development of advanced mobile functionality, including video editing, cryptography and SDK development. Exploring cross-domain innovations across AOSP, robotics with ArduPilot, computer vision and machine learning.",
                    iconPainter = painterResource(Res.drawable.research_and_development),
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun ExperienceCard(
    title: String,
    description: String,
    iconPainter: Painter,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    
    val animatedScale by animateFloatAsState(
        targetValue = if (isHovered) HOVER_SCALE_FACTOR else 1f,
        animationSpec = tween(ANIMATION_DURATION_MS),
        label = "cardScale"
    )
    
    val animatedElevation by animateDpAsState(
        targetValue = if (isHovered) HOVER_ELEVATION_DP.dp else 0.dp,
        animationSpec = tween(ANIMATION_DURATION_MS),
        label = "cardElevation"
    )
    
    val animatedIconScale by animateFloatAsState(
        targetValue = if (isHovered) 1.1f else 1f,
        animationSpec = tween(ANIMATION_DURATION_MS),
        label = "iconScale"
    )
    
    val animatedBorderAlpha by animateFloatAsState(
        targetValue = if (isHovered) 0.6f else BORDER_ALPHA,
        animationSpec = tween(ANIMATION_DURATION_MS),
        label = "borderAlpha"
    )
    
    Row(
        modifier = modifier
            .hoverable(interactionSource)
            .scale(animatedScale)
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = BORDER_WIDTH_DP.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = animatedBorderAlpha),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(CARD_PADDING_DP.dp)
    ) {
        Box(
            modifier = Modifier
                .size(ICON_CIRCLE_SIZE_DP.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                    shape = CircleShape
                )
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = iconPainter,
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .size(ICON_SIZE_DP.dp)
                    .scale(animatedIconScale)
            )
        }

        Spacer(Modifier.width(ICON_RIGHT_PADDING_DP.dp))

        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = CARD_TITLE_FONT_SIZE_SP.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 6.dp, bottom = CARD_SPACING_DP.dp)
            )

            Text(
                text = description,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = CARD_DESCRIPTION_FONT_SIZE_SP.sp
                ),
                color = MaterialTheme.colorScheme.onSurface,
                lineHeight = 24.sp
            )
        }
    }
}
