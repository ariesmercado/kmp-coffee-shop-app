package coffeeshop.shared.data.model

data class PaymentResult(
    val status: PaymentStatus,
    val transactionId: String,
    val message: String,
    val timestamp: Long = System.currentTimeMillis(),
    val errorCode: String? = null
)
