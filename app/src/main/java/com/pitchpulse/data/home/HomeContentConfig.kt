package com.pitchpulse.data.home

object HomeContentConfig {
    const val DAILY_QUIZ_COUNT = 10
    const val CACHE_VERSION = 2

    val BANNED_QUESTION_PHRASES = listOf(
        "how many players",
        "players on the pitch",
        "players on the field",
        "players are on a team",
        "how long is a match",
        "how many minutes",
        "what colour is",
        "what color is",
        "shape of the ball",
        "round ball"
    )

    fun difficultyLabel(level: Int): String = when (level) {
        in 1..2 -> "Level $level · Starter"
        in 3..4 -> "Level $level · Tricky"
        in 5..6 -> "Level $level · Sharp"
        in 7..8 -> "Level $level · Expert"
        9 -> "Level 9 · Brutal"
        else -> "Level 10 · Legend"
    }
}
