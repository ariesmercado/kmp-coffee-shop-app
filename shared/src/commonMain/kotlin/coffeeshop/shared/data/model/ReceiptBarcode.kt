package coffeeshop.shared.data.model

/**
 * Represents a barcode from an in-store receipt
 * Format: RECEIPT-<timestamp>-<amount>-<checksum>
 * Example: RECEIPT-1705363200-2550-A3F
 */
data class ReceiptBarcode(
    val code: String,
    val timestamp: Long = 0,
    val amountCents: Int = 0,
    val isValid: Boolean = false
)

/**
 * Result of scanning or processing a barcode
 */
data class BarcodeScanResult(
    val success: Boolean,
    val pointsAdded: Int = 0,
    val message: String,
    val receiptBarcode: ReceiptBarcode? = null
)
