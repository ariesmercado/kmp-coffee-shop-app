package coffeeshop.shared.data.model

data class BillingAddress(
    val fullName: String,
    val addressLine1: String,
    val addressLine2: String = "",
    val city: String,
    val state: String,
    val zipCode: String,
    val country: String
)
