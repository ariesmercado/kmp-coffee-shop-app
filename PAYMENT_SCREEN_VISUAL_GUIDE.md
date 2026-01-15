# Payment Screen Visual Guide

This document provides a visual description of the Payment Screen UI for both Android and iOS platforms.

## Screen Layout

### Overall Structure

```
┌─────────────────────────────────────────┐
│ HEADER (Coffee Brown Background)        │
│ ╔═══════════════════════════════════╗  │
│ ║ Payment                            ║  │
│ ║ Complete your purchase securely    ║  │
│ ╚═══════════════════════════════════╝  │
├─────────────────────────────────────────┤
│ SCROLLABLE CONTENT                      │
│                                         │
│ ╔═══════════════════════════════════╗  │
│ ║ Total Amount                       ║  │
│ ║ $23.27                             ║  │
│ ║ Including tax: $1.70               ║  │
│ ╚═══════════════════════════════════╝  │
│                                         │
│ Payment Method                          │
│ ┌─────────────────────────────────┐    │
│ │ Credit Card              ( • )  │    │ (Selected)
│ └─────────────────────────────────┘    │
│ ┌─────────────────────────────────┐    │
│ │ Debit Card               (   )  │    │
│ └─────────────────────────────────┘    │
│ ┌─────────────────────────────────┐    │
│ │ PayPal                   (   )  │    │
│ └─────────────────────────────────┘    │
│ ┌─────────────────────────────────┐    │
│ │ Apple Pay                (   )  │    │
│ └─────────────────────────────────┘    │
│ ┌─────────────────────────────────┐    │
│ │ Google Pay               (   )  │    │
│ └─────────────────────────────────┘    │
│                                         │
│ Billing Address                         │
│ ╔═══════════════════════════════════╗  │
│ ║ Full Name:                         ║  │
│ ║ [________________]                 ║  │
│ ║                                    ║  │
│ ║ Address Line 1:                    ║  │
│ ║ [________________]                 ║  │
│ ║                                    ║  │
│ ║ Address Line 2 (Optional):         ║  │
│ ║ [________________]                 ║  │
│ ║                                    ║  │
│ ║ City:                              ║  │
│ ║ [________________]                 ║  │
│ ║                                    ║  │
│ ║ State:         ZIP Code:           ║  │
│ ║ [______]       [______]            ║  │
│ ║                                    ║  │
│ ║ Country:                           ║  │
│ ║ [________________]                 ║  │
│ ╚═══════════════════════════════════╝  │
│                                         │
├─────────────────────────────────────────┤
│ BOTTOM BUTTONS (Fixed)                  │
│ ┌─────────────────────────────────────┐ │
│ │   Confirm Payment (Coffee Brown)    │ │
│ └─────────────────────────────────────┘ │
│ ┌─────────────────────────────────────┐ │
│ │   Cancel (Outlined)                 │ │
│ └─────────────────────────────────────┘ │
└─────────────────────────────────────────┘
```

## Component Details

### 1. Header Section
- **Background Color**: Coffee Brown (#6F4E37)
- **Text Color**: White
- **Title**: "Payment" (28sp/28pt, Bold)
- **Subtitle**: "Complete your purchase securely" (16sp/16pt)
- **Padding**: 24dp/24pt all around

### 2. Total Amount Card
- **Background**: Light Coffee Brown (10% opacity)
- **Corner Radius**: 12dp/12pt
- **Elevation/Shadow**: 2dp/2pt
- **Content**:
  - "Total Amount" label (16sp/16pt, gray)
  - Amount: "$23.27" (36sp/36pt, Bold, Coffee Brown)
  - Tax info: "Including tax: $1.70" (14sp/14pt, light gray)
- **Padding**: 20dp/20pt

### 3. Payment Method Section
- **Title**: "Payment Method" (20sp/20pt, Bold, Dark Coffee)
- **Cards**:
  - Background: Latte Foam (#FFFAF0) when unselected, Light tint when selected
  - Corner Radius: 12dp/12pt
  - Border: 2dp/2pt Coffee Brown when selected, none when unselected
  - Elevation: 4dp/4pt when selected, 2dp/2pt when unselected
  - Height: ~56dp/56pt
  - Content:
    - Payment method name (16sp/16pt)
    - Radio button on the right (24dp/24pt)

### 4. Billing Address Form Card
- **Title**: "Billing Address" (20sp/20pt, Bold, Dark Coffee)
- **Card Background**: Latte Foam (#FFFAF0)
- **Corner Radius**: 12dp/12pt
- **Elevation/Shadow**: 2dp/2pt
- **Fields**:
  - Each field has a label above it (14sp/14pt, Medium weight)
  - Text fields have:
    - White background
    - 1dp/1pt border (light gray)
    - 8dp/8pt corner radius
    - 12dp/12pt padding
  - State and ZIP Code are side by side (50% width each)
  - All fields full width except State/ZIP row
- **Form Padding**: 16dp/16pt

### 5. Action Buttons
- **Container Background**: Latte Foam
- **Container Padding**: 16dp/16pt
- **Confirm Payment Button**:
  - Background: Coffee Brown (#6F4E37)
  - Text: White, Bold, 16sp/16pt
  - Height: 56dp/56pt
  - Corner Radius: 12dp/12pt
  - Elevation: 4dp/4pt
  - Shows spinner when processing
- **Cancel Button**:
  - Background: Transparent
  - Border: 2dp/2pt Coffee Brown
  - Text: Coffee Brown, Bold, 16sp/16pt
  - Height: 56dp/56pt
  - Corner Radius: 12dp/12pt
- **Spacing**: 12dp/12pt between buttons

### 6. Error Message
- **Text Color**: Red
- **Font Size**: 14sp/14pt
- **Position**: Below billing address form
- **Padding**: 8dp/8pt vertical
- **Visibility**: Only shown when validation fails

## Interaction States

### Payment Method Selection
- **Unselected**: 
  - Normal background (Latte Foam)
  - No border
  - Empty radio circle
  - Normal elevation
- **Selected**: 
  - Tinted background (Coffee Brown 5% opacity)
  - 2dp/2pt Coffee Brown border
  - Filled radio circle
  - Increased elevation

### Form Fields
- **Default**: 
  - White background
  - Light gray border
  - Dark Coffee text
- **Focused**: 
  - Coffee Brown border (Android)
  - Native focus indicator (iOS)
- **Error State**: 
  - Red error message appears below form

### Confirm Payment Button
- **Default**: 
  - Coffee Brown background
  - White text
  - "Confirm Payment" label
- **Processing**: 
  - 60% opacity Coffee Brown background
  - White spinner/progress indicator
  - Disabled state
- **Disabled**: 
  - Reduced opacity
  - No interaction

## Responsive Behavior

### Android (Jetpack Compose)
- Uses LazyColumn for efficient scrolling
- Forms adapt to screen width with fillMaxWidth()
- Buttons fixed at bottom using Column with weight(1f)
- State and ZIP Code use Row with equal weights

### iOS (SwiftUI)
- Uses ScrollView for content
- Forms use VStack with maxWidth alignment
- Buttons fixed at bottom
- State and ZIP Code use HStack with equal spacing

## Accessibility

### Text Contrast
- All text meets WCAG AA contrast requirements
- Primary text: Dark Coffee on Light backgrounds
- Button text: White on Coffee Brown

### Touch Targets
- All interactive elements minimum 56dp/56pt height
- Adequate spacing between elements (12-16dp/pt)
- Clear visual feedback on interaction

### Form Labels
- Clear labels above each field
- Required vs Optional clearly indicated
- Error messages specific and actionable

## Validation Feedback

### Success State
- No error message shown
- Confirm button enabled
- Normal form appearance

### Error State
- Red error message below form
- Error text explains the issue:
  - "Full name is required"
  - "Address line 1 is required"
  - "City is required"
  - "State is required"
  - "ZIP code is required"
  - "Invalid ZIP code format"
  - "Country is required"
- Form fields maintain focus
- Confirm button remains enabled (validates on click)

## Coffee Theme Integration

The Payment Screen fully integrates with the coffee-themed design:
1. **Color Palette**: Uses coffee-inspired colors throughout
2. **Typography**: Consistent with other screens
3. **Card Design**: Matches Menu and Order Summary screens
4. **Button Style**: Consistent with existing navigation buttons
5. **Spacing**: Uses 12dp/16dp grid system
6. **Elevation**: Subtle shadows for depth

## Platform Differences

### Android (Jetpack Compose)
- Material Design components
- Uses Material TextField with outlined style
- Radio buttons use Material RadioButton
- Native Android keyboard types

### iOS (SwiftUI)
- SwiftUI native components
- Custom TextField style for consistency
- Custom radio button implementation (Circle with fill)
- Native iOS keyboard types

Both platforms achieve visual parity while respecting platform conventions.
