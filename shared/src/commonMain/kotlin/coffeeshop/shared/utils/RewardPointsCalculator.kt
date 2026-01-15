package coffeeshop.shared.utils

/**
 * Utility class for calculating reward points
 * Rules:
 * - Earn 5 points per dollar spent
 * - Minimum redemption: 100 points = $5 discount
 * - Points tiers: 100, 200, 500, 1000 points
 */
object RewardPointsCalculator {
    
    private const val POINTS_PER_DOLLAR = 5
    private const val MIN_REDEMPTION_POINTS = 100
    private const val REDEMPTION_DOLLAR_VALUE = 5.0
    
    /**
     * Calculate points earned from a purchase amount
     * @param purchaseAmount The total purchase amount in dollars
     * @return Number of points earned
     */
    fun calculatePointsEarned(purchaseAmount: Double): Int {
        return (purchaseAmount * POINTS_PER_DOLLAR).toInt()
    }
    
    /**
     * Check if user has enough points to redeem
     * @param currentPoints User's current points
     * @return True if user can redeem points
     */
    fun canRedeemPoints(currentPoints: Int): Boolean {
        return currentPoints >= MIN_REDEMPTION_POINTS
    }
    
    /**
     * Calculate discount amount from points
     * @param pointsToRedeem Number of points to redeem (must be multiple of 100)
     * @return Discount amount in dollars
     * Note: Only multiples of MIN_REDEMPTION_POINTS are credited. 
     * For example, 150 points = $5 (50 points ignored)
     */
    fun calculateDiscountFromPoints(pointsToRedeem: Int): Double {
        if (pointsToRedeem < MIN_REDEMPTION_POINTS) return 0.0
        return (pointsToRedeem / MIN_REDEMPTION_POINTS) * REDEMPTION_DOLLAR_VALUE
    }
    
    /**
     * Get the next reward tier
     * @param currentPoints User's current points
     * @return Points needed to reach next tier, or 0 if at max tier
     */
    fun getPointsToNextTier(currentPoints: Int): Int {
        val tiers = listOf(100, 200, 500, 1000)
        for (tier in tiers) {
            if (currentPoints < tier) {
                return tier - currentPoints
            }
        }
        return 0 // Already at or above max tier
    }
    
    /**
     * Get available redemption options based on current points
     * @param currentPoints User's current points
     * @return List of redemption options (points, discount value)
     */
    fun getRedemptionOptions(currentPoints: Int): List<Pair<Int, Double>> {
        val options = mutableListOf<Pair<Int, Double>>()
        val maxRedemptions = currentPoints / MIN_REDEMPTION_POINTS
        
        for (i in 1..maxRedemptions) {
            val points = i * MIN_REDEMPTION_POINTS
            val discount = i * REDEMPTION_DOLLAR_VALUE
            options.add(Pair(points, discount))
        }
        
        return options
    }
}
