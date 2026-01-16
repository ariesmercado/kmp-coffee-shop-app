package coffeeshop.shared.utils

import coffeeshop.shared.data.model.ReceiptBarcode

/**
 * Utility class for validating and parsing receipt barcodes
 * Barcode format: RECEIPT-<timestamp>-<amountCents>-<checksum>
 * Example: RECEIPT-1705363200-2550-A3F
 */
object BarcodeValidator {
    
    private const val BARCODE_PREFIX = "RECEIPT-"
    
    /**
     * Validates and parses a barcode string
     * @param code The barcode string to validate
     * @return ReceiptBarcode with validation status
     */
    fun validateBarcode(code: String): ReceiptBarcode {
        if (!code.startsWith(BARCODE_PREFIX)) {
            return ReceiptBarcode(code = code, isValid = false)
        }
        
        val parts = code.removePrefix(BARCODE_PREFIX).split("-")
        if (parts.size != 3) {
            return ReceiptBarcode(code = code, isValid = false)
        }
        
        val timestamp = parts[0].toLongOrNull() ?: return ReceiptBarcode(code = code, isValid = false)
        val amountCents = parts[1].toIntOrNull() ?: return ReceiptBarcode(code = code, isValid = false)
        val checksum = parts[2]
        
        // Validate checksum (simple validation: first 3 chars of hash)
        val expectedChecksum = calculateChecksum(timestamp, amountCents)
        if (checksum != expectedChecksum) {
            return ReceiptBarcode(
                code = code,
                timestamp = timestamp,
                amountCents = amountCents,
                isValid = false
            )
        }
        
        // Validate timestamp is not too old (within last 30 days)
        val currentTime = System.currentTimeMillis() / 1000
        val thirtyDaysInSeconds = 30 * 24 * 60 * 60
        if (timestamp > currentTime || timestamp < currentTime - thirtyDaysInSeconds) {
            return ReceiptBarcode(
                code = code,
                timestamp = timestamp,
                amountCents = amountCents,
                isValid = false
            )
        }
        
        return ReceiptBarcode(
            code = code,
            timestamp = timestamp,
            amountCents = amountCents,
            isValid = true
        )
    }
    
    /**
     * Calculate checksum for validation
     * Uses a simple hash of timestamp and amount
     */
    private fun calculateChecksum(timestamp: Long, amountCents: Int): String {
        val combined = "$timestamp$amountCents"
        val hash = combined.hashCode().toString(16).uppercase()
        return hash.takeLast(3).padStart(3, '0')
    }
    
    /**
     * Generate a test barcode for development/testing
     * @param amountCents Purchase amount in cents
     * @return Valid barcode string
     */
    fun generateTestBarcode(amountCents: Int): String {
        val timestamp = System.currentTimeMillis() / 1000
        val checksum = calculateChecksum(timestamp, amountCents)
        return "$BARCODE_PREFIX$timestamp-$amountCents-$checksum"
    }
    
    /**
     * Calculate reward points from amount in cents
     * @param amountCents Amount in cents
     * @return Points to award (5 points per dollar)
     */
    fun calculatePointsFromAmount(amountCents: Int): Int {
        val dollars = amountCents / 100.0
        return RewardPointsCalculator.calculatePointsEarned(dollars)
    }
}
