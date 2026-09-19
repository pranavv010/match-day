package com.pitchpulse.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.pitchpulse.core.ui.Dimens
import com.pitchpulse.data.model.EventType
import com.pitchpulse.data.model.Match
import com.pitchpulse.data.model.MatchEvent
import com.pitchpulse.ui.theme.AppAccent
import com.pitchpulse.ui.theme.TextPrimary
import com.pitchpulse.ui.theme.TextSecondary

@Composable
fun MatchRow(
    match: Match,
    favoriteTeamIds: Set<Int>,
    onFavoriteToggle: (Int, String, String?) -> Unit,
    modifier: Modifier = Modifier,
    onMatchClick: (Int) -> Unit = {},
    onTeamClick: (Int) -> Unit = {}
) {
    val context = LocalContext.current

    // Cache image requests
    val homeLogoRequest = remember(match.homeTeamLogo) {
        ImageRequest.Builder(context).data(match.homeTeamLogo).crossfade(true).build()
    }
    val awayLogoRequest = remember(match.awayTeamLogo) {
        ImageRequest.Builder(context).data(match.awayTeamLogo).crossfade(true).build()
    }

    // Cache derived data
    val homeScorers = remember(match.events, match.homeTeamId) {
        match.events.filter { it.type == EventType.GOAL && it.teamId == match.homeTeamId }.take(2)
    }
    val awayScorers = remember(match.events, match.awayTeamId) {
        match.events.filter { it.type == EventType.GOAL && it.teamId == match.awayTeamId }.take(2)
    }
    val hasGoals = remember(match.events) { homeScorers.isNotEmpty() || awayScorers.isNotEmpty() }
    val dateShort = remember(match.date) { if (match.date.isNotEmpty()) match.date.substringAfter("-") else "" }
    val isHomeFav = remember(match.homeTeamId, favoriteTeamIds) { match.homeTeamId in favoriteTeamIds }
    val isAwayFav = remember(match.awayTeamId, favoriteTeamIds) { match.awayTeamId in favoriteTeamIds }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onMatchClick(match.id) }
            .padding(vertical = Dimens.SpacingMedium)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.SpacingLarge),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Time + Date
            Column(
                modifier = Modifier.width(50.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = match.time,
                    style = MaterialTheme.typography.labelSmall,
                    color = if (match.isLive) AppAccent else TextSecondary,
                    fontWeight = FontWeight.Bold
                )
                if (dateShort.isNotEmpty()) {
                    Text(
                        text = dateShort,
                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 8.sp),
                        color = TextSecondary.copy(alpha = 0.6f)
                    )
                }
            }

            Spacer(modifier = Modifier.width(Dimens.SpacingMedium))

            // Home
            Row(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onTeamClick(match.homeTeamId) },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(
                    onClick = { onFavoriteToggle(match.homeTeamId, match.homeTeam, match.homeTeamLogo) },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = if (isHomeFav) Icons.Filled.Star else Icons.Outlined.StarOutline,
                        contentDescription = "Favorite ${match.homeTeam}",
                        tint = if (isHomeFav) AppAccent else TextSecondary.copy(alpha = 0.4f),
                        modifier = Modifier.size(16.dp)
                    )
                }
                Text(
                    text = match.homeTeam,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextPrimary,
                    maxLines = 1,
                    textAlign = TextAlign.End,
                    modifier = Modifier.weight(1f),
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.width(Dimens.SpacingSmall))
                AsyncImage(
                    model = homeLogoRequest,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    contentScale = ContentScale.Fit
                )
            }

            // Score
            Text(
                text = if (match.homeScore != null && match.awayScore != null) "${match.homeScore} - ${match.awayScore}" else "vs",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                ),
                color = TextPrimary,
                modifier = Modifier.padding(horizontal = Dimens.SpacingMedium),
                textAlign = TextAlign.Center
            )

            // Away
            Row(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onTeamClick(match.awayTeamId) },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                AsyncImage(
                    model = awayLogoRequest,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.width(Dimens.SpacingSmall))
                Text(
                    text = match.awayTeam,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextPrimary,
                    maxLines = 1,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.weight(1f),
                    overflow = TextOverflow.Ellipsis
                )
                IconButton(
                    onClick = { onFavoriteToggle(match.awayTeamId, match.awayTeam, match.awayTeamLogo) },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = if (isAwayFav) Icons.Filled.Star else Icons.Outlined.StarOutline,
                        contentDescription = "Favorite ${match.awayTeam}",
                        tint = if (isAwayFav) AppAccent else TextSecondary.copy(alpha = 0.4f),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }

        // Goal Scorers
        if (hasGoals) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 64.dp, end = 24.dp, top = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.Start) {
                    homeScorers.forEach { event ->
                        Text(
                            text = scorerText(event),
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                            color = TextSecondary.copy(alpha = 0.7f),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                Spacer(modifier = Modifier.width(Dimens.SpacingMedium))

                Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.End) {
                    awayScorers.forEach { event ->
                        Text(
                            text = scorerText(event, away = true),
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                            color = TextSecondary.copy(alpha = 0.7f),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.End
                        )
                    }
                }
            }
        }
    }
}

private fun scorerText(event: MatchEvent, away: Boolean = false): String {
    val base = if (away) "${event.minute}' ${event.player}" else "${event.player} ${event.minute}'"
    return if (event.assist != null) "$base (a. ${event.assist})" else base
}
