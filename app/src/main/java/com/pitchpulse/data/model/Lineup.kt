package com.pitchpulse.data.model

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class Lineup(
    val teamName: String,
    val teamLogo: String?,
    val formation: String?,
    val startXI: List<LineupPlayer>,
    val substitutes: List<LineupPlayer>,
    val coachName: String?
)

@Immutable
@Serializable
data class LineupPlayer(
    val id: Int,
    val name: String,
    val number: Int?,
    val position: String?,
    val grid: String? = null
)
