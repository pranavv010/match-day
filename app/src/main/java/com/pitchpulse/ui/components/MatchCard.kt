package com.pitchpulse.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.pitchpulse.data.model.EventType
import com.pitchpulse.data.model.Match
import com.pitchpulse.data.model.MatchEvent
import com.pitchpulse.ui.theme.*

@Composable
fun MatchCard(
    match: Match,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    // Cache derived data to avoid recomputation
    val homeScorers = remember(match.events, match.homeTeamId) {
        match.events.filter { it.type == EventType.GOAL && it.teamId == match.homeTeamId }.take(2)
    }
    val awayScorers = remember(match.events, match.awayTeamId) {
        match.events.filter { it.type == EventType.GOAL && it.teamId == match.awayTeamId }.take(2)
    }
    val hasGoals = remember(match.events) { homeScorers.isNotEmpty() || awayScorers.isNotEmpty() }
    val competitionText = remember(match.competition) { match.competition.uppercase() }
    val scoreText = remember(match.homeScore, match.awayScore) {
        if (match.homeScore != null && match.awayScore != null) "${match.homeScore} - ${match.awayScore}" else "VS"
    }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick),
        color = AppCard
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // League Name
            Text(
                text = competitionText,
                style = MaterialTheme.typography.labelSmall,
                color = TextSecondary,
                fontWeight = FontWeight.Medium
            )

            // Teams + Score Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Home Team
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = match.homeTeamLogo,
                        contentDescription = null,
                        modifier = Modifier.size(32.dp),
                        contentScale = ContentScale.Fit
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = match.homeTeam,
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextPrimary,
                        textAlign = TextAlign.Center,
                        maxLines = 1
                    )
                }

                // Score
                Text(
                    text = scoreText,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    color = TextPrimary,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )

                // Away Team
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = match.awayTeamLogo,
                        contentDescription = null,
                        modifier = Modifier.size(32.dp),
                        contentScale = ContentScale.Fit
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = match.awayTeam,
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextPrimary,
                        textAlign = TextAlign.Center,
                        maxLines = 1
                    )
                }
            }

            // Goal Scorers
            if (hasGoals) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.Start) {
                        homeScorers.forEach { event ->
                            Text(
                                text = buildScorerText(event),
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                                color = TextSecondary,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.End) {
                        awayScorers.forEach { event ->
                            Text(
                                text = buildScorerText(event, away = true),
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                                color = TextSecondary,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.End
                            )
                        }
                    }
                }
            }

            // Status
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = if (match.isLive) "LIVE" else match.time,
                    style = MaterialTheme.typography.labelSmall,
                    color = if (match.isLive) AppAccent else TextMuted,
                    fontWeight = FontWeight.Bold
                )

                if (match.date.isNotEmpty()) {
                    Text(
                        text = " • ${match.date}",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextMuted
                    )
                }

                if (match.isLive) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = match.time,
                        style = MaterialTheme.typography.labelSmall,
                        color = TextMuted
                    )
                }
            }
        }
    }
}

private fun buildScorerText(event: MatchEvent, away: Boolean = false): String {
    val base = if (away) "${event.minute}' ${event.player}" else "${event.player} ${event.minute}'"
    return if (event.assist != null) "$base (a. ${event.assist})" else base
}
