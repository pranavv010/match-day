package com.pitchpulse.data.model

data class LeagueTodaySummary(
    val leagueId: Int,
    val name: String,
    val logoUrl: String?,
    val matchCount: Int
)

data class QuizQuestion(
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val difficulty: Int = 1
)

data class FootballQuote(
    val text: String,
    val author: String
)
