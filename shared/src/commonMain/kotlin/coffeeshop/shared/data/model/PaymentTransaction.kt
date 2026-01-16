package coffeeshop.shared.data.model

data class PaymentTransaction(
    val transactionId: String,
    val orderId: String,
    val amount: Double,
    val paymentMethod: PaymentMethod,
    val status: PaymentStatus,
    val timestamp: Long,
    val gatewayResponse: String? = null
)
