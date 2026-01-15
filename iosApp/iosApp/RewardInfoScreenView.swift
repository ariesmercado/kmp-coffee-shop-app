import SwiftUI

struct RewardInfoScreenView: View {
    @Environment(\.dismiss) var dismiss
    
    var body: some View {
        NavigationView {
            ScrollView {
                VStack(spacing: 0) {
                    RewardInfoHeader()
                    
                    HowItWorksSection()
                        .padding(16)
                    
                    EarningPointsSection()
                        .padding(.horizontal, 16)
                        .padding(.vertical, 8)
                    
                    RedeemingPointsSection()
                        .padding(.horizontal, 16)
                        .padding(.vertical, 8)
                    
                    RewardTiersSection()
                        .padding(.horizontal, 16)
                        .padding(.vertical, 8)
                    
                    Spacer(minLength: 16)
                }
            }
            .background(CoffeeColors.creamyWhite)
            .navigationBarTitleDisplayMode(.inline)
            .toolbar {
                ToolbarItem(placement: .navigationBarTrailing) {
                    Button(action: { dismiss() }) {
                        Image(systemName: "xmark.circle.fill")
                            .foregroundColor(.white)
                            .font(.system(size: 24))
                    }
                }
            }
        }
    }
}

struct RewardInfoHeader: View {
    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            Text("Reward Program")
                .font(.system(size: 28, weight: .bold))
                .foregroundColor(.white)
            
            Text("Earn points with every purchase and redeem for discounts")
                .font(.system(size: 16))
                .foregroundColor(.white.opacity(0.9))
        }
        .frame(maxWidth: .infinity, alignment: .leading)
        .padding(24)
        .background(CoffeeColors.coffeeBrown)
    }
}

struct HowItWorksSection: View {
    var body: some View {
        VStack(alignment: .leading, spacing: 16) {
            HStack(alignment: .center, spacing: 12) {
                Text("☕")
                    .font(.system(size: 32))
                
                Text("How It Works")
                    .font(.system(size: 22, weight: .bold))
                    .foregroundColor(CoffeeColors.darkCoffee)
            }
            
            Text("Our reward program is designed to thank you for being a loyal customer. Every time you make a purchase, you'll automatically earn points that can be redeemed for discounts on future orders.")
                .font(.system(size: 15))
                .foregroundColor(CoffeeColors.darkCoffee)
                .lineSpacing(7)
        }
        .padding(20)
        .frame(maxWidth: .infinity, alignment: .leading)
        .background(CoffeeColors.latteFoam)
        .cornerRadius(16)
        .shadow(color: Color.black.opacity(0.1), radius: 4, x: 0, y: 2)
    }
}

struct EarningPointsSection: View {
    var body: some View {
        VStack(alignment: .leading, spacing: 16) {
            HStack(alignment: .center, spacing: 12) {
                Text("⭐")
                    .font(.system(size: 32))
                
                Text("Earning Points")
                    .font(.system(size: 22, weight: .bold))
                    .foregroundColor(.white)
            }
            
            VStack(alignment: .leading, spacing: 8) {
                Text("5 points per $1 spent")
                    .font(.system(size: 20, weight: .bold))
                    .foregroundColor(.white)
                
                Text("Example: A $5 coffee earns you 25 points!")
                    .font(.system(size: 14))
                    .foregroundColor(.white.opacity(0.9))
            }
            .padding(16)
            .frame(maxWidth: .infinity, alignment: .leading)
            .background(Color.white.opacity(0.15))
            .cornerRadius(12)
            
            Text("Points are automatically added to your account after each successful purchase.")
                .font(.system(size: 14))
                .foregroundColor(.white.opacity(0.8))
        }
        .padding(20)
        .frame(maxWidth: .infinity, alignment: .leading)
        .background(CoffeeColors.coffeeBrown)
        .cornerRadius(16)
        .shadow(color: Color.black.opacity(0.1), radius: 4, x: 0, y: 2)
    }
}

struct RedeemingPointsSection: View {
    var body: some View {
        VStack(alignment: .leading, spacing: 16) {
            HStack(alignment: .center, spacing: 12) {
                Text("🎁")
                    .font(.system(size: 32))
                
                Text("Redeeming Points")
                    .font(.system(size: 22, weight: .bold))
                    .foregroundColor(.white)
            }
            
            VStack(alignment: .leading, spacing: 8) {
                Text("100 points = $5 discount")
                    .font(.system(size: 20, weight: .bold))
                    .foregroundColor(.white)
                
                Text("Redeem in multiples of 100 points")
                    .font(.system(size: 14))
                    .foregroundColor(.white.opacity(0.9))
            }
            .padding(16)
            .frame(maxWidth: .infinity, alignment: .leading)
            .background(Color.white.opacity(0.15))
            .cornerRadius(12)
            
            Text("Apply your points at checkout to receive an instant discount on your order. Choose how many points to redeem each time!")
                .font(.system(size: 14))
                .foregroundColor(.white.opacity(0.8))
        }
        .padding(20)
        .frame(maxWidth: .infinity, alignment: .leading)
        .background(CoffeeColors.caramelBrown)
        .cornerRadius(16)
        .shadow(color: Color.black.opacity(0.1), radius: 4, x: 0, y: 2)
    }
}

struct RewardTiersSection: View {
    var body: some View {
        VStack(alignment: .leading, spacing: 16) {
            HStack(alignment: .center, spacing: 12) {
                Text("🏆")
                    .font(.system(size: 32))
                
                Text("Reward Tiers")
                    .font(.system(size: 22, weight: .bold))
                    .foregroundColor(CoffeeColors.darkCoffee)
            }
            
            VStack(spacing: 8) {
                RewardTierItemView(
                    tier: "Bronze",
                    points: "100 points",
                    benefit: "First redemption available"
                )
                
                RewardTierItemView(
                    tier: "Silver",
                    points: "200 points",
                    benefit: "Unlock bonus offers"
                )
                
                RewardTierItemView(
                    tier: "Gold",
                    points: "500 points",
                    benefit: "Priority rewards & specials"
                )
                
                RewardTierItemView(
                    tier: "Platinum",
                    points: "1000 points",
                    benefit: "Exclusive perks & surprises"
                )
            }
        }
        .padding(20)
        .frame(maxWidth: .infinity, alignment: .leading)
        .background(CoffeeColors.latteFoam)
        .cornerRadius(16)
        .shadow(color: Color.black.opacity(0.1), radius: 4, x: 0, y: 2)
    }
}

struct RewardTierItemView: View {
    let tier: String
    let points: String
    let benefit: String
    
    var body: some View {
        HStack(alignment: .center) {
            VStack(alignment: .leading, spacing: 2) {
                Text(tier)
                    .font(.system(size: 16, weight: .bold))
                    .foregroundColor(CoffeeColors.darkCoffee)
                
                Text(points)
                    .font(.system(size: 14))
                    .foregroundColor(CoffeeColors.coffeeBrown)
            }
            
            Spacer()
            
            Text(benefit)
                .font(.system(size: 12))
                .foregroundColor(CoffeeColors.darkCoffee.opacity(0.7))
                .multilineTextAlignment(.trailing)
        }
        .padding(12)
        .frame(maxWidth: .infinity)
        .background(CoffeeColors.coffeeBrown.opacity(0.1))
        .cornerRadius(8)
    }
}

#Preview {
    RewardInfoScreenView()
}
