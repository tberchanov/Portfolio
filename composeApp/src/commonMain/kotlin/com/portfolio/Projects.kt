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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalUriHandler
import org.jetbrains.compose.resources.painterResource
import portfolio.composeapp.generated.resources.Res
import portfolio.composeapp.generated.resources.external_link
import portfolio.composeapp.generated.resources.github

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
    val isMobileScreen = isMobile()
    val isTabletScreen = isTablet()
    val contentWidth = getResponsiveContentWidth()
    val horizontalPadding = getResponsiveHorizontalPadding()
    val verticalPadding = getResponsiveVerticalPadding()
    val fontScale = getResponsiveFontScale()
    
    Column(
        modifier = modifier
            .then(
                if (isMobileScreen) {
                    Modifier.fillMaxWidth()
                } else {
                    Modifier.widthIn(max = contentWidth).fillMaxWidth()
                }
            )
            .padding(
                horizontal = horizontalPadding,
                vertical = verticalPadding
            )
    ) {
        Text(
            text = "Side Projects",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontSize = (SECTION_TITLE_FONT_SIZE_SP * fontScale).sp,
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(
                bottom = if (isMobileScreen) 24.dp else 32.dp
            )
        )

        if (isMobileScreen) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ProjectCard(
                    title = "StrictPro",
                    description = "A powerful library designed to extend and improve Android's StrictMode by offering more flexibility and informative UI for violations.",
                    linkText = "View on GitHub",
                    iconPainter = painterResource(Res.drawable.github),
                    onClick = { 
                        uriHandler.openUri("https://github.com/tberchanov/StrictPro")
                    }
                )
                
                ProjectCard(
                    title = "Donatta",
                    description = "A mobile application for gamification and analytics of donations to support the Armed Forces of Ukraine, with rewards and personal analytics.",
                    linkText = "Visit Website",
                    iconPainter = painterResource(Res.drawable.external_link),
                    onClick = { 
                        uriHandler.openUri("https://donatta.app/")
                    }
                )
                
                ProjectCard(
                    title = "Portfolio Website",
                    description = "This portfolio website showcasing my work and experience, fully implemented using Kotlin Multiplatform with Compose.",
                    linkText = "View Source",
                    iconPainter = painterResource(Res.drawable.github),
                    onClick = { 
                        uriHandler.openUri("https://github.com/tberchanov/Portfolio")
                    }
                )
            }
        } else {
            Column(
                verticalArrangement = Arrangement.spacedBy(
                    if (isTabletScreen) 20.dp else SECTION_SPACING_DP.dp
                )
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
    val isMobileScreen = isMobile()
    val isTabletScreen = isTablet()
    val fontScale = getResponsiveFontScale()
    
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    val animatedScale by animateFloatAsState(
        targetValue = if (!isMobileScreen && isHovered) HOVER_SCALE_FACTOR else 1f,
        animationSpec = tween(ANIMATION_DURATION_MS),
        label = "cardScale"
    )
    
    val animatedElevation by animateDpAsState(
        targetValue = if (!isMobileScreen && isHovered) HOVER_ELEVATION_DP.dp else 0.dp,
        animationSpec = tween(ANIMATION_DURATION_MS),
        label = "cardElevation"
    )
    
    val animatedIconScale by animateFloatAsState(
        targetValue = if (!isMobileScreen && isHovered) 1.1f else 1f,
        animationSpec = tween(ANIMATION_DURATION_MS),
        label = "iconScale"
    )
    
    val animatedBorderAlpha by animateFloatAsState(
        targetValue = if (!isMobileScreen && isHovered) 0.6f else BORDER_ALPHA,
        animationSpec = tween(ANIMATION_DURATION_MS),
        label = "borderAlpha"
    )
    
    val iconSize = (ICON_SIZE_DP * fontScale).dp
    val iconTextSpacing = if (isMobileScreen) 6.dp else if (isTabletScreen) 7.dp else ICON_TEXT_SPACING_DP.dp
    val cardPadding = if (isMobileScreen) 16.dp else if (isTabletScreen) 20.dp else CARD_PADDING_DP.dp
    val cardSpacing = if (isMobileScreen) 12.dp else if (isTabletScreen) 14.dp else CARD_SPACING_DP.dp
    val linkTouchTargetHeight = if (isMobileScreen) 48.dp else null

    Column(
        modifier = modifier
            .then(
                if (!isMobileScreen) {
                    Modifier.hoverable(interactionSource)
                } else {
                    Modifier
                }
            )
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
            .padding(cardPadding)
            .then(
                if (isMobileScreen) {
                    Modifier.fillMaxWidth()
                } else {
                    Modifier
                }
            ),
        verticalArrangement = Arrangement.spacedBy(cardSpacing)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(
                fontSize = (PROJECT_TITLE_FONT_SIZE_SP * fontScale).sp,
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.primary,
            lineHeight = if (isMobileScreen) 20.sp else 24.sp,
            modifier = Modifier.fillMaxWidth(),
            softWrap = true,
            overflow = TextOverflow.Visible
        )
        
        Text(
            text = description,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = (PROJECT_DESCRIPTION_FONT_SIZE_SP * fontScale).sp
            ),
            color = MaterialTheme.colorScheme.onSurface,
            lineHeight = if (isMobileScreen) 18.sp else 24.sp,
            modifier = Modifier.fillMaxWidth(),
            softWrap = true,
            overflow = TextOverflow.Visible
        )

        if (isMobileScreen && linkTouchTargetHeight != null) {
            Box(
                modifier = Modifier
                    .height(linkTouchTargetHeight)
                    .fillMaxWidth()
                    .clickable { onClick() },
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = iconPainter,
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                        modifier = Modifier.size(iconSize)
                    )
                    Text(
                        text = linkText,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = (LINK_FONT_SIZE_SP * fontScale).sp,
                            fontWeight = FontWeight.Medium
                        ),
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(start = iconTextSpacing)
                    )
                }
            }
        } else {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { onClick() }
            ) {
                Image(
                    painter = iconPainter,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                    modifier = Modifier
                        .size(iconSize)
                        .scale(animatedIconScale)
                )
                Text(
                    text = linkText,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = (LINK_FONT_SIZE_SP * fontScale).sp,
                        fontWeight = FontWeight.Medium
                    ),
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(start = iconTextSpacing)
                )
            }
        }
    }
}
