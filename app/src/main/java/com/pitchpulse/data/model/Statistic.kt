package com.pitchpulse.data.model

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class MatchStatistics(
    val homeStats: List<StatItem>,
    val awayStats: List<StatItem>
)

@Immutable
@Serializable
data class StatItem(
    val type: String,
    val value: String
)
