package coffeeshop.shared.data.model

data class Order(
    val id: String,
    val items: List<OrderItem>,
    val subtotal: Double,
    val taxRate: Double = 0.08, // 8% tax rate
    val tax: Double,
    val total: Double
)
