# Custom Drink Builder - Quick Start Guide

## Overview
The Custom Drink Builder allows users to create and save personalized coffee drinks by customizing base drinks, sizes, and add-ons.

## Quick Access

### Android
1. Open `android/src/main/kotlin/coffeeshop/app/MainActivity.kt`
2. Ensure `CustomDrinkBuilderScreen()` is uncommented in the `setContent` block
3. Run the app

### iOS
1. Open `iosApp/iosApp/CoffeeShopApp.swift`
2. Ensure `CustomDrinkBuilderScreenView()` is uncommented in the `body` of `CoffeeShopApp`
3. Run the app

## User Flow

### Step 1: Choose Base Drink
- Browse through all available menu items
- Tap/click on a drink to select it as the base
- Selected drink shows a checkmark

### Step 2: Select Size
- Choose from three sizes:
  - **Small**: 85% of base price
  - **Medium**: 100% of base price (default)
  - **Large**: 125% of base price
- Price updates automatically based on selection

### Step 3: Add Extras (Optional)
- Select from 10 available add-ons:
  - Extra Shot ($0.75)
  - Whipped Cream ($0.50)
  - Almond Milk ($0.50)
  - Oat Milk ($0.50)
  - Soy Milk ($0.50)
  - Extra Caramel ($0.50)
  - Vanilla Syrup ($0.50)
  - Hazelnut Syrup ($0.50)
  - Chocolate Drizzle ($0.50)
  - Cinnamon ($0.25)
- Multiple add-ons can be selected
- Tap/click again to deselect

### Saving Your Custom Drink
1. After completing all three steps, tap "Save Drink"
2. Optional: Enter a custom name for your drink
3. Tap "Save" to confirm
4. Success message confirms the drink is saved

## Price Preview
The total price is displayed at the top of the screen and updates in real-time as you make selections:
- **Formula**: (Base Price × Size Multiplier) + Sum of Add-on Prices

## Example
Creating a custom "Large Cappuccino with Extra Shot and Whipped Cream":
1. **Step 1**: Select "Cappuccino" ($3.95)
2. **Step 2**: Select "Large" (1.25x = $4.94)
3. **Step 3**: Select "Extra Shot" ($0.75) and "Whipped Cream" ($0.50)
4. **Total**: $4.94 + $0.75 + $0.50 = **$6.19**

## Navigation
- **Previous**: Go back to the previous step (available from Step 2 onwards)
- **Next**: Proceed to the next step (available when required selections are made)
- **Save Drink**: Available on Step 3 to save your custom creation

## Visual Indicators
- Selected items have a **checkmark icon** and **colored border**
- Current step is highlighted in the **step indicator** at the top
- The **price preview card** shows the running total

## Theme
The Custom Drink Builder uses the app's coffee shop theme with warm brown tones for a consistent user experience across Android and iOS.

## Code Structure

### Shared Module
- `DrinkSize.kt` - Enum for size options
- `AddOn.kt` - Data model for add-ons
- `CustomDrink.kt` - Data model for custom drinks
- `CustomDrinkBuilderPresenter.kt` - Business logic

### Android
- `CustomDrinkBuilderScreen.kt` - Jetpack Compose UI

### iOS
- `CustomDrinkBuilderScreenView.swift` - SwiftUI implementation

## Future Features
- View saved custom drinks in a dedicated screen
- Quick reorder from saved drinks
- Share custom drink recipes
- Add drink photos
- Nutritional information
