# KMP Coffee Shop App

This is a Kotlin Multiplatform project for a Coffee Shop app.

## Project Structure

- **shared**: Contains business logic, data models, networking, and shared code following a clean architecture approach.
- **androidApp**: Android-specific implementation using Jetpack Compose.
- **iosApp**: iOS-specific implementation using SwiftUI.

## Features

### Custom Drink Builder ✨ NEW
Create personalized beverages with our intuitive three-step builder:
- **Step 1**: Choose your base drink from the full menu
- **Step 2**: Select your preferred size (Small, Medium, Large)
- **Step 3**: Add extras like syrups, shots, and milk alternatives
- **Live Price Preview**: See the total price update in real-time
- **Save & Reorder**: Save your favorite custom drinks for quick reordering

📖 [Implementation Guide](CUSTOM_DRINK_BUILDER_IMPLEMENTATION.md) | [Quick Start](CUSTOM_DRINK_BUILDER_QUICK_START.md) | [Visual Guide](CUSTOM_DRINK_BUILDER_VISUAL_GUIDE.md)

### Other Features
- Menu browsing with categories
- Favorites management
- Order history tracking
- Reward points system
- Notifications
- Payment processing
- User profiles