import SwiftUI
import shared

struct ProfileScreenView: View {
    @StateObject private var viewModel = ProfileViewModel()
    @State private var showRewardInfo = false
    @State private var showBarcodeScanner = false
    
    var body: some View {
        ScrollView {
            VStack(spacing: 0) {
                ProfileHeader(userName: viewModel.userName)
                
                if let membership = viewModel.loyaltyMembership {
                    LoyaltyMembershipCardView(membership: membership)
                        .padding(16)
                }
                
                RewardPointsCard(
                    points: viewModel.rewardPoints,
                    pointsToNextTier: viewModel.pointsToNextTier,
                    canRedeem: viewModel.canRedeem,
                    onRewardInfoClick: {
                        showRewardInfo = true
                    },
                    onScanBarcodeClick: {
                        showBarcodeScanner = true
                    }
                )
                .padding(16)
                
                VStack(alignment: .leading, spacing: 8) {
                    Text("Recent Activity")
                        .font(.system(size: 24, weight: .bold))
                        .foregroundColor(CoffeeColors.darkCoffee)
                        .padding(.horizontal, 16)
                        .padding(.top, 16)
                    
                    ForEach(viewModel.transactions, id: \.id) { transaction in
                        TransactionItemView(transaction: transaction)
                    }
                }
                .padding(.bottom, 16)
            }
        }
        .background(CoffeeColors.creamyWhite)
        .sheet(isPresented: $showRewardInfo) {
            RewardInfoScreenView()
        }
        .sheet(isPresented: $showBarcodeScanner) {
            NavigationView {
                BarcodeScannerView(onScanComplete: { success, points in
                    // Refresh view model when scan completes successfully
                    if success {
                        viewModel.loadData()
                    }
                })
            }
        }
    }
}

struct ProfileHeader: View {
    let userName: String
    
    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            Text("Profile")
                .font(.system(size: 28, weight: .bold))
                .foregroundColor(.white)
            
            Text("Welcome, \(userName)!")
                .font(.system(size: 16))
                .foregroundColor(.white.opacity(0.9))
        }
        .frame(maxWidth: .infinity, alignment: .leading)
        .padding(24)
        .background(CoffeeColors.coffeeBrown)
    }
}

struct RewardPointsCard: View {
    let points: Int
    let pointsToNextTier: Int
    let canRedeem: Bool
    let onRewardInfoClick: () -> Void
    let onScanBarcodeClick: () -> Void
    
    var body: some View {
        VStack(alignment: .leading, spacing: 16) {
            HStack {
                Text("Reward Points")
                    .font(.system(size: 20, weight: .bold))
                    .foregroundColor(.white)
                
                Spacer()
                
                Text("⭐")
                    .font(.system(size: 28))
            }
            
            VStack(alignment: .leading, spacing: 4) {
                Text("\(points)")
                    .font(.system(size: 48, weight: .bold))
                    .foregroundColor(.white)
                
                Text("points available")
                    .font(.system(size: 14))
                    .foregroundColor(.white.opacity(0.8))
            }
            
            if pointsToNextTier > 0 {
                HStack(spacing: 8) {
                    Text("🎯")
                        .font(.system(size: 20))
                    
                    Text("\(pointsToNextTier) points to next reward tier")
                        .font(.system(size: 14))
                        .foregroundColor(.white)
                }
                .padding(12)
                .frame(maxWidth: .infinity, alignment: .leading)
                .background(Color.white.opacity(0.2))
                .cornerRadius(8)
            }
            
            // Scan Barcode button (full width)
            Button(action: onScanBarcodeClick) {
                Text("📱 Scan Receipt Barcode")
                    .font(.system(size: 16, weight: .semibold))
                    .foregroundColor(.white)
                    .frame(maxWidth: .infinity)
                    .padding(.vertical, 12)
                    .background(CoffeeColors.caramelBrown)
                    .cornerRadius(8)
            }
            
            HStack(spacing: 8) {
                Button(action: onRewardInfoClick) {
                    Text("Learn More")
                        .font(.system(size: 16, weight: .semibold))
                        .foregroundColor(CoffeeColors.coffeeBrown)
                        .frame(maxWidth: .infinity)
                        .padding(.vertical, 12)
                        .background(Color.white)
                        .cornerRadius(8)
                }
                
                if canRedeem {
                    Button(action: {
                        // Redeem action would go here
                    }) {
                        Text("Redeem")
                            .font(.system(size: 16, weight: .semibold))
                            .foregroundColor(.white)
                            .frame(maxWidth: .infinity)
                            .padding(.vertical, 12)
                            .background(CoffeeColors.caramelBrown)
                            .cornerRadius(8)
                    }
                }
            }
        }
        .padding(20)
        .background(CoffeeColors.coffeeBrown)
        .cornerRadius(16)
        .shadow(color: Color.black.opacity(0.1), radius: 4, x: 0, y: 2)
    }
}

struct TransactionItemView: View {
    let transaction: RewardTransaction
    
    private var dateString: String {
        let formatter = DateFormatter()
        formatter.dateFormat = "MMM dd, yyyy"
        return formatter.string(from: Date(timeIntervalSince1970: Double(transaction.timestamp) / 1000.0))
    }
    
    private var isPositive: Bool {
        transaction.type == .earned
    }
    
    var body: some View {
        HStack(spacing: 12) {
            ZStack {
                Circle()
                    .fill(isPositive ? CoffeeColors.coffeeBrown.opacity(0.2) : CoffeeColors.caramelBrown.opacity(0.2))
                    .frame(width: 40, height: 40)
                
                Text(isPositive ? "+" : "−")
                    .font(.system(size: 20, weight: .bold))
                    .foregroundColor(isPositive ? CoffeeColors.coffeeBrown : CoffeeColors.caramelBrown)
            }
            
            VStack(alignment: .leading, spacing: 4) {
                Text(transaction.details)
                    .font(.system(size: 14, weight: .medium))
                    .foregroundColor(CoffeeColors.darkCoffee)
                
                Text(dateString)
                    .font(.system(size: 12))
                    .foregroundColor(CoffeeColors.coffeeBrown.opacity(0.6))
            }
            
            Spacer()
            
            Text(isPositive ? "+\(transaction.points)" : "\(transaction.points)")
                .font(.system(size: 16, weight: .bold))
                .foregroundColor(isPositive ? CoffeeColors.coffeeBrown : CoffeeColors.caramelBrown)
        }
        .padding(16)
        .background(CoffeeColors.latteFoam)
        .cornerRadius(12)
        .shadow(color: Color.black.opacity(0.1), radius: 2, x: 0, y: 2)
        .padding(.horizontal, 16)
        .padding(.vertical, 6)
    }
}

class ProfileViewModel: ObservableObject {
    @Published var userName: String = ""
    @Published var rewardPoints: Int = 0
    @Published var pointsToNextTier: Int = 0
    @Published var canRedeem: Bool = false
    @Published var transactions: [RewardTransaction] = []
    @Published var loyaltyMembership: LoyaltyMembership?
    
    private let presenter: ProfilePresenter
    
    init() {
        let repository = MockCoffeeRepository()
        self.presenter = ProfilePresenter(repository: repository)
        loadData()
    }
    
    func loadData() {
        let user = presenter.getCurrentUser()
        userName = user.name
        rewardPoints = Int(presenter.getRewardPointsBalance())
        pointsToNextTier = Int(presenter.getPointsToNextTier())
        canRedeem = presenter.canRedeemPoints()
        transactions = presenter.getRewardTransactions()
        loyaltyMembership = presenter.getLoyaltyMembership()
    }
}

#Preview {
    ProfileScreenView()
}

struct LoyaltyMembershipCardView: View {
    let membership: LoyaltyMembership
    
    var tierColor: Color {
        switch membership.currentTier {
        case .bronze:
            return Color(red: 0.804, green: 0.498, blue: 0.196) // #CD7F32
        case .silver:
            return Color(red: 0.753, green: 0.753, blue: 0.753) // #C0C0C0
        case .gold:
            return Color(red: 1.0, green: 0.843, blue: 0.0) // #FFD700
        case .platinum:
            return Color(red: 0.898, green: 0.894, blue: 0.886) // #E5E4E2
        }
    }
    
    var body: some View {
        VStack(alignment: .leading, spacing: 16) {
            HStack {
                Text("Loyalty Membership")
                    .font(.system(size: 18, weight: .bold))
                    .foregroundColor(.white)
                
                Spacer()
                
                Text(membership.currentTier.emoji)
                    .font(.system(size: 28))
            }
            
            HStack(alignment: .bottom, spacing: 8) {
                Text(membership.currentTier.tierName)
                    .font(.system(size: 36, weight: .bold))
                    .foregroundColor(.white)
                
                Text("TIER")
                    .font(.system(size: 16, weight: .bold))
                    .foregroundColor(.white.opacity(0.8))
                    .padding(.bottom, 4)
            }
            
            // Tier Benefits
            VStack(alignment: .leading, spacing: 8) {
                Text("Your Benefits")
                    .font(.system(size: 14, weight: .bold))
                    .foregroundColor(.white)
                
                ForEach(Array(membership.currentTier.benefits.prefix(3)), id: \.self) { benefit in
                    HStack(alignment: .top, spacing: 4) {
                        Text("•")
                            .font(.system(size: 12))
                            .foregroundColor(.white)
                        Text(benefit)
                            .font(.system(size: 12))
                            .foregroundColor(.white)
                    }
                }
                
                if membership.currentTier.benefits.count > 3 {
                    Text("• And \(membership.currentTier.benefits.count - 3) more...")
                        .font(.system(size: 12))
                        .foregroundColor(.white)
                }
            }
            .padding(12)
            .frame(maxWidth: .infinity, alignment: .leading)
            .background(Color.white.opacity(0.2))
            .cornerRadius(8)
            
            // Progress to next tier
            if let nextTier = membership.nextTier {
                VStack(spacing: 8) {
                    HStack {
                        Text("Progress to \(nextTier.tierName)")
                            .font(.system(size: 12))
                            .foregroundColor(.white)
                        
                        Spacer()
                        
                        Text("\(membership.progressPercentage)%")
                            .font(.system(size: 12, weight: .bold))
                            .foregroundColor(.white)
                    }
                    
                    ProgressView(value: Double(membership.progressPercentage), total: 100)
                        .progressViewStyle(LinearProgressViewStyle(tint: .white))
                        .background(Color.white.opacity(0.3))
                        .cornerRadius(4)
                    
                    Text("\(membership.pointsToNextTier) points needed")
                        .font(.system(size: 11))
                        .foregroundColor(.white.opacity(0.8))
                        .frame(maxWidth: .infinity, alignment: .leading)
                }
            } else {
                Text("🎉 You've reached the highest tier!")
                    .font(.system(size: 12, weight: .bold))
                    .foregroundColor(.white)
            }
        }
        .padding(20)
        .background(tierColor)
        .cornerRadius(16)
        .shadow(color: Color.black.opacity(0.1), radius: 4, x: 0, y: 2)
    }
}
