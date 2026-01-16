package coffeeshop.shared.utils

import kotlin.random.Random

/**
 * Utility object for generating unique IDs
 * 
 * Uses a combination of timestamp and random characters to ensure uniqueness
 * even when called rapidly in succession.
 */
object IdGenerator {
    
    private const val CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
    
    /**
     * Generate a unique transaction ID
     * Format: TXN_{timestamp}_{random}
     */
    fun generateTransactionId(): String {
        return generateId("TXN")
    }
    
    /**
     * Generate a unique receipt ID
     * Format: RCP_{timestamp}_{random}
     */
    fun generateReceiptId(): String {
        return generateId("RCP")
    }
    
    /**
     * Generate a unique payment method ID
     * Format: PM_{timestamp}_{random}
     */
    fun generatePaymentMethodId(): String {
        return generateId("PM")
    }
    
    /**
     * Generate a unique ID with the given prefix
     * Format: {prefix}_{timestamp}_{random}
     */
    private fun generateId(prefix: String): String {
        val timestamp = System.currentTimeMillis()
        val random = (1..6)
            .map { CHARS.random() }
            .joinToString("")
        return "${prefix}_${timestamp}_${random}"
    }
}
