# Barcode Scanner - Quick Reference

## For Users

### How to Scan a Receipt

1. Open the app and go to the **Profile** screen
2. Tap **"📱 Scan Receipt Barcode"** button
3. **Option A - Camera Scan:**
   - Grant camera permission if prompted
   - Point camera at the barcode on your receipt
   - Hold steady until scan completes
   - See success message with points added

4. **Option B - Manual Entry:**
   - Tap **"Enter Code Manually"**
   - Type the barcode code from your receipt
   - Tap **"Submit"**
   - See success message with points added

### Sample Test Barcode

For testing, you can generate a valid barcode using the format:
```
RECEIPT-<timestamp>-<amount_in_cents>-<checksum>
```

Or use the barcode generator in the app to create test barcodes.

## For Developers

### Quick Integration

#### Android
```kotlin
// Show barcode scanner
BarcodeScannerScreen(
    repository = repository,
    onScanComplete = { success, points, message ->
        // Handle result
        if (success) {
            Toast.makeText(context, "Added $points points!", Toast.LENGTH_LONG).show()
        }
    },
    onBackClick = { /* Navigate back */ }
)
```

#### iOS
```swift
// Show barcode scanner
BarcodeScannerView(
    onScanComplete: { success, points in
        // Handle result
        if success {
            print("Added \(points) points!")
        }
    }
)
```

#### Shared Logic
```kotlin
// Process a barcode
val result = repository.processReceiptBarcode("RECEIPT-...")
if (result.success) {
    println("Added ${result.pointsAdded} points")
} else {
    println("Error: ${result.message}")
}

// Generate test barcode
val testCode = BarcodeValidator.generateTestBarcode(2550) // $25.50
```

### Barcode Format

**Valid Format:**
```
RECEIPT-<timestamp>-<amountCents>-<checksum>
```

**Example:**
```
RECEIPT-1705363200-2550-A3F
```
- Timestamp: Unix timestamp in seconds
- Amount: 2550 cents = $25.50
- Checksum: Last 3 chars of hash for validation

**Validation Rules:**
- Must start with "RECEIPT-"
- Must have valid timestamp (not too old, max 30 days)
- Must have valid checksum
- Cannot be scanned twice

### Points Calculation

**Rule:** 5 points per $1 spent

**Examples:**
- $5.00 → 25 points
- $25.50 → 127 points
- $100.00 → 500 points

### Key Files

**Shared (KMP):**
- `shared/.../data/model/ReceiptBarcode.kt` - Data model
- `shared/.../utils/BarcodeValidator.kt` - Validation logic
- `shared/.../repository/CoffeeRepository.kt` - Processing interface

**Android:**
- `android/.../ui/screen/BarcodeScannerScreen.kt` - Scanner UI
- `android/.../ui/screen/ProfileScreen.kt` - Integration
- `android/src/main/AndroidManifest.xml` - Permissions

**iOS:**
- `iosApp/.../BarcodeScannerView.swift` - Scanner UI
- `iosApp/.../ProfileScreenView.swift` - Integration
- `iosApp/.../Info.plist` - Permissions

### Common Issues

**Camera not working:**
- Check permissions are granted
- Verify Info.plist (iOS) or AndroidManifest.xml (Android) has camera permission

**Barcode not scanning:**
- Try manual entry option
- Ensure good lighting
- Verify barcode format is correct

**"Already scanned" error:**
- Each barcode can only be scanned once
- This prevents duplicate point additions

**Invalid barcode error:**
- Check barcode format matches: RECEIPT-timestamp-amount-checksum
- Verify checksum is correct
- Ensure receipt is not older than 30 days

### Testing

**Generate Test Barcode:**
```kotlin
// Generate a barcode for $25.50
val testBarcode = BarcodeValidator.generateTestBarcode(2550)
// Use this code in manual entry or print it to scan
```

**Test Scenarios:**
1. Valid scan → Points added ✅
2. Duplicate scan → Error shown ✅
3. Invalid format → Error shown ✅
4. Manual entry → Works same as scan ✅
5. No camera permission → Shows permission UI ✅

### API Reference

#### BarcodeValidator
```kotlin
// Validate a barcode string
fun validateBarcode(code: String): ReceiptBarcode

// Calculate points from amount in cents
fun calculatePointsFromAmount(amountCents: Int): Int

// Generate a test barcode
fun generateTestBarcode(amountCents: Int): String
```

#### CoffeeRepository
```kotlin
// Process a scanned barcode
fun processReceiptBarcode(barcodeCode: String): BarcodeScanResult

// Check if barcode was already scanned
fun hasScannedBarcode(barcodeCode: String): Boolean
```

#### BarcodeScanResult
```kotlin
data class BarcodeScanResult(
    val success: Boolean,
    val pointsAdded: Int,
    val message: String,
    val receiptBarcode: ReceiptBarcode?
)
```

## Need More Info?

See [BARCODE_SCANNER_IMPLEMENTATION.md](BARCODE_SCANNER_IMPLEMENTATION.md) for complete documentation.
