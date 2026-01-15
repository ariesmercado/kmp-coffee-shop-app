# Reward Points System - Quick Start Guide

## Testing the Reward Points System

This guide will help you quickly test the newly implemented reward points system on both Android and iOS platforms.

## Prerequisites

- **For Android**: Android Studio Arctic Fox or later
- **For iOS**: Xcode 13 or later, macOS required
- **For Both**: Kotlin Multiplatform Mobile (KMM) plugin installed

## Quick Start - Android

### Step 1: Open the Project
```bash
cd kmp-coffee-shop-app
# Open the project in Android Studio
```

### Step 2: View Profile Screen
The `MainActivity.kt` is already configured to display the Profile screen by default:
```kotlin
ProfileScreen()
```

### Step 3: Build and Run
1. Select an Android emulator or connect a physical device
2. Click the "Run" button (green play icon)
3. Wait for the build to complete

### Step 4: Test the Features
You should see:
- **Profile Header**: "Welcome, Coffee Lover!"
- **Reward Points Card**: 
  - Current balance: 325 points
  - Progress indicator: "175 points to next reward tier"
  - Two buttons: "Learn More" and "Redeem"
- **Transaction History**: List of recent point activities

### Step 5: Explore Reward Info
1. Tap the "Learn More" button
2. Scroll through the information screens:
   - How It Works
   - Earning Points (5 per $1)
   - Redeeming Points (100 = $5)
   - Reward Tiers (Bronze, Silver, Gold, Platinum)

## Quick Start - iOS

### Step 1: Open the iOS Project
```bash
cd kmp-coffee-shop-app/iosApp
# Open iosApp.xcodeproj in Xcode
```

### Step 2: View Profile Screen
The `CoffeeShopApp.swift` is already configured to display the Profile screen:
```swift
ProfileScreenView()
```

### Step 3: Build and Run
1. Select an iOS simulator (iPhone 14 Pro recommended)
2. Click the "Run" button (play icon)
3. Wait for the build to complete

### Step 4: Test the Features
You should see:
- **Profile Header**: "Welcome, Coffee Lover!"
- **Reward Points Card**: 
  - Current balance: 325 points
  - Progress indicator: "175 points to next reward tier"
  - Two buttons: "Learn More" and "Redeem"
- **Transaction History**: List of recent point activities

### Step 5: Explore Reward Info
1. Tap the "Learn More" button
2. A modal sheet will appear with program information
3. Scroll through the sections
4. Tap the "X" button in the top-right to dismiss

## What to Look For

### ✅ Visual Elements to Verify

#### Profile Screen
- [ ] Coffee-brown header with user greeting
- [ ] Large points display (325) in white on brown card
- [ ] Tier progress indicator with 🎯 emoji
- [ ] "Learn More" button (white background)
- [ ] "Redeem" button (caramel/orange background)
- [ ] Transaction list with +/- indicators
- [ ] Dates formatted correctly (MMM dd, yyyy)
- [ ] Scrolling works smoothly

#### Reward Info Screen
- [ ] Four main sections with different colors
- [ ] Emoji headers (☕, ⭐, 🎁, 🏆)
- [ ] Clear explanation of earning rule (5 pts/$1)
- [ ] Clear explanation of redemption (100 pts=$5)
- [ ] Four reward tiers displayed
- [ ] Consistent coffee theme colors

### ✅ Functional Elements to Test

#### Profile Screen
- [ ] Screen loads without errors
- [ ] Points balance displays correctly
- [ ] Transaction history shows 5 sample transactions
- [ ] "Learn More" button responds to taps
- [ ] "Redeem" button is visible (balance ≥ 100)
- [ ] Scrolling through transaction history works

#### Reward Info Screen
- [ ] Opens when "Learn More" is tapped
- [ ] All sections are visible and readable
- [ ] Scrolling through content works
- [ ] Can dismiss/close the screen
- [ ] Returns to profile screen after closing

## Sample Data

The mock repository includes these demo transactions:

| Date | Type | Description | Points |
|------|------|-------------|--------|
| 1 day ago | Earned | Purchase at Coffee Shop - $16.90 | +85 |
| 3 days ago | Earned | Purchase at Coffee Shop - $4.86 | +25 |
| 5 days ago | Redeemed | Redeemed for $5 discount | -100 |
| 7 days ago | Earned | Purchase at Coffee Shop - $23.38 | +115 |
| 10 days ago | Earned | Welcome Bonus | +200 |

**Total Balance**: 325 points (85 + 25 - 100 + 115 + 200)

## Common Issues and Solutions

### Android

**Issue**: "Could not resolve dependencies"
- **Solution**: Ensure you have internet connection for dependency download
- Run: `./gradlew clean build` in terminal

**Issue**: "Shared module not found"
- **Solution**: Build the shared module first
- In Android Studio: Build → Make Module 'shared'

**Issue**: "Compose not found"
- **Solution**: Ensure Android Studio has Jetpack Compose support enabled
- File → Settings → Plugins → Enable Jetpack Compose

### iOS

**Issue**: "Shared framework not found"
- **Solution**: Build the shared framework first
- In Terminal: `cd iosApp && xcodebuild -scheme iosApp -configuration Debug`

**Issue**: "Simulator won't launch"
- **Solution**: Reset the simulator
- Device → Erase All Content and Settings

**Issue**: "Swift cannot find shared module types"
- **Solution**: Clean and rebuild
- Product → Clean Build Folder (Cmd+Shift+K)
- Then rebuild (Cmd+B)

## Switching Between Screens

Want to view other screens? Simply edit the respective files:

### Android (MainActivity.kt)
```kotlin
// Comment out ProfileScreen() and uncomment another:
// ProfileScreen()        // ← Currently active
// MenuScreen()
// OrderHistoryScreen()
// PaymentScreen()
```

### iOS (CoffeeShopApp.swift)
```swift
// Comment out ProfileScreenView() and uncomment another:
// ProfileScreenView()           // ← Currently active
// OrderHistoryScreenView()
// PaymentScreenView()
```

## Next Steps

After verifying the basic functionality:

1. **Integrate with existing flows**: Connect points earning to actual purchases
2. **Implement redemption UI**: Create a dialog/sheet for selecting points to redeem
3. **Add persistence**: Save points to local storage or backend
4. **Add animations**: Enhance the UI with transitions and animations
5. **Add notifications**: Notify users when they reach new tiers

## Expected Behavior

### Points Calculation
- $5 purchase → 25 points (5 × 5)
- $10 purchase → 50 points (10 × 5)
- $16.90 purchase → 84 points (16.90 × 5 = 84.5, truncated to 84)

### Redemption
- 100 points → $5 discount
- 200 points → $10 discount
- 300 points → $15 discount

### Tier Progress
With 325 points:
- Already achieved: Bronze (100), Silver (200)
- Next tier: Gold (500)
- Points needed: 175 (500 - 325)

## Screenshots Location

Once you've tested the implementation, you can take screenshots:
- **Android**: Use Android Studio's screenshot tool or device capture
- **iOS**: Use Xcode's screenshot tool or Simulator menu → File → Screenshot

Save screenshots with descriptive names:
- `android_profile_screen.png`
- `android_reward_info_screen.png`
- `ios_profile_screen.png`
- `ios_reward_info_screen.png`

## Support

If you encounter issues:
1. Check the implementation guide: `REWARD_POINTS_IMPLEMENTATION.md`
2. Review the visual guide: `REWARD_POINTS_VISUAL_GUIDE.md`
3. Verify all files are in place (12 modified/created files)
4. Ensure dependencies are up to date

## Summary

The reward points system is ready to test! You should be able to:
- ✅ View reward points balance
- ✅ See transaction history
- ✅ View tier progress
- ✅ Access reward program information
- ✅ See redemption options

Happy testing! ☕⭐
