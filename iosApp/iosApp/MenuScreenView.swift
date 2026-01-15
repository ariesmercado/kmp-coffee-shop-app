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
            
            MenuItemsList(menuItems: viewModel.filteredMenuItems)
        }
        .background(CoffeeColors.creamyWhite)
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
                .foregroundColor(CoffeeColors.coffeeBrown)
                .padding(.leading, 12)
            
            TextField("Search for drinks...", text: $searchText)
                .font(.system(size: 16))
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
        .background(CoffeeColors.latteFoam)
        .cornerRadius(12)
        .shadow(color: Color.black.opacity(0.1), radius: 4, x: 0, y: 2)
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
                .font(.system(size: 16, weight: isSelected ? .bold : .regular))
                .foregroundColor(isSelected ? .white : CoffeeColors.darkCoffee)
                .padding(.horizontal, 16)
                .padding(.vertical, 10)
                .background(isSelected ? CoffeeColors.coffeeBrown : CoffeeColors.latteFoam)
                .cornerRadius(20)
                .shadow(color: Color.black.opacity(isSelected ? 0.15 : 0.05), radius: isSelected ? 4 : 2, x: 0, y: 2)
        }
    }
}

struct MenuItemsList: View {
    let menuItems: [MenuItem]
    
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
                        MenuItemCardView(item: item)
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
                Text(item.name)
                    .font(.system(size: 18, weight: .semibold))
                    .foregroundColor(CoffeeColors.darkCoffee)
                
                Text(item.description)
                    .font(.system(size: 14))
                    .foregroundColor(CoffeeColors.coffeeBrown)
                    .lineLimit(2)
                
                HStack {
                    Text(String(format: "$%.2f", item.price))
                        .font(.system(size: 16, weight: .bold))
                        .foregroundColor(CoffeeColors.coffeeBrown)
                    
                    Spacer()
                    
                    Text("⭐ \(String(format: "%.1f", item.rating))")
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

class MenuViewModel: ObservableObject {
    @Published var searchQuery: String = ""
    @Published var selectedCategoryId: String? = nil
    @Published var categories: [MenuCategory] = []
    @Published var filteredMenuItems: [MenuItem] = []
    
    private let presenter: MenuScreenPresenter
    private var allMenuItems: [MenuItem] = []
    
    init() {
        let repository = MockCoffeeRepository()
        self.presenter = MenuScreenPresenter(repository: repository)
        loadData()
    }
    
    private func loadData() {
        categories = presenter.getCategories()
        allMenuItems = presenter.getMenuItems()
        updateFilteredItems()
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
}

#Preview {
    MenuScreenView()
}
