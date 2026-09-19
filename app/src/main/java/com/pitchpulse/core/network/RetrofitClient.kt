package com.pitchpulse.core.network

import retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.pitchpulse.BuildConfig
import com.pitchpulse.data.remote.FootballApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private const val BASE_URL = "https://v3.football.api-sports.io/"

    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    // ── API Key Pool ────────────────────────────────────────────────────────────

    private val API_KEYS = listOf(
        BuildConfig.API_KEY_PRIMARY,
        BuildConfig.API_KEY_BACKUP_1,
        BuildConfig.API_KEY_BACKUP_2
    ).filter { it.isNotEmpty() }

    @Volatile
    private var currentKeyIndex = 0

    fun getActiveKey(): String =
        if (API_KEYS.isNotEmpty()) API_KEYS[currentKeyIndex] else "MISSING_API_KEY"

    fun getActiveKeyIndex(): Int = currentKeyIndex

    /**
     * Advances to the next available key.
     * Thread-safe. Returns true if rotation succeeded, false if all keys are exhausted.
     */
    fun rotateKey(): Boolean = synchronized(this) {
        if (API_KEYS.isEmpty()) return false
        return if (currentKeyIndex < API_KEYS.size - 1) {
            currentKeyIndex++
            android.util.Log.w(
                "RetrofitClient",
                "🔑 API KEY ROTATED → index $currentKeyIndex (****${API_KEYS[currentKeyIndex].takeLast(6)})"
            )
            true
        } else {
            android.util.Log.e("RetrofitClient", "🚫 ALL ${API_KEYS.size} API KEYS EXHAUSTED")
            false
        }
    }

    // ── Quota / Error Detection ─────────────────────────────────────────────────

    /**
     * Detects API-Sports quota exhaustion from an OkHttp response using two signals:
     *
     * 1. Header:  x-ratelimit-requests-remaining == "0"
     * 2. Body:    HTTP 200 with JSON errors object containing "requests" quota message
     *             e.g. {"errors":{"requests":"You have reached the request limit..."}}
     *
     * Uses peekBody so the body is NOT consumed and Retrofit can still parse it.
     */
    private fun isQuotaExhausted(response: okhttp3.Response): Boolean {
        // Primary: check the rate-limit header (fast, no body read needed)
        val remaining = response.header("x-ratelimit-requests-remaining")
        if (remaining == "0") {
            android.util.Log.w("RetrofitClient", "⚠️ x-ratelimit-requests-remaining = 0")
            return true
        }

        // Secondary: peek the body for API-Sports JSON quota errors
        return try {
            val body = response.peekBody(1024 * 16).string()
            // API-Sports quota error: {"errors":{"requests":"You have reached the request limit..."}}
            // Normal success:         {"errors":[], ...}   ← errors is an array, not object
            val hasErrorsObject = body.contains("\"errors\":{")  // object, not array []
            val hasQuotaMsg = body.contains("request limit", ignoreCase = true) ||
                body.contains("rate limit", ignoreCase = true) ||
                body.contains("\"requests\"", ignoreCase = true)
            (hasErrorsObject && hasQuotaMsg).also {
                if (it) android.util.Log.w("RetrofitClient", "⚠️ Quota error detected in body")
            }
        } catch (e: Exception) {
            false
        }
    }

    // ── Interceptors ────────────────────────────────────────────────────────────

    /**
     * Injects the active API key and auto-rotates on any quota signal.
     * Retries with the next key when HTTP 429 or a JSON quota-error body is detected.
     */
    private val authInterceptor = Interceptor { chain ->
        var attempts = 0
        var response: okhttp3.Response

        do {
            val request = chain.request().newBuilder()
                .header("x-apisports-key", getActiveKey())
                .build()

            android.util.Log.d(
                "RetrofitClient",
                "→ [key $currentKeyIndex] ${request.url}"
            )

            response = chain.proceed(request)
            attempts++

            val isQuotaError = response.code == 429 || isQuotaExhausted(response)
            val canRotate = synchronized(this) { currentKeyIndex < API_KEYS.size - 1 }

            if (isQuotaError && canRotate) {
                response.close()
                rotateKey()
            } else {
                break
            }
        } while (attempts < API_KEYS.size)

        response
    }

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC
    }

    // ── OkHttpClient ────────────────────────────────────────────────────────────

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)    // auth + key rotation (innermost)
        .addInterceptor(loggingInterceptor) // logging (outermost)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    // ── Retrofit Instance ───────────────────────────────────────────────────────

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    // ── Public API Service ──────────────────────────────────────────────────────

    val api: FootballApi = retrofit.create(FootballApi::class.java)
}
