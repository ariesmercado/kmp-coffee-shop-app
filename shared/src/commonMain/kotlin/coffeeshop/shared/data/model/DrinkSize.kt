package coffeeshop.shared.data.model

enum class DrinkSize(val displayName: String, val priceMultiplier: Double) {
    SMALL("Small", 0.85),
    MEDIUM("Medium", 1.0),
    LARGE("Large", 1.25)
}
