package com.pitchpulse.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.pitchpulse.ui.theme.*

@Composable
fun MatchesTopBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onFilterClick: () -> Unit,
    hasActiveFilters: Boolean,
    modifier: Modifier = Modifier
) {
    var localSearchQuery by remember { mutableStateOf(searchQuery) }

    LaunchedEffect(searchQuery) {
        if (searchQuery.isEmpty() && localSearchQuery.isNotEmpty()) {
            localSearchQuery = ""
        }
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = "Match Day",
            style = MaterialTheme.typography.titleLarge,
            color = TextPrimary,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Search Bar
        Box(
            modifier = Modifier
                .weight(1f)
                .background(AppCard, RoundedCornerShape(12.dp))
                .border(
                    width = 1.dp,
                    color = if (searchQuery.isNotEmpty()) AppAccent.copy(alpha = 0.5f) else AppSurface,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(horizontal = 12.dp, vertical = 10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = TextMuted,
                    modifier = Modifier.size(20.dp)
                )

                Box(modifier = Modifier.weight(1f)) {
                    if (localSearchQuery.isEmpty()) {
                        Text(
                            text = "Search teams or leagues...",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextMuted
                        )
                    }
                    BasicTextField(
                        value = localSearchQuery,
                        onValueChange = { 
                            localSearchQuery = it
                            onSearchQueryChange(it) 
                        },
                        textStyle = TextStyle(
                            color = TextPrimary,
                            fontSize = MaterialTheme.typography.bodyMedium.fontSize
                        ),
                        singleLine = true,
                        cursorBrush = SolidColor(AppAccent),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                if (localSearchQuery.isNotEmpty()) {
                    IconButton(
                        onClick = { 
                            localSearchQuery = ""
                            onSearchQueryChange("") 
                        },
                        modifier = Modifier.size(20.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = "Clear search",
                            tint = TextSecondary,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }

        // Filter Button
        Box(
            modifier = Modifier.size(44.dp)
        ) {
            IconButton(
                onClick = onFilterClick,
                modifier = Modifier
                    .fillMaxSize()
                    .background(AppCard, RoundedCornerShape(12.dp))
            ) {
                Icon(
                    imageVector = Icons.Default.FilterList,
                    contentDescription = "Filter",
                    tint = if (hasActiveFilters) AppAccent else TextSecondary
                )
            }
            if (hasActiveFilters) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(4.dp)
                        .size(8.dp)
                        .background(AppAccent, RoundedCornerShape(50))
                )
            }
        }
    }
}
}
