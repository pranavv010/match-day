package com.pitchpulse.data.repository

import android.util.Log
import com.pitchpulse.core.network.GeminiClient
import com.pitchpulse.data.home.HomeContentConfig
import com.pitchpulse.data.home.HomeFallbackContent
import com.pitchpulse.data.local.dao.FootballDao
import com.pitchpulse.data.local.entity.HomeDailyContentEntity
import com.pitchpulse.data.model.FootballQuote
import com.pitchpulse.data.model.LeagueTodaySummary
import com.pitchpulse.data.model.Match
import com.pitchpulse.data.model.TrackedLeagues
import com.pitchpulse.data.model.QuizQuestion
import com.pitchpulse.data.remote.GeminiApi
import com.pitchpulse.data.remote.dto.GeminiContent
import com.pitchpulse.data.remote.dto.GeminiGenerateRequest
import com.pitchpulse.data.remote.dto.GeminiGenerationConfig
import com.pitchpulse.data.remote.dto.GeminiPart
import com.pitchpulse.data.remote.dto.HomeAiPayload
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val TAG = "HomeContentRepository"

class HomeContentRepository(
    private val dao: FootballDao,
    private val geminiApi: GeminiApi = GeminiClient.api,
    private val geminiApiKey: String = GeminiClient.apiKey()
) {
    private val json = Json { ignoreUnknownKeys = true }

    fun observeLeagueSummariesToday(): Flow<List<LeagueTodaySummary>> {
        val today = todayString()
        return dao.getDailyMatchesFlow(today).map { entities ->
            val matches = entities.map { it.toDomainModel() }
            buildLeagueSummaries(matches)
        }
    }

    /**
     * Returns today's home content. Uses Room cache for the current date so quiz, quote,
     * and fact stay stable for the day and refresh automatically after midnight.
     */
    suspend fun getTodayHomeContent(forceRefresh: Boolean = false): AiHomeBundle = withContext(Dispatchers.IO) {
        val today = todayString()
        if (!forceRefresh) {
            loadCachedBundle(today)?.let { return@withContext it }
        }

        val bundle = if (geminiApiKey.isBlank()) {
            Log.w(TAG, "GEMINI_API_KEY missing — using daily rotated fallback content")
            fallbackBundleForDate(today)
        } else {
            fetchFromGemini(today) ?: fallbackBundleForDate(today)
        }

        cacheBundle(today, bundle)
        bundle
    }

    private suspend fun loadCachedBundle(date: String): AiHomeBundle? {
        val entity = dao.getHomeDailyContent(date) ?: return null
        return runCatching {
            val payload = json.decodeFromString<CachedHomePayload>(entity.contentJson)
            when {
                payload.cacheVersion != HomeContentConfig.CACHE_VERSION ||
                    payload.quizzes.size < HomeContentConfig.DAILY_QUIZ_COUNT -> {
                    Log.d(TAG, "Stale home cache for $date — will refresh")
                    null
                }
                else -> payload.toBundle()
            }
        }.onFailure {
            Log.w(TAG, "Failed to read cached home content for $date: ${it.message}")
        }.getOrNull()
    }

    private suspend fun cacheBundle(date: String, bundle: AiHomeBundle) {
        val payload = CachedHomePayload.fromBundle(bundle)
        dao.insertHomeDailyContent(
            HomeDailyContentEntity(
                dateString = date,
                contentJson = json.encodeToString(payload)
            )
        )
        dao.clearOldHomeDailyContent(date)
    }

    private suspend fun fetchFromGemini(today: String): AiHomeBundle? {
        try {
            val prompt = """
                You are an elite football trivia author. Today is $today.
                Respond with JSON only, no markdown:
                {
                  "quizzes": [
                    {"question": "string", "options": ["A","B","C","D"], "correctIndex": 0, "difficulty": 1}
                  ],
                  "quote": {"text": "string", "author": "string"},
                  "fact": "string"
                }
                Rules:
                - Provide exactly ${HomeContentConfig.DAILY_QUIZ_COUNT} quiz questions.
                - difficulty must be 1 through 10 (one question per level). Level 1 = knowledgeable fan, level 10 = obscure expert trivia.
                - Each question must be longer and more specific than the last; difficulty must strictly increase.
                - 4 plausible options each; correctIndex 0-3.
                - BANNED: basic rules (player counts, pitch size, match length, ball shape, obvious definitions).
                - Focus on: historic matches, records, transfers, managers, tournament lore, tactical milestones.
                - One fresh inspirational football quote with attributed author.
                - One surprising expert-level fact (max 160 chars).
                Content must be unique for $today.
            """.trimIndent()

            val response = geminiApi.generateContent(
                apiKey = geminiApiKey,
                request = GeminiGenerateRequest(
                    contents = listOf(GeminiContent(parts = listOf(GeminiPart(prompt)))),
                    generationConfig = GeminiGenerationConfig()
                )
            )

            val rawText = response.candidates
                ?.firstOrNull()
                ?.content
                ?.parts
                ?.firstOrNull()
                ?.text
                ?: return null

            return parseGeminiPayload(rawText, today)
        } catch (e: Exception) {
            Log.e(TAG, "Gemini fetch failed: ${e.message}", e)
            return null
        }
    }

    private fun parseGeminiPayload(rawText: String, today: String): AiHomeBundle? {
        val payload = json.decodeFromString<HomeAiPayload>(rawText.trim())
        val quizzes = payload.quizzes
            .mapNotNull { dto -> dto.toQuizOrNull() }
            .filterNot { isBannedQuestion(it.question) }
            .sortedBy { it.difficulty }
            .let { HomeFallbackContent.padQuizzesToDailySet(it, today) }

        if (quizzes.size < HomeContentConfig.DAILY_QUIZ_COUNT) return null

        val quote = payload.quote?.let { FootballQuote(it.text, it.author) } ?: return null
        val fact = payload.fact?.takeIf { it.isNotBlank() } ?: return null

        return AiHomeBundle(
            quizzes = quizzes,
            quote = quote,
            fact = fact,
            fromNetwork = true
        )
    }

    private fun com.pitchpulse.data.remote.dto.QuizQuestionDto.toQuizOrNull(): QuizQuestion? {
        if (options.size < 4 || question.isBlank()) return null
        if (isBannedQuestion(question)) return null
        val idx = correctIndex.coerceIn(0, options.lastIndex)
        val level = difficulty.coerceIn(1, HomeContentConfig.DAILY_QUIZ_COUNT)
        return QuizQuestion(question.trim(), options.take(4), idx, level)
    }

    private fun isBannedQuestion(question: String): Boolean {
        val lower = question.lowercase(Locale.US)
        return HomeContentConfig.BANNED_QUESTION_PHRASES.any { lower.contains(it) }
    }

    private suspend fun fallbackBundleForDate(date: String): AiHomeBundle {
        val usedQuestions = buildUsedQuestionSet()
        val fallback = HomeFallbackContent.bundleForDate(date, usedQuestions)
        return AiHomeBundle(
            quizzes = fallback.quizzes,
            quote = fallback.quote,
            fact = fallback.fact,
            fromNetwork = false
        )
    }

    private suspend fun buildUsedQuestionSet(): Set<String> {
        val recent = dao.getRecentHomeContent(limit = 365)
        val questions = mutableSetOf<String>()
        for (entry in recent) {
            try {
                val payload = json.decodeFromString<CachedHomePayload>(entry.contentJson)
                questions.addAll(payload.quizzes.map { it.question })
            } catch (_: Exception) { }
        }
        return questions
    }

    /**
     * Builds today's league list from synced fixtures: any tracked major league or
     * international tournament with at least one match today, sorted by activity.
     */
    fun buildLeagueSummaries(matches: List<Match>): List<LeagueTodaySummary> {
        val trackedToday = matches.filter { match ->
            when {
                match.leagueId != 0 -> TrackedLeagues.isTracked(match.leagueId)
                else -> match.isFavoriteLeague
            }
        }

        val grouped = trackedToday.groupBy { match ->
            if (match.leagueId != 0) match.leagueId
            else match.competition.lowercase(Locale.US).hashCode()
        }

        return grouped.map { (key, group) ->
            val sample = group.first()
            val leagueId = sample.leagueId.takeIf { it != 0 }
                ?: TrackedLeagues.catalog.find {
                    sample.competition.equals(it.name, ignoreCase = true)
                }?.id
                ?: key
            val name = TrackedLeagues.nameForId(leagueId) ?: sample.competition
            val logo = sample.leagueLogo?.takeIf { it.isNotBlank() }
                ?: TrackedLeagues.logoUrl(leagueId)
            LeagueTodaySummary(
                leagueId = leagueId,
                name = name,
                logoUrl = logo,
                matchCount = group.size
            )
        }
            .sortedWith(
                compareByDescending<LeagueTodaySummary> { it.matchCount }
                    .thenBy { it.name }
            )
    }

    private fun todayString(): String =
        SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date())
}

@Serializable
private data class CachedHomePayload(
    val cacheVersion: Int = HomeContentConfig.CACHE_VERSION,
    val quizzes: List<CachedQuiz>,
    val quoteText: String,
    val quoteAuthor: String,
    val fact: String,
    val fromNetwork: Boolean
) {
    fun toBundle() = AiHomeBundle(
        quizzes = quizzes.map {
            QuizQuestion(it.question, it.options, it.correctIndex, it.difficulty)
        },
        quote = FootballQuote(quoteText, quoteAuthor),
        fact = fact,
        fromNetwork = fromNetwork
    )

    companion object {
        fun fromBundle(bundle: AiHomeBundle) = CachedHomePayload(
            cacheVersion = HomeContentConfig.CACHE_VERSION,
            quizzes = bundle.quizzes.map {
                CachedQuiz(it.question, it.options, it.correctIndex, it.difficulty)
            },
            quoteText = bundle.quote.text,
            quoteAuthor = bundle.quote.author,
            fact = bundle.fact,
            fromNetwork = bundle.fromNetwork
        )
    }
}

@Serializable
private data class CachedQuiz(
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val difficulty: Int = 1
)

data class AiHomeBundle(
    val quizzes: List<QuizQuestion>,
    val quote: FootballQuote,
    val fact: String,
    val fromNetwork: Boolean
)
