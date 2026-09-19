package com.pitchpulse.data.model

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class Match(
    val id: Int,
    val homeTeam: String,
    val homeTeamId: Int,
    val homeTeamLogo: String?,
    val awayTeam: String,
    val awayTeamId: Int,
    val awayTeamLogo: String?,
    val homeScore: Int? = null,
    val awayScore: Int? = null,
    val time: String,
    val date: String = "",
    val isLive: Boolean = false,
    val competition: String = "",
    val leagueId: Int = 0,
    val leagueLogo: String? = null,
    val isFavoriteLeague: Boolean = false,
    val events: List<MatchEvent> = emptyList()
)

@Immutable
@Serializable
data class MatchEvent(
    val player: String,
    val minute: Int,
    val type: EventType,
    val teamId: Int? = null,
    val assist: String? = null
)

@Immutable
@Serializable
enum class EventType {
    GOAL, RED_CARD, YELLOW_CARD
}
