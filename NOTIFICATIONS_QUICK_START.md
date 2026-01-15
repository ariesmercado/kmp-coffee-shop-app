# Notifications System Quick Start Guide

## Overview

This guide will help you quickly integrate and use the Notifications System in the KMP Coffee Shop App.

## 🚀 Quick Setup

### Android

1. Open `android/src/main/kotlin/coffeeshop/app/MainActivity.kt`
2. Import the NotificationsScreen:
   ```kotlin
   import coffeeshop.app.ui.screen.NotificationsScreen
   ```
3. Set it as your content view:
   ```kotlin
   setContent {
       CoffeeShopTheme {
           NotificationsScreen()
       }
   }
   ```
4. Build and run the app

### iOS

1. Open `iosApp/iosApp/CoffeeShopApp.swift`
2. Set NotificationsScreenView as your initial view:
   ```swift
   var body: some Scene {
       WindowGroup {
           NotificationsScreenView()
       }
   }
   ```
3. Build and run the app

## 📱 What You'll See

### Initial State (With Notifications)
- **Header**: "Notifications" with unread count
- **Trash Icon**: Button to clear all notifications
- **6 Sample Notifications** including:
  - 2 unread notifications (with blue dot indicator)
  - 4 read notifications
  - Various types: deals, promotions, order updates

### Empty State
- Bell emoji (🔔)
- "No notifications yet" message
- Helper text

## 🎯 Key Features to Test

### 1. View Notifications
- Scroll through the list
- Notice color-coded type badges
- Check relative timestamps (e.g., "2h ago")

### 2. Clear Individual Notification
- Tap the ❌ button on any notification card
- Notification disappears instantly

### 3. Clear All Notifications
- Tap the trash icon in the header
- Confirmation dialog appears
- Tap "Clear All" to delete all notifications
- See the empty state

### 4. Unread Indicators
- Unread notifications have:
  - Blue dot next to title
  - Slightly different background color
  - Counted in header ("2 new notifications")

## 🔧 Customization

### Add Your Own Notifications

Edit `shared/src/commonMain/kotlin/coffeeshop/shared/data/repository/MockCoffeeRepository.kt`:

```kotlin
notifications.add(
    Notification(
        id = "custom_1",
        type = NotificationType.DEAL,
        title = "Your Custom Title",
        message = "Your custom message here",
        timestamp = System.currentTimeMillis(),
        isRead = false
    )
)
```

### Change Notification Types

Available types:
- `NotificationType.DEAL` - Orange badge
- `NotificationType.PROMOTION` - Purple badge
- `NotificationType.ORDER_ACCEPTED` - Blue badge
- `NotificationType.ORDER_SHIPPED` - Orange badge
- `NotificationType.ORDER_DELIVERED` - Green badge
- `NotificationType.GENERAL` - Gray badge

## 📂 File Structure

```
kmp-coffee-shop-app/
├── shared/src/commonMain/kotlin/coffeeshop/shared/
│   ├── data/
│   │   ├── model/
│   │   │   └── Notification.kt          # Data model
│   │   └── repository/
│   │       ├── CoffeeRepository.kt      # Interface
│   │       └── MockCoffeeRepository.kt  # Implementation
│   └── presentation/
│       └── NotificationsPresenter.kt    # Business logic
├── android/src/main/kotlin/coffeeshop/app/ui/screen/
│   └── NotificationsScreen.kt           # Android UI
└── iosApp/iosApp/
    └── NotificationsScreenView.swift    # iOS UI
```

## 🎨 Design Elements

### Color Scheme
- Primary: Coffee Brown (#6F4E37)
- Background: Creamy White
- Unread Indicator: Blue
- Type Badges: Color-coded by type

### Typography
- Header Title: 28sp/pt, Bold
- Card Title: 16sp/pt, Bold
- Message: 14sp/pt, Regular
- Timestamp: 12sp/pt, Regular

## 💡 Tips

1. **Testing Empty State**: Clear all notifications to see the empty state
2. **Timestamps**: The system uses relative time (e.g., "2h ago") for recent notifications
3. **State Management**: Notifications update reactively when cleared
4. **Theme Consistency**: UI matches the coffee shop theme throughout

## 🐛 Troubleshooting

### Android Build Issues
- Ensure you're using Java 11 or higher
- Sync Gradle files
- Clean and rebuild: `./gradlew clean build`

### iOS Build Issues
- Ensure Xcode is up to date
- Clean build folder: `Cmd + Shift + K`
- Rebuild: `Cmd + B`

### Notifications Not Appearing
- Check `MockCoffeeRepository.kt` has sample data in init block
- Verify imports are correct
- Ensure presenter is initialized properly

## 📖 Next Steps

1. Review the full implementation in `NOTIFICATIONS_SYSTEM_IMPLEMENTATION.md`
2. Explore the code structure
3. Customize notifications for your use case
4. Consider adding push notification support (Phase 2)

## ✅ Checklist

- [ ] App builds successfully
- [ ] Notifications screen displays
- [ ] Can clear individual notifications
- [ ] Can clear all notifications
- [ ] Empty state appears correctly
- [ ] Unread indicators work
- [ ] Type badges are color-coded
- [ ] Timestamps display correctly

## 🎉 Success!

You've successfully integrated the Notifications System! Users can now view and manage their notifications for deals, promotions, and order updates.

## Need Help?

- Check `NOTIFICATIONS_SYSTEM_IMPLEMENTATION.md` for detailed documentation
- Review the code comments in the implementation files
- Look at existing screens (OrderHistory, Profile) for similar patterns
