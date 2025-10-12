package com.portfolio

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalUriHandler
import org.jetbrains.compose.resources.painterResource
import portfolio.composeapp.generated.resources.Res
import portfolio.composeapp.generated.resources.external_link
import portfolio.composeapp.generated.resources.github

@Composable
fun Projects(
    modifier: Modifier = Modifier
) {
    val uriHandler = LocalUriHandler.current
    Column(
        modifier = modifier
            .width(900.dp)
            .padding(horizontal = 32.dp, vertical = 64.dp)
    ) {
        // Section title
        Text(
            text = "Side Projects",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        
        // Project cards in horizontal layout
        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // First row - StrictPro and Donatta
            Row(
                horizontalArrangement = Arrangement.spacedBy(24.dp)
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
            
            // Second row - Portfolio website
            Row(
                horizontalArrangement = Arrangement.spacedBy(24.dp)
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
                
                // Empty space to maintain layout balance
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
    Column(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Project title
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.primary
        )
        
        // Project description
        Text(
            text = description,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 16.sp
            ),
            color = MaterialTheme.colorScheme.onSurface,
            lineHeight = 24.sp
        )
        
        // Clickable link with icon
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { onClick() }
        ) {
            Image(
                painter = iconPainter,
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                modifier = Modifier.size(17.dp)
            )
            Text(
                text = linkText,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                ),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}
