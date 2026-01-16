import SwiftUI
import shared

struct MenuScreenView: View {
    @StateObject private var viewModel = MenuViewModel()
    
    var body: some View {
        VStack(spacing: 0) {
            MenuHeader()
            
            SearchBar(searchText: Binding(
                get: { viewModel.searchQuery },
                set: { viewModel.setSearchQuery($0) }
            ))
                .padding(.horizontal, 16)
                .padding(.vertical, 16)
            
            CategoryFilter(
                categories: viewModel.categories,
                selectedCategoryId: viewModel.selectedCategoryId,
                onCategorySelected: { categoryId in
                    viewModel.toggleCategory(categoryId: categoryId)
                }
            )
            .padding(.vertical, 8)
            
            MenuItemsList(
                menuItems: viewModel.filteredMenuItems,
                favoriteIds: viewModel.favoriteIds,
                onToggleFavorite: { item in
                    viewModel.toggleFavorite(item: item)
                }
            )
        }
        .background(CoffeeColors.softBeige)
        .edgesIgnoringSafeArea(.top)
    }
}

struct MenuHeader: View {
    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            Text("Our Menu")
                .font(.system(size: 28, weight: .bold))
                .foregroundColor(.white)
            
            Text("Browse our selection of drinks")
                .font(.system(size: 16))
                .foregroundColor(.white.opacity(0.9))
        }
        .frame(maxWidth: .infinity, alignment: .leading)
        .padding(24)
        .background(CoffeeColors.coffeeBrown)
    }
}

struct SearchBar: View {
    @Binding var searchText: String
    
    var body: some View {
        HStack {
            Image(systemName: "magnifyingglass")
                .foregroundColor(CoffeeColors.goldenAccent)
                .padding(.leading, 12)
            
            TextField("Search for drinks...", text: $searchText)
                .font(CoffeeTypography.sansSerifBody(size: 16, weight: .regular))
                .foregroundColor(CoffeeColors.darkCoffee)
                .padding(.vertical, 12)
            
            if !searchText.isEmpty {
                Button(action: {
                    searchText = ""
                }) {
                    Image(systemName: "xmark.circle.fill")
                        .foregroundColor(CoffeeColors.coffeeBrown.opacity(0.6))
                        .padding(.trailing, 12)
                }
            }
        }
        .background(CoffeeColors.warmIvory)
        .cornerRadius(16)
        .shadow(color: CoffeeColors.cardShadow, radius: 6, x: 0, y: 3)
    }
}

struct CategoryFilter: View {
    let categories: [MenuCategory]
    let selectedCategoryId: String?
    let onCategorySelected: (String) -> Void
    
    var body: some View {
        ScrollView(.horizontal, showsIndicators: false) {
            HStack(spacing: 8) {
                ForEach(categories, id: \.id) { category in
                    CategoryChip(
                        category: category,
                        isSelected: selectedCategoryId == category.id,
                        onClick: {
                            onCategorySelected(category.id)
                        }
                    )
                }
            }
            .padding(.horizontal, 16)
        }
    }
}

struct CategoryChip: View {
    let category: MenuCategory
    let isSelected: Bool
    let onClick: () -> Void
    
    var body: some View {
        Button(action: onClick) {
            Text(category.name)
                .font(CoffeeTypography.sansSerifBody(size: 15, weight: isSelected ? .bold : .medium))
                .foregroundColor(isSelected ? CoffeeColors.creamyWhite : CoffeeColors.darkCoffee)
                .padding(.horizontal, 20)
                .padding(.vertical, 12)
                .background(isSelected ? CoffeeColors.coffeeBrown : CoffeeColors.warmIvory)
                .cornerRadius(24)
                .shadow(color: isSelected ? CoffeeColors.cardShadow : CoffeeColors.shadowColor, radius: isSelected ? 6 : 3, x: 0, y: 2)
        }
    }
}

struct MenuItemsList: View {
    let menuItems: [MenuItem]
    let favoriteIds: Set<String>
    let onToggleFavorite: (MenuItem) -> Void
    
    var body: some View {
        if menuItems.isEmpty {
            VStack {
                Spacer()
                Text("No drinks found")
                    .font(.system(size: 16))
                    .foregroundColor(CoffeeColors.coffeeBrown.opacity(0.6))
                    .padding(32)
                Spacer()
            }
        } else {
            ScrollView {
                VStack(spacing: 12) {
                    ForEach(menuItems, id: \.id) { item in
                        MenuItemCardView(
                            item: item,
                            isFavorite: favoriteIds.contains(item.id),
                            onToggleFavorite: {
                                onToggleFavorite(item)
                            }
                        )
                    }
                }
                .padding(.horizontal, 16)
                .padding(.vertical, 8)
            }
        }
    }
}

struct MenuItemCardView: View {
    let item: MenuItem
    let isFavorite: Bool
    let onToggleFavorite: () -> Void
    
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
                Text(item.name)
                    .font(CoffeeTypography.serifHeader(size: 18, weight: .semibold))
                    .foregroundColor(CoffeeColors.darkCoffee)
                
                Text(item.description)
                    .font(CoffeeTypography.sansSerifBody(size: 13, weight: .regular))
                    .foregroundColor(CoffeeColors.coffeeBrown)
                    .lineLimit(2)
                
                Spacer()
                
                HStack {
                    Text(String(format: "$%.2f", item.price))
                        .font(CoffeeTypography.serifHeader(size: 18, weight: .bold))
                        .foregroundColor(CoffeeColors.goldenAccent)
                    
                    Spacer()
                    
                    HStack(spacing: 4) {
                        Text("⭐")
                            .font(.system(size: 16))
                        Text(String(format: "%.1f", item.rating))
                            .font(CoffeeTypography.sansSerifBody(size: 15, weight: .medium))
                            .foregroundColor(CoffeeColors.coffeeBrown)
                    }
                }
            }
            
            // Enhanced favorite button
            Button(action: onToggleFavorite) {
                Image(systemName: isFavorite ? "heart.fill" : "heart")
                    .resizable()
                    .frame(width: 28, height: 28)
                    .foregroundColor(isFavorite ? Color(red: 0.914, green: 0.118, blue: 0.388) : CoffeeColors.coffeeBrown.opacity(0.4))
            }
            .padding(.leading, 8)
        }
        .padding(16)
        .frame(height: 130)
        .background(CoffeeColors.warmIvory)
        .cornerRadius(16)
        .shadow(color: CoffeeColors.cardShadow, radius: 6, x: 0, y: 3)
    }
}

class MenuViewModel: ObservableObject {
    @Published var searchQuery: String = ""
    @Published var selectedCategoryId: String? = nil
    @Published var categories: [MenuCategory] = []
    @Published var filteredMenuItems: [MenuItem] = []
    @Published var favoriteIds: Set<String> = []
    
    private let presenter: MenuScreenPresenter
    private let favoritesPresenter: FavoritesPresenter
    private var allMenuItems: [MenuItem] = []
    
    init() {
        let repository = MockCoffeeRepository()
        self.presenter = MenuScreenPresenter(repository: repository)
        self.favoritesPresenter = FavoritesPresenter(repository: repository)
        loadData()
    }
    
    private func loadData() {
        categories = presenter.getCategories()
        allMenuItems = presenter.getMenuItems()
        updateFilteredItems()
        updateFavoriteIds()
    }
    
    func toggleCategory(categoryId: String) {
        if selectedCategoryId == categoryId {
            selectedCategoryId = nil
        } else {
            selectedCategoryId = categoryId
        }
        updateFilteredItems()
    }
    
    func updateFilteredItems() {
        filteredMenuItems = presenter.searchMenuItemsByCategory(
            query: searchQuery,
            categoryId: selectedCategoryId
        )
    }
    
    func setSearchQuery(_ query: String) {
        searchQuery = query
        updateFilteredItems()
    }
    
    func toggleFavorite(item: MenuItem) {
        let favoriteDrink = FavoriteDrink(
            id: item.id,
            name: item.name,
            description: item.description,
            price: item.price,
            imageUrl: item.imageUrl,
            rating: item.rating
        )
        favoritesPresenter.toggleFavorite(drink: favoriteDrink)
        updateFavoriteIds()
    }
    
    func updateFavoriteIds() {
        favoriteIds = Set(favoritesPresenter.getFavoriteIds())
    }
}

#Preview {
    MenuScreenView()
}
