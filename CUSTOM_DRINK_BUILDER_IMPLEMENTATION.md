# Custom Drink Builder Feature

## Overview
The Custom Drink Builder feature allows users to create personalized beverages by customizing their drink base, size, and add-ons. Users can save their custom drinks for quick reordering in the future.

## Feature Components

### Shared Module (Kotlin Multiplatform)

#### Data Models

1. **DrinkSize** (`shared/src/commonMain/kotlin/coffeeshop/shared/data/model/DrinkSize.kt`)
   - Enum with three options: SMALL, MEDIUM, LARGE
   - Each size has a display name and price multiplier
   - Small: 0.85x base price
   - Medium: 1.0x base price (default)
   - Large: 1.25x base price

2. **AddOn** (`shared/src/commonMain/kotlin/coffeeshop/shared/data/model/AddOn.kt`)
   - Represents an add-on item with ID, name, and price
   - Examples: Extra Shot ($0.75), Whipped Cream ($0.50), etc.

3. **CustomDrink** (`shared/src/commonMain/kotlin/coffeeshop/shared/data/model/CustomDrink.kt`)
   - Contains the complete customized drink configuration
   - Includes: base menu item, selected size, list of add-ons, total price
   - Optional custom name for saved drinks

#### Repository Methods

Added to `CoffeeRepository` interface:
- `getAvailableAddOns()`: Returns list of available add-ons
- `saveCustomDrink(drink: CustomDrink)`: Saves a custom drink
- `getSavedCustomDrinks()`: Retrieves all saved custom drinks
- `removeCustomDrink(drinkId: String)`: Removes a saved custom drink

#### Presenter

**CustomDrinkBuilderPresenter** (`shared/src/commonMain/kotlin/coffeeshop/shared/presentation/CustomDrinkBuilderPresenter.kt`)
- Manages business logic for drink customization
- `calculateTotalPrice()`: Computes total price based on base item, size, and add-ons
- `saveCustomDrink()`: Creates and saves a custom drink configuration
- `getSavedCustomDrinks()`: Retrieves saved custom drinks
- `getAvailableAddOns()`: Gets list of available add-ons

### Android UI (Jetpack Compose)

**CustomDrinkBuilderScreen** (`android/src/main/kotlin/coffeeshop/app/ui/screen/CustomDrinkBuilderScreen.kt`)

#### Features:
1. **Three-Step Process**:
   - Step 1: Choose base drink from full menu
   - Step 2: Select size (Small, Medium, Large)
   - Step 3: Add optional extras

2. **Live Price Preview**:
   - Displays real-time total price as user makes selections
   - Updates automatically when size or add-ons change

3. **Visual Design**:
   - Consistent with coffee shop branding (brown tones)
   - Step indicator showing current progress
   - Selected items highlighted with checkmarks and border
   - Navigation buttons (Previous/Next/Save Drink)

4. **Save Functionality**:
   - Dialog to optionally name custom drink
   - Success confirmation message
   - Saved drinks stored for quick reordering

#### UI Components:
- `CustomDrinkBuilderHeader`: Header with title and step indicator
- `PricePreviewCard`: Card showing real-time price calculation
- `MenuItemSelectionCard`: Selectable menu item card
- `SizeSelectionCard`: Size selection with price adjustment
- `AddOnSelectionCard`: Add-on selection with price
- `NavigationButtons`: Previous/Next/Save buttons
- `SaveCustomDrinkDialog`: Dialog for naming custom drink

### iOS UI (SwiftUI)

**CustomDrinkBuilderScreenView** (`iosApp/iosApp/CustomDrinkBuilderScreenView.swift`)

#### Features:
1. **Three-Step Process** (identical to Android):
   - Step 1: Choose base drink
   - Step 2: Select size
   - Step 3: Add extras

2. **Live Price Preview**:
   - Real-time price calculation
   - Automatic updates

3. **Visual Design**:
   - Matches coffee shop theme colors
   - Step indicator for navigation
   - Selection states with checkmarks
   - Native iOS navigation buttons

4. **Save Functionality**:
   - Sheet modal for naming drink
   - Alert confirmation on success
   - Persistent storage via shared repository

#### SwiftUI Components:
- `CustomDrinkBuilderHeader`: Header with branding and steps
- `PricePreviewCard`: Price display card
- `StepOneContent/StepTwoContent/StepThreeContent`: Step-specific content
- `MenuItemSelectionCard`: Selectable menu items
- `SizeSelectionCard`: Size options with pricing
- `AddOnSelectionCard`: Add-on options
- `NavigationButtons`: Navigation controls
- `SaveCustomDrinkDialog`: Sheet for custom naming
- `CustomDrinkBuilderViewModel`: ObservableObject managing state

## Available Add-Ons

The following add-ons are available in the mock repository:
1. Extra Shot - $0.75
2. Whipped Cream - $0.50
3. Almond Milk - $0.50
4. Oat Milk - $0.50
5. Soy Milk - $0.50
6. Extra Caramel - $0.50
7. Vanilla Syrup - $0.50
8. Hazelnut Syrup - $0.50
9. Chocolate Drizzle - $0.50
10. Cinnamon - $0.25

## Price Calculation

The total price is calculated as follows:
```
Total = (Base Price × Size Multiplier) + Sum of Add-on Prices
```

Example:
- Base: Cappuccino ($3.95)
- Size: Large (1.25x multiplier)
- Add-ons: Extra Shot ($0.75) + Whipped Cream ($0.50)
- Total: ($3.95 × 1.25) + $0.75 + $0.50 = $4.94 + $1.25 = $6.19

## Testing Instructions

### Android
1. Open the project in Android Studio
2. Ensure MainActivity is set to display CustomDrinkBuilderScreen()
3. Run on emulator or physical device
4. Test the three-step flow:
   - Select a base drink (e.g., Cappuccino)
   - Choose a size (e.g., Large)
   - Add extras (e.g., Extra Shot, Whipped Cream)
   - Verify price updates correctly
   - Save the drink with a custom name
   - Verify success message appears

### iOS
1. Open the iOS project in Xcode
2. Ensure CoffeeShopApp is set to display CustomDrinkBuilderScreenView()
3. Run on simulator or physical device
4. Test the three-step flow (same as Android)
5. Verify price calculations match expected values
6. Test save functionality with and without custom name

## Integration Notes

- The feature uses the existing `MockCoffeeRepository` for data persistence
- Custom drinks are stored in-memory in the current implementation
- For production, implement persistent storage (database or file system)
- The feature is fully integrated with the existing app theme and color scheme
- No breaking changes to existing functionality

## Future Enhancements

1. Add ability to view and reorder saved custom drinks from a dedicated screen
2. Implement persistent storage (Room for Android, CoreData for iOS)
3. Add drink photos or icons for better visual appeal
4. Include nutritional information calculation
5. Add ability to share custom drink recipes with other users
6. Implement favorites for custom drinks
7. Add quantity selector before saving
8. Include allergen warnings based on selected ingredients
