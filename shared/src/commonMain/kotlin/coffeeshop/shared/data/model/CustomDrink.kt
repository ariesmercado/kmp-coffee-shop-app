package coffeeshop.shared.data.model

data class CustomDrink(
    val id: String,
    val baseMenuItem: MenuItem,
    val size: DrinkSize,
    val addOns: List<AddOn>,
    val totalPrice: Double,
    val customName: String? = null // Optional custom name for saved drinks
)
