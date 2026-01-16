# KMP Coffee Shop App

A Kotlin Multiplatform (KMP) Coffee Shop application with native UI on both Android and iOS.

## Features

- **Home Screen**: Main entry point with navigation to all features
- **Drink Builder**: Customize your coffee with various options
- **Loyalty Rewards**: Track points and redeem rewards
- **QR Scanner**: Scan codes for payments and loyalty points
- **Payment System**: Manage payment methods and view transactions

## Architecture

- **Shared Module**: Common business logic and UI components using Compose Multiplatform
- **Android App**: Native Android app using Jetpack Compose Navigation
- **iOS App**: Native iOS app using SwiftUI NavigationStack

## Navigation

The app uses platform-specific navigation:
- **Android**: Jetpack Compose Navigation with NavHost
- **iOS**: SwiftUI NavigationStack

Navigation routes are defined in a shared sealed class for type safety.

## Build Instructions

### Android
```bash
./gradlew :composeApp:assembleDebug
```

### iOS
Open `iosApp/iosApp.xcodeproj` in Xcode and build the project.

## Requirements

- Kotlin 1.9.21
- Android Studio Hedgehog or later
- Xcode 15 or later (for iOS development)
- JDK 17