package coffeeshop.shared.data.model

data class RewardPoints(
    val totalPoints: Int = 0,
    val pointsToNextReward: Int = 0,
    val rewardHistory: List<RewardTransaction> = emptyList()
)

data class RewardTransaction(
    val id: String,
    val type: RewardTransactionType,
    val points: Int,
    val timestamp: Long,
    val details: String
)

enum class RewardTransactionType {
    EARNED,
    REDEEMED
}
