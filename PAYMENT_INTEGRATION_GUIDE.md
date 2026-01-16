# Payment System - Quick Integration Guide

## For Developers: How to Integrate Real Payment Gateway

### Step 1: Add Payment Gateway SDK Dependencies

#### For Stripe Integration

**Android** (`android/build.gradle.kts`):
```kotlin
dependencies {
    // Stripe Android SDK
    implementation("com.stripe:stripe-android:20.35.0")
}
```

**iOS** (via CocoaPods or SPM):
```ruby
# Podfile
pod 'Stripe'
```

**Shared Module** (`shared/build.gradle.kts`):
```kotlin
// Add Stripe API client for shared logic
commonMain.dependencies {
    implementation("com.stripe:stripe-kotlin:20.35.0")
}
```

### Step 2: Create Platform-Specific Gateway Implementation

#### Android Implementation

```kotlin
// android/src/main/kotlin/coffeeshop/app/payment/StripePaymentGateway.kt
package coffeeshop.app.payment

import coffeeshop.shared.data.gateway.PaymentGateway
import coffeeshop.shared.data.model.PaymentInfo
import coffeeshop.shared.data.model.PaymentResult
import coffeeshop.shared.data.model.PaymentStatus
import com.stripe.android.PaymentConfiguration
import com.stripe.android.Stripe

class StripePaymentGateway(
    private val context: Context,
    private val publishableKey: String
) : PaymentGateway {
    
    private val stripe = Stripe(context, publishableKey)
    
    override suspend fun processPayment(paymentInfo: PaymentInfo): PaymentResult {
        return withContext(Dispatchers.IO) {
            try {
                // 1. Create payment intent on your backend
                val paymentIntent = createPaymentIntent(paymentInfo)
                
                // 2. Confirm payment with Stripe
                val result = stripe.confirmPayment(...)
                
                // 3. Return result
                if (result.status == StripePaymentStatus.Succeeded) {
                    PaymentResult(
                        status = PaymentStatus.SUCCESS,
                        transactionId = result.paymentIntentId,
                        message = "Payment successful",
                        timestamp = System.currentTimeMillis()
                    )
                } else {
                    PaymentResult(
                        status = PaymentStatus.FAILED,
                        transactionId = result.paymentIntentId,
                        message = "Payment failed",
                        timestamp = System.currentTimeMillis(),
                        errorCode = result.errorCode
                    )
                }
            } catch (e: Exception) {
                PaymentResult(
                    status = PaymentStatus.FAILED,
                    transactionId = "",
                    message = e.message ?: "Unknown error",
                    timestamp = System.currentTimeMillis(),
                    errorCode = "EXCEPTION"
                )
            }
        }
    }
    
    private suspend fun createPaymentIntent(paymentInfo: PaymentInfo): String {
        // Call your backend API to create payment intent
        // POST /api/create-payment-intent
        // Body: { amount, currency, customer, etc. }
        // Returns: { clientSecret, paymentIntentId }
    }
    
    override suspend fun verifyTransaction(transactionId: String): PaymentResult {
        // Implement transaction verification
    }
    
    override suspend fun refundPayment(transactionId: String, amount: Double?): PaymentResult {
        // Implement refund logic
    }
}
```

#### iOS Implementation

```swift
// iosApp/iosApp/StripePaymentGateway.swift
import shared
import Stripe

class StripePaymentGateway: PaymentGateway {
    private let publishableKey: String
    
    init(publishableKey: String) {
        self.publishableKey = publishableKey
        STPAPIClient.shared.publishableKey = publishableKey
    }
    
    func processPayment(paymentInfo: PaymentInfo) async throws -> PaymentResult {
        do {
            // 1. Create payment intent on your backend
            let paymentIntent = try await createPaymentIntent(paymentInfo: paymentInfo)
            
            // 2. Confirm payment with Stripe
            let result = try await STPAPIClient.shared.confirmPayment(...)
            
            // 3. Return result
            if result.status == .succeeded {
                return PaymentResult(
                    status: .success,
                    transactionId: result.paymentIntentId,
                    message: "Payment successful",
                    timestamp: Int64(Date().timeIntervalSince1970 * 1000),
                    errorCode: nil
                )
            } else {
                return PaymentResult(
                    status: .failed,
                    transactionId: result.paymentIntentId,
                    message: "Payment failed",
                    timestamp: Int64(Date().timeIntervalSince1970 * 1000),
                    errorCode: result.errorCode
                )
            }
        } catch {
            return PaymentResult(
                status: .failed,
                transactionId: "",
                message: error.localizedDescription,
                timestamp: Int64(Date().timeIntervalSince1970 * 1000),
                errorCode: "EXCEPTION"
            )
        }
    }
    
    private func createPaymentIntent(paymentInfo: PaymentInfo) async throws -> String {
        // Call your backend API to create payment intent
    }
    
    func verifyTransaction(transactionId: String) async throws -> PaymentResult {
        // Implement verification
    }
    
    func refundPayment(transactionId: String, amount: Double?) async throws -> PaymentResult {
        // Implement refund
    }
}
```

### Step 3: Update PaymentScreen to Use Real Gateway

#### Android

```kotlin
// In PaymentScreen.kt, pass real gateway instead of default
@Composable
fun PaymentScreen(
    paymentPresenter: PaymentPresenter = remember { 
        PaymentPresenter(
            paymentGateway = StripePaymentGateway(
                context = LocalContext.current,
                publishableKey = "pk_test_..." // From environment config
            )
        )
    },
    // ... rest of parameters
) {
    // ... existing code
}
```

#### iOS

```swift
// In PaymentScreenView.swift
class PaymentViewModel: ObservableObject {
    private let paymentPresenter: PaymentPresenter
    
    init() {
        let stripeGateway = StripePaymentGateway(
            publishableKey: "pk_test_..." // From environment config
        )
        self.paymentPresenter = PaymentPresenter(
            paymentGateway: stripeGateway,
            paymentMethodsRepository: PaymentMethodsRepository()
        )
        // ... rest of init
    }
}
```

### Step 4: Backend API Requirements

Your backend needs these endpoints:

#### 1. Create Payment Intent
```
POST /api/payment/create-intent
Headers:
  Authorization: Bearer {user_token}
Body:
{
  "amount": 1299,  // in cents
  "currency": "usd",
  "orderId": "ORD_123",
  "customer": "cus_123"
}
Response:
{
  "clientSecret": "pi_xxx_secret_xxx",
  "paymentIntentId": "pi_xxx"
}
```

#### 2. Verify Payment
```
GET /api/payment/verify/{transactionId}
Headers:
  Authorization: Bearer {user_token}
Response:
{
  "status": "succeeded",
  "amount": 1299,
  "transactionId": "pi_xxx"
}
```

#### 3. Create Refund
```
POST /api/payment/refund
Headers:
  Authorization: Bearer {user_token}
Body:
{
  "transactionId": "pi_xxx",
  "amount": 1299  // optional, full refund if not specified
}
Response:
{
  "refundId": "re_xxx",
  "status": "succeeded",
  "amount": 1299
}
```

### Step 5: Security Configuration

#### Store API Keys Securely

**Android** (`local.properties`):
```properties
stripe.publishable.key=pk_test_xxx
stripe.secret.key=sk_test_xxx  # Never commit this!
```

**iOS** (Use Info.plist or environment variables):
```xml
<key>StripePublishableKey</key>
<string>pk_test_xxx</string>
```

#### Access in Code

**Android** (`build.gradle.kts`):
```kotlin
android {
    buildTypes {
        getByName("debug") {
            buildConfigField("String", "STRIPE_KEY", "\"${project.findProperty("stripe.publishable.key")}\"")
        }
    }
}
```

**iOS**:
```swift
guard let key = Bundle.main.object(forInfoDictionaryKey: "StripePublishableKey") as? String else {
    fatalError("Missing Stripe key")
}
```

### Step 6: Testing

#### Test with Stripe Test Cards

```
Success: 4242 4242 4242 4242
Decline: 4000 0000 0000 0002
3D Secure: 4000 0025 0000 3155
Insufficient funds: 4000 0000 0000 9995
```

#### Test Payment Flow

1. Fill in billing address
2. Select payment method
3. Use test card number
4. Confirm payment
5. Verify success dialog appears
6. Check transaction ID is displayed
7. Test failure scenario
8. Verify error message is clear

### Step 7: Production Checklist

- [ ] Switch to live API keys (pk_live_xxx, sk_live_xxx)
- [ ] Enable SSL certificate pinning
- [ ] Implement webhook handlers for payment events
- [ ] Set up payment failure alerts
- [ ] Configure fraud detection rules
- [ ] Test with real payment methods
- [ ] Implement PCI DSS compliance requirements
- [ ] Add transaction logging
- [ ] Set up customer support for payment issues
- [ ] Implement refund UI
- [ ] Add payment analytics

## Alternative: PayPal Integration

### PayPal SDK Setup

**Android**:
```kotlin
implementation("com.paypal.checkout:android-sdk:1.0.0")
```

**iOS**:
```ruby
pod 'PayPalCheckout'
```

### PayPal Gateway Implementation

Similar structure to Stripe, but using PayPal SDK:

```kotlin
class PayPalPaymentGateway : PaymentGateway {
    override suspend fun processPayment(paymentInfo: PaymentInfo): PaymentResult {
        // Use PayPal SDK to process payment
        // PayPal provides UI components for payment flow
    }
}
```

## Support

For questions or issues:
1. Check Stripe/PayPal documentation
2. Review payment gateway interface documentation
3. Contact backend team for API issues
4. Check security guidelines for compliance questions

## Additional Resources

- Stripe Android: https://stripe.com/docs/mobile/android
- Stripe iOS: https://stripe.com/docs/mobile/ios
- PayPal Android: https://developer.paypal.com/docs/checkout/android/
- PayPal iOS: https://developer.paypal.com/docs/checkout/ios/
- PCI DSS Compliance: https://www.pcisecuritystandards.org/
