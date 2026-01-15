# KMP Coffee Shop App - Architecture Overview

## Project Structure

```
kmp-coffee-shop-app/
├── android/                          # Android application module
│   ├── src/main/
│   │   ├── kotlin/coffeeshop/app/
│   │   │   ├── MainActivity.kt      # App entry point
│   │   │   └── ui/
│   │   │       ├── screen/
│   │   │       │   └── HomeScreen.kt        # Home screen composables
│   │   │       └── theme/
│   │   │           ├── Color.kt             # Color palette
│   │   │           ├── Type.kt              # Typography
│   │   │           └── Theme.kt             # Material theme setup
│   │   └── AndroidManifest.xml
│   └── build.gradle.kts
│
├── shared/                           # Kotlin Multiplatform shared module
│   ├── src/
│   │   ├── commonMain/kotlin/coffeeshop/shared/
│   │   │   ├── data/
│   │   │   │   ├── model/
│   │   │   │   │   ├── Banner.kt            # Banner data model
│   │   │   │   │   ├── FeaturedDrink.kt     # Drink data model
│   │   │   │   │   └── User.kt              # User data model
│   │   │   │   └── repository/
│   │   │   │       ├── CoffeeRepository.kt  # Repository interface
│   │   │   │       └── MockCoffeeRepository.kt  # Mock implementation
│   │   │   ├── presentation/
│   │   │   │   └── HomeScreenPresenter.kt   # Presentation logic
│   │   │   └── utils/
│   │   │       └── GreetingUtils.kt         # Greeting utilities (expect)
│   │   ├── androidMain/kotlin/coffeeshop/shared/
│   │   │   └── utils/
│   │   │       └── GreetingUtils.kt         # Android-specific impl (actual)
│   │   └── iosMain/kotlin/coffeeshop/shared/
│   │       └── utils/
│   │           └── GreetingUtils.kt         # iOS-specific impl (actual)
│   └── build.gradle.kts
│
├── iosApp/                           # iOS application
│   └── iosApp/
│       ├── CoffeeShopApp.swift      # App entry point
│       ├── HomeScreenView.swift     # Home screen SwiftUI views
│       └── Theme.swift              # Color theme
│
├── build.gradle.kts                  # Root build configuration
├── settings.gradle.kts               # Project settings
├── gradle.properties                 # Gradle properties
├── HOME_SCREEN_IMPLEMENTATION.md     # Implementation guide
└── HOME_SCREEN_UI_DESIGN.md         # UI design documentation
```

## Architecture Layers

### 1. Data Layer (Shared)
```
┌─────────────────────────────────────────┐
│          Data Models                    │
│  ┌─────────┐  ┌────────┐  ┌──────────┐ │
│  │  User   │  │ Banner │  │ Featured │ │
│  │         │  │        │  │  Drink   │ │
│  └─────────┘  └────────┘  └──────────┘ │
└─────────────────────────────────────────┘
                  ▲
                  │
┌─────────────────────────────────────────┐
│       Repository Interface              │
│  ┌────────────────────────────────────┐ │
│  │   CoffeeRepository                 │ │
│  │   - getCurrentUser()               │ │
│  │   - getBanners()                   │ │
│  │   - getFeaturedDrinks()            │ │
│  └────────────────────────────────────┘ │
└─────────────────────────────────────────┘
                  ▲
                  │
┌─────────────────────────────────────────┐
│    Repository Implementation            │
│  ┌────────────────────────────────────┐ │
│  │   MockCoffeeRepository             │ │
│  │   - Provides sample data           │ │
│  │   - 3 banners                      │ │
│  │   - 6 featured drinks              │ │
│  └────────────────────────────────────┘ │
└─────────────────────────────────────────┘
```

### 2. Presentation Layer (Shared)
```
┌─────────────────────────────────────────┐
│      HomeScreenPresenter                │
│  ┌────────────────────────────────────┐ │
│  │  Business Logic                    │ │
│  │  - getGreeting()                   │ │
│  │  - getBanners()                    │ │
│  │  - getFeaturedDrinks()             │ │
│  │                                    │ │
│  │  Uses: CoffeeRepository            │ │
│  │  Uses: GreetingUtils               │ │
│  └────────────────────────────────────┘ │
└─────────────────────────────────────────┘
```

### 3. Platform-Specific Utilities (Expect/Actual)
```
┌─────────────────────────────────────────┐
│      Common (expect)                    │
│  ┌────────────────────────────────────┐ │
│  │  expect fun getCurrentHour(): Int  │ │
│  └────────────────────────────────────┘ │
└─────────────────────────────────────────┘
          ▲                    ▲
          │                    │
┌─────────────────┐  ┌─────────────────────┐
│ Android (actual)│  │  iOS (actual)       │
│ ┌─────────────┐ │  │ ┌─────────────────┐ │
│ │ Uses Java   │ │  │ │ Uses Foundation │ │
│ │ Calendar    │ │  │ │ NSCalendar      │ │
│ └─────────────┘ │  │ └─────────────────┘ │
└─────────────────┘  └─────────────────────┘
```

### 4. UI Layer - Android (Jetpack Compose)
```
┌─────────────────────────────────────────┐
│          MainActivity                   │
│  ┌────────────────────────────────────┐ │
│  │  setContent {                      │ │
│  │    CoffeeShopTheme {               │ │
│  │      HomeScreen()                  │ │
│  │    }                               │ │
│  │  }                                 │ │
│  └────────────────────────────────────┘ │
└─────────────────────────────────────────┘
                  │
                  ▼
┌─────────────────────────────────────────┐
│          HomeScreen                     │
│  ┌────────────────────────────────────┐ │
│  │  @Composable                       │ │
│  │  LazyColumn {                      │ │
│  │    GreetingSection()               │ │
│  │    BannerCarousel()                │ │
│  │    FeaturedDrinksSection()         │ │
│  │  }                                 │ │
│  └────────────────────────────────────┘ │
└─────────────────────────────────────────┘
                  │
                  ▼
┌─────────────────────────────────────────┐
│       UI Components                     │
│  ┌──────────────┐  ┌──────────────────┐ │
│  │ Greeting     │  │ BannerCard       │ │
│  │ Section      │  │                  │ │
│  └──────────────┘  └──────────────────┘ │
│  ┌──────────────┐                       │
│  │ DrinkCard    │                       │
│  └──────────────┘                       │
└─────────────────────────────────────────┘
```

### 5. UI Layer - iOS (SwiftUI)
```
┌─────────────────────────────────────────┐
│       CoffeeShopApp                     │
│  ┌────────────────────────────────────┐ │
│  │  @main                             │ │
│  │  WindowGroup {                     │ │
│  │    HomeScreenView()                │ │
│  │  }                                 │ │
│  └────────────────────────────────────┘ │
└─────────────────────────────────────────┘
                  │
                  ▼
┌─────────────────────────────────────────┐
│       HomeViewModel                     │
│  ┌────────────────────────────────────┐ │
│  │  @StateObject                      │ │
│  │  ObservableObject                  │ │
│  │  @Published greeting               │ │
│  │  @Published banners                │ │
│  │  @Published featuredDrinks         │ │
│  │                                    │ │
│  │  Uses: HomeScreenPresenter         │ │
│  └────────────────────────────────────┘ │
└─────────────────────────────────────────┘
                  │
                  ▼
┌─────────────────────────────────────────┐
│       HomeScreenView                    │
│  ┌────────────────────────────────────┐ │
│  │  ScrollView {                      │ │
│  │    VStack {                        │ │
│  │      GreetingSection()             │ │
│  │      BannerCarousel()              │ │
│  │      FeaturedDrinksSection()       │ │
│  │    }                               │ │
│  │  }                                 │ │
│  └────────────────────────────────────┘ │
└─────────────────────────────────────────┘
                  │
                  ▼
┌─────────────────────────────────────────┐
│       UI Components                     │
│  ┌──────────────┐  ┌──────────────────┐ │
│  │ Greeting     │  │ BannerCard       │ │
│  │ Section      │  │                  │ │
│  └──────────────┘  └──────────────────┘ │
│  ┌──────────────┐                       │
│  │ DrinkCard    │                       │
│  └──────────────┘                       │
└─────────────────────────────────────────┘
```

## Data Flow

### 1. App Initialization
```
┌─────────────┐
│   Start     │
│   App       │
└──────┬──────┘
       │
       ▼
┌─────────────────────┐
│  Platform UI Entry  │
│  (MainActivity /    │
│   CoffeeShopApp)    │
└──────┬──────────────┘
       │
       ▼
┌─────────────────────┐
│   HomeScreen /      │
│   HomeScreenView    │
└──────┬──────────────┘
       │
       ▼
┌─────────────────────┐
│  Create Presenter   │
│  with Repository    │
└──────┬──────────────┘
       │
       ▼
┌─────────────────────┐
│  Load Data          │
│  - User             │
│  - Banners          │
│  - Featured Drinks  │
└─────────────────────┘
```

### 2. Greeting Generation
```
┌─────────────────────┐
│  Presenter          │
│  getGreeting()      │
└──────┬──────────────┘
       │
       ▼
┌─────────────────────┐
│  Repository         │
│  getCurrentUser()   │
└──────┬──────────────┘
       │
       ▼
┌─────────────────────┐
│  GreetingUtils      │
│  getCurrentHour()   │ ──┐
└─────────────────────┘   │
                          │
    ┌─────────────────────┘
    │
    ▼
┌─────────────────────┐    ┌─────────────────────┐
│  Android            │    │  iOS                │
│  Calendar.getInstance│   │  NSCalendar         │
│  .get(HOUR_OF_DAY)  │    │  .components(...)   │
└─────────────────────┘    └─────────────────────┘
    │                          │
    └────────┬─────────────────┘
             │
             ▼
    ┌─────────────────────┐
    │  getGreetingForTime │
    │  - Morning (5-11)   │
    │  - Afternoon (12-16)│
    │  - Evening (17-21)  │
    └──────┬──────────────┘
           │
           ▼
    ┌─────────────────────┐
    │  "Good Morning,     │
    │   Coffee Lover!"    │
    └─────────────────────┘
```

### 3. Data Rendering
```
┌────────────────────────────┐
│  HomeScreenPresenter       │
└────┬───────────────────────┘
     │
     ├─── getGreeting()      ────► GreetingSection
     │
     ├─── getBanners()       ────► BannerCarousel
     │                              └─► BannerCard (×3)
     │
     └─── getFeaturedDrinks()────► FeaturedDrinksSection
                                    └─► DrinkCard (×6)
```

## Design Patterns

### 1. Repository Pattern
- **Interface**: `CoffeeRepository`
- **Implementation**: `MockCoffeeRepository`
- **Benefits**: 
  - Abstraction of data source
  - Easy to swap implementations
  - Testable

### 2. Presenter Pattern
- **Class**: `HomeScreenPresenter`
- **Responsibilities**:
  - Business logic
  - Data transformation
  - No platform-specific code
- **Benefits**:
  - Shared across platforms
  - Testable
  - Single source of truth

### 3. Expect/Actual Pattern
- **Common**: `expect fun getCurrentHour(): Int`
- **Android**: `actual fun getCurrentHour()` using Java Calendar
- **iOS**: `actual fun getCurrentHour()` using Foundation NSCalendar
- **Benefits**:
  - Platform-specific implementations
  - Common interface
  - Type-safe

### 4. MVVM (iOS)
- **Model**: Shared data models
- **View**: SwiftUI views
- **ViewModel**: `HomeViewModel` (ObservableObject)
- **Benefits**:
  - SwiftUI-friendly
  - Reactive updates
  - Testable

### 5. Composable UI (Android)
- **Components**: Reusable @Composable functions
- **State**: remember, mutableStateOf
- **Benefits**:
  - Declarative UI
  - Reusable components
  - Less boilerplate

## Key Technologies

### Kotlin Multiplatform
- **Version**: 1.8.0
- **Targets**: Android, iOS (arm64, x64, simulatorArm64)
- **Shared Code**: ~80% business logic

### Android
- **Jetpack Compose**: 1.4.0
- **Material Design**: Material 1.4.0
- **Min SDK**: 21 (Android 5.0)
- **Target SDK**: 33 (Android 13)

### iOS
- **SwiftUI**: Native framework
- **Min iOS**: 14.0+
- **Framework**: Dynamic framework

### Build System
- **Gradle**: 7.2+
- **Android Gradle Plugin**: 7.4.2
- **Kotlin Gradle Plugin**: 1.8.0

## Testing Strategy

### Unit Tests (Future)
- Test `HomeScreenPresenter` logic
- Test `GreetingUtils` functions
- Test `MockCoffeeRepository` data

### UI Tests (Future)
- Android: Compose UI tests
- iOS: XCTest UI tests

### Integration Tests (Future)
- End-to-end user flows
- Platform-specific behavior

## Future Enhancements

1. **Networking Layer**
   - Add Ktor for HTTP calls
   - Real API integration
   - Response caching

2. **State Management**
   - Add Kotlin Coroutines
   - Add Flow for reactive streams
   - Proper error handling

3. **Dependency Injection**
   - Add Koin or Kodein
   - Proper DI setup

4. **Navigation**
   - Android: Navigation Compose
   - iOS: NavigationView/NavigationStack
   - Shared routing logic

5. **Images**
   - Add Coil (Android) / Kingfisher (iOS)
   - Image loading and caching
   - Real drink images

6. **Persistence**
   - Add SQLDelight
   - Local data caching
   - Offline support
