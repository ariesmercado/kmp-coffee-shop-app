import SwiftUI
import shared

struct HomeScreenView: View {
    @StateObject private var viewModel = HomeViewModel()
    
    var body: some View {
        ScrollView {
            VStack(spacing: 0) {
                GreetingSection(greeting: viewModel.greeting)
                
                BannerCarousel(banners: viewModel.banners)
                    .padding(.vertical, 16)
                
                FeaturedDrinksSection(drinks: viewModel.featuredDrinks)
            }
        }
        .background(CoffeeColors.creamyWhite)
        .edgesIgnoringSafeArea(.top)
    }
}

struct GreetingSection: View {
    let greeting: String
    
    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            Text(greeting)
                .font(.system(size: 28, weight: .bold))
                .foregroundColor(.white)
            
            Text("What can we brew for you today?")
                .font(.system(size: 16))
                .foregroundColor(.white.opacity(0.9))
        }
        .frame(maxWidth: .infinity, alignment: .leading)
        .padding(24)
        .background(CoffeeColors.coffeeBrown)
    }
}

struct BannerCarousel: View {
    let banners: [Banner]
    
    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            Text("Special Offers")
                .font(.system(size: 24, weight: .bold))
                .foregroundColor(CoffeeColors.darkCoffee)
                .padding(.horizontal, 16)
            
            ScrollView(.horizontal, showsIndicators: false) {
                HStack(spacing: 12) {
                    ForEach(banners, id: \.id) { banner in
                        BannerCard(banner: banner)
                    }
                }
                .padding(.horizontal, 16)
            }
        }
    }
}

struct BannerCard: View {
    let banner: Banner
    
    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            Text(banner.title)
                .font(.system(size: 20, weight: .bold))
                .foregroundColor(.white)
            
            Text(banner.subtitle)
                .font(.system(size: 16))
                .foregroundColor(.white.opacity(0.9))
        }
        .frame(width: 300, height: 150)
        .padding(20)
        .background(colorFromHex(banner.backgroundColor))
        .cornerRadius(16)
    }
    
    private func colorFromHex(_ hex: String) -> Color {
        let hex = hex.trimmingCharacters(in: CharacterSet.alphanumerics.inverted)
        var int: UInt64 = 0
        Scanner(string: hex).scanHexInt64(&int)
        let r, g, b: UInt64
        r = (int >> 16) & 0xFF
        g = (int >> 8) & 0xFF
        b = int & 0xFF
        return Color(red: Double(r) / 255, green: Double(g) / 255, blue: Double(b) / 255)
    }
}

struct FeaturedDrinksSection: View {
    let drinks: [FeaturedDrink]
    
    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            Text("Featured Drinks")
                .font(.system(size: 24, weight: .bold))
                .foregroundColor(CoffeeColors.darkCoffee)
                .padding(.horizontal, 16)
            
            VStack(spacing: 12) {
                ForEach(drinks, id: \.id) { drink in
                    DrinkCard(drink: drink)
                }
            }
            .padding(.horizontal, 16)
        }
    }
}

struct DrinkCard: View {
    let drink: FeaturedDrink
    
    var body: some View {
        HStack(spacing: 16) {
            // Placeholder for image
            ZStack {
                RoundedRectangle(cornerRadius: 8)
                    .fill(CoffeeColors.coffeeBrown.opacity(0.3))
                    .frame(width: 80, height: 80)
                
                Text("☕")
                    .font(.system(size: 40))
            }
            
            VStack(alignment: .leading, spacing: 4) {
                Text(drink.name)
                    .font(.system(size: 18, weight: .semibold))
                    .foregroundColor(CoffeeColors.darkCoffee)
                
                Text(drink.description)
                    .font(.system(size: 14))
                    .foregroundColor(CoffeeColors.coffeeBrown)
                    .lineLimit(2)
                
                HStack {
                    Text(String(format: "$%.2f", drink.price))
                        .font(.system(size: 16, weight: .bold))
                        .foregroundColor(CoffeeColors.coffeeBrown)
                    
                    Spacer()
                    
                    Text("⭐ \(String(format: "%.1f", drink.rating))")
                        .font(.system(size: 14))
                        .foregroundColor(CoffeeColors.coffeeBrown)
                }
            }
            
            Spacer()
        }
        .padding(16)
        .frame(height: 120)
        .background(CoffeeColors.latteFoam)
        .cornerRadius(12)
        .shadow(color: Color.black.opacity(0.1), radius: 2, x: 0, y: 2)
    }
}

class HomeViewModel: ObservableObject {
    @Published var greeting: String = ""
    @Published var banners: [Banner] = []
    @Published var featuredDrinks: [FeaturedDrink] = []
    
    private let presenter: HomeScreenPresenter
    
    init() {
        let repository = MockCoffeeRepository()
        self.presenter = HomeScreenPresenter(repository: repository)
        loadData()
    }
    
    private func loadData() {
        greeting = presenter.getGreeting()
        banners = presenter.getBanners()
        featuredDrinks = presenter.getFeaturedDrinks()
    }
}

#Preview {
    HomeScreenView()
}
