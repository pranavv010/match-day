package com.pitchpulse.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pitchpulse.core.ui.Dimens
import com.pitchpulse.ui.theme.AppAccent
import com.pitchpulse.ui.theme.TextPrimary
import com.pitchpulse.ui.theme.TextSecondary

@Composable
fun DateRibbon(
    dates: List<Triple<String, String, String>>,
    selectedDate: String,
    onDateSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()

    LaunchedEffect(selectedDate, dates) {
        val index = dates.indexOfFirst { it.third == selectedDate }
        if (index >= 0) {
            listState.animateScrollToItem(maxOf(0, index - 2))
        }
    }

    LazyRow(
        state = listState,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = Dimens.SpacingMedium),
        contentPadding = PaddingValues(horizontal = Dimens.SpacingLarge),
        horizontalArrangement = Arrangement.spacedBy(Dimens.SpacingLarge)
    ) {
        items(
            items = dates,
            key = { it.third }
        ) { (dayName, dayNumber, fullDate) ->
            DateItem(
                dayName = dayName,
                dayNumber = dayNumber,
                isSelected = fullDate == selectedDate,
                onClick = { onDateSelected(fullDate) }
            )
        }
    }
}

@Composable
private fun DateItem(
    dayName: String,
    dayNumber: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    // Single animation instead of 4
    val bgColor by animateColorAsState(
        targetValue = if (isSelected) AppAccent else Color.Transparent,
        animationSpec = tween(200),
        label = "Bg"
    )
    val textColor = if (isSelected) Color.Black else TextPrimary
    val labelColor = if (isSelected) AppAccent else TextSecondary

    Column(
        modifier = Modifier
            .clip(CircleShape)
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = dayName,
            style = MaterialTheme.typography.labelSmall,
            color = labelColor,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )

        Spacer(modifier = Modifier.height(4.dp))

        Box(
            modifier = Modifier
                .size(32.dp)
                .background(color = bgColor, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = dayNumber,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = textColor
            )
        }
    }
}
