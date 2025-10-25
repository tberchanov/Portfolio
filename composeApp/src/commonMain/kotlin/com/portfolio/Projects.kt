package com.portfolio

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.Image
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalUriHandler
import org.jetbrains.compose.resources.painterResource
import portfolio.composeapp.generated.resources.Res
import portfolio.composeapp.generated.resources.external_link
import portfolio.composeapp.generated.resources.github

private const val CONTENT_WIDTH_DP = 900
private const val SECTION_TITLE_FONT_SIZE_SP = 36
private const val PROJECT_TITLE_FONT_SIZE_SP = 20
private const val PROJECT_DESCRIPTION_FONT_SIZE_SP = 16
private const val LINK_FONT_SIZE_SP = 15
private const val ICON_SIZE_DP = 17
private const val CARD_PADDING_DP = 24
private const val SECTION_SPACING_DP = 24
private const val ROW_SPACING_DP = 24
private const val CARD_SPACING_DP = 16
private const val ICON_TEXT_SPACING_DP = 8
private const val BORDER_WIDTH_DP = 1
private const val BORDER_ALPHA = 0.3f
private const val HOVER_SCALE_FACTOR = 1.05f
private const val HOVER_ELEVATION_DP = 8
private const val ANIMATION_DURATION_MS = 200

@Composable
fun Projects(
    modifier: Modifier = Modifier
) {
    val uriHandler = LocalUriHandler.current
    Column(
        modifier = modifier
            .width(CONTENT_WIDTH_DP.dp)
            .padding(horizontal = 32.dp, vertical = 64.dp)
    ) {
        Text(
            text = "Side Projects",
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
                ProjectCard(
                    title = "StrictPro",
                    description = "A powerful library designed to extend and improve Android's StrictMode by offering more flexibility and informative UI for violations.",
                    linkText = "View on GitHub",
                    iconPainter = painterResource(Res.drawable.github),
                    onClick = { 
                        uriHandler.openUri("https://github.com/tberchanov/StrictPro")
                    },
                    modifier = Modifier.weight(1f)
                )
                
                ProjectCard(
                    title = "Donatta",
                    description = "A mobile application for gamification and analytics of donations to support the Armed Forces of Ukraine, with rewards and personal analytics.",
                    linkText = "Visit Website",
                    iconPainter = painterResource(Res.drawable.external_link),
                    onClick = { 
                        uriHandler.openUri("https://donatta.app/")
                    },
                    modifier = Modifier.weight(1f)
                )
            }
            
            Row(
                horizontalArrangement = Arrangement.spacedBy(ROW_SPACING_DP.dp)
            ) {
                ProjectCard(
                    title = "Portfolio Website",
                    description = "This portfolio website showcasing my work and experience, fully implemented using Kotlin Multiplatform with Compose.",
                    linkText = "View Source",
                    iconPainter = painterResource(Res.drawable.github),
                    onClick = { 
                        uriHandler.openUri("https://github.com/tberchanov/Portfolio")
                    },
                    modifier = Modifier.weight(1f)
                )
                
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun ProjectCard(
    title: String,
    description: String,
    linkText: String,
    iconPainter: androidx.compose.ui.graphics.painter.Painter,
    onClick: () -> Unit,
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
    
    Column(
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
            .padding(CARD_PADDING_DP.dp),
        verticalArrangement = Arrangement.spacedBy(CARD_SPACING_DP.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(
                fontSize = PROJECT_TITLE_FONT_SIZE_SP.sp,
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.primary
        )
        
        Text(
            text = description,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = PROJECT_DESCRIPTION_FONT_SIZE_SP.sp
            ),
            color = MaterialTheme.colorScheme.onSurface,
            lineHeight = 24.sp
        )
        
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { onClick() }
        ) {
            Image(
                painter = iconPainter,
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .size(ICON_SIZE_DP.dp)
                    .scale(animatedIconScale)
            )
            Text(
                text = linkText,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = LINK_FONT_SIZE_SP.sp,
                    fontWeight = FontWeight.Medium
                ),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(start = ICON_TEXT_SPACING_DP.dp)
            )
        }
    }
}
