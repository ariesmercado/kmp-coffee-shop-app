package coffeeshop.shared.data.model

data class SavedPaymentMethod(
    val id: String,
    val paymentMethod: PaymentMethod,
    val displayName: String,
    val lastFourDigits: String? = null,
    val expiryMonth: Int? = null,
    val expiryYear: Int? = null,
    val isDefault: Boolean = false,
    val billingAddress: BillingAddress? = null
)
