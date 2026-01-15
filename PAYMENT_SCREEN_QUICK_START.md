# Payment Screen Quick Start Guide

## For Developers

This guide helps you quickly get started with the Payment Screen implementation.

## Quick Preview

### Android
```kotlin
// In MainActivity.kt, replace HomeScreen() with:
PaymentScreen(
    onPaymentSuccess = { 
        // Handle successful payment
        println("Payment successful!")
    },
    onCancel = {
        // Handle cancellation
        println("Payment cancelled")
    }
)
```

### iOS
```swift
// In CoffeeShopApp.swift, replace HomeScreenView() with:
PaymentScreenView()
```

## Testing the Screen

### Option 1: Switch in Main Entry Points

**Android:** Edit `android/src/main/kotlin/coffeeshop/app/MainActivity.kt`
```kotlin
setContent {
    CoffeeShopTheme {
        // PaymentScreen()  // Uncomment this
        HomeScreen()        // Comment this
    }
}
```

**iOS:** Edit `iosApp/iosApp/CoffeeShopApp.swift`
```swift
WindowGroup {
    // PaymentScreenView()  // Uncomment this
    HomeScreenView()        // Comment this
}
```

### Option 2: Navigate from Order Summary

You can integrate the Payment Screen with the Order Summary by updating the `onProceedToPayment` callback:

**Android:**
```kotlin
OrderSummaryScreen(
    onProceedToPayment = {
        // Navigate to PaymentScreen
        navController.navigate("payment")
    }
)
```

**iOS:**
```swift
NavigationView {
    OrderSummaryScreenView(
        onProceedToPayment: {
            // Navigate to PaymentScreenView
            self.showPaymentScreen = true
        }
    )
}
```

## Key Features to Test

### 1. Payment Method Selection
- Tap/Click different payment methods
- Observe radio button selection change
- Notice the card styling changes (border and background)

### 2. Form Validation
Try these test cases:

**Test Case 1: Empty Form**
- Leave all fields empty
- Click "Confirm Payment"
- Should show: "Full name is required"

**Test Case 2: Invalid ZIP Code**
- Fill in all fields
- Enter ZIP code as "123" (invalid)
- Click "Confirm Payment"
- Should show: "Invalid ZIP code format"

**Test Case 3: Valid Form**
- Fill in all required fields:
  - Full Name: "John Doe"
  - Address Line 1: "123 Main St"
  - City: "Seattle"
  - State: "WA"
  - ZIP Code: "98101"
  - Country: "United States"
- Click "Confirm Payment"
- Should process successfully (see console log)

### 3. Loading State
- Fill valid form
- Click "Confirm Payment"
- Observe loading spinner/indicator briefly

### 4. Responsive Scrolling
- Scroll through the entire screen
- Verify all content is accessible
- Bottom buttons should remain fixed

## Sample Test Data

Use this data for quick testing:

```
Full Name: Sarah Thompson
Address Line 1: 456 Coffee Lane
Address Line 2: Apt 2B
City: Portland
State: OR
ZIP Code: 97201
Country: United States
```

Or with 9-digit ZIP:
```
ZIP Code: 97201-1234
```

## Validation Rules Reference

| Field | Rule | Error Message |
|-------|------|---------------|
| Full Name | Required | "Full name is required" |
| Address Line 1 | Required | "Address line 1 is required" |
| Address Line 2 | Optional | N/A |
| City | Required | "City is required" |
| State | Required | "State is required" |
| ZIP Code | Required + Format | "ZIP code is required" or "Invalid ZIP code format" |
| Country | Required | "Country is required" |

### ZIP Code Format
- Valid: `12345` (5 digits)
- Valid: `12345-6789` (9 digits with hyphen)
- Invalid: `123`, `12345-`, `abcde`

## Integration with Backend (Future)

The current implementation uses mock payment processing. To integrate with a real payment gateway:

1. **Update PaymentPresenter.processPayment()**
```kotlin
fun processPayment(paymentInfo: PaymentInfo): Boolean {
    // Replace with actual API call
    // val response = paymentGateway.processPayment(paymentInfo)
    // return response.success
    return true // Current mock
}
```

2. **Add payment token handling** (for credit card)
```kotlin
data class PaymentInfo(
    val orderId: String,
    val paymentMethod: PaymentMethod,
    val billingAddress: BillingAddress,
    val totalAmount: Double,
    val paymentToken: String? = null  // Add this
)
```

3. **Implement secure token generation** (client-side)
```kotlin
// Use payment gateway SDK to tokenize card details
// Never send raw card numbers to your backend
```

## Customization Tips

### Change Colors
Edit the theme files to customize colors:
- Android: `android/src/main/kotlin/coffeeshop/app/ui/theme/Color.kt`
- iOS: `iosApp/iosApp/Theme.swift`

### Add Payment Methods
Edit `PaymentMethod.kt` enum:
```kotlin
enum class PaymentMethod(val displayName: String) {
    CREDIT_CARD("Credit Card"),
    // Add new methods here
    CRYPTOCURRENCY("Cryptocurrency")
}
```

### Modify Validation Rules
Edit `PaymentPresenter.validateBillingAddress()`:
```kotlin
fun validateBillingAddress(...): ValidationResult {
    // Add custom validation logic
    if (zipCode.length != 5) {
        return ValidationResult(false, "ZIP must be 5 digits")
    }
    // ...
}
```

### Change Form Fields
Add or remove fields in both:
- `BillingAddress.kt` (data model)
- `PaymentScreen.kt` (Android UI)
- `PaymentScreenView.swift` (iOS UI)
- `PaymentPresenter.kt` (validation)

## Troubleshooting

### Android Build Issues
```bash
# Clean and rebuild
./gradlew clean
./gradlew :android:assembleDebug
```

### iOS Build Issues
```bash
# Clean build folder
rm -rf iosApp/build
# Rebuild shared framework
./gradlew :shared:assembleXCFramework
```

### Common Errors

**Error: "Cannot find PaymentScreen"**
- Check import: `import coffeeshop.app.ui.screen.PaymentScreen`

**Error: "Cannot find PaymentMethod"**
- Rebuild shared module first
- For iOS: Rebuild shared framework

**Error: Form validation not working**
- Verify PaymentPresenter is initialized
- Check validation logic in presenter

## Next Steps

1. **Add Navigation**: Integrate with your navigation system
2. **Backend Integration**: Connect to payment gateway API
3. **Add More Fields**: Credit card number, CVV, expiration
4. **Error Handling**: Network errors, timeout handling
5. **Analytics**: Track payment attempts and success rate
6. **Testing**: Write unit tests for validation logic

## Resources

- Implementation: `PAYMENT_SCREEN_IMPLEMENTATION.md`
- Visual Guide: `PAYMENT_SCREEN_VISUAL_GUIDE.md`
- Shared Code: `shared/src/commonMain/kotlin/coffeeshop/shared/`
- Android UI: `android/src/main/kotlin/coffeeshop/app/ui/screen/PaymentScreen.kt`
- iOS UI: `iosApp/iosApp/PaymentScreenView.swift`

## Support

For questions or issues:
1. Check the implementation documentation
2. Review the visual guide for UI details
3. Examine existing screen implementations (HomeScreen, MenuScreen, OrderSummaryScreen)
4. Review Kotlin Multiplatform documentation
