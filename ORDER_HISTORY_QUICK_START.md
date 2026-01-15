# Order History Screen - Quick Start Guide

## Overview
The Order History Screen displays a user's past coffee orders with search and filtering capabilities.

## Viewing the Screen

### Android
1. Open `MainActivity.kt`
2. The `OrderHistoryScreen()` is currently set as the default view
3. Run the app to see the Order History Screen

### iOS
1. Open `CoffeeShopApp.swift`
2. The `OrderHistoryScreenView()` is currently set as the default view
3. Run the app to see the Order History Screen

## Features

### 1. Order List
- Displays 8 sample orders from the past 30 days
- Each order shows:
  - Order ID (last 6 characters)
  - Date and time
  - Total price
  - Order status
  - Item summary (first 2 items)

### 2. Search
- Type in the search bar to filter orders
- Search by item name or order ID
- Real-time filtering as you type

### 3. Date Filters
- **All Time**: Shows all orders (default)
- **Last 7 Days**: Shows orders from the past week
- **Last 30 Days**: Shows orders from the past month

### 4. Expandable Details
- Tap "View Details" to see all order items
- Shows complete information:
  - Item quantities
  - Size selections
  - Add-ons
  - Individual prices
- Tap "Hide Details" to collapse

## Architecture

### Shared Module
```
shared/
├── data/
│   ├── model/
│   │   └── OrderHistory.kt          # Order data model
│   └── repository/
│       ├── CoffeeRepository.kt      # Repository interface
│       └── MockCoffeeRepository.kt  # Mock data implementation
└── presentation/
    └── OrderHistoryPresenter.kt     # Business logic
```

### Android Module
```
android/
└── ui/
    └── screen/
        └── OrderHistoryScreen.kt    # Compose UI implementation
```

### iOS Module
```
iosApp/
└── OrderHistoryScreenView.swift     # SwiftUI implementation
```

## Mock Data Details

The mock repository includes 8 orders with:
- Various coffee drinks (Caramel Macchiato, Cold Brew, Cappuccino, etc.)
- Different sizes (Small, Medium, Large)
- Add-ons (Extra Shot, Whipped Cream, Almond Milk, Extra Caramel)
- Proper pricing calculations including add-ons
- 8% tax rate applied
- All orders marked as "Completed"

Add-on pricing:
- Extra Shot: $0.75
- Whipped Cream: $0.50
- Almond Milk: $0.50
- Extra Caramel: $0.50

## Customization

### Changing the Date Filter Options
Edit `DateFilter` enum in:
- Android: `OrderHistoryScreen.kt`
- iOS: `OrderHistoryScreenView.swift`

### Modifying Order Data
Edit `getOrderHistory()` in `MockCoffeeRepository.kt`

### Adjusting Colors
Colors are defined in:
- Android: `app/ui/theme/Color.kt`
- iOS: `Theme.swift`

## Integration with Navigation

To integrate with navigation (not implemented to keep changes minimal):

### Android
```kotlin
// In your NavHost
composable("orderHistory") {
    OrderHistoryScreen()
}
```

### iOS
```swift
// In your NavigationView
NavigationLink("Order History") {
    OrderHistoryScreenView()
}
```

## Testing

The implementation includes:
- Mock data for realistic testing
- Empty state handling
- Search functionality
- Date filtering logic
- Expandable card interactions

## Notes

- The SimpleDateFormat in Android is created per call for thread safety
- The iOS Combine subscriptions are properly stored to prevent deallocation
- All pricing calculations are accurate and documented
- The UI matches the existing coffee-themed design system
