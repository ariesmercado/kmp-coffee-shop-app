import SwiftUI
import shared

struct PaymentScreenView: View {
    @StateObject private var viewModel = PaymentViewModel()
    
    var body: some View {
        VStack(spacing: 0) {
            PaymentHeader()
            
            ScrollView {
                VStack(spacing: 16) {
                    // Total Amount Section
                    TotalAmountCardView(order: viewModel.order)
                        .padding(.horizontal, 16)
                        .padding(.top, 16)
                    
                    // Payment Method Section
                    VStack(alignment: .leading, spacing: 8) {
                        Text("Payment Method")
                            .font(.system(size: 20, weight: .bold))
                            .foregroundColor(CoffeeColors.darkCoffee)
                            .padding(.horizontal, 16)
                        
                        ForEach(viewModel.paymentMethods, id: \.self) { method in
                            PaymentMethodCardView(
                                paymentMethod: method,
                                isSelected: viewModel.selectedPaymentMethod == method,
                                onSelect: {
                                    viewModel.selectedPaymentMethod = method
                                }
                            )
                            .padding(.horizontal, 16)
                        }
                    }
                    .padding(.top, 8)
                    
                    // Billing Address Section
                    VStack(alignment: .leading, spacing: 8) {
                        Text("Billing Address")
                            .font(.system(size: 20, weight: .bold))
                            .foregroundColor(CoffeeColors.darkCoffee)
                            .padding(.horizontal, 16)
                            .padding(.top, 8)
                        
                        BillingAddressFormView(
                            fullName: $viewModel.fullName,
                            addressLine1: $viewModel.addressLine1,
                            addressLine2: $viewModel.addressLine2,
                            city: $viewModel.city,
                            state: $viewModel.state,
                            zipCode: $viewModel.zipCode,
                            country: $viewModel.country
                        )
                        .padding(.horizontal, 16)
                    }
                    
                    // Error Message
                    if viewModel.showError {
                        Text(viewModel.errorMessage)
                            .font(.system(size: 14))
                            .foregroundColor(.red)
                            .padding(.horizontal, 16)
                            .padding(.vertical, 8)
                    }
                    
                    Spacer()
                        .frame(height: 16)
                }
                .padding(.bottom, 16)
            }
            
            PaymentActionButtonsView(
                onConfirmPayment: {
                    viewModel.confirmPayment()
                },
                onCancel: {
                    print("Cancel payment tapped")
                },
                isProcessing: viewModel.isProcessing
            )
        }
        .background(CoffeeColors.creamyWhite)
        .ignoresSafeArea(edges: .top)
    }
}

struct PaymentHeader: View {
    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            Text("Payment")
                .font(.system(size: 28, weight: .bold))
                .foregroundColor(.white)
            
            Text("Complete your purchase securely")
                .font(.system(size: 16))
                .foregroundColor(.white.opacity(0.9))
        }
        .frame(maxWidth: .infinity, alignment: .leading)
        .padding(24)
        .background(CoffeeColors.coffeeBrown)
    }
}

struct TotalAmountCardView: View {
    let order: Order
    
    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            Text("Total Amount")
                .font(.system(size: 16))
                .foregroundColor(CoffeeColors.darkCoffee.opacity(0.7))
            
            Text("$\(String(format: "%.2f", order.total))")
                .font(.system(size: 36, weight: .bold))
                .foregroundColor(CoffeeColors.coffeeBrown)
            
            Text("Including tax: $\(String(format: "%.2f", order.tax))")
                .font(.system(size: 14))
                .foregroundColor(CoffeeColors.darkCoffee.opacity(0.6))
        }
        .frame(maxWidth: .infinity, alignment: .leading)
        .padding(20)
        .background(CoffeeColors.coffeeBrown.opacity(0.1))
        .cornerRadius(12)
        .shadow(color: Color.black.opacity(0.1), radius: 2, x: 0, y: 2)
    }
}

struct PaymentMethodCardView: View {
    let paymentMethod: PaymentMethod
    let isSelected: Bool
    let onSelect: () -> Void
    
    var body: some View {
        Button(action: onSelect) {
            HStack {
                Text(paymentMethod.displayName)
                    .font(.system(size: 16, weight: isSelected ? .bold : .regular))
                    .foregroundColor(CoffeeColors.darkCoffee)
                
                Spacer()
                
                ZStack {
                    Circle()
                        .stroke(isSelected ? CoffeeColors.coffeeBrown : CoffeeColors.darkCoffee.opacity(0.5), lineWidth: 2)
                        .frame(width: 24, height: 24)
                    
                    if isSelected {
                        Circle()
                            .fill(CoffeeColors.coffeeBrown)
                            .frame(width: 12, height: 12)
                    }
                }
            }
            .padding(16)
            .frame(maxWidth: .infinity)
            .background(isSelected ? CoffeeColors.coffeeBrown.opacity(0.05) : CoffeeColors.latteFoam)
            .cornerRadius(12)
            .overlay(
                RoundedRectangle(cornerRadius: 12)
                    .stroke(isSelected ? CoffeeColors.coffeeBrown : Color.clear, lineWidth: 2)
            )
            .shadow(color: Color.black.opacity(isSelected ? 0.15 : 0.1), radius: isSelected ? 4 : 2, x: 0, y: 2)
        }
    }
}

struct BillingAddressFormView: View {
    @Binding var fullName: String
    @Binding var addressLine1: String
    @Binding var addressLine2: String
    @Binding var city: String
    @Binding var state: String
    @Binding var zipCode: String
    @Binding var country: String
    
    var body: some View {
        VStack(spacing: 12) {
            // Full Name
            VStack(alignment: .leading, spacing: 4) {
                Text("Full Name")
                    .font(.system(size: 14, weight: .medium))
                    .foregroundColor(CoffeeColors.darkCoffee)
                
                TextField("", text: $fullName)
                    .textFieldStyle(CustomTextFieldStyle())
            }
            
            // Address Line 1
            VStack(alignment: .leading, spacing: 4) {
                Text("Address Line 1")
                    .font(.system(size: 14, weight: .medium))
                    .foregroundColor(CoffeeColors.darkCoffee)
                
                TextField("", text: $addressLine1)
                    .textFieldStyle(CustomTextFieldStyle())
            }
            
            // Address Line 2
            VStack(alignment: .leading, spacing: 4) {
                Text("Address Line 2 (Optional)")
                    .font(.system(size: 14, weight: .medium))
                    .foregroundColor(CoffeeColors.darkCoffee)
                
                TextField("", text: $addressLine2)
                    .textFieldStyle(CustomTextFieldStyle())
            }
            
            // City
            VStack(alignment: .leading, spacing: 4) {
                Text("City")
                    .font(.system(size: 14, weight: .medium))
                    .foregroundColor(CoffeeColors.darkCoffee)
                
                TextField("", text: $city)
                    .textFieldStyle(CustomTextFieldStyle())
            }
            
            // State and ZIP Code
            HStack(spacing: 8) {
                VStack(alignment: .leading, spacing: 4) {
                    Text("State")
                        .font(.system(size: 14, weight: .medium))
                        .foregroundColor(CoffeeColors.darkCoffee)
                    
                    TextField("", text: $state)
                        .textFieldStyle(CustomTextFieldStyle())
                }
                
                VStack(alignment: .leading, spacing: 4) {
                    Text("ZIP Code")
                        .font(.system(size: 14, weight: .medium))
                        .foregroundColor(CoffeeColors.darkCoffee)
                    
                    TextField("", text: $zipCode)
                        .keyboardType(.numberPad)
                        .textFieldStyle(CustomTextFieldStyle())
                }
            }
            
            // Country
            VStack(alignment: .leading, spacing: 4) {
                Text("Country")
                    .font(.system(size: 14, weight: .medium))
                    .foregroundColor(CoffeeColors.darkCoffee)
                
                TextField("", text: $country)
                    .textFieldStyle(CustomTextFieldStyle())
            }
        }
        .padding(16)
        .background(CoffeeColors.latteFoam)
        .cornerRadius(12)
        .shadow(color: Color.black.opacity(0.1), radius: 2, x: 0, y: 2)
    }
}

struct CustomTextFieldStyle: TextFieldStyle {
    func _body(configuration: TextField<Self._Label>) -> some View {
        configuration
            .padding(12)
            .background(Color.white)
            .cornerRadius(8)
            .overlay(
                RoundedRectangle(cornerRadius: 8)
                    .stroke(CoffeeColors.darkCoffee.opacity(0.2), lineWidth: 1)
            )
    }
}

struct PaymentActionButtonsView: View {
    let onConfirmPayment: () -> Void
    let onCancel: () -> Void
    let isProcessing: Bool
    
    var body: some View {
        VStack(spacing: 12) {
            // Confirm Payment Button
            Button(action: onConfirmPayment) {
                HStack {
                    if isProcessing {
                        ProgressView()
                            .progressViewStyle(CircularProgressViewStyle(tint: .white))
                            .frame(width: 24, height: 24)
                    } else {
                        Text("Confirm Payment")
                            .font(.system(size: 16, weight: .bold))
                            .foregroundColor(.white)
                    }
                }
                .frame(maxWidth: .infinity)
                .frame(height: 56)
                .background(isProcessing ? CoffeeColors.coffeeBrown.opacity(0.6) : CoffeeColors.coffeeBrown)
                .cornerRadius(12)
                .shadow(color: Color.black.opacity(0.15), radius: 4, x: 0, y: 2)
            }
            .disabled(isProcessing)
            
            // Cancel Button
            Button(action: onCancel) {
                Text("Cancel")
                    .font(.system(size: 16, weight: .bold))
                    .foregroundColor(CoffeeColors.coffeeBrown)
                    .frame(maxWidth: .infinity)
                    .frame(height: 56)
                    .background(Color.clear)
                    .overlay(
                        RoundedRectangle(cornerRadius: 12)
                            .stroke(CoffeeColors.coffeeBrown, lineWidth: 2)
                    )
            }
            .disabled(isProcessing)
        }
        .padding(16)
        .background(CoffeeColors.latteFoam)
    }
}

class PaymentViewModel: ObservableObject {
    @Published var order: Order
    @Published var selectedPaymentMethod: PaymentMethod = PaymentMethod.creditCard
    @Published var paymentMethods: [PaymentMethod] = []
    
    @Published var fullName: String = ""
    @Published var addressLine1: String = ""
    @Published var addressLine2: String = ""
    @Published var city: String = ""
    @Published var state: String = ""
    @Published var zipCode: String = ""
    @Published var country: String = "United States"
    
    @Published var errorMessage: String = ""
    @Published var showError: Bool = false
    @Published var isProcessing: Bool = false
    
    private let orderSummaryPresenter: OrderSummaryPresenter
    private let paymentPresenter: PaymentPresenter
    
    init() {
        self.orderSummaryPresenter = OrderSummaryPresenter()
        self.paymentPresenter = PaymentPresenter()
        self.order = orderSummaryPresenter.getSampleOrder()
        self.paymentMethods = paymentPresenter.getAvailablePaymentMethods()
    }
    
    func confirmPayment() {
        // Validate billing address
        let validationResult = paymentPresenter.validateBillingAddress(
            fullName: fullName,
            addressLine1: addressLine1,
            city: city,
            state: state,
            zipCode: zipCode,
            country: country
        )
        
        if validationResult.isValid {
            isProcessing = true
            showError = false
            
            // Create payment info
            let paymentInfo = paymentPresenter.createPaymentInfo(
                orderId: order.id,
                paymentMethod: selectedPaymentMethod,
                fullName: fullName,
                addressLine1: addressLine1,
                addressLine2: addressLine2,
                city: city,
                state: state,
                zipCode: zipCode,
                country: country,
                totalAmount: order.total
            )
            
            // Process payment
            let success = paymentPresenter.processPayment(paymentInfo: paymentInfo)
            isProcessing = false
            
            if success {
                print("Payment successful!")
                // Navigate to success screen or show success message
            }
        } else {
            errorMessage = validationResult.errorMessage
            showError = true
        }
    }
}

#Preview {
    PaymentScreenView()
}
