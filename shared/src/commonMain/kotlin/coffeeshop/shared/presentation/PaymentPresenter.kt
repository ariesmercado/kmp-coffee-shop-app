package coffeeshop.shared.presentation

import coffeeshop.shared.data.gateway.MockPaymentGateway
import coffeeshop.shared.data.gateway.PaymentGateway
import coffeeshop.shared.data.model.BillingAddress
import coffeeshop.shared.data.model.PaymentInfo
import coffeeshop.shared.data.model.PaymentMethod
import coffeeshop.shared.data.model.PaymentReceipt
import coffeeshop.shared.data.model.PaymentResult
import coffeeshop.shared.data.model.PaymentTransaction
import coffeeshop.shared.data.model.SavedPaymentMethod
import coffeeshop.shared.data.repository.PaymentMethodsRepository

data class ValidationResult(
    val isValid: Boolean,
    val errorMessage: String = ""
)

class PaymentPresenter(
    private val paymentGateway: PaymentGateway = MockPaymentGateway(),
    private val paymentMethodsRepository: PaymentMethodsRepository = PaymentMethodsRepository()
) {
    
    /**
     * Validates billing address fields
     */
    fun validateBillingAddress(
        fullName: String,
        addressLine1: String,
        city: String,
        state: String,
        zipCode: String,
        country: String
    ): ValidationResult {
        if (fullName.isBlank()) {
            return ValidationResult(false, "Full name is required")
        }
        if (addressLine1.isBlank()) {
            return ValidationResult(false, "Address line 1 is required")
        }
        if (city.isBlank()) {
            return ValidationResult(false, "City is required")
        }
        if (state.isBlank()) {
            return ValidationResult(false, "State is required")
        }
        if (zipCode.isBlank()) {
            return ValidationResult(false, "ZIP code is required")
        }
        if (!isValidZipCode(zipCode)) {
            return ValidationResult(false, "Invalid ZIP code format")
        }
        if (country.isBlank()) {
            return ValidationResult(false, "Country is required")
        }
        return ValidationResult(true)
    }
    
    /**
     * Validates ZIP code format (basic validation for US ZIP codes)
     */
    private fun isValidZipCode(zipCode: String): Boolean {
        // Supports both 5-digit and 9-digit ZIP codes (e.g., 12345 or 12345-6789)
        val zipPattern = Regex("^\\d{5}(-\\d{4})?$")
        return zipPattern.matches(zipCode)
    }
    
    /**
     * Creates a PaymentInfo object from validated data
     */
    fun createPaymentInfo(
        orderId: String,
        paymentMethod: PaymentMethod,
        fullName: String,
        addressLine1: String,
        addressLine2: String,
        city: String,
        state: String,
        zipCode: String,
        country: String,
        totalAmount: Double
    ): PaymentInfo {
        val billingAddress = BillingAddress(
            fullName = fullName.trim(),
            addressLine1 = addressLine1.trim(),
            addressLine2 = addressLine2.trim(),
            city = city.trim(),
            state = state.trim(),
            zipCode = zipCode.trim(),
            country = country.trim()
        )
        
        return PaymentInfo(
            orderId = orderId,
            paymentMethod = paymentMethod,
            billingAddress = billingAddress,
            totalAmount = totalAmount
        )
    }
    
    /**
     * Process payment through payment gateway
     * @return PaymentResult with transaction status
     */
    suspend fun processPayment(paymentInfo: PaymentInfo): PaymentResult {
        return paymentGateway.processPayment(paymentInfo)
    }
    
    /**
     * Gets list of available payment methods
     */
    fun getAvailablePaymentMethods(): List<PaymentMethod> {
        return listOf(
            PaymentMethod.CREDIT_CARD,
            PaymentMethod.DEBIT_CARD,
            PaymentMethod.PAYPAL,
            PaymentMethod.APPLE_PAY,
            PaymentMethod.GOOGLE_PAY
        )
    }
    
    /**
     * Get saved payment methods
     */
    fun getSavedPaymentMethods(): List<SavedPaymentMethod> {
        return paymentMethodsRepository.getSavedPaymentMethods()
    }
    
    /**
     * Save a payment method for future use
     */
    fun savePaymentMethod(
        paymentMethod: PaymentMethod,
        displayName: String,
        lastFourDigits: String? = null,
        billingAddress: BillingAddress? = null,
        setAsDefault: Boolean = false
    ) {
        val savedMethod = SavedPaymentMethod(
            id = generatePaymentMethodId(),
            paymentMethod = paymentMethod,
            displayName = displayName,
            lastFourDigits = lastFourDigits,
            isDefault = setAsDefault,
            billingAddress = billingAddress
        )
        paymentMethodsRepository.savePaymentMethod(savedMethod)
    }
    
    /**
     * Remove a saved payment method
     */
    fun removeSavedPaymentMethod(methodId: String) {
        paymentMethodsRepository.removePaymentMethod(methodId)
    }
    
    /**
     * Generate a payment receipt
     */
    fun generateReceipt(
        transactionId: String,
        orderId: String,
        orderItems: List<coffeeshop.shared.data.model.OrderItem>,
        subtotal: Double,
        tax: Double,
        total: Double,
        paymentMethod: PaymentMethod,
        billingAddress: BillingAddress
    ): PaymentReceipt {
        return PaymentReceipt(
            receiptId = "RCP_${System.currentTimeMillis()}",
            transactionId = transactionId,
            orderId = orderId,
            orderItems = orderItems,
            subtotal = subtotal,
            tax = tax,
            total = total,
            paymentMethod = paymentMethod,
            timestamp = System.currentTimeMillis(),
            billingAddress = billingAddress
        )
    }
    
    private fun generatePaymentMethodId(): String {
        return "PM_${System.currentTimeMillis()}"
    }
}
