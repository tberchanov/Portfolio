package com.portfolio

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.painterResource
import portfolio.composeapp.generated.resources.Res
import portfolio.composeapp.generated.resources.portrait

@Composable
fun AboutMe(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .width(900.dp)
            .padding(horizontal = 32.dp, vertical = 64.dp),
        horizontalArrangement = Arrangement.spacedBy(48.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier.size(250.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(250.dp)
                    .clip(CircleShape)
                    .background(
                        brush = Brush.radialGradient(
                            colorStops = arrayOf(
                                0.87f to MaterialTheme.colorScheme.primary,
                                1.0f to Color.Transparent
                            ),
                            radius = 250f,
                        )
                    )
            )
            
            Image(
                painter = painterResource(Res.drawable.portrait),
                contentDescription = "Portrait",
                modifier = Modifier
                    .size(220.dp)
                    .clip(CircleShape)
                    .alpha(0.95f)
            )
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "About Me",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.primary
            )

            val yearsOfExperience = remember { getYearsOfExperience() }

            DescriptionText(
                text = "I am a Mobile Applications Architect and Team Lead with $yearsOfExperience years of experience driving the design and delivery of scalable mobile solutions. My expertise spans Android, Kotlin, and cross-platform technologies such as Kotlin Multiplatform, Flutter, React Native.",
            )
            DescriptionText(
                text = "I have successfully led initiatives to reduce crash rates, improve security score, enable cross-platform delivery, and scale applications serving thousands of users.",
            )
            DescriptionText(
                text = "Beyond hands-on development, I provide technical leadership by mentoring engineers, establishing best practices, leading teams organizing their work to ensure consistent delivery.",
            )
        }
    }
}

@Composable
private fun DescriptionText(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        modifier = modifier,
        style = MaterialTheme.typography.bodyLarge.copy(
            fontSize = 18.sp
        ),
        color = MaterialTheme.colorScheme.onBackground,
        lineHeight = 24.sp
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
