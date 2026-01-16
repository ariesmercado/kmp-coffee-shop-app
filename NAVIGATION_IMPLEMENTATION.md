# Navigation Implementation Guide

## Overview

This document describes the navigation implementation for the Coffee Shop Android app using Jetpack Compose Navigation.

## Architecture

### Navigation Package

The navigation logic is organized in the `coffeeshop.app.navigation` package:

- **Navigation.kt**: Contains navigation routes and NavHost configuration

### Navigation Routes

Navigation routes are defined as a sealed class for type safety:

```kotlin
sealed class Screen(val route: String) {
    object Home : Screen("home")
    object DrinkBuilder : Screen("drink_builder")
    object Loyalty : Screen("loyalty")
    object Scanner : Screen("scanner")
    object PaymentSystem : Screen("payment_system")
}
```

## Screen Mapping

| Route | Screen | Description |
|-------|--------|-------------|
| `home` | HomeScreen | Main screen with Quick Actions navigation buttons |
| `drink_builder` | CustomDrinkBuilderScreen | Custom drink creation interface |
| `loyalty` | RewardInfoScreen | Loyalty program information |
| `scanner` | BarcodeScannerScreen | Barcode scanning for rewards |
| `payment_system` | PaymentScreen | Payment processing |

## Navigation Flow

### Home Screen

The Home screen is the entry point and displays four navigation buttons:

1. **Build Drink** (🎨) → Navigates to Custom Drink Builder
2. **Rewards** (⭐) → Navigates to Loyalty Program
3. **Scan Code** (📷) → Navigates to Barcode Scanner
4. **Payment** (💳) → Navigates to Payment System

### Back Navigation

All screens (except Home) include back navigation:

- **Custom Drink Builder**: Back button in header
- **Loyalty Program**: Back button in header
- **Barcode Scanner**: Automatic navigation back on scan complete + back button
- **Payment System**: Navigation to Home on success, back on cancel

## Implementation Details

### Dependencies

Added to `android/build.gradle.kts`:

```kotlin
implementation("androidx.navigation:navigation-compose:2.5.3")
```

### MainActivity Setup

The MainActivity now uses the navigation host:

```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoffeeShopTheme {
                CoffeeShopNavigation()
            }
        }
    }
}
```

### NavHost Configuration

The `CoffeeShopNavigation` composable sets up the NavHost with all routes:

```kotlin
@Composable
fun CoffeeShopNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        // Route definitions...
    }
}
```

## Usage

### For Users

1. Launch the app to see the Home screen
2. Tap any Quick Action button to navigate to that screen
3. Use back buttons or device back button to return to previous screens

### For Developers

To add a new screen to navigation:

1. Define a new route in the `Screen` sealed class
2. Add a `composable` block in the `CoffeeShopNavigation` NavHost
3. Add navigation logic (e.g., button) to navigate to the new screen
4. Ensure the new screen has appropriate back navigation

Example:

```kotlin
// 1. Add route
object NewScreen : Screen("new_screen")

// 2. Add composable
composable(Screen.NewScreen.route) {
    NewScreen(
        onBackClick = { navController.popBackStack() }
    )
}

// 3. Add navigation trigger
Button(onClick = { navController.navigate(Screen.NewScreen.route) }) {
    Text("Go to New Screen")
}
```

## Best Practices

1. **Type Safety**: Use the sealed class routes instead of string literals
2. **Back Stack Management**: Use `popBackStack()` for back navigation
3. **Navigation Arguments**: Can be passed via route parameters (not used in current implementation)
4. **Deep Links**: Can be added to route definitions for external navigation
5. **Lifecycle Awareness**: Navigation automatically handles lifecycle and state preservation

## Future Enhancements

Possible improvements for the navigation system:

- [ ] Add navigation animations/transitions
- [ ] Implement deep linking support
- [ ] Add argument passing between screens
- [ ] Create bottom navigation bar for main sections
- [ ] Add nested navigation graphs for complex flows
- [ ] Implement shared element transitions

## Testing

To test navigation:

1. Build and run the app: `./gradlew :android:installDebug`
2. Tap each Quick Action button on Home screen
3. Verify each screen loads correctly
4. Test back button functionality on each screen
5. Verify proper return to Home screen

## Troubleshooting

**Issue**: Navigation doesn't work
- Ensure navigation dependency is added to build.gradle.kts
- Check that NavHost is properly set up in MainActivity

**Issue**: Back button crashes app
- Verify all screens accept appropriate callback parameters
- Check that onBackClick is properly connected

**Issue**: Screen doesn't update on navigation
- Ensure LaunchedEffect or proper state management is used
- Check that navigation callbacks are properly invoked

## Resources

- [Jetpack Compose Navigation Documentation](https://developer.android.com/jetpack/compose/navigation)
- [Navigation Best Practices](https://developer.android.com/guide/navigation/navigation-principles)
- [Navigation Codelab](https://developer.android.com/codelabs/jetpack-compose-navigation)
