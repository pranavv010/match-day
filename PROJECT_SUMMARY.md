# Live Scores - Project Summary

## Overview
Production-grade Android app for elite football (soccer) fans, focusing on top European leagues and major international tournaments. Built with Jetpack Compose, MVVM architecture, Room database, and Retrofit networking.

- **Root project name:** Live Scores
- **App package:** `com.pitchpulse`
- **App name (user-facing):** Live Scores
- **Min SDK:** 24 | **Target SDK:** 36 | **Compile SDK:** 36
- **Version:** 1.1 (versionCode 2)
- **Firebase project:** pitchpulse-native-app-101

## Tech Stack & Key Versions
- **Kotlin:** 2.2.10 | **AGP:** 9.1.0 | **Gradle:** 9.3.1
- **UI:** Jetpack Compose (BOM 2024.12.01) + Material 3 (1.3.1)
- **Navigation:** Navigation Compose (2.9.7)
- **Networking:** Retrofit 3.0.0 + OkHttp 4.12.0
- **Serialization:** Kotlinx Serialization (1.10.0)
- **Local DB:** Room (2.8.0) with KSP 2.2.10-2.0.2
- **Image Loading:** Coil 3.4.0 (Coil3)
- **Background Work:** WorkManager (2.10.0)
- **Firebase:** BOM 33.10.0 (Messaging)
- **Desugaring:** desugar_jdk_libs 2.1.5
- **No DI framework** (manual dependency injection, scalable to Hilt)

## Architecture: MVVM + Clean Data Layer

### Package Structure (`com.pitchpulse`)
```
com.pitchpulse/
├── MainActivity.kt                   # Entry point, NavHost, bottom nav
├── core/
│   ├── network/
│   │   ├── RetrofitClient.kt         # API-Sports Retrofit + multi-key rotation
│   │   └── GeminiClient.kt           # Gemini AI Retrofit setup
│   ├── ui/
│   │   └── Dimens.kt                 # Design system spacing/sizing constants
│   └── util/
│       └── DateObserver.kt           # Midnight detection flow
├── data/
│   ├── model/
│   │   ├── Match.kt                  # Core Match + MatchEvent + EventType models
│   │   ├── TeamDetails.kt            # Team details (venue, founded, etc.)
│   │   ├── Lineup.kt                 # Lineup + LineupPlayer models
│   │   ├── Statistic.kt              # MatchStatistics + StatItem
│   │   ├── HomeContent.kt            # LeagueTodaySummary, QuizQuestion, FootballQuote
│   │   ├── TrackedLeagues.kt         # Whitelisted leagues catalog (39 IDs)
│   │   └── SampleData.kt             # Preview/sample match data
│   ├── remote/
│   │   ├── FootballApi.kt            # API-Sports Retrofit interface
│   │   ├── GeminiApi.kt              # Gemini AI Retrofit interface
│   │   └── dto/
│   │       ├── FootballDtos.kt       # All API-Sports response DTOs + mappers
│   │       └── GeminiDtos.kt         # Gemini request/response DTOs + HomeAiPayload
│   ├── local/
│   │   └── LiveScoresDatabase.kt     # Room DB
│   │   ├── Converters.kt             # Room TypeConverters (JSON serialization)
│   │   ├── dao/
│   │   │   ├── FootballDao.kt        # Main DAO (matches, metadata, teams, content)
│   │   │   └── FavoriteTeamDao.kt    # Favorites CRUD
│   │   └── entity/
│   │       ├── FootballEntities.kt    # MatchEntity, FavoriteTeamEntity, FetchMetadataEntity, LineupEntity, StatisticEntity, ApiUsageEntity
│   │       ├── TeamDetailsEntity.kt   # Cached team info
│   │       ├── SuggestedTeamEntity.kt # Daily suggested teams
│   │       └── HomeDailyContentEntity.kt # Cached quiz/quote/fact JSON
│   └── repository/
│       ├── FootballRepository.kt     # Main business logic + API guards
│       └── HomeContentRepository.kt  # Home screen extras (quiz, quote, fact)
├── ui/
│   ├── state/
│   │   └── MatchUiState.kt           # MatchUiState + MatchDetailUiState sealed classes
│   ├── viewmodel/
│   │   ├── MatchViewModel.kt         # Main match list + favorites + sync
│   │   ├── MatchDetailViewModel.kt   # Match detail with lineups/stats
│   │   ├── TeamDetailViewModel.kt    # Team info + fixtures
│   │   ├── SearchViewModel.kt        # Team search with debounce
│   │   └── HomeExtrasViewModel.kt    # Quiz, quote, fact + leagues today
│   ├── screens/
│   │   ├── HomeScreen.kt             # Home: leagues, quiz, quote, fact, teams
│   │   ├── MatchesScreen.kt          # Match list with date ribbon
│   │   ├── SearchScreen.kt           # Team search + suggestions
│   │   ├── MatchDetailScreen.kt      # Detail: overview, lineups, stats tabs
│   │   ├── TeamDetailScreen.kt       # Team header + upcoming/results tabs
│   │   ├── SettingsScreen.kt         # Privacy policy, terms links
│   │   └── DocumentScreen.kt         # Markdown renderer for legal docs
│   ├── components/
│   │   ├── BottomNav.kt              # 3-tab navigation (Home, Matches, Search)
│   │   ├── MatchCard.kt              # Match card with press animation
│   │   ├── MatchRow.kt               # Compact match row with favorite toggles
│   │   ├── LiveBadge.kt              # Animated pulsing LIVE badge
│   │   ├── DateRibbon.kt             # Horizontal scrollable date picker
│   │   ├── AppCard.kt                # Reusable card wrapper
│   │   ├── EmptyState.kt             # Generic empty state component
│   │   └── HomeExtrasComponents.kt   # League card, quiz card, quote/fact cards
│   └── theme/
│       ├── Color.kt                  # Dark theme palette (green accents)
│       ├── Theme.kt                  # Material 3 dark color scheme
│       └── Type.kt                   # Inter font via Google Fonts
└── service/
    └── LiveScoresMessagingService.kt # FCM service (declared in manifest)
└── sync/
    └── SyncWorker.kt                 # Background sync (WorkManager, 1hr interval)
```

## Screens & Navigation
Routes defined in `MainActivity.kt`:
- `home_screen` → HomeScreen (leagues today, quiz, quote, fact, my teams)
- `matches_screen` → MatchesScreen (match list with date ribbon, -7 to +7 days)
- `search_screen` → SearchScreen (team search + suggested teams)
- `match_detail_screen/{fixtureId}` → MatchDetailScreen (3 tabs: Overview, Lineups, Stats)
- `team_detail_screen/{teamId}` → TeamDetailScreen (2 tabs: Upcoming, Recent Results)
- `settings` → SettingsScreen (privacy policy, terms)
- `document/{fileName}` → DocumentScreen (renders markdown from assets)

Bottom navigation appears only on the 3 main tabs (Home, Matches, Search).

## Data Flow
1. **ViewModel** observes `StateFlow` from Room via `FootballDao`
2. **MatchViewModel.startSyncObserver()** triggers `syncIfNeeded()` when date changes
3. **FootballRepository** checks 5-level API guard: past-date freeze, date mutex, live throttle (2min), failure cooldown (5min), stale threshold (1hr)
4. API calls use **RetrofitClient** which auto-rotates between up to 3 API keys on quota exhaustion
5. All data written to Room → Flow auto-updates UI

## API Guard System (in FootballRepository)
- **Past Date Freeze:** Past dates w/ data get re-fetched only if missing goal scorers
- **Date Mutex:** Per-date lock prevents concurrent API calls for same date
- **Live Throttle:** Live matches refresh every 2 minutes
- **Failure Cooldown:** 5min cooldown after failed fetch
- **Stale Threshold:** 1-hour cache freshness for non-live data
- **Per-key call tracking:** 95 calls max per key per day, auto-rotates to next key
- **Favorite sync throttle:** 15min throttle on favorite team fixture sync

## API Keys (from local.properties, not committed)
- `API_KEY_PRIMARY`, `API_KEY_BACKUP_1`, `API_KEY_BACKUP_2` → API-Sports Football API
- `GEMINI_API_KEY` → Google Gemini AI (optional; fallback content used if missing)

## Key Features
1. **Real-time scores** from top 5 European leagues + UEFA competitions
2. **IST timezone** (Asia/Kolkata, 12hr format) for Indian viewers
3. **Multi-key rotation** for 24/7 API uptime
4. **Smart caching** with Room as single source of truth
5. **Live badges** with alpha-pulsing animation
6. **Goal scorer tracking** on match cards
7. **Football quiz** (10 daily questions via Gemini AI or fallback)
8. **Daily football quote & fact** (Gemini AI or fallback)
9. **Team search** with debounced API calls
10. **Favorite teams** with upcoming match feed on home screen
11. **Background sync** via WorkManager (hourly)
12. **FCM notifications** for goals and match starts
13. **Pull-to-refresh** on team detail screen
14. **Assist tracking** — assists parsed from API and shown in match cards, rows, and detail timeline
15. **Event timeline** — fixed 72dp row height with visible connecting lines between badges

## Tracked Leagues (in TrackedLeagues.kt)
Top 5 European leagues + domestic cups + UEFA club competitions + international tournaments. 39 league IDs total. Only matches from these leagues are stored/displayed.

## Gradle Plugins
- `com.android.application` (9.1.0)
- `org.jetbrains.kotlin.plugin.compose` (2.2.10)
- `org.jetbrains.kotlin.plugin.serialization` (2.2.10)
- `com.google.devtools.ksp` (2.2.10-2.0.2)
- `com.google.gms.google-services` (4.4.2)

## Theme / Design
- **Dark mode only** (no light theme), solid surfaces, no transparency/blur/glassmorphism
- **Colors:** AppBackground #0B0F14, AppAccent #4CAF50 (plain green), TextPrimary #FFFFFF
- **Typography:** Google Fonts "Inter" (weights: Normal, Medium, SemiBold, Bold)

## Key Config Files
- `PRD.md` — Product Requirements Document
- `README.md` — Setup instructions
- `.firebaserc` — Firebase project: pitchpulse-native-app-101
- `app/proguard-rules.pro` — Keep rules for Retrofit, kotlinx.serialization, data models, OkHttp

## Known Issues / Side Notes
- Tests are placeholder (ExampleUnitTest, ExampleInstrumentedTest)
- Uses manual DI (no Hilt/Dagger/Koin)
- Both `@Serializable` annotation and Room converters are used for serialization
- ViewModels use `AndroidViewModel` (have access to Application context)
