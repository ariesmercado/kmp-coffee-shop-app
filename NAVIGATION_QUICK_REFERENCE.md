# Navigation Quick Reference

## Quick Start

The Coffee Shop app now uses Jetpack Compose Navigation for seamless screen transitions.

## Main Navigation Routes

```kotlin
Screen.Home           → "home"             // Home screen with Quick Actions
Screen.DrinkBuilder   → "drink_builder"    // Custom drink creation
Screen.Loyalty        → "loyalty"          // Reward program info
Screen.Scanner        → "scanner"          // Barcode scanning
Screen.PaymentSystem  → "payment_system"   // Payment processing
```

## Home Screen Quick Actions

| Button | Emoji | Destination | Description |
|--------|-------|-------------|-------------|
| Build Drink | 🎨 | Custom Drink Builder | Create custom beverages |
| Rewards | ⭐ | Loyalty Program | View reward program details |
| Scan Code | 📷 | Barcode Scanner | Scan receipt barcodes |
| Payment | 💳 | Payment System | Process payments |

## Navigation Patterns

### Navigate to Screen
```kotlin
navController.navigate(Screen.DrinkBuilder.route)
```

### Navigate Back
```kotlin
navController.popBackStack()
```

### Navigate Back to Specific Screen
```kotlin
navController.popBackStack(Screen.Home.route, inclusive = false)
```

## Screen Back Navigation

- ✅ Custom Drink Builder: Header "← Back" button
- ✅ Loyalty Program: Header "← Back" button  
- ✅ Barcode Scanner: Header back button + auto-back on scan
- ✅ Payment System: Cancel button + auto-home on success

## File Locations

| Component | Path |
|-----------|------|
| Navigation Setup | `android/src/main/kotlin/coffeeshop/app/navigation/Navigation.kt` |
| MainActivity | `android/src/main/kotlin/coffeeshop/app/MainActivity.kt` |
| Home Screen | `android/src/main/kotlin/coffeeshop/app/ui/screen/HomeScreen.kt` |

## Key Features

✨ **Type-Safe Routes**: Sealed class prevents typos
🔙 **Back Navigation**: All screens support back navigation
🎨 **Visual Buttons**: Emoji-based Quick Actions on Home
📱 **Simple Integration**: Clean NavHost setup in MainActivity

## Dependencies

```kotlin
implementation("androidx.navigation:navigation-compose:2.5.3")
```

## Code Snippets

### Basic Screen with Navigation
```kotlin
@Composable
fun MyScreen(
    onBackClick: () -> Unit = {}
) {
    // Screen content
}
```

### Adding to NavHost
```kotlin
composable(Screen.MyScreen.route) {
    MyScreen(
        onBackClick = { navController.popBackStack() }
    )
}
```

## Testing Checklist

- [ ] Home screen loads with Quick Actions
- [ ] Each Quick Action navigates to correct screen
- [ ] Back buttons work on all screens
- [ ] Device back button works correctly
- [ ] Payment success returns to Home
- [ ] Scanner auto-navigates back after scan

## Need Help?

See full documentation: [NAVIGATION_IMPLEMENTATION.md](NAVIGATION_IMPLEMENTATION.md)
