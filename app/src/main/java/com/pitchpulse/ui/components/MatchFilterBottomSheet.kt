package com.pitchpulse.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Check
import coil3.compose.AsyncImage
import com.pitchpulse.ui.state.MatchFilters
import com.pitchpulse.data.model.TrackedLeagues
import com.pitchpulse.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchFilterBottomSheet(
    onDismissRequest: () -> Unit,
    activeFilters: MatchFilters,
    allTrackedLeagues: List<Pair<Int, String>>,
    leaguesWithMatchesToday: Set<Int>,
    selectedDate: String,
    availableDates: List<Triple<String, String, String>>,
    onDateSelected: (String) -> Unit,
    onApplyFilters: (MatchFilters) -> Unit,
    onClearFilters: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var localDate by remember { mutableStateOf(selectedDate) }
    var localFilters by remember { mutableStateOf(activeFilters) }
    
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = AppSurface,
        dragHandle = { BottomSheetDefaults.DragHandle(color = TextMuted) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Filters",
                    style = MaterialTheme.typography.titleLarge,
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = {
                            if (localDate != selectedDate) onDateSelected(localDate)
                            onApplyFilters(localFilters)
                            onDismissRequest()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = AppAccent
                        )
                    }
                    TextButton(onClick = { 
                        localFilters = MatchFilters() 
                        availableDates.find { it.first == "Today" }?.let { today ->
                            localDate = today.third
                        }
                    }) {
                        Text("Clear All", color = AppAccent)
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Date Picker Section
            Text(
                text = "Select Date",
                style = MaterialTheme.typography.titleMedium,
                color = TextPrimary,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            androidx.compose.foundation.lazy.LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(availableDates) { dateTuple ->
                    val isSelected = dateTuple.third == localDate
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(if (isSelected) AppAccent else AppCard)
                            .clickable { localDate = dateTuple.third }
                            .padding(horizontal = 16.dp, vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = dateTuple.first, // Day name (Today, Tomorrow, etc)
                                style = MaterialTheme.typography.labelMedium,
                                color = if (isSelected) TextPrimary else TextSecondary
                            )
                            Text(
                                text = dateTuple.second, // Day number
                                style = MaterialTheme.typography.titleMedium,
                                color = if (isSelected) TextPrimary else TextSecondary,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Leagues Section
            Text(
                text = "Tournaments & Leagues",
                style = MaterialTheme.typography.titleMedium,
                color = TextPrimary,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, fill = false),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = allTrackedLeagues,
                    key = { it.first }
                ) { (id, name) ->
                    val isSelected = localFilters.selectedLeagueIds.contains(id)
                    val hasMatches = leaguesWithMatchesToday.contains(id)
                    
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isSelected) AppAccent.copy(alpha = 0.2f) else AppCard)
                            .clickable { 
                                val newLeagues = if (isSelected) {
                                    localFilters.selectedLeagueIds - id
                                } else {
                                    localFilters.selectedLeagueIds + id
                                }
                                localFilters = localFilters.copy(selectedLeagueIds = newLeagues)
                            }
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = TrackedLeagues.logoUrl(id),
                            contentDescription = name,
                            modifier = Modifier
                                .size(24.dp)
                                .padding(end = 12.dp)
                        )
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = name,
                                style = MaterialTheme.typography.bodyLarge,
                                color = if (isSelected) AppAccent else TextPrimary
                            )
                        }
                        
                        if (isSelected) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Selected",
                                tint = AppAccent,
                                modifier = Modifier.size(24.dp)
                            )
                        } else {
                            Spacer(modifier = Modifier.size(24.dp))
                        }
                    }
                }
                
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}
