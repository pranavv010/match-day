package com.pitchpulse.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.viewModelScope
import com.pitchpulse.core.util.DateObserver
import com.pitchpulse.data.home.HomeContentConfig
import com.pitchpulse.data.local.LiveScoresDatabase
import com.pitchpulse.data.model.FootballQuote
import com.pitchpulse.data.model.LeagueTodaySummary
import com.pitchpulse.data.model.QuizQuestion
import com.pitchpulse.data.repository.HomeContentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val TAG = "HomeExtrasViewModel"

data class HomeExtrasUiState(
    val leagueSummaries: List<LeagueTodaySummary> = emptyList(),
    val currentQuiz: QuizQuestion? = null,
    val quizIndex: Int = 0,
    val quizPool: List<QuizQuestion> = emptyList(),
    val selectedOptionIndex: Int? = null,
    val quizCompleted: Boolean = false,
    val quote: FootballQuote? = null,
    val fact: String? = null,
    val isLoadingContent: Boolean = true,
    val contentError: String? = null,
    val contentDate: String? = null
) {
    val totalQuizQuestions: Int get() = quizPool.size.coerceAtLeast(HomeContentConfig.DAILY_QUIZ_COUNT)
    val currentQuestionNumber: Int get() = (quizIndex + 1).coerceAtMost(totalQuizQuestions)
}

class HomeExtrasViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = HomeContentRepository(
        dao = LiveScoresDatabase.getInstance(application).dao
    )

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    private val _uiState = MutableStateFlow(HomeExtrasUiState())
    val uiState: StateFlow<HomeExtrasUiState> = _uiState.asStateFlow()

    val leagueSummaries: StateFlow<List<LeagueTodaySummary>> =
        repository.observeLeagueSummariesToday()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    private val lifecycleObserver = LifecycleEventObserver { _, event ->
        if (event == Lifecycle.Event.ON_START) {
            val today = dateFormat.format(Date())
            val state = _uiState.value
            if (state.contentDate != null && state.contentDate != today) {
                loadTodayContent(forceRefresh = true)
            }
        }
    }

    init {
        loadTodayContent()
        observeMidnightRefresh()
        ProcessLifecycleOwner.get().lifecycle.addObserver(lifecycleObserver)
        viewModelScope.launch {
            leagueSummaries.collect { leagues ->
                _uiState.value = _uiState.value.copy(leagueSummaries = leagues)
            }
        }
    }

    override fun onCleared() {
        ProcessLifecycleOwner.get().lifecycle.removeObserver(lifecycleObserver)
        super.onCleared()
    }

    private fun observeMidnightRefresh() {
        viewModelScope.launch {
            DateObserver.dateFlow().collect { newDate ->
                val current = _uiState.value.contentDate
                if (current != null && current != newDate) {
                    Log.d(TAG, "Date changed $current → $newDate, refreshing home content")
                    loadTodayContent(forceRefresh = true)
                }
            }
        }
    }

    fun loadTodayContent(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            val today = dateFormat.format(Date())
            if (!forceRefresh &&
                _uiState.value.contentDate == today &&
                _uiState.value.quote != null &&
                _uiState.value.quizPool.size >= HomeContentConfig.DAILY_QUIZ_COUNT
            ) {
                return@launch
            }

            _uiState.value = _uiState.value.copy(isLoadingContent = true, contentError = null)
            try {
                val bundle = repository.getTodayHomeContent(forceRefresh)
                val pool = bundle.quizzes
                    .sortedBy { it.difficulty }
                    .take(HomeContentConfig.DAILY_QUIZ_COUNT)
                _uiState.value = _uiState.value.copy(
                    quizPool = pool,
                    quizIndex = 0,
                    currentQuiz = pool.firstOrNull(),
                    selectedOptionIndex = null,
                    quizCompleted = false,
                    quote = bundle.quote,
                    fact = bundle.fact,
                    contentDate = today,
                    isLoadingContent = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoadingContent = false,
                    contentError = e.message ?: "Could not load home content"
                )
            }
        }
    }

    fun onQuizOptionSelected(index: Int) {
        val state = _uiState.value
        if (state.selectedOptionIndex != null || state.quizCompleted) return
        _uiState.value = state.copy(selectedOptionIndex = index)
    }

    fun nextQuizQuestion() {
        val state = _uiState.value
        val pool = state.quizPool
        if (pool.isEmpty() || state.selectedOptionIndex == null) return

        val isLast = state.quizIndex >= pool.lastIndex
        if (isLast) {
            _uiState.value = state.copy(quizCompleted = true)
            return
        }

        val nextIndex = state.quizIndex + 1
        _uiState.value = state.copy(
            quizIndex = nextIndex,
            currentQuiz = pool[nextIndex],
            selectedOptionIndex = null
        )
    }

    fun restartQuiz() {
        val state = _uiState.value
        val pool = state.quizPool
        if (pool.isEmpty()) return
        _uiState.value = state.copy(
            quizIndex = 0,
            currentQuiz = pool.first(),
            selectedOptionIndex = null,
            quizCompleted = false
        )
    }
}
