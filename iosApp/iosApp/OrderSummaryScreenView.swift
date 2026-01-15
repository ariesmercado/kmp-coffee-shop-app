import SwiftUI
import shared

struct OrderSummaryScreenView: View {
    @StateObject private var viewModel = OrderSummaryViewModel()
    
    var body: some View {
        VStack(spacing: 0) {
            OrderSummaryHeader()
            
            ScrollView {
                VStack(spacing: 12) {
                    // Order Items Section
                    VStack(alignment: .leading, spacing: 8) {
                        Text("Order Items")
                            .font(.system(size: 20, weight: .bold))
                            .foregroundColor(CoffeeColors.darkCoffee)
                            .padding(.horizontal, 16)
                            .padding(.top, 16)
                        
                        ForEach(viewModel.order.items, id: \.id) { orderItem in
                            OrderItemCardView(orderItem: orderItem)
                                .padding(.horizontal, 16)
                        }
                    }
                    
                    // Cost Breakdown Section
                    VStack(alignment: .leading, spacing: 8) {
                        Text("Cost Breakdown")
                            .font(.system(size: 20, weight: .bold))
                            .foregroundColor(CoffeeColors.darkCoffee)
                            .padding(.horizontal, 16)
                            .padding(.top, 8)
                        
                        CostBreakdownCardView(order: viewModel.order)
                            .padding(.horizontal, 16)
                    }
                    
                    Spacer()
                        .frame(height: 16)
                }
                .padding(.bottom, 16)
            }
            
            OrderActionButtonsView(
                onEditOrder: {
                    print("Edit Order tapped")
                },
                onProceedToPayment: {
                    print("Proceed to Payment tapped")
                }
            )
        }
        .background(CoffeeColors.creamyWhite)
        .edgesIgnoringSafeArea(.top)
    }
}

struct OrderSummaryHeader: View {
    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            Text("Order Summary")
                .font(.system(size: 28, weight: .bold))
                .foregroundColor(.white)
            
            Text("Review your order before checkout")
                .font(.system(size: 16))
                .foregroundColor(.white.opacity(0.9))
        }
        .frame(maxWidth: .infinity, alignment: .leading)
        .padding(24)
        .background(CoffeeColors.coffeeBrown)
    }
}

struct OrderItemCardView: View {
    let orderItem: OrderItem
    
    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            // Item Name and Quantity
            HStack {
                Text(orderItem.name)
                    .font(.system(size: 18, weight: .bold))
                    .foregroundColor(CoffeeColors.darkCoffee)
                
                Spacer()
                
                Text("×\(orderItem.quantity)")
                    .font(.system(size: 16, weight: .bold))
                    .foregroundColor(CoffeeColors.coffeeBrown)
            }
            
            // Size
            HStack(spacing: 4) {
                Text("Size:")
                    .font(.system(size: 14, weight: .bold))
                    .foregroundColor(CoffeeColors.darkCoffee.opacity(0.7))
                Text(orderItem.size)
                    .font(.system(size: 14))
                    .foregroundColor(CoffeeColors.darkCoffee)
            }
            
            // Add-ons
            if !orderItem.addOns.isEmpty {
                VStack(alignment: .leading, spacing: 2) {
                    Text("Add-ons:")
                        .font(.system(size: 14, weight: .bold))
                        .foregroundColor(CoffeeColors.darkCoffee.opacity(0.7))
                    
                    ForEach(orderItem.addOns, id: \.self) { addOn in
                        Text("• \(addOn)")
                            .font(.system(size: 14))
                            .foregroundColor(CoffeeColors.darkCoffee)
                    }
                }
            }
            
            // Price
            HStack {
                Text("Base Price: $\(String(format: "%.2f", orderItem.basePrice))")
                    .font(.system(size: 14))
                    .foregroundColor(CoffeeColors.darkCoffee.opacity(0.7))
                
                Spacer()
                
                Text("$\(String(format: "%.2f", orderItem.itemTotalPrice))")
                    .font(.system(size: 18, weight: .bold))
                    .foregroundColor(CoffeeColors.coffeeBrown)
            }
        }
        .padding(16)
        .frame(maxWidth: .infinity, alignment: .leading)
        .background(CoffeeColors.latteFoam)
        .cornerRadius(12)
        .shadow(color: Color.black.opacity(0.1), radius: 2, x: 0, y: 2)
    }
}

struct CostBreakdownCardView: View {
    let order: Order
    
    var body: some View {
        VStack(spacing: 8) {
            // Subtotal
            CostBreakdownRowView(
                label: "Subtotal",
                amount: order.subtotal,
                isTotal: false
            )
            
            // Tax
            CostBreakdownRowView(
                label: "Tax (\(Int(order.taxRate * 100))%)",
                amount: order.tax,
                isTotal: false
            )
            
            Divider()
                .background(CoffeeColors.darkCoffee.opacity(0.2))
                .padding(.vertical, 4)
            
            // Total
            CostBreakdownRowView(
                label: "Total",
                amount: order.total,
                isTotal: true
            )
        }
        .padding(16)
        .frame(maxWidth: .infinity)
        .background(CoffeeColors.latteFoam)
        .cornerRadius(12)
        .shadow(color: Color.black.opacity(0.1), radius: 2, x: 0, y: 2)
    }
}

struct CostBreakdownRowView: View {
    let label: String
    let amount: Double
    let isTotal: Bool
    
    var body: some View {
        HStack {
            Text(label)
                .font(.system(size: isTotal ? 20 : 16, weight: isTotal ? .bold : .regular))
                .foregroundColor(CoffeeColors.darkCoffee)
            
            Spacer()
            
            Text("$\(String(format: "%.2f", amount))")
                .font(.system(size: isTotal ? 20 : 16, weight: isTotal ? .bold : .regular))
                .foregroundColor(isTotal ? CoffeeColors.coffeeBrown : CoffeeColors.darkCoffee)
        }
    }
}

struct OrderActionButtonsView: View {
    let onEditOrder: () -> Void
    let onProceedToPayment: () -> Void
    
    var body: some View {
        VStack(spacing: 12) {
            // Proceed to Payment Button (Primary)
            Button(action: onProceedToPayment) {
                Text("Proceed to Payment")
                    .font(.system(size: 16, weight: .bold))
                    .foregroundColor(.white)
                    .frame(maxWidth: .infinity)
                    .frame(height: 56)
                    .background(CoffeeColors.coffeeBrown)
                    .cornerRadius(12)
                    .shadow(color: Color.black.opacity(0.15), radius: 4, x: 0, y: 2)
            }
            
            // Edit Order Button (Secondary)
            Button(action: onEditOrder) {
                Text("Edit Order")
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
        }
        .padding(16)
        .background(CoffeeColors.latteFoam)
    }
}

class OrderSummaryViewModel: ObservableObject {
    @Published var order: Order
    
    private let presenter: OrderSummaryPresenter
    
    init() {
        self.presenter = OrderSummaryPresenter()
        self.order = presenter.getSampleOrder()
    }
}

#Preview {
    OrderSummaryScreenView()
}
