# Loyalty Program Quick Reference

## Tier Thresholds
| Tier | Points Required | Discount | Emoji |
|------|----------------|----------|-------|
| Bronze | 0 | 0% | 🥉 |
| Silver | 500 | 5% | 🥈 |
| Gold | 1500 | 10% | 🥇 |
| Platinum | 3000 | 15% | 💎 |

## Key APIs

### Get User's Tier
```kotlin
val membership = presenter.getLoyaltyMembership()
val tier = membership.currentTier // LoyaltyTier enum
val tierName = tier.tierName // "Bronze", "Silver", "Gold", "Platinum"
val discount = tier.discountPercentage // 0, 5, 10, 15
```

### Check Progress
```kotlin
val membership = presenter.getLoyaltyMembership()
val progress = membership.progressPercentage // 0-100
val pointsNeeded = membership.pointsToNextTier // Int
val nextTier = membership.nextTier // LoyaltyTier? (null if max)
```

### Add Points (Auto-notifies on tier upgrade)
```kotlin
repository.addRewardPoints(points, description)
// Automatically checks tier upgrade and creates notification
```

### Calculate Tier Discount
```kotlin
val discount = RewardPointsCalculator.calculateTierDiscount(
    purchaseAmount = 50.0,
    tier = membership.currentTier
)
```

## UI Components

### Android (Jetpack Compose)
```kotlin
@Composable
fun ProfileScreen() {
    val membership = presenter.getLoyaltyMembership()
    LoyaltyMembershipCard(membership) // Displays tier card
}
```

### iOS (SwiftUI)
```swift
LoyaltyMembershipCardView(membership: membership)
```

## Color Codes
- Bronze: `#CD7F32`
- Silver: `#C0C0C0`
- Gold: `#FFD700`
- Platinum: `#E5E4E2`
