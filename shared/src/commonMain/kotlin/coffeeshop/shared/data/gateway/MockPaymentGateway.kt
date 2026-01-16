package coffeeshop.shared.data.gateway

import coffeeshop.shared.data.model.PaymentInfo
import coffeeshop.shared.data.model.PaymentResult
import coffeeshop.shared.data.model.PaymentStatus
import kotlinx.coroutines.delay
import kotlin.random.Random

/**
 * Mock Payment Gateway Implementation
 * 
 * This is a simulated payment gateway for development and testing.
 * In production, replace this with real gateway implementations:
 * 
 * For Stripe:
 * - Use Stripe SDK for Android/iOS
 * - Integrate with your backend Stripe API
 * - Handle 3D Secure authentication
 * 
 * For PayPal:
 * - Use PayPal Mobile SDK
 * - Implement PayPal OAuth flow
 * - Handle PayPal callbacks
 * 
 * Security Features to Implement:
 * - SSL Certificate Pinning
 * - Request signing and validation
 * - Rate limiting and fraud detection
 * - Secure token storage
 */
class MockPaymentGateway : PaymentGateway {
    
    private val successRate = 0.95 // 95% success rate for testing
    
    override suspend fun processPayment(paymentInfo: PaymentInfo): PaymentResult {
        // Simulate network delay
        delay(2000)
        
        // Generate transaction ID
        val transactionId = generateTransactionId()
        
        // Simulate payment processing with occasional failures for testing
        val isSuccess = Random.nextDouble() < successRate
        
        return if (isSuccess) {
            PaymentResult(
                status = PaymentStatus.SUCCESS,
                transactionId = transactionId,
                message = "Payment processed successfully",
                timestamp = System.currentTimeMillis()
            )
        } else {
            PaymentResult(
                status = PaymentStatus.FAILED,
                transactionId = transactionId,
                message = "Payment failed: Insufficient funds or card declined",
                timestamp = System.currentTimeMillis(),
                errorCode = "PAYMENT_DECLINED"
            )
        }
    }
    
    override suspend fun verifyTransaction(transactionId: String): PaymentResult {
        // Simulate verification delay
        delay(500)
        
        return PaymentResult(
            status = PaymentStatus.SUCCESS,
            transactionId = transactionId,
            message = "Transaction verified",
            timestamp = System.currentTimeMillis()
        )
    }
    
    override suspend fun refundPayment(transactionId: String, amount: Double?): PaymentResult {
        // Simulate refund processing
        delay(1500)
        
        return PaymentResult(
            status = PaymentStatus.SUCCESS,
            transactionId = generateTransactionId(),
            message = "Refund processed successfully",
            timestamp = System.currentTimeMillis()
        )
    }
    
    private fun generateTransactionId(): String {
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        return "TXN_" + (1..12)
            .map { chars.random() }
            .joinToString("")
    }
}
