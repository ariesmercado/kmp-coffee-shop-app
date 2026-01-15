# Notifications System Visual Guide

## Screen Mockups and Visual Design

This document provides a detailed visual guide for the Notifications System UI design.

---

## 📱 Screen 1: Notifications List View (With Notifications)

### Layout Structure

```
┌─────────────────────────────────────┐
│  ☰  Notifications          🗑️       │  <- Header (Coffee Brown)
│  2 new notifications                │
├─────────────────────────────────────┤
│                                     │
│  ┌───────────────────────────────┐ │
│  │ [Deal]              2h ago    │ │
│  │ ● ☕ Happy Hour Deal!         │ │  <- Unread (blue dot)
│  │ Get 20% off all Frappuccinos │ │
│  │ from 2-5 PM today...       ❌ │ │
│  └───────────────────────────────┘ │
│                                     │
│  ┌───────────────────────────────┐ │
│  │ [Delivered]         5h ago    │ │
│  │ ● Order Delivered             │ │  <- Unread (blue dot)
│  │ Your order #ORD123 has been  │ │
│  │ delivered. Enjoy!          ❌ │ │
│  └───────────────────────────────┘ │
│                                     │
│  ┌───────────────────────────────┐ │
│  │ [Promotion]         1d ago    │ │
│  │ 🎉 New Menu Items!            │ │  <- Read (no dot)
│  │ Try our new Pumpkin Spice    │ │
│  │ Latte and Maple Pecan...   ❌ │ │
│  └───────────────────────────────┘ │
│                                     │
│  ┌───────────────────────────────┐ │
│  │ [Shipping]          2d ago    │ │
│  │ Order On The Way              │ │
│  │ Your order #ORD122 is on its │ │
│  │ way. Expected delivery...   ❌ │ │
│  └───────────────────────────────┘ │
│                                     │
│  ┌───────────────────────────────┐ │
│  │ [Order]             3d ago    │ │
│  │ Order Confirmed               │ │
│  │ We've received your order    │ │
│  │ #ORD124...                  ❌ │ │
│  └───────────────────────────────┘ │
│                                     │
│  ┌───────────────────────────────┐ │
│  │ [Deal]              4d ago    │ │
│  │ ☕ Weekend Special            │ │
│  │ Buy one coffee, get one 50%  │ │
│  │ off this weekend...         ❌ │ │
│  └───────────────────────────────┘ │
│                                     │
└─────────────────────────────────────┘
```

### Visual Elements

#### Header
- **Background**: Coffee Brown (#6F4E37)
- **Title**: "Notifications" - White, 28sp, Bold
- **Subtitle**: "2 new notifications" - White 90% opacity, 16sp
- **Trash Icon**: White, circular background with 20% white overlay
- **Padding**: 24dp all sides

#### Notification Card (Unread)
- **Background**: Light blue tint (5% opacity of primary color)
- **Border Radius**: 12dp
- **Shadow**: Subtle elevation (2dp)
- **Padding**: 16dp
- **Unread Dot**: Blue circle, 8dp diameter, next to title

#### Notification Card (Read)
- **Background**: White/Latte Foam color
- **Border Radius**: 12dp
- **Shadow**: Subtle elevation (2dp)
- **Padding**: 16dp
- **No Dot**: No unread indicator

#### Type Badge
- **Position**: Top-left of card
- **Border Radius**: 12dp
- **Padding**: 8dp horizontal, 4dp vertical
- **Font Size**: 11sp, Bold
- **Colors by Type**:
  - Deal: Orange background (20% opacity), Orange text
  - Promotion: Purple background (20% opacity), Purple text
  - Order: Blue background (20% opacity), Blue text
  - Shipping: Orange background (20% opacity), Orange text
  - Delivered: Green background (20% opacity), Green text

#### Timestamp
- **Position**: Top-right of card
- **Font Size**: 12sp
- **Color**: Gray 50% opacity
- **Format**: Relative time (e.g., "2h ago", "Just now")

#### Clear Button
- **Icon**: ❌ (X mark)
- **Size**: 20dp icon, 32dp touch target
- **Color**: Gray 50% opacity
- **Position**: Top-right corner

#### Card Spacing
- **Between Cards**: 12dp
- **Content Padding**: 16dp horizontal, 8dp vertical

---

## 📱 Screen 2: Empty State

### Layout Structure

```
┌─────────────────────────────────────┐
│  ☰  Notifications          🗑️       │  <- Header (Coffee Brown)
│  No new notifications               │
├─────────────────────────────────────┤
│                                     │
│                                     │
│                                     │
│                                     │
│             🔔                      │  <- Bell Emoji (64sp)
│                                     │
│      No notifications yet           │  <- Title (20sp, Semi-bold)
│                                     │
│   We'll notify you about deals,     │  <- Subtitle (14sp)
│   promotions, and order updates     │
│                                     │
│                                     │
│                                     │
│                                     │
│                                     │
└─────────────────────────────────────┘
```

### Visual Elements

#### Empty State Container
- **Alignment**: Center of screen
- **Padding**: 32dp all sides

#### Bell Icon
- **Emoji**: 🔔
- **Size**: 64sp
- **Alignment**: Center

#### Title Text
- **Text**: "No notifications yet"
- **Font Size**: 20sp
- **Weight**: Semi-bold
- **Color**: Dark coffee 60% opacity
- **Alignment**: Center
- **Spacing**: 16dp below icon

#### Description Text
- **Text**: "We'll notify you about deals, promotions, and order updates"
- **Font Size**: 14sp
- **Weight**: Regular
- **Color**: Coffee brown 40% opacity
- **Alignment**: Center
- **Multiline**: Yes
- **Spacing**: 8dp below title

---

## 📱 Screen 3: Clear All Confirmation Dialog

### Android Dialog

```
┌─────────────────────────────────────┐
│                                     │
│  Clear All Notifications            │  <- Title (Bold)
│                                     │
│  Are you sure you want to clear     │
│  all notifications? This action     │  <- Message
│  cannot be undone.                  │
│                                     │
│              [Cancel] [Clear All]   │  <- Actions
│                                     │
└─────────────────────────────────────┘
```

### iOS Alert

```
┌─────────────────────────────────────┐
│                                     │
│  Clear All Notifications            │  <- Title (Bold)
│                                     │
│  Are you sure you want to clear     │
│  all notifications? This action     │  <- Message
│  cannot be undone.                  │
│                                     │
│  ┌─────────────────────────────┐   │
│  │         Cancel              │   │  <- Cancel Button
│  └─────────────────────────────┘   │
│  ┌─────────────────────────────┐   │
│  │       Clear All             │   │  <- Destructive Button (Red)
│  └─────────────────────────────┘   │
│                                     │
└─────────────────────────────────────┘
```

### Visual Elements

#### Dialog/Alert
- **Background**: White
- **Border Radius**: 12dp (Android), System default (iOS)
- **Shadow**: Material elevation
- **Padding**: 24dp

#### Title
- **Font Size**: 18sp
- **Weight**: Bold
- **Color**: Dark text

#### Message
- **Font Size**: 14sp
- **Weight**: Regular
- **Color**: Dark text 70% opacity
- **Line Spacing**: 20sp

#### Buttons
- **Cancel**: Secondary style, regular weight
- **Clear All**: Primary color (Android), destructive red (iOS), bold

---

## 🎨 Color Palette

### Primary Colors
```
Coffee Brown (Primary):    #6F4E37
Dark Coffee:               #4A3728
Light Brown:               #8B6F47
```

### Background Colors
```
Creamy White:              #FFFEF9
Latte Foam:                #F5F5F0
Card Background:           #FFFFFF
```

### Notification Type Colors

#### Deal (Orange)
```
Background: #FF9800 (20% opacity)
Text:       #E65100
```

#### Promotion (Purple)
```
Background: #9C27B0 (20% opacity)
Text:       #6A1B9A
```

#### Order (Blue)
```
Background: #2196F3 (20% opacity)
Text:       #1565C0
```

#### Shipping (Orange)
```
Background: #FF9800 (20% opacity)
Text:       #E65100
```

#### Delivered (Green)
```
Background: #4CAF50 (20% opacity)
Text:       #2E7D32
```

#### General (Gray)
```
Background: #607D8B (20% opacity)
Text:       #37474F
```

### Status Colors
```
Unread Indicator:  #2196F3 (Blue)
Clear Button:      #757575 (Gray 50% opacity)
```

---

## 📐 Dimensions

### Spacing
```
Screen Padding:     16dp
Card Padding:       16dp
Card Spacing:       12dp
Header Padding:     24dp
Empty State:        32dp
```

### Sizes
```
Header Title:       28sp
Card Title:         16sp
Card Message:       14sp
Badge Text:         11sp
Timestamp:          12sp
Empty Icon:         64sp
Empty Title:        20sp
Empty Message:      14sp
```

### Touch Targets
```
Clear Button:       32dp minimum
Trash Icon:         48dp minimum
```

### Card Dimensions
```
Width:              Match parent - 32dp (16dp padding each side)
Min Height:         ~120dp (dynamic based on content)
Border Radius:      12dp
Elevation:          2dp
```

---

## 🎭 Animation & Interaction

### Card Dismissal
- **Duration**: 300ms
- **Easing**: Ease-out
- **Effect**: Fade out + slide right

### Clear All
- **Dialog**: Fade in (200ms)
- **List Clear**: Staggered fade out (50ms delay between items)
- **Empty State**: Fade in (300ms)

### Scroll Behavior
- **Over-scroll**: Rubber band effect (iOS), glow effect (Android)
- **Momentum**: Native platform behavior

---

## ♿ Accessibility

### Touch Targets
- All interactive elements: Minimum 48dp x 48dp
- Clear buttons: 32dp icon + padding to reach 48dp
- Trash icon: 48dp touch target

### Color Contrast
- Text on Coffee Brown: White (21:1 ratio)
- Card text on white: Dark coffee (>7:1 ratio)
- Badge text on backgrounds: All meet WCAG AA standards (>4.5:1)

### Screen Reader
- **Android**: Content descriptions for all icons
- **iOS**: VoiceOver labels for all interactive elements

---

## 📱 Platform Differences

### Android (Material Design)
- Material shadows and elevation
- Ripple effects on buttons
- Material dialogs
- System back button support

### iOS (Human Interface Guidelines)
- No elevation, subtle shadows only
- Native alert controller
- Swipe gesture support
- Safe area insets respected

---

## 🔍 Details to Notice

1. **Unread Indicator**: Small blue dot (8dp) appears only on unread notifications
2. **Background Tint**: Unread cards have a subtle blue tint (5% opacity)
3. **Type Badges**: Always visible, color-coded, uppercase labels
4. **Timestamps**: Relative time for recent (< 7 days), date format for older
5. **Clear Button**: Subtle gray, increases opacity on hover/press
6. **Empty State**: Centered vertically, uses emoji for warmth
7. **Header Count**: Dynamic, only shows when unread count > 0

---

## ✨ Polish Details

- **Card Shadows**: Subtle, consistent across all cards
- **Rounded Corners**: 12dp on all cards and badges
- **Spacing**: Consistent 12dp vertical spacing between cards
- **Padding**: 16dp horizontal screen padding maintained throughout
- **Typography**: Hierarchy clear with size and weight variations
- **Icons**: Consistent 20-24dp size for all icons
- **Touch Feedback**: Native platform ripple/highlight effects

---

This visual guide ensures consistent implementation across both Android and iOS platforms while maintaining the coffee shop's warm and inviting aesthetic.
