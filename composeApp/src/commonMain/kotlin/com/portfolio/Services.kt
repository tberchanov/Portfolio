package com.portfolio

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private const val CONTENT_WIDTH_DP = 900
private const val SECTION_TITLE_FONT_SIZE_SP = 36
private const val CARD_TITLE_FONT_SIZE_SP = 20
private const val CARD_DESCRIPTION_FONT_SIZE_SP = 16
private const val CARD_PADDING_DP = 24
private const val SECTION_SPACING_DP = 24
private const val ROW_SPACING_DP = 24
private const val CARD_SPACING_DP = 12
private const val BORDER_WIDTH_DP = 1
private const val BORDER_ALPHA = 0.3f

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
                    modifier = Modifier.weight(1f)
                )
                ExperienceCard(
                    title = "Cross-Platform Development",
                    description = "Building cross-platform solutions that deliver native-like experiences across iOS and Android platforms using Kotlin Multiplatform, Flutter and React Native.",
                    modifier = Modifier.weight(1f)
                )
            }
            
            Row(
                horizontalArrangement = Arrangement.spacedBy(ROW_SPACING_DP.dp)
            ) {
                ExperienceCard(
                    title = "Team Leadership",
                    description = "Leading development teams, establishing best practices, planning and organizing work and mentoring developers to ensure consistent, predictable delivery.",
                    modifier = Modifier.weight(1f)
                )
                ExperienceCard(
                    title = "R&D",
                    description = "Research and development of advanced mobile functionality, including video editing, cryptography and SDK development. Exploring cross-domain innovations across AOSP, robotics with ArduPilot, computer vision and machine learning.",
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
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = BORDER_WIDTH_DP.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = BORDER_ALPHA),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(CARD_PADDING_DP.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(
                fontSize = CARD_TITLE_FONT_SIZE_SP.sp,
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = CARD_SPACING_DP.dp)
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
