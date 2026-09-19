package com.pitchpulse.data.model

/**
 * Major club leagues and international tournaments the app tracks.
 * Home "Leagues Today" is built from today's fixtures in these competitions.
 */
object TrackedLeagues {
    data class Entry(
        val id: Int,
        val name: String,
        val isInternational: Boolean = false
    )

    val catalog: List<Entry> = listOf(
        // UEFA club
        Entry(2, "UEFA Champions League"),
        Entry(3, "UEFA Europa League"),
        // Top 5 Leagues
        Entry(39, "Premier League"),
        Entry(140, "La Liga"),
        Entry(135, "Serie A"),
        Entry(78, "Bundesliga"),
        Entry(61, "Ligue 1"),
        // Other UEFA
        Entry(848, "UEFA Conference League"),
        // Domestic Cups
        Entry(45, "FA Cup"),
        Entry(48, "League Cup"),
        Entry(46, "Community Shield"),
        Entry(143, "Copa del Rey"),
        Entry(141, "Supercopa"),
        Entry(137, "Coppa Italia"),
        Entry(549, "Supercoppa"),
        Entry(81, "DFB Pokal"),
        Entry(529, "DFL-Supercup"),
        Entry(66, "Coupe de France"),
        Entry(65, "Trophée des Champions"),
        // International
        Entry(1, "FIFA World Cup", isInternational = true),
        Entry(4, "UEFA Euro", isInternational = true),
        Entry(5, "UEFA Nations League", isInternational = true),
        Entry(33, "World Cup Qualifiers", isInternational = true),
        Entry(13, "CONCACAF Nations League", isInternational = true),
        Entry(22, "CONCACAF Gold Cup", isInternational = true),
        Entry(31, "CONCACAF Qualifiers", isInternational = true),
        Entry(9, "Copa America", isInternational = true),
        Entry(30, "CONMEBOL Qualifiers", isInternational = true),
        Entry(10, "International Friendlies", isInternational = true)
    )

    val allIds: Set<Int> = catalog.map { it.id }.toSet()

    fun isTracked(leagueId: Int): Boolean = leagueId in allIds

    fun logoUrl(leagueId: Int): String =
        "https://media.api-sports.io/football/leagues/$leagueId.png"

    fun nameForId(leagueId: Int): String? =
        catalog.find { it.id == leagueId }?.name
}
