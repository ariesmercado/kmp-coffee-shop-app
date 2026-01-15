import SwiftUI
import shared

struct FavoritesScreenView: View {
    @StateObject private var viewModel = FavoritesViewModel()
    
    var body: some View {
        VStack(spacing: 0) {
            FavoritesHeader()
            
            if viewModel.favoriteDrinks.isEmpty {
                EmptyFavoritesState()
            } else {
                FavoritesDrinksList(
                    drinks: viewModel.favoriteDrinks,
                    onToggleFavorite: { drink in
                        viewModel.removeFavorite(drink: drink)
                    }
                )
            }
        }
        .background(CoffeeColors.creamyWhite)
        .edgesIgnoringSafeArea(.top)
    }
}

struct FavoritesHeader: View {
    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            Text("My Favorites")
                .font(.system(size: 28, weight: .bold))
                .foregroundColor(.white)
            
            Text("Your favorite coffee drinks")
                .font(.system(size: 16))
                .foregroundColor(.white.opacity(0.9))
        }
        .frame(maxWidth: .infinity, alignment: .leading)
        .padding(24)
        .background(CoffeeColors.coffeeBrown)
    }
}

struct EmptyFavoritesState: View {
    var body: some View {
        VStack {
            Spacer()
            
            VStack(spacing: 16) {
                Text("☕")
                    .font(.system(size: 72))
                    .padding(.bottom, 8)
                
                Text("No Favorites Yet")
                    .font(.system(size: 24, weight: .bold))
                    .foregroundColor(CoffeeColors.darkCoffee)
                
                Text("Start adding your favorite drinks\nto see them here!")
                    .font(.system(size: 16))
                    .foregroundColor(CoffeeColors.coffeeBrown.opacity(0.6))
                    .multilineTextAlignment(.center)
                    .padding(.horizontal, 32)
            }
            
            Spacer()
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
    }
}

struct FavoritesDrinksList: View {
    let drinks: [FavoriteDrink]
    let onToggleFavorite: (FavoriteDrink) -> Void
    
    var body: some View {
        ScrollView {
            VStack(spacing: 12) {
                ForEach(drinks, id: \.id) { drink in
                    FavoriteDrinkCardView(
                        drink: drink,
                        onToggleFavorite: {
                            onToggleFavorite(drink)
                        }
                    )
                }
            }
            .padding(.horizontal, 16)
            .padding(.vertical, 16)
        }
    }
}

struct FavoriteDrinkCardView: View {
    let drink: FavoriteDrink
    let onToggleFavorite: () -> Void
    
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
            
            // Favorite button
            Button(action: onToggleFavorite) {
                Image(systemName: "heart.fill")
                    .resizable()
                    .frame(width: 28, height: 28)
                    .foregroundColor(Color(red: 0.914, green: 0.118, blue: 0.388))
            }
            .padding(.leading, 8)
        }
        .padding(16)
        .frame(height: 120)
        .background(CoffeeColors.latteFoam)
        .cornerRadius(12)
        .shadow(color: Color.black.opacity(0.1), radius: 2, x: 0, y: 2)
    }
}

class FavoritesViewModel: ObservableObject {
    @Published var favoriteDrinks: [FavoriteDrink] = []
    
    private let presenter: FavoritesPresenter
    
    init() {
        let repository = MockCoffeeRepository()
        self.presenter = FavoritesPresenter(repository: repository)
        loadFavorites()
    }
    
    func loadFavorites() {
        favoriteDrinks = presenter.getFavoriteDrinks()
    }
    
    func addFavorite(drink: FavoriteDrink) {
        presenter.addFavoriteDrink(drink: drink)
        loadFavorites()
    }
    
    func removeFavorite(drink: FavoriteDrink) {
        presenter.removeFavoriteDrink(drinkId: drink.id)
        loadFavorites()
    }
    
    func toggleFavorite(drink: FavoriteDrink) {
        presenter.toggleFavorite(drink: drink)
        loadFavorites()
    }
}

#Preview {
    FavoritesScreenView()
}
