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
        .background(CoffeeColors.softBeige)
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
        ZStack(alignment: .leading) {
            colorFromHex(banner.backgroundColor)
            
            VStack(alignment: .leading, spacing: 8) {
                Text(banner.title)
                    .font(CoffeeTypography.serifHeader(size: 20, weight: .bold))
                    .foregroundColor(.white)
                
                Text(banner.subtitle)
                    .font(CoffeeTypography.sansSerifBody(size: 15, weight: .regular))
                    .foregroundColor(.white.opacity(0.95))
            }
            .padding(20)
        }
        .frame(width: 300, height: 150)
        .cornerRadius(16)
        .shadow(color: CoffeeColors.cardShadow, radius: 8, x: 0, y: 4)
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
            // Enhanced image placeholder
            ZStack {
                RoundedRectangle(cornerRadius: 12)
                    .fill(CoffeeColors.coffeeBrown.opacity(0.15))
                    .frame(width: 90, height: 90)
                
                Text("☕")
                    .font(.system(size: 48))
            }
            
            VStack(alignment: .leading, spacing: 6) {
                Text(drink.name)
                    .font(CoffeeTypography.serifHeader(size: 18, weight: .semibold))
                    .foregroundColor(CoffeeColors.darkCoffee)
                
                Text(drink.description)
                    .font(CoffeeTypography.sansSerifBody(size: 13, weight: .regular))
                    .foregroundColor(CoffeeColors.coffeeBrown)
                    .lineLimit(2)
                
                Spacer()
                
                HStack {
                    Text(String(format: "$%.2f", drink.price))
                        .font(CoffeeTypography.serifHeader(size: 18, weight: .bold))
                        .foregroundColor(CoffeeColors.goldenAccent)
                    
                    Spacer()
                    
                    HStack(spacing: 4) {
                        Text("⭐")
                            .font(.system(size: 16))
                        Text(String(format: "%.1f", drink.rating))
                            .font(CoffeeTypography.sansSerifBody(size: 15, weight: .medium))
                            .foregroundColor(CoffeeColors.coffeeBrown)
                    }
                }
            }
            
            Spacer()
        }
        .padding(16)
        .frame(height: 130)
        .background(CoffeeColors.warmIvory)
        .cornerRadius(16)
        .shadow(color: CoffeeColors.cardShadow, radius: 6, x: 0, y: 3)
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
