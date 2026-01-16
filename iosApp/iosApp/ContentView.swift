import SwiftUI
import shared

/**
 * Main entry point for iOS app
 */
@main
struct CoffeeShopApp: App {
    var body: some Scene {
        WindowGroup {
            NavigationStack {
                ContentView()
            }
        }
    }
}

/**
 * Main content view with navigation
 */
struct ContentView: View {
    @State private var navigationPath = NavigationPath()
    
    var body: some View {
        NavigationStack(path: $navigationPath) {
            HomeScreenView(navigationPath: $navigationPath)
                .navigationDestination(for: ScreenDestination.self) { destination in
                    switch destination {
                    case .drinkBuilder:
                        DrinkBuilderScreenView()
                    case .loyalty:
                        LoyaltyScreenView()
                    case .scanner:
                        ScannerScreenView()
                    case .paymentSystem:
                        PaymentSystemScreenView()
                    }
                }
        }
    }
}

/**
 * Screen destinations enum for iOS navigation
 */
enum ScreenDestination: Hashable {
    case drinkBuilder
    case loyalty
    case scanner
    case paymentSystem
}

/**
 * Home Screen SwiftUI View
 */
struct HomeScreenView: View {
    @Binding var navigationPath: NavigationPath
    
    var body: some View {
        ScrollView {
            VStack(spacing: 16) {
                Spacer(minLength: 24)
                
                Text("☕")
                    .font(.system(size: 72))
                
                Text("Welcome to Coffee Shop")
                    .font(.largeTitle)
                    .fontWeight(.bold)
                    .multilineTextAlignment(.center)
                
                Text("Your favorite coffee, just a tap away")
                    .font(.body)
                    .foregroundColor(.secondary)
                    .multilineTextAlignment(.center)
                
                Spacer(minLength: 24)
                
                NavigationButton(
                    text: "Build Your Drink",
                    description: "Customize your perfect coffee",
                    icon: "🍵"
                ) {
                    navigationPath.append(ScreenDestination.drinkBuilder)
                }
                
                NavigationButton(
                    text: "Loyalty Rewards",
                    description: "Check your points and rewards",
                    icon: "⭐"
                ) {
                    navigationPath.append(ScreenDestination.loyalty)
                }
                
                NavigationButton(
                    text: "QR Scanner",
                    description: "Scan to earn points or pay",
                    icon: "📱"
                ) {
                    navigationPath.append(ScreenDestination.scanner)
                }
                
                NavigationButton(
                    text: "Payment System",
                    description: "Manage payment methods",
                    icon: "💳"
                ) {
                    navigationPath.append(ScreenDestination.paymentSystem)
                }
                
                Spacer(minLength: 24)
            }
            .padding(24)
        }
        .navigationTitle("Coffee Shop")
        .navigationBarTitleDisplayMode(.large)
    }
}

/**
 * Navigation button component
 */
struct NavigationButton: View {
    let text: String
    let description: String
    let icon: String
    let action: () -> Void
    
    var body: some View {
        Button(action: action) {
            HStack(spacing: 16) {
                Text(icon)
                    .font(.system(size: 40))
                
                VStack(alignment: .leading, spacing: 4) {
                    Text(text)
                        .font(.headline)
                        .fontWeight(.semibold)
                        .foregroundColor(.primary)
                    
                    Text(description)
                        .font(.caption)
                        .foregroundColor(.secondary)
                }
                
                Spacer()
                
                Image(systemName: "chevron.right")
                    .foregroundColor(.secondary)
            }
            .padding(20)
            .background(Color(.systemBackground))
            .cornerRadius(12)
            .shadow(color: Color.black.opacity(0.1), radius: 4, x: 0, y: 2)
        }
    }
}

/**
 * Drink Builder Screen SwiftUI View
 */
struct DrinkBuilderScreenView: View {
    @State private var drinkSize = "Medium"
    @State private var coffeeType = "Latte"
    @State private var milkType = "Whole Milk"
    @State private var shots = 2
    
    let drinkSizes = ["Small", "Medium", "Large", "Extra Large"]
    let coffeeTypes = ["Espresso", "Americano", "Latte", "Cappuccino", "Mocha"]
    let milkTypes = ["Whole Milk", "Skim Milk", "Oat Milk", "Almond Milk", "Soy Milk"]
    
    var body: some View {
        ScrollView {
            VStack(spacing: 16) {
                Text("🍵")
                    .font(.system(size: 64))
                
                Text("Customize Your Coffee")
                    .font(.title2)
                    .fontWeight(.bold)
                
                OptionPicker(title: "Coffee Type", selection: $coffeeType, options: coffeeTypes)
                OptionPicker(title: "Size", selection: $drinkSize, options: drinkSizes)
                OptionPicker(title: "Milk Type", selection: $milkType, options: milkTypes)
                
                VStack(alignment: .leading, spacing: 8) {
                    Text("Espresso Shots: \(shots)")
                        .font(.headline)
                    
                    HStack {
                        Button(action: { if shots > 1 { shots -= 1 } }) {
                            Text("-")
                                .frame(maxWidth: .infinity)
                        }
                        .buttonStyle(.bordered)
                        
                        Button(action: { if shots < 5 { shots += 1 } }) {
                            Text("+")
                                .frame(maxWidth: .infinity)
                        }
                        .buttonStyle(.bordered)
                    }
                }
                .padding()
                .background(Color(.systemGray6))
                .cornerRadius(12)
                
                Spacer(minLength: 16)
                
                Button(action: {}) {
                    Text("Add to Order")
                        .font(.headline)
                        .frame(maxWidth: .infinity)
                        .padding()
                }
                .buttonStyle(.borderedProminent)
            }
            .padding(24)
        }
        .navigationTitle("Build Your Drink")
        .navigationBarTitleDisplayMode(.inline)
    }
}

/**
 * Option picker component
 */
struct OptionPicker: View {
    let title: String
    @Binding var selection: String
    let options: [String]
    
    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            Text(title)
                .font(.headline)
            
            VStack(alignment: .leading, spacing: 4) {
                ForEach(options, id: \.self) { option in
                    Button(action: { selection = option }) {
                        HStack {
                            Image(systemName: selection == option ? "circle.fill" : "circle")
                                .foregroundColor(selection == option ? .blue : .gray)
                            Text(option)
                                .foregroundColor(.primary)
                            Spacer()
                        }
                        .contentShape(Rectangle())
                    }
                    .buttonStyle(.plain)
                    .padding(.vertical, 4)
                }
            }
        }
        .padding()
        .background(Color(.systemGray6))
        .cornerRadius(12)
    }
}

/**
 * Loyalty Screen SwiftUI View
 */
struct LoyaltyScreenView: View {
    var body: some View {
        ScrollView {
            VStack(spacing: 16) {
                Text("⭐")
                    .font(.system(size: 64))
                
                Text("Your Rewards")
                    .font(.title2)
                    .fontWeight(.bold)
                
                VStack(spacing: 8) {
                    Text("150")
                        .font(.system(size: 60))
                        .fontWeight(.bold)
                    Text("Total Points")
                        .font(.headline)
                }
                .frame(maxWidth: .infinity)
                .padding(24)
                .background(Color.blue.opacity(0.1))
                .cornerRadius(12)
                
                Text("Available Rewards")
                    .font(.title3)
                    .fontWeight(.semibold)
                    .frame(maxWidth: .infinity, alignment: .leading)
                
                RewardCard(title: "Free Coffee", points: "100 points", icon: "☕", isAvailable: true)
                RewardCard(title: "Free Pastry", points: "75 points", icon: "🥐", isAvailable: true)
                RewardCard(title: "20% Off Next Order", points: "200 points", icon: "🎫", isAvailable: false)
                RewardCard(title: "Free Upgrade", points: "50 points", icon: "⬆️", isAvailable: true)
            }
            .padding(24)
        }
        .navigationTitle("Loyalty Rewards")
        .navigationBarTitleDisplayMode(.inline)
    }
}

/**
 * Reward card component
 */
struct RewardCard: View {
    let title: String
    let points: String
    let icon: String
    let isAvailable: Bool
    
    var body: some View {
        HStack(spacing: 16) {
            Text(icon)
                .font(.system(size: 40))
            
            VStack(alignment: .leading, spacing: 4) {
                Text(title)
                    .font(.headline)
                Text(points)
                    .font(.caption)
                    .foregroundColor(.secondary)
            }
            
            Spacer()
            
            Button(action: {}) {
                Text(isAvailable ? "Redeem" : "Locked")
            }
            .buttonStyle(.bordered)
            .disabled(!isAvailable)
        }
        .padding()
        .background(Color(.systemGray6))
        .cornerRadius(12)
    }
}

/**
 * Scanner Screen SwiftUI View
 */
struct ScannerScreenView: View {
    var body: some View {
        VStack(spacing: 24) {
            Spacer()
            
            Text("📱")
                .font(.system(size: 80))
            
            Text("Scan QR Code")
                .font(.title)
                .fontWeight(.bold)
            
            Text("Position the QR code within the frame to scan")
                .font(.body)
                .foregroundColor(.secondary)
                .multilineTextAlignment(.center)
                .padding(.horizontal)
            
            RoundedRectangle(cornerRadius: 12)
                .fill(Color(.systemGray6))
                .frame(width: 280, height: 280)
                .overlay(
                    Text("Scanner View\n(Camera integration required)")
                        .multilineTextAlignment(.center)
                        .foregroundColor(.secondary)
                )
            
            Button(action: {}) {
                Text("Enter Code Manually")
                    .frame(maxWidth: .infinity)
            }
            .buttonStyle(.bordered)
            .padding(.horizontal)
            
            Spacer()
        }
        .padding(24)
        .navigationTitle("QR Scanner")
        .navigationBarTitleDisplayMode(.inline)
    }
}

/**
 * Payment System Screen SwiftUI View
 */
struct PaymentSystemScreenView: View {
    var body: some View {
        ScrollView {
            VStack(spacing: 16) {
                Text("💳")
                    .font(.system(size: 64))
                
                Text("Your Payment Methods")
                    .font(.title2)
                    .fontWeight(.bold)
                
                VStack(alignment: .leading, spacing: 8) {
                    Text("Wallet Balance")
                        .font(.headline)
                    Text("$25.00")
                        .font(.system(size: 40))
                        .fontWeight(.bold)
                }
                .frame(maxWidth: .infinity, alignment: .leading)
                .padding(20)
                .background(Color.blue.opacity(0.1))
                .cornerRadius(12)
                
                Text("Saved Cards")
                    .font(.title3)
                    .fontWeight(.semibold)
                    .frame(maxWidth: .infinity, alignment: .leading)
                
                PaymentMethodCard(icon: "💳", name: "Visa ending in 4242", isDefault: true)
                PaymentMethodCard(icon: "💳", name: "Mastercard ending in 8888", isDefault: false)
                PaymentMethodCard(icon: "📱", name: "Apple Pay", isDefault: false)
                
                Button(action: {}) {
                    Text("+ Add Payment Method")
                        .frame(maxWidth: .infinity)
                }
                .buttonStyle(.bordered)
                
                Text("Recent Transactions")
                    .font(.title3)
                    .fontWeight(.semibold)
                    .frame(maxWidth: .infinity, alignment: .leading)
                    .padding(.top)
                
                TransactionCard(description: "Caffe Latte - Medium", amount: "$4.50", date: "Today, 10:30 AM")
                TransactionCard(description: "Cappuccino - Large", amount: "$5.25", date: "Yesterday, 2:15 PM")
                TransactionCard(description: "Espresso - Small", amount: "$3.00", date: "Jan 14, 9:00 AM")
            }
            .padding(24)
        }
        .navigationTitle("Payment Methods")
        .navigationBarTitleDisplayMode(.inline)
    }
}

/**
 * Payment method card component
 */
struct PaymentMethodCard: View {
    let icon: String
    let name: String
    let isDefault: Bool
    
    var body: some View {
        HStack(spacing: 16) {
            Text(icon)
                .font(.system(size: 32))
            
            VStack(alignment: .leading, spacing: 4) {
                Text(name)
                    .font(.headline)
                if isDefault {
                    Text("Default")
                        .font(.caption)
                        .foregroundColor(.blue)
                }
            }
            
            Spacer()
        }
        .padding()
        .background(Color(.systemGray6))
        .cornerRadius(12)
    }
}

/**
 * Transaction card component
 */
struct TransactionCard: View {
    let description: String
    let amount: String
    let date: String
    
    var body: some View {
        HStack(spacing: 16) {
            VStack(alignment: .leading, spacing: 4) {
                Text(description)
                    .font(.headline)
                Text(date)
                    .font(.caption)
                    .foregroundColor(.secondary)
            }
            
            Spacer()
            
            Text(amount)
                .font(.headline)
                .fontWeight(.bold)
        }
        .padding()
        .background(Color(.systemGray6))
        .cornerRadius(12)
    }
}
