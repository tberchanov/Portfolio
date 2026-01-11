package com.portfolio

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.painterResource
import portfolio.composeapp.generated.resources.Res
import portfolio.composeapp.generated.resources.portrait

private const val PROJECTS_QUANTITY = 10

@Composable
fun AboutMe(
    modifier: Modifier = Modifier
) {
    val isMobileScreen = isMobile()
    val contentWidth = getResponsiveContentWidth()
    val horizontalPadding = getResponsiveHorizontalPadding()
    val verticalPadding = getResponsiveVerticalPadding()

    if (isMobileScreen) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(
                    horizontal = horizontalPadding,
                    vertical = verticalPadding
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PortraitSection()
            Spacer(modifier = Modifier.height(24.dp))
            TextContentSection(includeStats = true)
        }
    } else {
        Column(
            modifier = modifier
                .widthIn(max = contentWidth)
                .fillMaxWidth()
                .padding(
                    horizontal = horizontalPadding,
                    vertical = verticalPadding
                )
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(48.dp),
                verticalAlignment = Alignment.Top,
                modifier = Modifier.fillMaxWidth()
            ) {
                PortraitSection()
                TextContentSection(
                    modifier = Modifier.weight(1f),
                    includeStats = false
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            StatsSection(
                fontScale = getResponsiveFontScale(),
                isMobile = false
            )
        }
    }
}

@Composable
private fun PortraitSection() {
    val portraitSize = getResponsivePortraitSize()
    val imageSize = (portraitSize.value * 0.88f).dp
    
    Box(
        modifier = Modifier.size(portraitSize),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(portraitSize)
                .clip(CircleShape)
                .background(
                    brush = Brush.radialGradient(
                        colorStops = arrayOf(
                            0.87f to MaterialTheme.colorScheme.primary,
                            1.0f to Color.Transparent
                        ),
                        radius = portraitSize.value,
                    )
                )
        )
        
        Image(
            painter = painterResource(Res.drawable.portrait),
            contentDescription = "Portrait",
            modifier = Modifier
                .size(imageSize)
                .clip(CircleShape)
                .alpha(0.95f)
        )
    }
}

@Composable
private fun TextContentSection(
    modifier: Modifier = Modifier,
    includeStats: Boolean = true
) {
    val fontScale = getResponsiveFontScale()
    val isMobileScreen = isMobile()
    
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "About Me",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontSize = (36 * fontScale).sp,
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.primary
        )

        DescriptionText(
            text = "I am a Mobile Applications Architect and Team Lead driving the design and delivery of scalable mobile solutions. My expertise spans Android, Kotlin, and cross-platform technologies such as Kotlin Multiplatform, Flutter, React Native.",
            fontScale = fontScale,
            isMobile = isMobileScreen
        )
        DescriptionText(
            text = "I have successfully led initiatives to reduce crash rates, improve security score, enable cross-platform delivery, and scale applications serving thousands of users.",
            fontScale = fontScale,
            isMobile = isMobileScreen
        )
        DescriptionText(
            text = "Beyond hands-on development, I provide technical leadership by mentoring engineers, establishing best practices, leading teams organizing their work to ensure consistent delivery.",
            fontScale = fontScale,
            isMobile = isMobileScreen
        )

        if (includeStats) {
            StatsSection(
                fontScale = fontScale,
                isMobile = isMobileScreen
            )
        }
    }
}

@Composable
private fun StatsSection(
    fontScale: Float,
    isMobile: Boolean
) {
    val yearsOfExperience = remember { getYearsOfExperience() }
    val statsSpacing = if (isMobile) 24.dp else 48.dp
    
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(statsSpacing)
        ) {
            StatItem(
                value = "$yearsOfExperience",
                label = "Years of Experience",
                fontScale = fontScale,
                isMobile = isMobile
            )
            StatItem(
                value = "$PROJECTS_QUANTITY",
                label = "Projects Delivered",
                fontScale = fontScale,
                isMobile = isMobile
            )
        }
    }
}

@Composable
private fun DescriptionText(
    text: String,
    modifier: Modifier = Modifier,
    fontScale: Float,
    isMobile: Boolean
) {
    Text(
        text = text,
        modifier = modifier.fillMaxWidth(),
        style = MaterialTheme.typography.bodyLarge.copy(
            fontSize = (18 * fontScale).sp
        ),
        color = MaterialTheme.colorScheme.onBackground,
        lineHeight = if (isMobile) 20.sp else 24.sp,
        softWrap = true,
        overflow = TextOverflow.Visible
    )
}

private val careerStartDate = LocalDate(2017, 7, 1)

private fun getYearsOfExperience(): Int {
    val today = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .date

    var years = today.year - careerStartDate.year
    if (
        today.monthNumber < careerStartDate.monthNumber ||
        (today.monthNumber == careerStartDate.monthNumber && today.dayOfMonth < careerStartDate.dayOfMonth)
    ) {
        years -= 1
    }

    return years.coerceAtLeast(0)
}

@Composable
private fun StatItem(
    value: String,
    label: String,
    modifier: Modifier = Modifier,
    fontScale: Float,
    isMobile: Boolean
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(
            if (isMobile) 8.dp else 24.dp
        ),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .width(1.dp)
                .heightIn(
                    min = if (isMobile) 100.dp else 140.dp
                )
                .background(
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.35f)
                )
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(
                if (isMobile) 6.dp else 8.dp
            )
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(
                    if (isMobile) 8.dp else 12.dp
                ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "+",
                    style = MaterialTheme.typography.displaySmall.copy(
                        fontSize = (56 * fontScale).sp,
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.35f)
                )

                val targetValue = value.toIntOrNull() ?: 0
                var animatedTarget by remember(value) { mutableStateOf(0) }

                LaunchedEffect(targetValue) {
                    if (animatedTarget != targetValue) {
                        animatedTarget = targetValue
                    }
                }
                
                val animatedValue by animateIntAsState(
                    targetValue = animatedTarget,
                    animationSpec = tween(durationMillis = 1000)
                )

                Text(
                    text = animatedValue.toString(),
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontSize = (96 * fontScale).sp,
                        fontWeight = FontWeight.ExtraBold
                    ),
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.9f)
                )
            }

            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = (16 * fontScale).sp,
                    fontWeight = FontWeight.Medium
                ),
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}
