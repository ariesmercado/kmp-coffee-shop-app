# Home Screen Implementation

## Overview
This document describes the implementation of the Home Screen for the KMP Coffee Shop App using Kotlin Multiplatform.

## Architecture

### Shared Module (`shared/`)
The shared module contains business logic and data models that are used by both Android and iOS platforms.

#### Data Models
- **`FeaturedDrink`**: Represents a featured coffee drink with properties:
  - `id`: Unique identifier
  - `name`: Drink name
  - `description`: Drink description
  - `price`: Price in dollars
  - `imageUrl`: Image URL (placeholder for now)
  - `rating`: User rating (1-5 scale)

- **`Banner`**: Represents a promotional banner with properties:
  - `id`: Unique identifier
  - `title`: Banner title
  - `subtitle`: Banner subtitle/description
  - `imageUrl`: Image URL (placeholder for now)
  - `backgroundColor`: Hex color code for banner background

- **`User`**: Represents the current user with properties:
  - `name`: User's name
  - `id`: User identifier

#### Repository Layer
- **`CoffeeRepository`**: Interface defining data access methods
  - `getCurrentUser()`: Returns current user
  - `getBanners()`: Returns list of promotional banners
  - `getFeaturedDrinks()`: Returns list of featured drinks

- **`MockCoffeeRepository`**: Mock implementation providing sample data
  - 3 promotional banners
  - 6 featured drinks with realistic descriptions and prices

#### Presentation Layer
- **`HomeScreenPresenter`**: Presentation logic for the Home Screen
  - `getGreeting()`: Returns personalized time-based greeting
  - `getBanners()`: Returns banners from repository
  - `getFeaturedDrinks()`: Returns featured drinks from repository

#### Utilities
- **`GreetingUtils`**: Time-based greeting generation
  - `getCurrentHour()`: Platform-specific implementation (expect/actual)
  - `getGreetingForTime()`: Returns appropriate greeting based on time
    - 5-11 AM: "Good Morning"
    - 12-4 PM: "Good Afternoon"
    - 5-9 PM: "Good Evening"
    - Otherwise: "Hello"
  - `getPersonalizedGreeting()`: Combines greeting with user name

### Android Implementation (`android/`)
Uses Jetpack Compose for UI implementation.

#### Theme System
- **`Color.kt`**: Coffee-themed color palette
  - `CoffeeBrown` (#6F4E37)
  - `LightCoffee` (#A0522D)
  - `DarkCoffee` (#3E2723)
  - `CreamyWhite` (#FFF8E7)
  - `EspressoBlack` (#1B0F0A)
  - `CaramelBrown` (#D2691E)
  - `MochaBrown` (#8B4513)
  - `LatteFoam` (#FFFAF0)

- **`Type.kt`**: Typography styles using Material Design
- **`Theme.kt`**: Light and dark theme configurations

#### UI Components
- **`HomeScreen`**: Main composable for the home screen
- **`GreetingSection`**: Displays personalized greeting with warm coffee theme
- **`BannerCarousel`**: Horizontal scrollable carousel of promotional banners
- **`BannerCard`**: Individual banner card with title and subtitle
- **`FeaturedDrinksSection`**: Vertical list of featured drinks
- **`DrinkCard`**: Individual drink card showing:
  - Coffee emoji as placeholder for image
  - Drink name and description
  - Price and rating

#### Activity
- **`MainActivity`**: Entry point that sets up Compose UI with theme

### iOS Implementation (`iosApp/`)
Uses SwiftUI for UI implementation.

#### Theme System
- **`Theme.swift`**: Coffee-themed colors matching Android palette

#### UI Components
- **`HomeScreenView`**: Main SwiftUI view
- **`GreetingSection`**: Greeting display
- **`BannerCarousel`**: Horizontal scroll view for banners
- **`BannerCard`**: Individual banner card
- **`FeaturedDrinksSection`**: Vertical stack of featured drinks
- **`DrinkCard`**: Individual drink card

#### View Model
- **`HomeViewModel`**: ObservableObject that wraps the shared presenter
  - Uses Kotlin Multiplatform shared code
  - Publishes data to SwiftUI views

## Design System

### Color Palette
The app uses a warm coffee-themed color palette:
- **Primary**: Coffee Brown tones
- **Secondary**: Caramel and Mocha browns
- **Background**: Creamy whites and light foam colors
- **Text**: Dark coffee and espresso colors for readability

### Typography
- **Headings**: Bold, prominent text for sections and titles
- **Body**: Regular weight for descriptions
- **Prices**: Bold to highlight pricing

## Data Flow

1. **HomeScreenPresenter** (Shared)
   - Fetches data from `MockCoffeeRepository`
   - Generates personalized greeting using platform-specific time

2. **Android**
   - `MainActivity` creates `HomeScreen` composable
   - `HomeScreen` creates presenter instance
   - UI components render data using Compose

3. **iOS**
   - `CoffeeShopApp` creates `HomeScreenView`
   - `HomeViewModel` wraps shared presenter
   - SwiftUI views bind to published data

## Features Implemented

### ✅ Personalized Greeting
- Time-based greeting (Morning, Afternoon, Evening)
- Displays user name
- Warm, welcoming message

### ✅ Promotional Banners
- Horizontal carousel layout
- 3 promotional offers
- Custom background colors for each banner
- Clean card design with shadows

### ✅ Featured Drinks
- 6 featured coffee drinks
- Detailed descriptions
- Pricing information
- Star ratings
- Coffee emoji placeholders for images

### ✅ Coffee-Themed Design
- Warm brown color palette
- Professional typography
- Consistent spacing and padding
- Card-based layouts
- Light and dark theme support (Android)

## Building the Project

### Android
```bash
./gradlew :android:build
```

### iOS
Open `iosApp.xcodeproj` in Xcode and build.

## Future Enhancements
1. Add actual images for drinks and banners
2. Implement navigation to drink detail screens
3. Add animations and transitions
4. Integrate with real backend API
5. Add shopping cart functionality
6. Implement user authentication
7. Add drink customization options
