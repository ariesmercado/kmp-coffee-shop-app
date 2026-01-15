package coffeeshop.shared.data.model

data class OrderHistory(
    val id: String,
    val orderDate: Long, // timestamp in milliseconds
    val items: List<OrderItem>,
    val subtotal: Double,
    val tax: Double,
    val total: Double,
    val status: String = "Completed" // e.g., "Completed", "In Progress", "Cancelled"
)
