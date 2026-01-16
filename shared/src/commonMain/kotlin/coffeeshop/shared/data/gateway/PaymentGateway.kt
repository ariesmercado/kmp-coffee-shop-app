package coffeeshop.shared.data.gateway

import coffeeshop.shared.data.model.PaymentInfo
import coffeeshop.shared.data.model.PaymentResult

/**
 * Payment Gateway Interface
 * 
 * This interface defines the contract for payment processing.
 * Implementations should integrate with real payment gateways like:
 * - Stripe SDK
 * - PayPal SDK
 * - Square SDK
 * 
 * Security Considerations:
 * - All communication should use HTTPS/SSL encryption
 * - Follow PCI DSS compliance standards
 * - Never store raw card data - use tokenization
 * - Implement proper error handling and logging
 */
interface PaymentGateway {
    
    /**
     * Process a payment transaction
     * @param paymentInfo Payment information including billing and amount
     * @return PaymentResult with transaction status and details
     */
    suspend fun processPayment(paymentInfo: PaymentInfo): PaymentResult
    
    /**
     * Verify payment transaction status
     * @param transactionId The transaction ID to verify
     * @return PaymentResult with current status
     */
    suspend fun verifyTransaction(transactionId: String): PaymentResult
    
    /**
     * Refund a payment transaction
     * @param transactionId The transaction ID to refund
     * @param amount Optional partial refund amount
     * @return PaymentResult indicating refund status
     */
    suspend fun refundPayment(transactionId: String, amount: Double? = null): PaymentResult
}
