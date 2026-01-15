# Favorites Screen Quick Start Guide

## Overview
The Favorites Screen allows users to save and manage their favorite coffee drinks. This guide provides quick instructions for using and testing the feature.

## Features at a Glance

✅ View all favorite drinks in one place
✅ Add drinks to favorites from the Menu Screen  
✅ Remove drinks from favorites with one tap
✅ Empty state when no favorites exist
✅ Cross-platform (Android & iOS)
✅ Consistent warm coffee-themed design

## How to Use

### Viewing Favorites

#### Android
1. Open the app
2. The Favorites Screen is displayed by default (or navigate to it)
3. Scroll through your favorite drinks

#### iOS
1. Open the app
2. Navigate to the Favorites tab
3. Browse your saved favorites

### Adding Favorites

**From Menu Screen:**
1. Navigate to the Menu Screen
2. Browse available drinks
3. Tap the **heart outline icon** (🤍) next to any drink
4. The icon fills (❤️) to indicate it's favorited
5. The drink is added to your Favorites list

### Removing Favorites

**From Favorites Screen:**
1. Open the Favorites Screen
2. Tap the **filled heart icon** (❤️) on any drink card
3. The drink is immediately removed from favorites

**From Menu Screen:**
1. Navigate to the Menu Screen
2. Find a drink with a filled heart icon (❤️)
3. Tap the heart icon
4. The icon changes to outline (🤍) and the drink is removed

## Testing the Feature

### Test Case 1: Empty State
```
1. Start with no favorites (or remove all existing ones)
2. Open Favorites Screen
3. Verify you see:
   - Coffee cup emoji (☕)
   - "No Favorites Yet" heading
   - "Start adding your favorite drinks to see them here!" message
```

### Test Case 2: Add Favorites
```
1. Navigate to Menu Screen
2. Tap heart icon on "Cappuccino"
3. Verify icon fills with pink/red color
4. Navigate to Favorites Screen
5. Verify "Cappuccino" appears in the list
```

### Test Case 3: Remove from Favorites Screen
```
1. Open Favorites Screen with at least one favorite
2. Tap the heart icon on "Caramel Macchiato"
3. Verify the drink disappears from the list
4. Navigate to Menu Screen
5. Verify "Caramel Macchiato" has outline heart icon
```

### Test Case 4: Remove from Menu Screen
```
1. Open Menu Screen
2. Find a favorited drink (filled heart icon)
3. Tap the heart icon
4. Verify icon changes to outline
5. Navigate to Favorites Screen
6. Verify the drink is not in the list
```

### Test Case 5: Multiple Favorites
```
1. Navigate to Menu Screen
2. Add 3-5 drinks to favorites
3. Navigate to Favorites Screen
4. Verify all drinks appear in the list
5. Verify each shows correct name, price, and rating
```

## Sample Data

The app comes with 3 pre-loaded favorite drinks for testing:

1. **Caramel Macchiato** - $4.95 ⭐ 4.8
2. **Mocha Latte** - $5.25 ⭐ 4.9
3. **Cold Brew** - $4.50 ⭐ 4.6

## Switching Between Screens

### Android (MainActivity.kt)

To view the **Favorites Screen**:
```kotlin
FavoritesScreen()
```

To view the **Menu Screen** (with favorites integration):
```kotlin
MenuScreen()
```

Comment out other screens when testing.

### iOS

Navigate between tabs in the main app interface.

## Visual Indicators

### Favorite Status
- **Not Favorited**: Heart outline icon (🤍) - Gray/Coffee Brown
- **Favorited**: Filled heart icon (❤️) - Pink/Red (#E91E63)

### Screen Elements
- **Header**: Coffee brown background (#6F4E37) with white text
- **Cards**: Cream/latte foam background (#FFFAF0)
- **Text**: Dark coffee color for readability

## Common Use Cases

### Scenario 1: Building a Favorites Collection
```
Morning routine user wants to save their go-to drinks:
1. Browse Menu Screen
2. Add "Americano", "Cold Brew", and "Cappuccino"
3. Access Favorites Screen for quick reordering
```

### Scenario 2: Trying New Drinks
```
User exploring new options:
1. Try a "Mocha Frappuccino" from Menu
2. Like it? Add to Favorites
3. Don't like it? Remove from Favorites
```

### Scenario 3: Managing Favorites List
```
User with many favorites wants to clean up:
1. Open Favorites Screen
2. Review all saved drinks
3. Remove drinks no longer preferred
4. Keep only current favorites
```

## Troubleshooting

### Issue: Favorites not persisting
**Cause**: Current implementation uses in-memory storage  
**Behavior**: Favorites reset when app closes  
**Future**: Will be addressed with persistent storage (database)

### Issue: Heart icon not updating
**Cause**: State synchronization issue  
**Fix**: Navigate away and back to refresh the screen

### Issue: Empty state not showing
**Cause**: Sample data is pre-loaded  
**Fix**: Remove all favorites first to see empty state

## Performance Notes

- ✅ Fast lookup: O(1) favorite checking using Set
- ✅ Smooth scrolling: LazyColumn/ScrollView for lists
- ✅ Efficient updates: Only affected items re-render
- ✅ Responsive: Immediate feedback on toggle actions

## Limitations

### Current Version
- No persistent storage (favorites reset on app restart)
- No sync between devices
- No favorite categories or sorting
- No favorite drink statistics
- Single repository instance per screen (see documentation)

### Planned Enhancements
See `FAVORITES_SCREEN_IMPLEMENTATION.md` for future features.

## Key Files

### Shared (Kotlin Multiplatform)
```
shared/src/commonMain/kotlin/coffeeshop/shared/
├── data/model/FavoriteDrink.kt
├── data/repository/CoffeeRepository.kt
├── data/repository/MockCoffeeRepository.kt
└── presentation/FavoritesPresenter.kt
```

### Android
```
android/src/main/kotlin/coffeeshop/app/
├── MainActivity.kt
└── ui/screen/
    ├── FavoritesScreen.kt
    └── MenuScreen.kt
```

### iOS
```
iosApp/iosApp/
├── FavoritesScreenView.swift
└── MenuScreenView.swift
```

## Documentation

For more details, see:
- **Implementation Guide**: `FAVORITES_SCREEN_IMPLEMENTATION.md`
- **Visual Specifications**: `FAVORITES_SCREEN_VISUAL_GUIDE.md`

## Getting Help

If you encounter issues:
1. Check this Quick Start Guide
2. Review the Implementation Guide
3. Verify you're following the test cases correctly
4. Check that sample data is loaded

## Summary

The Favorites Screen is ready to use! Simply:
1. Open the Menu Screen
2. Tap heart icons to add favorites
3. View your favorites in the Favorites Screen
4. Tap heart icons to remove favorites

Enjoy your personalized coffee experience! ☕
