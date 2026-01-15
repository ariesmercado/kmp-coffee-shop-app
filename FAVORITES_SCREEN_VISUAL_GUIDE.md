# Favorites Screen Visual Guide

## Screen Layouts

### Favorites Screen with Items

```
┌─────────────────────────────────────┐
│  My Favorites                 ☕    │  <- Coffee Brown Header (#6F4E37)
│  Your favorite coffee drinks        │     White text
└─────────────────────────────────────┘
│                                     │
│  ┌───────────────────────────────┐ │
│  │  ☕   Caramel Macchiato    ❤️  │ │  <- Card (Latte Foam #FFFAF0)
│  │       Espresso with vanilla... │ │
│  │       $4.95        ⭐ 4.8      │ │
│  └───────────────────────────────┘ │
│                                     │
│  ┌───────────────────────────────┐ │
│  │  ☕   Mocha Latte          ❤️  │ │
│  │       Chocolate and espresso...│ │
│  │       $5.25        ⭐ 4.9      │ │
│  └───────────────────────────────┘ │
│                                     │
│  ┌───────────────────────────────┐ │
│  │  ☕   Cold Brew            ❤️  │ │
│  │       Smooth cold-steeped...   │ │
│  │       $4.50        ⭐ 4.6      │ │
│  └───────────────────────────────┘ │
│                                     │
└─────────────────────────────────────┘
```

### Favorites Screen Empty State

```
┌─────────────────────────────────────┐
│  My Favorites                 ☕    │  <- Coffee Brown Header
│  Your favorite coffee drinks        │
└─────────────────────────────────────┘
│                                     │
│                                     │
│                                     │
│              ☕                      │  <- Large coffee emoji (72pt)
│                                     │
│        No Favorites Yet             │  <- Bold heading (24pt)
│                                     │
│    Start adding your favorite       │  <- Instructional text
│    drinks to see them here!         │     (16pt, 60% opacity)
│                                     │
│                                     │
│                                     │
└─────────────────────────────────────┘
```

### Menu Screen with Favorites Integration

```
┌─────────────────────────────────────┐
│  Our Menu                      ☕   │  <- Coffee Brown Header
│  Browse our selection of drinks     │
└─────────────────────────────────────┘
│  ┌───────────────────────────────┐ │  <- Search Bar
│  │  🔍 Search for drinks...      │ │
│  └───────────────────────────────┘ │
│                                     │
│  [Espresso] [Blended] [Hot] [Iced] │  <- Category chips
│                                     │
│  ┌───────────────────────────────┐ │
│  │  ☕   Espresso            🤍  │ │  <- Unfavorited (outline)
│  │       Rich and bold...         │ │
│  │       $2.50        ⭐ 4.7      │ │
│  └───────────────────────────────┘ │
│                                     │
│  ┌───────────────────────────────┐ │
│  │  ☕   Americano           🤍  │ │
│  │       Espresso with hot water..│ │
│  │       $3.25        ⭐ 4.4      │ │
│  └───────────────────────────────┘ │
│                                     │
│  ┌───────────────────────────────┐ │
│  │  ☕   Cappuccino          ❤️  │ │  <- Favorited (filled)
│  │       Rich espresso with...    │ │
│  │       $3.95        ⭐ 4.7      │ │
│  └───────────────────────────────┘ │
│                                     │
└─────────────────────────────────────┘
```

## Color Palette

### Primary Colors
```
Coffee Brown:    ████  #6F4E37  RGB(111, 78, 55)
Light Coffee:    ████  #A0522D  RGB(160, 82, 45)
Dark Coffee:     ████  #3E2723  RGB(62, 39, 35)
```

### Background Colors
```
Creamy White:    ████  #FFF8E7  RGB(255, 248, 231)
Latte Foam:      ████  #FFFAF0  RGB(255, 250, 240)
```

### Accent Colors
```
Favorite Pink:   ████  #E91E63  RGB(233, 30, 99)
```

## Typography Scale

### Headers
```
Screen Title:     28pt/sp  Bold       Coffee Brown/White
Subtitle:         16pt/sp  Regular    Coffee Brown/White (90% opacity)
Section Title:    24pt/sp  Bold       Dark Coffee
```

### Body Text
```
Drink Name:       18pt/sp  Semibold   Dark Coffee
Description:      14pt/sp  Regular    Coffee Brown
Price:            16pt/sp  Bold       Coffee Brown
Rating:           14pt/sp  Regular    Coffee Brown
Empty State Msg:  16pt/sp  Regular    Coffee Brown (60% opacity)
```

## Component Specifications

### Card Component
```
Dimensions:
- Width: Match parent
- Height: 120dp/pt
- Corner Radius: 12dp/pt
- Elevation/Shadow: 2dp/pt

Padding:
- Internal: 16dp/pt
- Between cards: 12dp/pt

Content:
- Image placeholder: 80x80dp/pt (rounded 8dp/pt)
- Space after image: 16dp/pt
- Favorite button: 48dp/pt touch area, 28-32dp/pt icon
```

### Header Component
```
Dimensions:
- Width: Match parent
- Padding: 24dp/pt all sides

Content:
- Background: Coffee Brown (#6F4E37)
- Text Color: White
- Title Size: 28pt/sp Bold
- Subtitle Size: 16pt/sp Regular
- Spacing: 8dp/pt between title and subtitle
```

### Empty State Component
```
Layout:
- Centered vertically and horizontally
- Padding: 32dp/pt

Content:
- Coffee emoji: 72pt/sp
- Spacing: 16dp/pt
- Heading: 24pt/sp Bold
- Spacing: 8dp/pt
- Message: 16pt/sp Regular, centered, 60% opacity
```

## Icon Specifications

### Favorite Icons
```
Android (Material Icons):
- Unfavorited: Icons.Default.FavoriteBorder
- Favorited: Icons.Default.Favorite
- Size: 28dp
- Color: #E91E63 (filled), 60% onSurface (outline)

iOS (SF Symbols):
- Unfavorited: "heart"
- Favorited: "heart.fill"
- Size: 24pt
- Color: #E91E63 (filled), Coffee Brown 60% (outline)
```

### Other Icons
```
Coffee Cup Emoji: ☕ (40sp/pt in cards, 72sp/pt in empty state)
Star Emoji: ⭐ (used with rating display)
Search Icon: 🔍 or system search icon
```

## Interaction States

### Button States
```
Normal:
- Scale: 1.0
- Opacity: 1.0

Pressed:
- Scale: 0.95
- Opacity: 0.7
- Duration: 100ms

Disabled:
- Opacity: 0.4
```

### Toggle Animation
```
Favorite Toggle:
- Duration: 200ms
- Easing: Ease-in-out
- Scale effect: 1.0 → 1.2 → 1.0
```

## Spacing System

### Padding Scale
```
Extra Small:  4dp/pt
Small:        8dp/pt
Medium:       16dp/pt
Large:        24dp/pt
Extra Large:  32dp/pt
```

### Usage
```
Card horizontal padding:     16dp/pt
Card vertical padding:       16dp/pt
Screen horizontal margins:   16dp/pt
List item spacing:           12dp/pt
Section spacing:             16-24dp/pt
Header padding:              24dp/pt
```

## Accessibility

### Content Descriptions
```
Favorite Button (Favorited):   "Remove from favorites"
Favorite Button (Unfavorited): "Add to favorites"
Coffee Image Placeholder:      "Coffee drink"
```

### Minimum Touch Targets
```
All interactive elements: 48dp/pt minimum
Icon buttons: 48dp/pt touch area (visual can be smaller)
```

### Text Contrast
```
All text meets WCAG AA standards:
- White on Coffee Brown: 4.8:1
- Dark Coffee on Creamy White: 11.2:1
- Coffee Brown on Latte Foam: 7.5:1
```

## Responsive Design

### Small Screens (< 360dp width)
```
- Reduce card height to 100dp
- Reduce header font to 24sp
- Reduce image size to 64dp
- Reduce padding to 12dp
```

### Large Screens (> 600dp width)
```
- Max content width: 600dp centered
- Increase header padding to 32dp
- Consider two-column grid for cards
```

## Platform-Specific Notes

### Android
- Uses Material Design 2 components
- Elevation for depth (2dp for cards)
- Ripple effect on touchable elements
- StatusBar color matches header

### iOS
- Uses native SwiftUI components
- Shadow for depth (opacity 0.1, radius 2, offset y:2)
- Native iOS button press animation
- Safe area insets respected

## Summary

This visual guide provides detailed specifications for implementing the Favorites Screen. All measurements, colors, and typography follow the existing Coffee Shop App design system and maintain consistency across Android and iOS platforms.
