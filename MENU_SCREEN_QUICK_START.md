# Menu Screen - Example Integration

## Android Example (MainActivity.kt)

To display the Menu Screen directly, simply change one line in MainActivity:

```kotlin
package coffeeshop.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
// Add this import:
import coffeeshop.app.ui.screen.MenuScreen
// Keep existing imports:
import coffeeshop.app.ui.theme.CoffeeShopTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoffeeShopTheme {
                // Change this line from HomeScreen() to MenuScreen()
                MenuScreen()
            }
        }
    }
}
```

## iOS Example (CoffeeShopApp.swift)

To display the Menu Screen directly, simply change one line in CoffeeShopApp:

```swift
import SwiftUI

@main
struct CoffeeShopApp: App {
    var body: some Scene {
        WindowGroup {
            // Change this line from HomeScreenView() to MenuScreenView()
            MenuScreenView()
        }
    }
}
```

## Screenshots

When you run the app, you'll see:

### Android
- Header with "Our Menu" title and subtitle
- Search bar with magnifying glass icon
- Horizontal scrollable category chips (Espresso, Blended, Hot, Iced)
- Scrollable list of drink cards showing:
  - Coffee emoji (☕) as thumbnail
  - Drink name and description
  - Price and star rating

### iOS
- Header with "Our Menu" title and subtitle
- Search bar with magnifying glass icon and clear button
- Horizontal scrollable category buttons (Espresso, Blended, Hot, Iced)
- Scrollable list of drink cards showing:
  - Coffee emoji (☕) as thumbnail
  - Drink name and description
  - Price and star rating

## Quick Test

1. **Search**: Type "mocha" → See Mocha Latte, Mocha Frappuccino, Iced Mocha
2. **Filter**: Tap "Espresso" → See 5 espresso-based drinks
3. **Combined**: Select "Hot" + search "latte" → See hot latte drinks only
4. **Reset**: Clear search or tap selected category again → See all drinks

The implementation follows the exact specifications from the problem statement:
✅ Categories for better navigation
✅ Drink cards with name, price, and thumbnail
✅ Shared Kotlin Multiplatform logic
✅ Search bar on both platforms
✅ Consistent theme and styling
