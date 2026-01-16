# Navigation Visual Guide

## Navigation Architecture Diagram

```
┌─────────────────────────────────────────────────────────────┐
│                        MainActivity                          │
│                     (Entry Point)                            │
│                            │                                 │
│                            ▼                                 │
│                   CoffeeShopNavigation                       │
│                      (NavHost)                               │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
        ┌─────────────────────────────────────────┐
        │          Home Screen (Start)            │
        │                                         │
        │  ┌───────────────────────────────────┐ │
        │  │     Quick Actions Section         │ │
        │  ├───────────────┬───────────────────┤ │
        │  │  🎨 Build     │  ⭐ Rewards      │ │
        │  │     Drink     │                   │ │
        │  ├───────────────┼───────────────────┤ │
        │  │  📷 Scan      │  💳 Payment      │ │
        │  │     Code      │                   │ │
        │  └───────────────┴───────────────────┘ │
        └─────────────────────────────────────────┘
                 │        │        │        │
    ┌────────────┘        │        │        └────────────┐
    │                     │        │                     │
    ▼                     ▼        ▼                     ▼
┌────────┐         ┌────────┐  ┌────────┐         ┌────────┐
│ Custom │         │ Reward │  │Barcode │         │Payment │
│ Drink  │         │  Info  │  │Scanner │         │ System │
│Builder │         │ Screen │  │ Screen │         │ Screen │
└────────┘         └────────┘  └────────┘         └────────┘
    │                   │          │                   │
    └───────────────────┴──────────┴───────────────────┘
                         │
                         ▼
                   [Back to Home]
```

## Screen Flow Details

### 1. Home Screen → Custom Drink Builder
```
[Home] --"Build Drink"→ [Custom Drink Builder]
         (navigate)       ┌──────────────────┐
                          │ ← Back Button    │
                          │ Step 1: Base     │
                          │ Step 2: Size     │
                          │ Step 3: Add-ons  │
                          └──────────────────┘
                                   │
                         [Save or ← Back]
```

### 2. Home Screen → Loyalty Program
```
[Home] --"Rewards"→ [Reward Info Screen]
        (navigate)    ┌──────────────────┐
                      │ ← Back Button    │
                      │ How It Works     │
                      │ Earning Points   │
                      │ Redeeming Points │
                      │ Reward Tiers     │
                      └──────────────────┘
                               │
                         [← Back]
```

### 3. Home Screen → Barcode Scanner
```
[Home] --"Scan Code"→ [Barcode Scanner]
         (navigate)     ┌──────────────────┐
                        │ ← Back Button    │
                        │ [Camera View]    │
                        │ Manual Entry     │
                        └──────────────────┘
                                 │
                    [Auto-back on scan complete]
                    or [← Back]
```

### 4. Home Screen → Payment System
```
[Home] --"Payment"→ [Payment Screen]
        (navigate)   ┌──────────────────┐
                     │ Payment Methods  │
                     │ Billing Info     │
                     │ [Cancel] [Pay]   │
                     └──────────────────┘
                              │
                    ┌─────────┴─────────┐
                    │                   │
              [Pay Success]        [Cancel]
                    │                   │
               [→ Home]          [← Back]
```

## UI Components

### Home Screen Quick Actions Layout
```
┌──────────────────────────────────────────────┐
│  Quick Actions                               │
├──────────────────────┬───────────────────────┤
│  ┌────────────────┐  │  ┌────────────────┐  │
│  │      🎨        │  │  │      ⭐        │  │
│  │                │  │  │                │  │
│  │  Build Drink   │  │  │    Rewards     │  │
│  └────────────────┘  │  └────────────────┘  │
├──────────────────────┼───────────────────────┤
│  ┌────────────────┐  │  ┌────────────────┐  │
│  │      📷        │  │  │      💳        │  │
│  │                │  │  │                │  │
│  │  Scan Code     │  │  │    Payment     │  │
│  └────────────────┘  │  └────────────────┘  │
└──────────────────────┴───────────────────────┘
```

### Screen Header with Back Button
```
┌──────────────────────────────────────────────┐
│  Screen Title              [← Back] Button   │
│  Subtitle description                        │
└──────────────────────────────────────────────┘
```

## Navigation States

### Route Stack Examples

**Initial State:**
```
Stack: [Home]
```

**After clicking "Build Drink":**
```
Stack: [Home, DrinkBuilder]
```

**After clicking Back:**
```
Stack: [Home]
```

**After clicking "Payment" then Cancel:**
```
Stack: [Home, Payment] → [Home]
```

**After clicking "Payment" then successful payment:**
```
Stack: [Home, Payment] → [Home]
(clears stack to Home)
```

## Button Visual Design

### Quick Action Button Structure
```
┌─────────────────┐
│                 │
│      🎨         │  ← Emoji (28sp)
│                 │
│  Build Drink    │  ← Label (Body2, Medium)
│                 │
└─────────────────┘
  80dp height
  Rounded corners (12dp)
  Elevation (4dp → 8dp on press)
```

### Back Button Style
```
[← Back]
  ↑
  │
  └─── Text Button with arrow
       Color: onPrimary
       Style: Button typography
```

## Screen Transitions

Current implementation uses default Compose Navigation transitions:
- **Enter**: Fade in + slide from right
- **Exit**: Fade out + slide to left
- **Pop Enter**: Fade in + slide from left
- **Pop Exit**: Fade out + slide to right

## Color Scheme

Navigation UI uses theme colors:
- **Buttons**: MaterialTheme.colors.surface (background)
- **Button Text**: MaterialTheme.colors.onSurface
- **Header Background**: MaterialTheme.colors.primary
- **Header Text**: MaterialTheme.colors.onPrimary
- **Back Button**: MaterialTheme.colors.onPrimary

## Interactive Elements

1. **Quick Action Buttons**: Tap to navigate
2. **Back Buttons**: Tap to go to previous screen
3. **Device Back Button**: Android back gesture/button
4. **Auto-navigation**: Scanner and Payment screens

## Accessibility

All navigation elements include:
- Touch target size (48dp minimum)
- Clear text labels
- Semantic navigation actions
- Support for screen readers
- Proper focus handling

## State Preservation

Navigation automatically handles:
- Screen state during navigation
- ViewModel lifecycle
- Configuration changes
- Process death and restoration

## Example User Journey

```
1. User opens app
   └→ Home Screen appears

2. User taps "Build Drink" 🎨
   └→ Custom Drink Builder opens
   └→ User selects coffee, size, adds syrup
   └→ User saves drink

3. User taps "← Back"
   └→ Returns to Home Screen

4. User taps "Rewards" ⭐
   └→ Reward Info Screen opens
   └→ User reads about loyalty program

5. User presses device back button
   └→ Returns to Home Screen

6. User taps "Scan Code" 📷
   └→ Scanner opens
   └→ User scans barcode
   └→ Automatically returns to Home

7. User taps "Payment" 💳
   └→ Payment Screen opens
   └→ User enters info and pays
   └→ Automatically returns to Home
```

## Technical Details

### Navigation Graph Structure
```kotlin
NavHost(startDestination = "home") {
    composable("home") { /* Home */ }
    composable("drink_builder") { /* Drink Builder */ }
    composable("loyalty") { /* Loyalty */ }
    composable("scanner") { /* Scanner */ }
    composable("payment_system") { /* Payment */ }
}
```

### Type-Safe Routes
```kotlin
sealed class Screen(val route: String) {
    object Home : Screen("home")
    object DrinkBuilder : Screen("drink_builder")
    // ...
}
```

## Future Enhancements

Potential UI improvements:
- 🎬 Custom transitions/animations
- 📍 Bottom navigation bar
- 🔄 Pull-to-refresh on Home
- 🌊 Shared element transitions
- 🎨 Theme-aware navigation colors
- 📱 Tablet/landscape optimizations

---

For implementation details, see [NAVIGATION_IMPLEMENTATION.md](NAVIGATION_IMPLEMENTATION.md)
For quick reference, see [NAVIGATION_QUICK_REFERENCE.md](NAVIGATION_QUICK_REFERENCE.md)
