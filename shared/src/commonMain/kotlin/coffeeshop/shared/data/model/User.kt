package coffeeshop.shared.data.model

data class User(
    val name: String,
    val id: String,
    val rewardPoints: Int = 0,
    val totalPointsEarned: Int = 0  // Total points earned throughout history for tier calculation
)
