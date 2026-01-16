# Barcode Scanner for Rewards - Implementation Guide

## Overview

The Barcode Scanner feature allows users to scan barcodes from their in-store receipts to automatically add reward points to their account. This feature is implemented using Kotlin Multiplatform (KMP) with native implementations for Android and iOS.

## Features

### ✅ Implemented Features

1. **Camera-based Barcode Scanning**
   - Android: Uses CameraX and ML Kit Barcode Scanning
   - iOS: Uses AVFoundation for camera and barcode detection
   
2. **Manual Code Entry**
   - Users can manually type in barcodes if camera scanning doesn't work
   - Accessible from both platforms via a toggle button
   
3. **Barcode Validation**
   - Shared KMP logic validates barcode format and authenticity
   - Prevents duplicate scans of the same receipt
   - Validates timestamp to ensure receipt is not too old (within 30 days)
   
4. **Reward Points Calculation**
   - Automatically calculates points based on receipt amount (5 points per $1)
   - Updates user's account immediately
   - Shows success/failure notifications
   
5. **UI Integration**
   - "Scan Receipt Barcode" button added to Profile/Rewards screen
   - Camera permission handling on both platforms
   - User-friendly error messages and guidance

## Architecture

### Shared Module (KMP)

**Location**: `/shared/src/commonMain/kotlin/coffeeshop/shared/`

#### Data Models
- `ReceiptBarcode` - Represents a receipt barcode with validation status
- `BarcodeScanResult` - Result of scanning/processing a barcode

#### Utilities
- `BarcodeValidator` - Validates and parses receipt barcodes
  - Format: `RECEIPT-<timestamp>-<amountCents>-<checksum>`
  - Example: `RECEIPT-1705363200-2550-A3F`

#### Repository Methods
- `processReceiptBarcode(barcodeCode: String): BarcodeScanResult` - Validates and processes a barcode
- `hasScannedBarcode(barcodeCode: String): Boolean` - Checks if barcode was already scanned

### Android Implementation

**Location**: `/android/src/main/kotlin/coffeeshop/app/ui/screen/BarcodeScannerScreen.kt`

#### Key Components
- `BarcodeScannerScreen` - Main screen composable
- `CameraPreview` - Camera preview with ML Kit integration
- `ManualBarcodeEntry` - Manual entry UI
- `CameraPermissionRequest` - Permission handling UI

#### Dependencies
```kotlin
// CameraX for barcode scanning
implementation("androidx.camera:camera-camera2:1.2.3")
implementation("androidx.camera:camera-lifecycle:1.2.3")
implementation("androidx.camera:camera-view:1.2.3")

// ML Kit Barcode Scanning
implementation("com.google.mlkit:barcode-scanning:17.1.0")

// Permissions handling
implementation("com.google.accompanist:accompanist-permissions:0.30.1")
```

#### Permissions
Added to `AndroidManifest.xml`:
```xml
<uses-feature android:name="android.hardware.camera" android:required="false" />
<uses-permission android:name="android.permission.CAMERA" />
```

### iOS Implementation

**Location**: `/iosApp/iosApp/BarcodeScannerView.swift`

#### Key Components
- `BarcodeScannerView` - Main SwiftUI view
- `BarcodeScannerViewModel` - Handles camera and barcode detection
- `CameraPreviewView` - UIViewRepresentable for camera preview
- `ManualBarcodeEntryView` - Manual entry UI

#### Permissions
Added to `Info.plist`:
```xml
<key>NSCameraUsageDescription</key>
<string>We need access to your camera to scan barcodes from your receipts and add reward points to your account.</string>
```

## Usage

### For Users

1. **Navigate to Profile/Rewards Screen**
2. **Click "📱 Scan Receipt Barcode" button**
3. **Two options:**
   - **Camera Scan**: Point camera at barcode on receipt
   - **Manual Entry**: Click "Enter Code Manually" and type the code
4. **View Result**: Success or error message appears
5. **Points Added**: Points automatically added to account on success

### Testing with Sample Barcodes

You can generate valid test barcodes using the `BarcodeValidator`:

```kotlin
// Kotlin (shared module)
val testBarcode = BarcodeValidator.generateTestBarcode(2550) // $25.50
// Returns: "RECEIPT-<timestamp>-2550-<checksum>"

// This generates a valid barcode that can be scanned/entered
```

**Test Scenarios:**
1. Generate a barcode for $25.50: `BarcodeValidator.generateTestBarcode(2550)`
2. Scan it → Should add 127 points
3. Try to scan the same barcode again → Should show "already scanned" error
4. Enter invalid barcode like "INVALID-CODE" → Should show validation error

## UI Design

### Android
- Follows Material Design with app's coffee theme
- Uses `CoffeeBrown` and `CaramelBrown` colors
- Toast notifications for feedback
- Camera preview with scanning frame overlay

### iOS
- Follows iOS design patterns with SwiftUI
- Uses custom `CoffeeColors` theme
- Alert dialogs for feedback
- Clean camera preview with overlay instructions

## Error Handling

The system handles various error scenarios:

1. **Invalid Barcode Format** - "Invalid barcode. Please check and try again."
2. **Already Scanned** - "This receipt has already been scanned"
3. **Expired Receipt** - Barcode older than 30 days is rejected
4. **Wrong Checksum** - Security validation fails
5. **Camera Permission Denied** - Shows permission request UI
6. **Camera Initialization Failed** - Falls back to manual entry option

## Future Enhancements

Possible improvements:
- QR code support in addition to linear barcodes
- Receipt history with timestamps
- Offline scanning with sync when online
- Bulk scanning for multiple receipts
- Receipt photo capture and OCR parsing
- Integration with loyalty tier bonuses
- Push notifications when points are added

## Testing

### Manual Testing Checklist
- [ ] Camera permission request works on both platforms
- [ ] Camera preview shows correctly
- [ ] Valid barcode is scanned and points are added
- [ ] Invalid barcode shows error message
- [ ] Manual entry works with valid code
- [ ] Manual entry shows error for invalid code
- [ ] Duplicate scan prevention works
- [ ] Back navigation works correctly
- [ ] UI matches app theme on both platforms
- [ ] Points update in profile after successful scan

### Code Quality
- Shared validation logic ensures consistency
- Platform-specific implementations follow best practices
- Error handling is comprehensive
- User experience is smooth and intuitive

## Troubleshooting

**Camera not working on Android:**
- Check that camera permission is granted
- Verify device has a working camera
- Try restarting the app

**Camera not working on iOS:**
- Check that camera permission is granted in Settings
- Verify Info.plist has NSCameraUsageDescription
- Try restarting the app

**Barcode not detected:**
- Ensure good lighting
- Hold steady and ensure barcode is in frame
- Try manual entry as alternative
- Verify barcode format is correct

**Points not added:**
- Check that barcode hasn't been scanned before
- Verify barcode is valid and not expired
- Check network connection (if backend integration exists)

## File Structure

```
shared/src/commonMain/kotlin/coffeeshop/shared/
├── data/
│   ├── model/
│   │   └── ReceiptBarcode.kt
│   └── repository/
│       ├── CoffeeRepository.kt
│       └── MockCoffeeRepository.kt
└── utils/
    └── BarcodeValidator.kt

android/src/main/kotlin/coffeeshop/app/
├── ui/
│   └── screen/
│       ├── BarcodeScannerScreen.kt
│       └── ProfileScreen.kt
└── AndroidManifest.xml

iosApp/iosApp/
├── BarcodeScannerView.swift
├── ProfileScreenView.swift
└── Info.plist
```

## Dependencies

### Android
- androidx.camera:camera-camera2:1.2.3
- androidx.camera:camera-lifecycle:1.2.3
- androidx.camera:camera-view:1.2.3
- com.google.mlkit:barcode-scanning:17.1.0
- com.google.accompanist:accompanist-permissions:0.30.1

### iOS
- AVFoundation (native framework)
- SwiftUI (native framework)

## Summary

The Barcode Scanner feature is fully implemented with:
- ✅ Shared KMP validation logic
- ✅ Android camera scanning with ML Kit
- ✅ iOS camera scanning with AVFoundation
- ✅ Manual entry option on both platforms
- ✅ Permission handling
- ✅ Error handling and validation
- ✅ UI integration with existing Profile screen
- ✅ Points calculation and account updates
- ✅ Duplicate scan prevention
- ✅ User-friendly feedback and notifications

The implementation follows best practices for KMP development, separates concerns properly, and provides a smooth user experience on both platforms.
