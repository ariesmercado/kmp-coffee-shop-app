# Reward Points System - Visual Guide

## Screen Layouts

### Profile Screen

```
┌─────────────────────────────────────┐
│  Profile                            │
│  Welcome, Coffee Lover!             │
│  (Coffee Brown Header)              │
└─────────────────────────────────────┘

┌─────────────────────────────────────┐
│  Reward Points              ⭐      │
│                                     │
│  325                                │
│  points available                   │
│                                     │
│  ┌───────────────────────────────┐ │
│  │ 🎯 175 points to next reward  │ │
│  │    tier                       │ │
│  └───────────────────────────────┘ │
│                                     │
│  [Learn More]    [Redeem]          │
│  (Coffee Brown Card)                │
└─────────────────────────────────────┘

Recent Activity
┌─────────────────────────────────────┐
│  (+)  Purchase at Coffee Shop      │
│       $16.90                        │
│       Jan 14, 2026              +85 │
└─────────────────────────────────────┘

┌─────────────────────────────────────┐
│  (+)  Purchase at Coffee Shop      │
│       $4.86                         │
│       Jan 12, 2026              +25 │
└─────────────────────────────────────┘

┌─────────────────────────────────────┐
│  (−)  Redeemed for $5 discount     │
│       Jan 10, 2026            -100  │
└─────────────────────────────────────┘
```

### Reward Info Screen

```
┌─────────────────────────────────────┐
│  Reward Program                  [X]│
│  Earn points with every purchase    │
│  and redeem for discounts           │
│  (Coffee Brown Header)              │
└─────────────────────────────────────┘

┌─────────────────────────────────────┐
│  ☕ How It Works                    │
│                                     │
│  Our reward program is designed to  │
│  thank you for being a loyal        │
│  customer. Every time you make a    │
│  purchase, you'll automatically...  │
└─────────────────────────────────────┘

┌─────────────────────────────────────┐
│  ⭐ Earning Points                  │
│                                     │
│  ┌───────────────────────────────┐ │
│  │ 5 points per $1 spent         │ │
│  │ Example: A $5 coffee earns    │ │
│  │ you 25 points!                │ │
│  └───────────────────────────────┘ │
│                                     │
│  Points are automatically added...  │
│  (Coffee Brown Card)                │
└─────────────────────────────────────┘

┌─────────────────────────────────────┐
│  🎁 Redeeming Points                │
│                                     │
│  ┌───────────────────────────────┐ │
│  │ 100 points = $5 discount      │ │
│  │ Redeem in multiples of 100    │ │
│  │ points                        │ │
│  └───────────────────────────────┘ │
│                                     │
│  Apply your points at checkout...   │
│  (Caramel Brown Card)               │
└─────────────────────────────────────┘

┌─────────────────────────────────────┐
│  🏆 Reward Tiers                    │
│                                     │
│  ┌───────────────────────────────┐ │
│  │ Bronze                        │ │
│  │ 100 points                    │ │
│  │         First redemption      │ │
│  └───────────────────────────────┘ │
│                                     │
│  ┌───────────────────────────────┐ │
│  │ Silver                        │ │
│  │ 200 points                    │ │
│  │         Unlock bonus offers   │ │
│  └───────────────────────────────┘ │
│                                     │
│  ┌───────────────────────────────┐ │
│  │ Gold                          │ │
│  │ 500 points                    │ │
│  │         Priority rewards      │ │
│  └───────────────────────────────┘ │
│                                     │
│  ┌───────────────────────────────┐ │
│  │ Platinum                      │ │
│  │ 1000 points                   │ │
│  │         Exclusive perks       │ │
│  └───────────────────────────────┘ │
└─────────────────────────────────────┘
```

## Color Scheme

### Android (Jetpack Compose)
```kotlin
val CoffeeBrown = Color(0xFF6F4E37)      // Primary brown
val CaramelBrown = Color(0xFFD2691E)     // Accent for redeem
val CreamyWhite = Color(0xFFFFF8E7)      // Background
val LatteFoam = Color(0xFFFFFAF0)        // Card surfaces
```

### iOS (SwiftUI)
```swift
CoffeeColors.coffeeBrown                  // Primary brown
CoffeeColors.caramelBrown                 // Accent for redeem
CoffeeColors.creamyWhite                  // Background
CoffeeColors.latteFoam                    // Card surfaces
```

## User Interaction Flow

### Viewing Points Balance
```
User Opens App
    ↓
Navigate to Profile
    ↓
View Reward Points Card
    ├── See current balance (325 points)
    ├── See progress to next tier (175 points needed)
    └── See available actions (Learn More / Redeem)
    ↓
Scroll to Transaction History
    └── Review recent earnings and redemptions
```

### Learning About Program
```
User on Profile Screen
    ↓
Tap "Learn More" Button
    ↓
Reward Info Screen Opens
    ├── Android: New screen navigation
    └── iOS: Modal sheet presentation
    ↓
User Reads Sections
    ├── How It Works
    ├── Earning Points (5 per $1)
    ├── Redeeming Points (100 = $5)
    └── Reward Tiers
    ↓
Close/Back to Profile
```

### Redeeming Points
```
User Has ≥ 100 Points
    ↓
"Redeem" Button Appears
    ↓
User Taps "Redeem"
    ↓
Redemption Process
    ├── Select points to redeem (100, 200, etc.)
    ├── Confirm redemption
    └── Apply discount at checkout
    ↓
Points Deducted
    └── Transaction recorded in history
```

## Points Calculation Examples

### Earning Points
```
Purchase Amount    Points Earned    Calculation
───────────────────────────────────────────────
$3.50              17 points        3.50 × 5 = 17
$5.00              25 points        5.00 × 5 = 25
$10.00             50 points        10.00 × 5 = 50
$16.90             84 points        16.90 × 5 = 84.5 → 84
$23.38             116 points       23.38 × 5 = 116.9 → 116
```

### Redeeming Points
```
Points Used    Discount Value    Calculation
──────────────────────────────────────────────
100            $5.00             100 ÷ 100 × 5 = 5
200            $10.00            200 ÷ 100 × 5 = 10
300            $15.00            300 ÷ 100 × 5 = 15
500            $25.00            500 ÷ 100 × 5 = 25
```

### Tier Progress
```
Current Points    Next Tier    Points Needed
────────────────────────────────────────────
25                100          75
150               200          50
325               500          175
750               1000         250
1000+             MAX          0
```

## Component Hierarchy

### Android (Jetpack Compose)
```
ProfileScreen
├── ProfileHeader(userName)
├── RewardPointsCard
│   ├── Points Display
│   ├── Tier Progress Indicator
│   └── Action Buttons
│       ├── Learn More Button
│       └── Redeem Button (conditional)
└── Transaction List
    └── TransactionItem (repeated)
        ├── Icon (+ or -)
        ├── Description & Date
        └── Points Amount

RewardInfoScreen
├── RewardInfoHeader
├── HowItWorksSection
├── EarningPointsSection
├── RedeemingPointsSection
└── RewardTiersSection
    └── RewardTierItem (repeated)
```

### iOS (SwiftUI)
```
ProfileScreenView
├── ProfileHeader(userName)
├── RewardPointsCard
│   ├── Points Display
│   ├── Tier Progress Indicator
│   └── Action Buttons
│       ├── Learn More Button
│       └── Redeem Button (conditional)
└── Transaction List
    └── TransactionItemView (ForEach)
        ├── Icon Circle
        ├── VStack (Description & Date)
        └── Points Amount

RewardInfoScreenView (Sheet)
├── NavigationView
│   └── ScrollView
│       ├── RewardInfoHeader
│       ├── HowItWorksSection
│       ├── EarningPointsSection
│       ├── RedeemingPointsSection
│       └── RewardTiersSection
│           └── RewardTierItemView (repeated)
└── Toolbar (Close Button)
```

## Key UI Elements

### Transaction Item States
```
EARNED (Positive)
┌────────────────────────────┐
│ (+)  Description           │
│      Date              +XX │
└────────────────────────────┘
Colors: Green/Brown tint

REDEEMED (Negative)
┌────────────────────────────┐
│ (−)  Description           │
│      Date              -XX │
└────────────────────────────┘
Colors: Orange/Caramel tint
```

### Reward Points Card States
```
CAN REDEEM (≥ 100 points)
┌─────────────────────────────┐
│ [Learn More]    [Redeem]    │
└─────────────────────────────┘

CANNOT REDEEM (< 100 points)
┌─────────────────────────────┐
│ [Learn More]                │
└─────────────────────────────┘
```

### Tier Progress Indicator
```
BELOW MAX TIER
┌───────────────────────────────┐
│ 🎯 XXX points to next reward  │
│    tier                       │
└───────────────────────────────┘

AT MAX TIER (1000+ points)
(Indicator not shown)
```

## Platform-Specific Details

### Android Specifics
- Material Design components
- Card elevation (2dp, 4dp)
- RoundedCornerShape (8dp, 12dp, 16dp)
- LazyColumn for scrolling
- SimpleDateFormat for dates

### iOS Specifics
- SwiftUI native components
- Shadow instead of elevation
- cornerRadius for rounded shapes
- ScrollView + ForEach for lists
- DateFormatter for dates
- Sheet presentation for modals
- NavigationView with toolbar

## Accessibility Considerations

### Text Sizes
- Headers: 28sp/28pt
- Subheaders: 20-24sp/pt
- Body text: 14-16sp/pt
- Small text: 12sp/pt

### Contrast
- White text on brown backgrounds (4.5:1 ratio)
- Brown text on cream backgrounds (4.5:1 ratio)
- Clear visual hierarchy

### Interactive Elements
- Minimum touch target: 48dp × 48dp
- Clear button labels
- Distinct visual states

## Summary

The Reward Points System provides:
- **Clear Visualization**: Large, prominent points display
- **Easy Navigation**: Simple flow between screens
- **Informative Content**: Detailed program explanation
- **Engaging Design**: Coffee-themed colors and emojis
- **Cross-Platform Consistency**: Same experience on Android and iOS
- **Intuitive Interactions**: Clear actions and feedback

Users can easily:
1. ✅ View their reward points balance
2. ✅ Track their transaction history
3. ✅ Learn about the program mechanics
4. ✅ Understand earning and redemption rules
5. ✅ See their progress toward reward tiers
6. ✅ Redeem points when eligible
