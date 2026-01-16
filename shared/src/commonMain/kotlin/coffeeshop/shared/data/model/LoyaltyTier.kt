package coffeeshop.shared.data.model

/**
 * Loyalty tiers based on total points earned
 */
enum class LoyaltyTier(
    val tierName: String,
    val pointsRequired: Int,
    val discountPercentage: Int,
    val benefits: List<String>,
    val emoji: String
) {
    BRONZE(
        tierName = "Bronze",
        pointsRequired = 0,
        discountPercentage = 0,
        benefits = listOf(
            "Earn 5 points per dollar",
            "Redeem 100 points for $5 off",
            "Birthday reward"
        ),
        emoji = "🥉"
    ),
    SILVER(
        tierName = "Silver",
        pointsRequired = 500,
        discountPercentage = 5,
        benefits = listOf(
            "Earn 5 points per dollar",
            "Redeem 100 points for $5 off",
            "5% discount on all purchases",
            "Birthday reward + bonus points",
            "Early access to new menu items"
        ),
        emoji = "🥈"
    ),
    GOLD(
        tierName = "Gold",
        pointsRequired = 1500,
        discountPercentage = 10,
        benefits = listOf(
            "Earn 5 points per dollar",
            "Redeem 100 points for $5 off",
            "10% discount on all purchases",
            "Birthday reward + double bonus points",
            "Early access to new menu items",
            "Free drink on your birthday",
            "Priority customer support"
        ),
        emoji = "🥇"
    ),
    PLATINUM(
        tierName = "Platinum",
        pointsRequired = 3000,
        discountPercentage = 15,
        benefits = listOf(
            "Earn 5 points per dollar",
            "Redeem 100 points for $5 off",
            "15% discount on all purchases",
            "Birthday reward + triple bonus points",
            "Early access to new menu items",
            "Free drink on your birthday",
            "Priority customer support",
            "Exclusive platinum-only drinks",
            "Free upgrade to larger size"
        ),
        emoji = "💎"
    );

    companion object {
        /**
         * Get loyalty tier based on total points earned
         */
        fun getTierByPoints(totalPoints: Int): LoyaltyTier {
            return when {
                totalPoints >= PLATINUM.pointsRequired -> PLATINUM
                totalPoints >= GOLD.pointsRequired -> GOLD
                totalPoints >= SILVER.pointsRequired -> SILVER
                else -> BRONZE
            }
        }

        /**
         * Get next tier or null if at max tier
         */
        fun getNextTier(currentTier: LoyaltyTier): LoyaltyTier? {
            return when (currentTier) {
                BRONZE -> SILVER
                SILVER -> GOLD
                GOLD -> PLATINUM
                PLATINUM -> null
            }
        }
    }
}

/**
 * Loyalty membership information
 */
data class LoyaltyMembership(
    val currentTier: LoyaltyTier,
    val totalPointsEarned: Int,
    val currentPoints: Int
) {
    /**
     * Get the next tier, or null if at max tier
     */
    val nextTier: LoyaltyTier?
        get() = LoyaltyTier.getNextTier(currentTier)

    /**
     * Points needed to reach the next tier
     */
    val pointsToNextTier: Int
        get() {
            val next = nextTier ?: return 0
            return next.pointsRequired - totalPointsEarned
        }

    /**
     * Progress percentage to next tier (0-100)
     */
    val progressPercentage: Int
        get() {
            val next = nextTier ?: return 100
            val currentRequired = currentTier.pointsRequired
            val nextRequired = next.pointsRequired
            val range = nextRequired - currentRequired
            val progress = totalPointsEarned - currentRequired
            return ((progress.toFloat() / range.toFloat()) * 100).toInt().coerceIn(0, 100)
        }
}
