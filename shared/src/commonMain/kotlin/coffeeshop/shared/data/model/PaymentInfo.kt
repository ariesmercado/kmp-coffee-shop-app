package coffeeshop.shared.data.model

data class PaymentInfo(
    val orderId: String,
    val paymentMethod: PaymentMethod,
    val billingAddress: BillingAddress,
    val totalAmount: Double
)
