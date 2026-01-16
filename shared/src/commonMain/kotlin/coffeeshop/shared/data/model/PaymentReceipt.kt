package coffeeshop.shared.data.model

data class PaymentReceipt(
    val receiptId: String,
    val transactionId: String,
    val orderId: String,
    val orderItems: List<OrderItem>,
    val subtotal: Double,
    val tax: Double,
    val total: Double,
    val paymentMethod: PaymentMethod,
    val timestamp: Long,
    val billingAddress: BillingAddress
)
