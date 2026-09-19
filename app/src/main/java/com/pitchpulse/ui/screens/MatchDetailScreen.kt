package com.pitchpulse.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.pitchpulse.core.ui.Dimens
import com.pitchpulse.ui.components.EmptyState
import com.pitchpulse.ui.state.MatchDetailUiState
import com.pitchpulse.ui.theme.AppAccent
import com.pitchpulse.ui.theme.AppAccentMuted
import com.pitchpulse.ui.theme.AppBackground
import com.pitchpulse.ui.theme.AppCard
import com.pitchpulse.ui.theme.TextPrimary
import com.pitchpulse.ui.theme.TextSecondary
import com.pitchpulse.ui.theme.TextMuted
import com.pitchpulse.ui.viewmodel.MatchDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchDetailScreen(
    viewModel: MatchDetailViewModel,
    onBack: () -> Unit,
    onTeamClick: (Int) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Overview", "Lineups", "Stats")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Match Center") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppBackground,
                    titleContentColor = TextPrimary,
                    navigationIconContentColor = TextPrimary
                )
            )
        },
        containerColor = AppBackground
    ) { innerPadding ->
        when (val state = uiState) {
            is MatchDetailUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = AppAccent)
                }
            }
            is MatchDetailUiState.Success -> {
                // Everything in one scrollable LazyColumn — scoreboard and tabs scroll away
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentPadding = PaddingValues(bottom = Dimens.SpacingExtraLarge)
                ) {
                    // 1. Compact Scoreboard
                    item {
                        CompactScoreboard(match = state.match, onTeamClick = onTeamClick)
                    }

                    // 2. Tabs — scroll up with content
                    item {
                        TabRow(
                            selectedTabIndex = selectedTab,
                            containerColor = AppBackground,
                            contentColor = AppAccent,
                            indicator = { tabPositions ->
                                TabRowDefaults.SecondaryIndicator(
                                    modifier = Modifier
                                        .tabIndicatorOffset(tabPositions[selectedTab])
                                        .height(3.dp)
                                        .clip(RoundedCornerShape(topStart = 3.dp, topEnd = 3.dp)),
                                    color = AppAccent
                                )
                            },
                            divider = {
                                HorizontalDivider(color = Color(0xFF252E38))
                            }
                        ) {
                            tabs.forEachIndexed { index, title ->
                                Tab(
                                    selected = selectedTab == index,
                                    onClick = { selectedTab = index },
                                    text = {
                                        Text(
                                            text = title,
                                            fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal,
                                            color = if (selectedTab == index) AppAccent else TextSecondary
                                        )
                                    }
                                )
                            }
                        }
                    }

                    // 3. Tab Content
                    when (selectedTab) {
                        0 -> overviewItems(state.match, onTeamClick)
                        1 -> lineupItems(state.lineups, state.match.homeTeam, state.match.awayTeam)
                        2 -> statsItems(state.stats)
                    }
                }
            }
            is MatchDetailUiState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = state.message, color = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}

// ── Compact Scoreboard ────────────────────────────────────────────────────

@Composable
private fun CompactScoreboard(
    match: com.pitchpulse.data.model.Match,
    onTeamClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.SpacingLarge, vertical = Dimens.SpacingMedium),
        shape = RoundedCornerShape(Dimens.RadiusLarge),
        colors = CardDefaults.cardColors(containerColor = AppCard),
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = if (match.isLive) AppAccent else Color(0xFF252E38)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.SpacingLarge, vertical = Dimens.SpacingMedium),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Competition & Date — single compact line
            Text(
                text = buildString {
                    append(match.competition.uppercase())
                    if (match.date.isNotEmpty()) append("  •  ${match.date}")
                },
                style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 1.sp),
                color = if (match.isLive) AppAccent else TextSecondary,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(Dimens.SpacingMedium))

            // Teams + Score — compact row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Home
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onTeamClick(match.homeTeamId) },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = match.homeTeamLogo,
                        contentDescription = match.homeTeam,
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = match.homeTeam,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary,
                        maxLines = 1,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }

                // Score
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = Dimens.SpacingMedium)
                ) {
                    if (match.homeScore != null && match.awayScore != null) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = match.homeScore.toString(),
                                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold),
                                color = TextPrimary
                            )
                            Text(
                                text = " - ",
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    color = AppAccent
                                )
                            )
                            Text(
                                text = match.awayScore.toString(),
                                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold),
                                color = TextPrimary
                            )
                        }
                    } else {
                        Text(
                            text = "VS",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 1.sp
                            ),
                            color = TextSecondary
                        )
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    // Status badge
                    if (match.isLive) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .background(Color(0xFF1B3A2C), RoundedCornerShape(100.dp))
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Box(modifier = Modifier.size(5.dp).background(AppAccent, CircleShape))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = match.time,
                                color = AppAccent,
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                    } else {
                        Text(
                            text = match.time,
                            color = TextMuted,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }

                // Away
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onTeamClick(match.awayTeamId) },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = match.awayTeamLogo,
                        contentDescription = match.awayTeam,
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = match.awayTeam,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary,
                        maxLines = 1,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
        }
    }
}

// ── Overview Tab Items ────────────────────────────────────────────────────

private fun LazyListScope.overviewItems(
    match: com.pitchpulse.data.model.Match,
    onTeamClick: (Int) -> Unit
) {
    val events = match.events.sortedBy { it.minute }
    if (events.isEmpty()) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 48.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.BarChart, null, tint = TextMuted, modifier = Modifier.size(48.dp))
                    Spacer(modifier = Modifier.height(Dimens.SpacingMedium))
                    Text("No key events recorded yet", style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
                }
            }
        }
        return
    }

    item {
        Text(
            text = "MATCH TIMELINE",
            style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 1.2.sp),
            color = AppAccent,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = Dimens.SpacingLarge, top = Dimens.SpacingLarge, bottom = Dimens.SpacingSmall)
        )
    }

    items(events.size) { index ->
        val event = events[index]
        val isHome = event.teamId == match.homeTeamId

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.SpacingLarge)
                .height(56.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left (Home)
            Box(
                modifier = Modifier.weight(1f).padding(end = Dimens.SpacingSmall),
                contentAlignment = Alignment.CenterEnd
            ) {
                if (isHome) {
                    Column(horizontalAlignment = Alignment.End) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End) {
                            Text(event.player, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, color = TextPrimary, maxLines = 1)
                            Spacer(modifier = Modifier.width(4.dp))
                            EventIcon(event.type)
                        }
                        if (event.assist != null) {
                            Text("a. ${event.assist}", style = MaterialTheme.typography.labelSmall, color = TextSecondary, maxLines = 1)
                        }
                    }
                }
            }

            // Center (minute badge + connector)
            Column(
                modifier = Modifier.width(44.dp).fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier.weight(1f).width(2.dp)
                        .background(if (index == 0) Color.Transparent else Color(0xFF252E38))
                )
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .background(MaterialTheme.colorScheme.surfaceVariant, CircleShape)
                        .border(
                            1.5.dp,
                            if (event.type == com.pitchpulse.data.model.EventType.GOAL) AppAccent else Color(0xFF252E38),
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "${event.minute}'",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (event.type == com.pitchpulse.data.model.EventType.GOAL) AppAccent else TextSecondary
                    )
                }
                Box(
                    modifier = Modifier.weight(1f).width(2.dp)
                        .background(if (index == events.lastIndex) Color.Transparent else Color(0xFF252E38))
                )
            }

            // Right (Away)
            Box(
                modifier = Modifier.weight(1f).padding(start = Dimens.SpacingSmall),
                contentAlignment = Alignment.CenterStart
            ) {
                if (!isHome) {
                    Column(horizontalAlignment = Alignment.Start) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            EventIcon(event.type)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(event.player, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, color = TextPrimary, maxLines = 1)
                        }
                        if (event.assist != null) {
                            Text("a. ${event.assist}", style = MaterialTheme.typography.labelSmall, color = TextSecondary, maxLines = 1)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun EventIcon(type: com.pitchpulse.data.model.EventType) {
    when (type) {
        com.pitchpulse.data.model.EventType.GOAL -> Text("⚽", fontSize = 12.sp)
        com.pitchpulse.data.model.EventType.YELLOW_CARD -> {
            Box(modifier = Modifier.size(width = 8.dp, height = 11.dp).background(Color(0xFFFFD54F), RoundedCornerShape(2.dp)))
        }
        com.pitchpulse.data.model.EventType.RED_CARD -> {
            Box(modifier = Modifier.size(width = 8.dp, height = 11.dp).background(Color(0xFFE53935), RoundedCornerShape(2.dp)))
        }
    }
}

// ── Lineups Tab Items ─────────────────────────────────────────────────────

private fun LazyListScope.lineupItems(
    lineups: List<com.pitchpulse.data.model.Lineup>,
    homeTeamName: String,
    awayTeamName: String
) {
    if (lineups.isEmpty()) {
        item {
            EmptyState(
                title = "Lineups Not Available",
                subtitle = "Team formations and starting lineups will appear here.",
                icon = Icons.Default.Groups
            )
        }
        return
    }

    items(lineups.size) { index ->
        TeamLineupCard(lineup = lineups[index], isHome = index == 0)
    }
}

@Composable
private fun TeamLineupCard(
    lineup: com.pitchpulse.data.model.Lineup,
    isHome: Boolean,
    modifier: Modifier = Modifier
) {
    val badgeColor = if (isHome) AppAccent else Color.White

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.SpacingLarge, vertical = Dimens.SpacingSmall),
        shape = RoundedCornerShape(Dimens.RadiusMedium),
        colors = CardDefaults.cardColors(containerColor = AppCard),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF252E38))
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(Dimens.SpacingLarge)) {
            // Team header
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                AsyncImage(
                    model = lineup.teamLogo,
                    contentDescription = lineup.teamName,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(Dimens.SpacingMedium))
                Column {
                    Text(lineup.teamName, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = TextPrimary)
                    if (lineup.formation != null) {
                        Text(lineup.formation, style = MaterialTheme.typography.labelSmall, color = AppAccent, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(Dimens.SpacingMedium))

            // Starting XI
            Text("STARTING XI", style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 1.sp), color = TextSecondary, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(Dimens.SpacingSmall))
            lineup.startXI.forEach { PlayerRow(player = it, badgeColor = badgeColor) }

            // Substitutes
            if (lineup.substitutes.isNotEmpty()) {
                Spacer(modifier = Modifier.height(Dimens.SpacingMedium))
                Text("SUBSTITUTES", style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 1.sp), color = TextSecondary, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(Dimens.SpacingSmall))
                lineup.substitutes.forEach { PlayerRow(player = it, badgeColor = if (isHome) AppAccentMuted else Color(0xFFB0BEC5)) }
            }

            // Coach
            if (!lineup.coachName.isNullOrEmpty()) {
                Spacer(modifier = Modifier.height(Dimens.SpacingMedium))
                HorizontalDivider(color = Color(0xFF252E38))
                Spacer(modifier = Modifier.height(Dimens.SpacingSmall))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Groups, "Coach", tint = AppAccent, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(Dimens.SpacingSmall))
                    Text(lineup.coachName, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Medium, color = TextPrimary)
                }
            }
        }
    }
}

@Composable
private fun PlayerRow(
    player: com.pitchpulse.data.model.LineupPlayer,
    badgeColor: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.size(22.dp).background(badgeColor, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(player.number?.toString() ?: "-", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            }
            Spacer(modifier = Modifier.width(Dimens.SpacingSmall))
            Text(player.name, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold, color = TextPrimary)
        }
        Text(player.position ?: "", style = MaterialTheme.typography.labelSmall, color = TextSecondary)
    }
}

// ── Stats Tab Items ───────────────────────────────────────────────────────

private fun LazyListScope.statsItems(stats: com.pitchpulse.data.model.MatchStatistics?) {
    if (stats == null || stats.homeStats.isEmpty()) {
        item {
            EmptyState(
                title = "Stats Not Available",
                subtitle = "Detailed match statistics will appear here once the match begins.",
                icon = Icons.Default.BarChart
            )
        }
        return
    }

    val priorityStats = listOf("Ball Possession", "Total Shots", "Fouls")
    val featuredStats = stats.homeStats.zip(stats.awayStats).filter { it.first.type in priorityStats }
    val remainingStats = stats.homeStats.zip(stats.awayStats).filter { it.first.type !in priorityStats }

    if (featuredStats.isNotEmpty()) {
        item {
            Text(
                "MATCH ANALYTICS",
                style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 1.2.sp),
                color = AppAccent,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = Dimens.SpacingLarge, top = Dimens.SpacingLarge, bottom = Dimens.SpacingSmall)
            )
        }
        items(featuredStats) { (home, away) ->
            StatRow(type = home.type, homeValue = home.value, awayValue = away.value, isFeatured = true)
        }
    }

    if (remainingStats.isNotEmpty()) {
        item {
            Text(
                "GENERAL STATS",
                style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 1.2.sp),
                color = TextSecondary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = Dimens.SpacingLarge, top = Dimens.SpacingLarge, bottom = Dimens.SpacingSmall)
            )
        }
        items(remainingStats) { (home, away) ->
            StatRow(type = home.type, homeValue = home.value, awayValue = away.value, isFeatured = false)
        }
    }
}

@Composable
private fun StatRow(type: String, homeValue: String, awayValue: String, isFeatured: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.SpacingLarge, vertical = Dimens.SpacingSmall)
            .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(Dimens.RadiusSmall))
            .padding(horizontal = Dimens.SpacingLarge, vertical = Dimens.SpacingMedium)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                homeValue,
                style = if (isFeatured) MaterialTheme.typography.titleSmall else MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
                color = if (isFeatured) AppAccent else TextPrimary
            )
            Text(
                type.uppercase(),
                style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 1.sp),
                color = TextSecondary,
                fontWeight = FontWeight.Bold
            )
            Text(
                awayValue,
                style = if (isFeatured) MaterialTheme.typography.titleSmall else MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
                color = if (isFeatured) Color.White else TextPrimary
            )
        }

        Spacer(modifier = Modifier.height(Dimens.SpacingSmall))

        val homeVal = homeValue.replace("%", "").toFloatOrNull() ?: 0f
        val awayVal = awayValue.replace("%", "").toFloatOrNull() ?: 0f
        val total = homeVal + awayVal
        val homeRatio = if (total == 0f) 0.5f else homeVal / total
        val awayRatio = if (total == 0f) 0.5f else awayVal / total

        Box(
            modifier = Modifier.fillMaxWidth().height(6.dp).clip(CircleShape).background(Color(0xFF252E38))
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val w = size.width
                val h = size.height
                drawRect(AppAccent, Offset(0f, 0f), Size(w * homeRatio, h))
                drawRect(Color.White, Offset(w - w * awayRatio, 0f), Size(w * awayRatio, h))
                drawLine(AppBackground, Offset(w / 2, 0f), Offset(w / 2, h), 2.dp.toPx())
            }
        }
    }
}
