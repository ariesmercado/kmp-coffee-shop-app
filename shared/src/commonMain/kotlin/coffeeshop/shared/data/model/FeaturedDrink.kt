package coffeeshop.shared.data.model

data class FeaturedDrink(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val rating: Double
)
