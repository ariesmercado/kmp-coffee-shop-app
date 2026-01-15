# Menu Screen Integration Guide

## Quick Start

### Android Integration

#### Option 1: Direct Display (Replace Home Screen)

Update `MainActivity.kt`:

```kotlin
package coffeeshop.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import coffeeshop.app.ui.screen.MenuScreen  // Add this import
import coffeeshop.app.ui.theme.CoffeeShopTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoffeeShopTheme {
                MenuScreen()  // Changed from HomeScreen()
            }
        }
    }
}
```

#### Option 2: Add Navigation (Recommended)

If you want to navigate between Home and Menu screens:

1. Add Navigation Compose dependency to `android/build.gradle.kts`:
```kotlin
dependencies {
    implementation("androidx.navigation:navigation-compose:2.5.3")
    // ... existing dependencies
}
```

2. Create a navigation graph:

```kotlin
// android/src/main/kotlin/coffeeshop/app/ui/navigation/Navigation.kt
package coffeeshop.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coffeeshop.app.ui.screen.HomeScreen
import coffeeshop.app.ui.screen.MenuScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Menu : Screen("menu")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToMenu = { navController.navigate(Screen.Menu.route) }
            )
        }
        composable(Screen.Menu.route) {
            MenuScreen()
        }
    }
}
```

3. Update `MainActivity.kt`:
```kotlin
import coffeeshop.app.ui.navigation.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoffeeShopTheme {
                AppNavigation()
            }
        }
    }
}
```

4. Add a button to HomeScreen to navigate to Menu:
```kotlin
// In HomeScreen.kt, add this button somewhere in your UI
Button(
    onClick = onNavigateToMenu,
    modifier = Modifier.padding(16.dp)
) {
    Text("View Full Menu")
}
```

### iOS Integration

#### Option 1: Direct Display (Replace Home Screen)

Update `CoffeeShopApp.swift`:

```swift
import SwiftUI

@main
struct CoffeeShopApp: App {
    var body: some Scene {
        WindowGroup {
            MenuScreenView()  // Changed from HomeScreenView()
        }
    }
}
```

#### Option 2: Add Navigation (Recommended)

If you want to navigate between Home and Menu screens:

1. Create a navigation wrapper:

```swift
// Create a new file: iosApp/iosApp/ContentView.swift
import SwiftUI

struct ContentView: View {
    var body: some View {
        NavigationView {
            HomeScreenViewWithNavigation()
        }
    }
}

struct HomeScreenViewWithNavigation: View {
    var body: some View {
        ZStack {
            HomeScreenView()
            
            VStack {
                Spacer()
                
                NavigationLink(destination: MenuScreenView()) {
                    Text("View Full Menu")
                        .font(.system(size: 18, weight: .semibold))
                        .foregroundColor(.white)
                        .padding(.horizontal, 24)
                        .padding(.vertical, 12)
                        .background(CoffeeColors.coffeeBrown)
                        .cornerRadius(25)
                }
                .padding(.bottom, 32)
            }
        }
    }
}

#Preview {
    ContentView()
}
```

2. Update `CoffeeShopApp.swift`:
```swift
import SwiftUI

@main
struct CoffeeShopApp: App {
    var body: some Scene {
        WindowGroup {
            ContentView()  // Use the new navigation wrapper
        }
    }
}
```

## Testing the Menu Screen

### Android Testing

1. **Build and Run**:
   ```bash
   ./gradlew :android:assembleDebug
   ./gradlew :android:installDebug
   ```

2. **Test Cases**:
   - Open the app and verify the Menu Screen displays
   - Type "latte" in the search bar → Should filter to latte drinks
   - Tap "Espresso" category → Should show only espresso drinks
   - Tap "Espresso" again → Should show all drinks
   - Select "Hot" category and search "mocha" → Should show Mocha Latte only
   - Clear search → Should show all hot drinks
   - Scroll through the list → All cards should be visible and properly formatted

### iOS Testing

1. **Build and Run**:
   - Open `iosApp.xcodeproj` in Xcode
   - Select a simulator or device
   - Press Run (Cmd+R)

2. **Test Cases**:
   - Open the app and verify the Menu Screen displays
   - Type "latte" in the search bar → Should filter to latte drinks
   - Tap X button in search bar → Should clear search and show all drinks
   - Tap "Blended" category → Should show only blended drinks
   - Tap "Blended" again → Should show all drinks
   - Select "Iced" category and search "caramel" → Should show Iced Caramel Macchiato
   - Scroll through the list → All cards should be visible and properly formatted

## Features Demonstrated

✅ **Search Functionality**: Real-time filtering as you type
✅ **Category Filtering**: Quick access to drink categories
✅ **Combined Filtering**: Search within a category
✅ **Consistent Design**: Matches the app's theme
✅ **Cross-Platform**: Same logic works on both Android and iOS
✅ **Responsive UI**: Adapts to different screen sizes

## Menu Data Overview

The menu includes **20 drinks** across **4 categories**:

- **Espresso** (5 drinks): $2.50 - $4.95
- **Blended** (4 drinks): $5.25 - $5.75
- **Hot** (5 drinks): $3.75 - $5.25
- **Iced** (6 drinks): $3.50 - $5.25

All drinks include:
- Name and description
- Price in USD format
- Star rating (out of 5.0)
- Category association

## Customization

### Adding More Drinks

Edit `shared/src/commonMain/kotlin/coffeeshop/shared/data/repository/MockCoffeeRepository.kt`:

```kotlin
MenuItem(
    id = "menu_21",
    name = "Your New Drink",
    description = "Description of your drink",
    price = 4.99,
    imageUrl = "your_drink.jpg",
    rating = 4.8,
    categoryId = "espresso"  // or "blended", "hot", "iced"
)
```

### Adding More Categories

1. Add to `getMenuCategories()` in `MockCoffeeRepository.kt`:
```kotlin
MenuCategory(
    id = "specialty",
    name = "Specialty",
    description = "Unique seasonal drinks"
)
```

2. Add drinks with `categoryId = "specialty"`

### Changing Colors

**Android**: Edit `android/src/main/kotlin/coffeeshop/app/ui/theme/Color.kt`

**iOS**: Edit `iosApp/iosApp/Theme.swift`

## Troubleshooting

### Android Build Issues

**Problem**: Build fails with "Cannot resolve CoffeeRepository"
**Solution**: Clean and rebuild:
```bash
./gradlew clean
./gradlew :shared:build
./gradlew :android:build
```

**Problem**: Search not working
**Solution**: Verify you're using `MenuScreen()` not `MenuScreen(presenter = ...)`

### iOS Build Issues

**Problem**: Build fails with "Cannot find MenuCategory in scope"
**Solution**: 
1. Clean build folder (Cmd+Shift+K)
2. Rebuild shared framework
3. Rebuild iOS app

**Problem**: Search not updating results
**Solution**: Verify you're using the Binding wrapper in SearchBar that calls `setSearchQuery`

## Next Steps

After integrating the Menu Screen, consider:

1. **Add to Cart**: Implement shopping cart functionality
2. **Drink Details**: Create a detailed view for each drink
3. **Favorites**: Let users save favorite drinks
4. **Order History**: Show previously ordered drinks
5. **Real Images**: Replace emoji placeholders with actual images
6. **Animations**: Add smooth transitions and animations

For more details, see `MENU_SCREEN_IMPLEMENTATION.md`.
