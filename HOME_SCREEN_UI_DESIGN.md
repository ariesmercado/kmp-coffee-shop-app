# Home Screen UI Design

## Screen Layout

```
┌─────────────────────────────────────┐
│  GREETING SECTION                   │
│  ┌───────────────────────────────┐  │
│  │ Good Morning, Coffee Lover!   │  │
│  │ What can we brew for you?     │  │
│  │                               │  │
│  │ [Coffee Brown Background]     │  │
│  └───────────────────────────────┘  │
│                                     │
│  PROMOTIONAL BANNERS                │
│  ┌─────────────────────┐            │
│  │ Special Offers      │            │
│  └─────────────────────┘            │
│                                     │
│  ┌──────────┐  ┌──────────┐  ┌────┐│
│  │ Banner 1 │  │ Banner 2 │  │ Ban││
│  │ New      │  │ Buy 2    │  │ Loy││
│  │ Season   │  │ Get 1    │  │ alt││
│  │ Specials │  │ Free     │  │ y  ││
│  └──────────┘  └──────────┘  └────┘│
│  [Horizontal Scroll]                │
│                                     │
│  FEATURED DRINKS                    │
│  ┌─────────────────────┐            │
│  │ Featured Drinks     │            │
│  └─────────────────────┘            │
│                                     │
│  ┌──────────────────────────────┐  │
│  │ ☕ │ Caramel Macchiato      │  │
│  │    │ Espresso with vanilla  │  │
│  │    │ $4.95      ⭐ 4.8      │  │
│  └──────────────────────────────┘  │
│                                     │
│  ┌──────────────────────────────┐  │
│  │ ☕ │ Cappuccino             │  │
│  │    │ Rich espresso with...  │  │
│  │    │ $3.95      ⭐ 4.7      │  │
│  └──────────────────────────────┘  │
│                                     │
│  ┌──────────────────────────────┐  │
│  │ ☕ │ Mocha Latte            │  │
│  │    │ Chocolate and espresso │  │
│  │    │ $5.25      ⭐ 4.9      │  │
│  └──────────────────────────────┘  │
│                                     │
│  [... more drinks ...]              │
│                                     │
└─────────────────────────────────────┘
```

## Color Scheme

### Primary Colors
- **Coffee Brown** (#6F4E37) - Primary brand color
- **Light Coffee** (#A0522D) - Secondary accents
- **Dark Coffee** (#3E2723) - Text and emphasis

### Background Colors
- **Creamy White** (#FFF8E7) - Main background
- **Latte Foam** (#FFFAF0) - Card backgrounds

### Accent Colors
- **Caramel Brown** (#D2691E) - Highlights
- **Mocha Brown** (#8B4513) - Banner backgrounds
- **Espresso Black** (#1B0F0A) - Dark theme background

## Components Breakdown

### 1. Greeting Section
```
┌─────────────────────────────────────┐
│  Good Morning, Coffee Lover! ☀️     │
│  What can we brew for you today?    │
└─────────────────────────────────────┘
```
- **Background**: Coffee Brown (#6F4E37)
- **Text Color**: White
- **Padding**: 24dp all sides
- **Time-based greeting**:
  - 5-11 AM: "Good Morning"
  - 12-4 PM: "Good Afternoon"
  - 5-9 PM: "Good Evening"
  - Otherwise: "Hello"

### 2. Banner Carousel
```
┌────────────────────┐ ┌────────────────────┐
│ New Season Specials│ │ Buy 2 Get 1 Free  │
│ Try our winter     │ │ On all espresso   │
│ warmers collection │ │ drinks today      │
└────────────────────┘ └────────────────────┘
```
- **Layout**: Horizontal scroll
- **Card Size**: 300dp × 150dp
- **Corner Radius**: 16dp
- **Elevation**: 4dp
- **Background**: Custom per banner
  - Banner 1: #8B4513 (Saddle Brown)
  - Banner 2: #A0522D (Sienna)
  - Banner 3: #6F4E37 (Coffee Brown)
- **Text Color**: White
- **Spacing**: 12dp between cards

### 3. Featured Drinks List
```
┌──────────────────────────────────────┐
│ ☕  Caramel Macchiato                │
│     Espresso with vanilla syrup and  │
│     steamed milk, topped with caramel│
│     $4.95                  ⭐ 4.8    │
└──────────────────────────────────────┘
```
- **Layout**: Vertical scroll list
- **Card Size**: Full width × 120dp height
- **Corner Radius**: 12dp
- **Elevation**: 2dp
- **Background**: Latte Foam (#FFFAF0)
- **Image Placeholder**: Coffee emoji (☕) 40sp
- **Image Background**: Coffee Brown 30% opacity
- **Image Size**: 80dp × 80dp
- **Spacing**: 12dp between cards

### 4. Drink Card Details
Each drink card displays:
- **Left Side**: Coffee emoji placeholder (80×80dp rounded)
- **Right Side**: 
  - **Name**: 18sp, Semi-bold, Dark Coffee color
  - **Description**: 14sp, Regular, Coffee Brown, 2 lines max
  - **Price**: 16sp, Bold, Coffee Brown
  - **Rating**: 14sp, with star emoji

## Typography

### Headers
- **H1** (Greeting): 28sp, Bold, Dark Coffee
- **H2** (Section Titles): 24sp, Bold, Dark Coffee
- **H3** (Drink Names): 18sp, Semi-bold, Coffee Brown

### Body Text
- **Body1** (Descriptions): 16sp, Regular, Dark Coffee
- **Body2** (Subtitles): 14sp, Regular, Coffee Brown

### Special Text
- **Price**: 16sp, Bold, Coffee Brown
- **Rating**: 14sp, Regular, Coffee Brown

## Spacing System
- **Extra Small**: 4dp
- **Small**: 8dp
- **Medium**: 12dp
- **Large**: 16dp
- **Extra Large**: 24dp

## Sample Data

### Banners (3 items)
1. **New Season Specials**
   - "Try our winter warmers collection"
   - Background: #8B4513

2. **Buy 2 Get 1 Free**
   - "On all espresso drinks today"
   - Background: #A0522D

3. **Loyalty Rewards**
   - "Earn points with every purchase"
   - Background: #6F4E37

### Featured Drinks (6 items)
1. **Caramel Macchiato** - $4.95, ⭐ 4.8
2. **Cappuccino** - $3.95, ⭐ 4.7
3. **Mocha Latte** - $5.25, ⭐ 4.9
4. **Cold Brew** - $4.50, ⭐ 4.6
5. **Vanilla Latte** - $4.45, ⭐ 4.5
6. **Americano** - $3.25, ⭐ 4.4

## Platform Differences

### Android (Jetpack Compose)
- Uses Material Design components
- Supports light and dark themes
- LazyColumn for scrolling
- LazyRow for horizontal banner carousel

### iOS (SwiftUI)
- Uses native SwiftUI components
- ScrollView for main content
- HStack with ScrollView for banner carousel
- VStack for vertical drink list

## Accessibility
- All text is readable with sufficient contrast
- Touch targets are at least 48dp
- Supports dynamic type sizing
- Screen reader friendly with semantic labels
