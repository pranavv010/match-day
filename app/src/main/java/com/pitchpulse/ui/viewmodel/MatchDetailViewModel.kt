package com.pitchpulse.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.pitchpulse.core.network.RetrofitClient
import com.pitchpulse.data.local.LiveScoresDatabase
import com.pitchpulse.data.repository.FootballRepository
import com.pitchpulse.ui.state.MatchDetailUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

class MatchDetailViewModel(
    application: Application,
    savedStateHandle: SavedStateHandle
) : AndroidViewModel(application) {

    private val db = LiveScoresDatabase.getInstance(application)
    private val repository = FootballRepository(
        api = RetrofitClient.api,
        dao = db.dao,
        favoriteTeamDao = db.favoriteTeamDao
    )

    private val fixtureId: Int = checkNotNull(savedStateHandle["fixtureId"])

    private val _uiState = MutableStateFlow<MatchDetailUiState>(MatchDetailUiState.Loading)
    val uiState: StateFlow<MatchDetailUiState> = _uiState.asStateFlow()

    private var lineupsLoaded = false
    private var lastStatsRefreshTime = 0L
    private var lastEventsSyncTime = 0L
    private var pollingJob: Job? = null

    init {
        loadMatchDetails()
    }

    private fun loadMatchDetails() {
        viewModelScope.launch {
            repository.getMatchDetails(fixtureId)
                .filterNotNull()
                .collect { match ->
                    val currentState = _uiState.value
                    if (currentState is MatchDetailUiState.Success) {
                        _uiState.value = currentState.copy(match = match)
                    } else {
                        _uiState.value = MatchDetailUiState.Success(
                            match = match,
                            lineups = emptyList(),
                            stats = null
                        )
                    }

                    // --- One-time fetches (lineups + events) ---
                    // Lineups: fetch exactly once per session. They never change during a match.
                    if (!lineupsLoaded) {
                        launch {
                            lineupsLoaded = true
                            val lineups = repository.getLineups(fixtureId)
                            val updatedState = _uiState.value
                            if (updatedState is MatchDetailUiState.Success) {
                                _uiState.value = updatedState.copy(lineups = lineups)
                            }
                        }
                    }

                    // Events: fetch once if missing (goal scorers for overview tab)
                    if (match.events.isEmpty() && currentState !is MatchDetailUiState.Success) {
                        launch {
                            repository.syncFixtureDetails(fixtureId)
                        }
                    }

                    // --- Stats: throttled refresh for live matches ---
                    if (match.isLive) {
                        val now = System.currentTimeMillis()
                        if (now - lastStatsRefreshTime > STATS_REFRESH_MILLIS) {
                            lastStatsRefreshTime = now
                            launch {
                                val stats = repository.getStatistics(fixtureId)
                                val updatedState = _uiState.value
                                if (updatedState is MatchDetailUiState.Success) {
                                    _uiState.value = updatedState.copy(stats = stats)
                                }
                            }
                        }
                    } else if (currentState !is MatchDetailUiState.Success || currentState.stats == null) {
                        // Finished match: fetch stats once (will be cached)
                        launch {
                            val stats = repository.getStatistics(fixtureId)
                            val updatedState = _uiState.value
                            if (updatedState is MatchDetailUiState.Success) {
                                _uiState.value = updatedState.copy(stats = stats)
                            }
                        }
                    }

                    // Start/stop polling based on live status
                    if (match.isLive && pollingJob == null) {
                        startLivePolling()
                    } else if (!match.isLive) {
                        pollingJob?.cancel()
                        pollingJob = null
                    }
                }
        }
    }

    private fun startLivePolling() {
        pollingJob = viewModelScope.launch {
            while (true) {
                kotlinx.coroutines.delay(POLL_INTERVAL_MILLIS)
                val state = _uiState.value
                if (state !is MatchDetailUiState.Success || !state.match.isLive) break

                val now = System.currentTimeMillis()

                // Sync fixture details (goal scorers) — throttled via repository (30s)
                if (now - lastEventsSyncTime > EVENTS_SYNC_MILLIS) {
                    lastEventsSyncTime = now
                    repository.syncFixtureDetails(fixtureId)
                }

                // Stats refresh — throttled via repository (5 min)
                if (now - lastStatsRefreshTime > STATS_REFRESH_MILLIS) {
                    lastStatsRefreshTime = now
                    val stats = repository.getStatistics(fixtureId)
                    val updatedState = _uiState.value
                    if (updatedState is MatchDetailUiState.Success) {
                        _uiState.value = updatedState.copy(stats = stats)
                    }
                }
            }
        }
    }

    companion object {
        private const val POLL_INTERVAL_MILLIS = 120_000L  // Poll every 2 minutes (not 1)
        private const val STATS_REFRESH_MILLIS = 5 * 60 * 1000L   // Stats: every 5 minutes
        private const val EVENTS_SYNC_MILLIS = 2 * 60 * 1000L     // Events sync: every 2 minutes
    }
}
