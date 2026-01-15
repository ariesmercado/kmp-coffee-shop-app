# Order Summary Screen - Visual Guide

## Screen Layout Overview

The Order Summary Screen follows a clean, organized layout with the warm coffee theme throughout.

### Layout Structure

```
┌─────────────────────────────────────┐
│  ORDER SUMMARY HEADER               │ ← Coffee Brown background
│  "Order Summary"                    │   White text
│  "Review your order before checkout"│
├─────────────────────────────────────┤
│                                     │
│  📋 Order Items                     │ ← Section Header
│                                     │
│  ┌─────────────────────────────┐   │
│  │ Caramel Macchiato      ×2   │   │ ← Order Item Card
│  │ Size: Medium                │   │   (Latte Foam background)
│  │ Add-ons:                    │   │
│  │  • Extra Shot               │   │
│  │  • Whipped Cream            │   │
│  │ Base Price: $4.95  $11.90   │   │
│  └─────────────────────────────┘   │
│                                     │
│  ┌─────────────────────────────┐   │
│  │ Cappuccino             ×1   │   │ ← Order Item Card
│  │ Size: Large                 │   │
│  │ Base Price: $4.45   $4.45   │   │
│  └─────────────────────────────┘   │
│                                     │
│  ┌─────────────────────────────┐   │
│  │ Cold Brew              ×1   │   │ ← Order Item Card
│  │ Size: Medium                │   │
│  │ Add-ons:                    │   │
│  │  • Vanilla Syrup            │   │
│  │ Base Price: $4.50   $5.00   │   │
│  └─────────────────────────────┘   │
│                                     │
│  💰 Cost Breakdown                  │ ← Section Header
│                                     │
│  ┌─────────────────────────────┐   │
│  │ Subtotal         $21.35     │   │ ← Cost Card
│  │ Tax (8%)          $1.71     │   │   (Latte Foam background)
│  │ ───────────────────────     │   │
│  │ Total            $23.06     │   │   Bold, Coffee Brown
│  └─────────────────────────────┘   │
│                                     │
├─────────────────────────────────────┤
│  ┌─────────────────────────────┐   │
│  │   Proceed to Payment        │   │ ← Primary Button
│  └─────────────────────────────┘   │   (Coffee Brown, White text)
│                                     │
│  ┌─────────────────────────────┐   │
│  │      Edit Order             │   │ ← Secondary Button
│  └─────────────────────────────┘   │   (Outlined, Coffee Brown)
└─────────────────────────────────────┘
```

## Color Scheme

### Primary Colors
- **Background**: Creamy White (#FFF8E7) - Warm, inviting base
- **Primary**: Coffee Brown (#6F4E37) - Main brand color
- **Surface**: Latte Foam (#FFFAF0) - Card backgrounds

### Text Colors
- **Headers**: White on Coffee Brown backgrounds
- **Body**: Dark Coffee (#3E2723) for main content
- **Price Highlights**: Coffee Brown for emphasis

## Component Details

### 1. Header Section
- **Background**: Coffee Brown gradient
- **Title**: "Order Summary" - Bold, 28sp/28pt
- **Subtitle**: "Review your order before checkout" - Regular, 16sp/16pt
- **Text Color**: White with 90% opacity for subtitle

### 2. Order Items Section

#### Order Item Card
Each card displays:
- **Item Name & Quantity**: Bold header with quantity badge (×N)
- **Size**: "Size: [Small/Medium/Large]"
- **Add-ons**: Bulleted list if present
- **Prices**: 
  - Base price (subtle, smaller text)
  - Item total (bold, prominent, Coffee Brown)

**Card Styling**:
- Rounded corners (12dp/12pt)
- Subtle shadow for depth
- Latte Foam background
- 16dp/16pt padding

### 3. Cost Breakdown Section

#### Cost Breakdown Card
Displays three rows:
1. **Subtotal**: Sum of all items
2. **Tax**: Calculated with percentage shown
3. **Total**: Bold, larger font, Coffee Brown color

**Separator**: Horizontal divider between tax and total

**Card Styling**:
- Same as order item cards
- Clean, spacious layout
- Right-aligned numbers

### 4. Action Buttons Section

#### Proceed to Payment (Primary)
- **Style**: Filled button
- **Color**: Coffee Brown background
- **Text**: White, bold, 16sp/16pt
- **Height**: 56dp/56pt
- **Shape**: Rounded corners (12dp/12pt)
- **Shadow**: Elevated appearance

#### Edit Order (Secondary)
- **Style**: Outlined button
- **Border**: 2dp/2pt Coffee Brown
- **Text**: Coffee Brown, bold, 16sp/16pt
- **Height**: 56dp/56pt
- **Shape**: Rounded corners (12dp/12pt)
- **Background**: Transparent

**Button Layout**:
- Primary button on top
- 12dp/12pt spacing
- Full width
- Fixed to bottom

## Typography

### Android (Jetpack Compose)
- **H1 Headers**: MaterialTheme.typography.h1, 28sp, Bold
- **H2 Section**: MaterialTheme.typography.h2, 20sp, Bold
- **H3 Item Names**: MaterialTheme.typography.h3, 18sp, Bold
- **Body Text**: MaterialTheme.typography.body1, 16sp
- **Small Text**: MaterialTheme.typography.body2, 14sp

### iOS (SwiftUI)
- **Headers**: System font, size 28, weight .bold
- **Section Headers**: System font, size 20, weight .bold
- **Item Names**: System font, size 18, weight .bold
- **Body Text**: System font, size 16
- **Small Text**: System font, size 14

## Spacing & Padding

### Consistent Spacing
- **Screen Padding**: 16dp/16pt horizontal
- **Card Padding**: 16dp/16pt all sides
- **Item Spacing**: 12dp/12pt between cards
- **Section Spacing**: 8dp/8pt between sections
- **Button Spacing**: 12dp/12pt between buttons

### Margins
- **Header**: 24dp/24pt all sides
- **Content**: 16dp/16pt horizontal
- **Bottom**: 16dp/16pt for button container

## Responsive Design

### Content Scrolling
- **Android**: LazyColumn for efficient scrolling
- **iOS**: ScrollView for vertical content
- Header and buttons remain fixed
- Content area is scrollable

### Overflow Handling
- Long item names: Single line, truncated
- Add-ons list: Vertical list, no limit
- Descriptions: Word wrap enabled

## Accessibility

### Text Contrast
- All text meets WCAG AA standards
- High contrast between text and backgrounds
- Primary action button has clear visual weight

### Touch Targets
- All buttons: 56dp/56pt height (minimum 48dp/48pt)
- Cards: Full-width tap targets
- Adequate spacing between interactive elements

## Design Consistency

### With Existing Screens
- Follows same header pattern as Menu Screen
- Uses consistent card design from Home Screen
- Maintains warm coffee theme throughout
- Same button styles as other screens

### Platform-Specific Adaptations
- **Android**: Material Design components
- **iOS**: Native SwiftUI components
- Both maintain visual consistency in:
  - Color scheme
  - Layout structure
  - Spacing and padding
  - Typography hierarchy

## Sample Data Visualization

### Order Example
```
Item 1: Caramel Macchiato (Medium, ×2)
├─ Base: $4.95
├─ Add-ons: Extra Shot, Whipped Cream
└─ Total: $11.90

Item 2: Cappuccino (Large, ×1)
├─ Base: $4.45
└─ Total: $4.45

Item 3: Cold Brew (Medium, ×1)
├─ Base: $4.50
├─ Add-ons: Vanilla Syrup
└─ Total: $5.00

Summary:
├─ Subtotal: $21.35
├─ Tax (8%): $1.71
└─ Total: $23.06
```

## User Flow

1. **Entry**: User arrives from menu/cart screen
2. **Review**: User reviews all order items
3. **Check Details**: Verifies sizes, add-ons, quantities
4. **Review Cost**: Checks subtotal, tax, and total
5. **Action**:
   - → "Edit Order": Returns to previous screen
   - → "Proceed to Payment": Continues to payment screen

## Animation & Interaction

### Transitions
- Smooth fade-in for screen appearance
- Subtle elevation on button press
- Card tap feedback (if interactive)

### Loading States
- Sample order loads immediately
- Future: Loading indicator for API calls

## Edge Cases

### Empty Order
- Not applicable (sample order always present)
- Future: Show empty state with CTA

### Long Add-ons List
- Vertical layout handles multiple items
- Scrollable container accommodates overflow

### Large Numbers
- Price formatting handles decimals correctly
- Thousands separator for large amounts

## Implementation Notes

### Android Specifics
- Uses `remember` for state management
- Composable functions for modularity
- Material Theme integration

### iOS Specifics
- Uses `@StateObject` for ViewModel
- View structs for component separation
- Native SwiftUI styling

### Shared Logic
- All calculations in Presenter
- Data models in shared module
- Business rules centralized
