# Menu Screen Implementation Guide

## Overview
This document describes the implementation of the Menu Screen for the KMP Coffee Shop App. The Menu Screen displays a complete list of coffee and drink options available in the coffee shop, organized by categories.

## Features

### 1. **Categories**
The menu is organized into four main categories:
- **Espresso**: Classic espresso-based drinks (Espresso, Americano, Cappuccino, Caramel Macchiato, Flat White)
- **Blended**: Smooth blended beverages (Mocha Frappuccino, Caramel Frappuccino, Vanilla Bean Frappuccino, Java Chip Frappuccino)
- **Hot**: Warm and comforting drinks (Vanilla Latte, Mocha Latte, Hot Chocolate, Chai Tea Latte, Green Tea Latte)
- **Iced**: Refreshing cold beverages (Iced Americano, Iced Latte, Cold Brew, Iced Caramel Macchiato, Iced Mocha, Iced Chai Latte)

### 2. **Drink Cards**
Each drink card displays:
- **Thumbnail image**: Coffee icon placeholder (☕)
- **Drink name**: Clear, readable name
- **Description**: Brief description of the drink
- **Price**: Formatted as $X.XX
- **Rating**: Star rating out of 5.0

### 3. **Search Functionality**
- Search bar at the top allows users to filter drinks
- Real-time filtering as user types
- Searches both drink name and description
- Case-insensitive search

### 4. **Category Filtering**
- Horizontal scrollable category chips
- Tap to filter by category
- Tap again to show all categories
- Visual indication of selected category

## Architecture

### Shared Module (Kotlin Multiplatform)

#### Data Models

**MenuCategory.kt**
```kotlin
data class MenuCategory(
    val id: String,
    val name: String,
    val description: String
)
```

**MenuItem.kt**
```kotlin
data class MenuItem(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val rating: Double,
    val categoryId: String
)
```

#### Repository Interface

**CoffeeRepository.kt** - Extended with:
- `fun getMenuCategories(): List<MenuCategory>`
- `fun getMenuItems(): List<MenuItem>`

**MockCoffeeRepository.kt** - Implements menu methods with 20 different drink items across 4 categories

#### Presenter

**MenuScreenPresenter.kt** - Business logic for:
- Getting all categories
- Getting all menu items
- Filtering by category
- Searching menu items
- Combined search and category filtering

### Android Implementation (Jetpack Compose)

**File**: `android/src/main/kotlin/coffeeshop/app/ui/screen/MenuScreen.kt`

**Components**:
- `MenuScreen`: Main composable with state management
- `MenuHeader`: Header section with title and subtitle
- `SearchBar`: Text field with search icon
- `CategoryFilter`: Horizontal scrollable category chips
- `CategoryChip`: Individual category selection chip
- `MenuItemsList`: Scrollable list of menu items
- `MenuItemCard`: Individual drink card

**State Management**:
- Uses `remember` and `mutableStateOf` for local state
- Reactive updates when search query or category changes
- `searchQuery`: String for search input
- `selectedCategoryId`: String? for selected category

**Styling**:
- Uses existing Material Theme colors
- Consistent with HomeScreen design
- Coffee-themed color palette (CoffeeBrown, LatteFoam, etc.)

### iOS Implementation (SwiftUI)

**File**: `iosApp/iosApp/MenuScreenView.swift`

**Components**:
- `MenuScreenView`: Main view with ViewModel
- `MenuHeader`: Header section with title and subtitle
- `SearchBar`: TextField with search icon and clear button
- `CategoryFilter`: Horizontal scrollable category chips
- `CategoryChip`: Individual category button
- `MenuItemsList`: Scrollable list of menu items
- `MenuItemCardView`: Individual drink card

**State Management**:
- `MenuViewModel`: ObservableObject with @Published properties
- `searchQuery`: String for search input
- `selectedCategoryId`: String? for selected category
- `categories`: List of menu categories
- `filteredMenuItems`: Filtered list based on search and category
- Automatic updates when search query or category changes

**Styling**:
- Uses CoffeeColors theme (from Theme.swift)
- Consistent with HomeScreenView design
- Native iOS styling with SF Symbols

## Usage

### Android

To navigate to the Menu Screen, update MainActivity.kt:

```kotlin
import coffeeshop.app.ui.screen.MenuScreen

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

Or add navigation between screens using Navigation Compose.

### iOS

To navigate to the Menu Screen, update CoffeeShopApp.swift:

```swift
@main
struct CoffeeShopApp: App {
    var body: some Scene {
        WindowGroup {
            MenuScreenView()  // Changed from HomeScreenView()
        }
    }
}
```

Or add navigation using NavigationView/NavigationStack.

## Data

The menu contains 20 different drinks:

### Espresso (5 items)
- Espresso ($2.50, ⭐4.7)
- Americano ($3.25, ⭐4.4)
- Cappuccino ($3.95, ⭐4.7)
- Caramel Macchiato ($4.95, ⭐4.8)
- Flat White ($4.25, ⭐4.6)

### Blended (4 items)
- Mocha Frappuccino ($5.50, ⭐4.9)
- Caramel Frappuccino ($5.50, ⭐4.8)
- Vanilla Bean Frappuccino ($5.25, ⭐4.7)
- Java Chip Frappuccino ($5.75, ⭐4.9)

### Hot (5 items)
- Vanilla Latte ($4.45, ⭐4.5)
- Mocha Latte ($5.25, ⭐4.9)
- Hot Chocolate ($3.75, ⭐4.6)
- Chai Tea Latte ($4.25, ⭐4.5)
- Green Tea Latte ($4.50, ⭐4.4)

### Iced (6 items)
- Iced Americano ($3.50, ⭐4.5)
- Iced Latte ($4.25, ⭐4.6)
- Cold Brew ($4.50, ⭐4.6)
- Iced Caramel Macchiato ($5.25, ⭐4.8)
- Iced Mocha ($4.95, ⭐4.7)
- Iced Chai Latte ($4.50, ⭐4.5)

## Consistent Design

The Menu Screen follows the same design principles as the existing Home Screen:

1. **Color Palette**: Uses the same coffee-themed colors
2. **Typography**: Consistent font sizes and weights
3. **Card Design**: Similar rounded corners and shadows
4. **Layout**: Similar padding and spacing
5. **Component Structure**: Follows the same modular pattern

## Testing

To test the Menu Screen:

1. **Search Functionality**:
   - Type "latte" in search bar → Should show all latte drinks
   - Type "iced" → Should show all iced drinks
   - Clear search → Should show all drinks

2. **Category Filtering**:
   - Tap "Espresso" → Should show 5 espresso drinks
   - Tap "Blended" → Should show 4 blended drinks
   - Tap selected category again → Should show all drinks

3. **Combined Filtering**:
   - Select "Hot" category
   - Type "latte" in search
   - Should show only hot latte drinks (Vanilla Latte, Mocha Latte, Chai Tea Latte, Green Tea Latte)

4. **Edge Cases**:
   - Search for non-existent drink → Should show "No drinks found"
   - Select category with no matching search results → Should show "No drinks found"

## Future Enhancements

1. **Real Images**: Replace placeholder emoji with actual drink photos
2. **Add to Cart**: Add button to add drinks to cart
3. **Favorites**: Allow users to favorite drinks
4. **Sorting**: Sort by price, rating, or name
5. **Detailed View**: Tap drink to see detailed information
6. **Customization**: Allow users to customize drinks (size, additions)
7. **Dietary Filters**: Filter by vegan, sugar-free, etc.
