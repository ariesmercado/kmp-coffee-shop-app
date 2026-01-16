# Custom Drink Builder - Visual Guide

## Screen Flow Overview

```
┌─────────────────────────────────────┐
│   Custom Drink Builder              │
│   Create your perfect beverage      │
│                                     │
│   ● ─── ○ ─── ○                    │
│   1     2     3                     │
└─────────────────────────────────────┘
```

## Step 1: Choose Your Base Drink

```
┌─────────────────────────────────────┐
│   Custom Drink Builder              │
│   Create your perfect beverage      │
│                                     │
│   ● ─── ○ ─── ○                    │
│   1     2     3                     │
├─────────────────────────────────────┤
│                                     │
│   Step 1: Choose Your Base Drink   │
│                                     │
│ ┌─────────────────────────────────┐ │
│ │ ☕  Espresso            $2.50   │ │
│ └─────────────────────────────────┘ │
│                                     │
│ ┌─────────────────────────────────┐ │
│ │ ☕  Cappuccino          $3.95  ✓│ │  ← Selected
│ └─────────────────────────────────┘ │
│                                     │
│ ┌─────────────────────────────────┐ │
│ │ ☕  Caramel Macchiato   $4.95   │ │
│ └─────────────────────────────────┘ │
│                                     │
│ ┌─────────────────────────────────┐ │
│ │ ☕  Mocha Latte         $5.25   │ │
│ └─────────────────────────────────┘ │
│                                     │
├─────────────────────────────────────┤
│              [ Next ]                │
└─────────────────────────────────────┘
```

## Step 2: Select Size

```
┌─────────────────────────────────────┐
│   Custom Drink Builder              │
│   Create your perfect beverage      │
│                                     │
│   ● ─── ● ─── ○                    │
│   1     2     3                     │
├─────────────────────────────────────┤
│   Total Price: $4.94               │
├─────────────────────────────────────┤
│                                     │
│   Step 2: Select Size               │
│                                     │
│ ┌─────────────────────────────────┐ │
│ │ Small                           │ │
│ │ $3.36                           │ │
│ └─────────────────────────────────┘ │
│                                     │
│ ┌─────────────────────────────────┐ │
│ │ Medium                          │ │
│ │ $3.95                           │ │
│ └─────────────────────────────────┘ │
│                                     │
│ ┌─────────────────────────────────┐ │
│ │ Large                         ✓ │ │  ← Selected
│ │ $4.94                           │ │
│ └─────────────────────────────────┘ │
│                                     │
├─────────────────────────────────────┤
│   [ Previous ]      [ Next ]        │
└─────────────────────────────────────┘
```

## Step 3: Add Extras

```
┌─────────────────────────────────────┐
│   Custom Drink Builder              │
│   Create your perfect beverage      │
│                                     │
│   ● ─── ● ─── ●                    │
│   1     2     3                     │
├─────────────────────────────────────┤
│   Total Price: $6.19               │  ← Updates in real-time
├─────────────────────────────────────┤
│                                     │
│   Step 3: Add Extras (Optional)    │
│                                     │
│ ┌─────────────────────────────────┐ │
│ │ Extra Shot              +$0.75 ✓│ │  ← Selected
│ └─────────────────────────────────┘ │
│                                     │
│ ┌─────────────────────────────────┐ │
│ │ Whipped Cream           +$0.50 ✓│ │  ← Selected
│ └─────────────────────────────────┘ │
│                                     │
│ ┌─────────────────────────────────┐ │
│ │ Almond Milk             +$0.50  │ │
│ └─────────────────────────────────┘ │
│                                     │
│ ┌─────────────────────────────────┐ │
│ │ Oat Milk                +$0.50  │ │
│ └─────────────────────────────────┘ │
│                                     │
│ ┌─────────────────────────────────┐ │
│ │ Vanilla Syrup           +$0.50  │ │
│ └─────────────────────────────────┘ │
│                                     │
├─────────────────────────────────────┤
│   [ Previous ]   [ Save Drink ]     │
└─────────────────────────────────────┘
```

## Save Dialog

```
┌─────────────────────────────────────┐
│        Save Custom Drink            │
├─────────────────────────────────────┤
│                                     │
│  Give your drink a custom name      │
│  (optional):                        │
│                                     │
│  ┌───────────────────────────────┐  │
│  │ My Perfect Morning Cappuccino │  │
│  └───────────────────────────────┘  │
│                                     │
│                                     │
├─────────────────────────────────────┤
│   [ Cancel ]           [ Save ]     │
└─────────────────────────────────────┘
```

## Success Message

```
┌─────────────────────────────────────┐
│             Success!                │
├─────────────────────────────────────┤
│                                     │
│  Your custom drink has been saved   │
│  for quick reordering.              │
│                                     │
├─────────────────────────────────────┤
│              [ OK ]                 │
└─────────────────────────────────────┘
```

## Price Calculation Example

```
Base Drink: Cappuccino
Base Price: $3.95

Size Selection: Large (1.25x)
Adjusted Base: $3.95 × 1.25 = $4.94

Add-ons Selected:
  ├─ Extra Shot:      +$0.75
  └─ Whipped Cream:   +$0.50
  
Add-ons Total: $1.25

─────────────────────────────
TOTAL PRICE: $6.19
─────────────────────────────
```

## Design Features

### Color Scheme (Coffee Shop Theme)
- **Primary**: Coffee Brown (#6F4E37)
- **Background**: Creamy White (#FFF8E7)
- **Accent**: Caramel Brown (#D2691E)
- **Text**: Dark Coffee (#3E2723)

### Visual Feedback
```
Unselected Item:
┌─────────────────────────┐
│ Item Name        Price  │
└─────────────────────────┘

Selected Item:
┏━━━━━━━━━━━━━━━━━━━━━━━━━┓  ← Brown border
┃ Item Name        Price ✓┃  ← Checkmark
┗━━━━━━━━━━━━━━━━━━━━━━━━━┛
```

### Step Indicator States
```
Active Step:   ●
Inactive Step: ○
Completed:     ● (filled)

Example Flow:
Step 1: ● ─── ○ ─── ○
Step 2: ● ─── ● ─── ○
Step 3: ● ─── ● ─── ●
```

## Platform-Specific Notes

### Android (Jetpack Compose)
- Material Design components
- Smooth animations between steps
- Elevation effects on cards
- Ripple effects on buttons

### iOS (SwiftUI)
- Native iOS design patterns
- Sheet presentation for save dialog
- Native alerts for success messages
- Smooth transitions

## Responsive Design
- Works on all screen sizes
- Scrollable content areas
- Fixed navigation buttons at bottom
- Fixed price preview at top

## Accessibility Features
- Content descriptions for screen readers
- High contrast color scheme
- Clear visual hierarchy
- Touch targets meet minimum size requirements

## User Experience Flow
```
1. User enters screen → Sees Step 1
2. Selects base drink → Next button enabled
3. Clicks Next → Proceeds to Step 2
4. Selects size → Sees price update
5. Clicks Next → Proceeds to Step 3
6. Optionally selects add-ons → Price updates
7. Clicks Save Drink → Shows dialog
8. Enters custom name (optional) → Clicks Save
9. Sees success message → Drink is saved
```

## Navigation Controls

```
Step 1: Only [Next] available
        (requires drink selection)

Step 2: [Previous] [Next] available

Step 3: [Previous] [Save Drink] available
```

## Data Persistence

The custom drink is saved with:
- Unique ID (timestamp + random)
- Base menu item reference
- Selected size
- List of add-ons
- Total calculated price
- Optional custom name

This data can be retrieved later for quick reordering.
