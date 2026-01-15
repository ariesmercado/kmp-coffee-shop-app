package coffeeshop.shared.data.model

data class OrderItem(
    val id: String,
    val menuItemId: String,
    val name: String,
    val size: String, // e.g., "Small", "Medium", "Large"
    val addOns: List<String>, // e.g., ["Extra Shot", "Whipped Cream"]
    val quantity: Int,
    val basePrice: Double,
    val itemTotalPrice: Double // basePrice * quantity + add-ons cost
)
