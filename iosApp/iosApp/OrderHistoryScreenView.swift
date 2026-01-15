import SwiftUI
import shared
import Combine

struct OrderHistoryScreenView: View {
    @StateObject private var viewModel = OrderHistoryViewModel()
    
    var body: some View {
        VStack(spacing: 0) {
            OrderHistoryHeaderView()
            
            VStack(spacing: 12) {
                // Search Bar
                HStack {
                    Image(systemName: "magnifyingglass")
                        .foregroundColor(CoffeeColors.coffeeBrown.opacity(0.6))
                    
                    TextField("Search by item or order ID", text: $viewModel.searchQuery)
                        .textFieldStyle(PlainTextFieldStyle())
                }
                .padding(12)
                .background(CoffeeColors.latteFoam)
                .cornerRadius(12)
                .overlay(
                    RoundedRectangle(cornerRadius: 12)
                        .stroke(CoffeeColors.coffeeBrown.opacity(0.3), lineWidth: 1)
                )
                
                // Date Filter Chips
                ScrollView(.horizontal, showsIndicators: false) {
                    HStack(spacing: 8) {
                        ForEach(DateFilter.allCases, id: \.self) { filter in
                            FilterChipView(
                                label: filter.label,
                                isSelected: viewModel.selectedDateFilter == filter,
                                onTap: { viewModel.selectedDateFilter = filter }
                            )
                        }
                    }
                }
            }
            .padding(16)
            
            // Orders List
            if viewModel.filteredOrders.isEmpty {
                EmptyOrdersViewIOS(hasSearchQuery: !viewModel.searchQuery.isEmpty)
            } else {
                ScrollView {
                    LazyVStack(spacing: 12) {
                        ForEach(viewModel.filteredOrders, id: \.id) { order in
                            OrderHistoryCardView(order: order)
                        }
                    }
                    .padding(.horizontal, 16)
                    .padding(.vertical, 8)
                }
            }
        }
        .background(CoffeeColors.creamyWhite)
        .edgesIgnoringSafeArea(.top)
    }
}

struct OrderHistoryHeaderView: View {
    var body: some View {
        VStack(alignment: .leading, spacing: 4) {
            Text("Order History")
                .font(.system(size: 28, weight: .bold))
                .foregroundColor(.white)
            
            Text("View your past orders")
                .font(.system(size: 16))
                .foregroundColor(.white.opacity(0.9))
        }
        .frame(maxWidth: .infinity, alignment: .leading)
        .padding(24)
        .background(CoffeeColors.coffeeBrown)
    }
}

struct FilterChipView: View {
    let label: String
    let isSelected: Bool
    let onTap: () -> Void
    
    var body: some View {
        Button(action: onTap) {
            Text(label)
                .font(.system(size: 14, weight: isSelected ? .bold : .regular))
                .foregroundColor(isSelected ? .white : CoffeeColors.darkCoffee)
                .padding(.horizontal, 16)
                .padding(.vertical, 8)
                .background(isSelected ? CoffeeColors.coffeeBrown : CoffeeColors.latteFoam)
                .cornerRadius(20)
                .shadow(color: Color.black.opacity(isSelected ? 0.15 : 0.05), radius: isSelected ? 4 : 2, x: 0, y: 2)
        }
    }
}

struct OrderHistoryCardView: View {
    let order: OrderHistory
    @State private var isExpanded = false
    
    var body: some View {
        VStack(alignment: .leading, spacing: 12) {
            // Order Header
            HStack(alignment: .top) {
                VStack(alignment: .leading, spacing: 4) {
                    Text("Order #\(String(order.id.suffix(6)))")
                        .font(.system(size: 16, weight: .bold))
                        .foregroundColor(CoffeeColors.darkCoffee)
                    
                    Text(formatDate(timestamp: order.orderDate))
                        .font(.system(size: 14))
                        .foregroundColor(CoffeeColors.coffeeBrown.opacity(0.6))
                }
                
                Spacer()
                
                VStack(alignment: .trailing, spacing: 4) {
                    Text(String(format: "$%.2f", order.total))
                        .font(.system(size: 18, weight: .bold))
                        .foregroundColor(CoffeeColors.coffeeBrown)
                    
                    StatusBadgeView(status: order.status)
                }
            }
            
            // Items Summary or Detail
            if !isExpanded {
                ItemsSummaryView(items: order.items)
            } else {
                ItemsDetailListView(items: order.items)
            }
            
            // View Details Button
            Button(action: { isExpanded.toggle() }) {
                Text(isExpanded ? "Hide Details" : "View Details")
                    .font(.system(size: 14, weight: .bold))
                    .foregroundColor(CoffeeColors.coffeeBrown)
            }
            .frame(maxWidth: .infinity, alignment: .trailing)
        }
        .padding(16)
        .background(CoffeeColors.latteFoam)
        .cornerRadius(12)
        .shadow(color: Color.black.opacity(0.1), radius: 2, x: 0, y: 2)
    }
}

struct StatusBadgeView: View {
    let status: String
    
    var backgroundColor: Color {
        switch status {
        case "Completed": return Color.green.opacity(0.2)
        case "In Progress": return Color.orange.opacity(0.2)
        default: return Color.red.opacity(0.2)
        }
    }
    
    var textColor: Color {
        switch status {
        case "Completed": return Color(red: 0.18, green: 0.49, blue: 0.2)
        case "In Progress": return Color(red: 0.9, green: 0.32, blue: 0)
        default: return Color(red: 0.78, green: 0.16, blue: 0.16)
        }
    }
    
    var body: some View {
        Text(status)
            .font(.system(size: 12, weight: .bold))
            .foregroundColor(textColor)
            .padding(.horizontal, 8)
            .padding(.vertical, 4)
            .background(backgroundColor)
            .cornerRadius(12)
    }
}

struct ItemsSummaryView: View {
    let items: [OrderItem]
    
    var body: some View {
        VStack(spacing: 4) {
            ForEach(Array(items.prefix(2)), id: \.id) { item in
                HStack {
                    Text("\(item.quantity)x \(item.name) (\(item.size))")
                        .font(.system(size: 14))
                        .foregroundColor(CoffeeColors.darkCoffee)
                    
                    Spacer()
                    
                    Text(String(format: "$%.2f", item.itemTotalPrice))
                        .font(.system(size: 14, weight: .medium))
                        .foregroundColor(CoffeeColors.darkCoffee)
                }
            }
            
            if items.count > 2 {
                Text("+\(items.count - 2) more item\(items.count - 2 > 1 ? "s" : "")")
                    .font(.system(size: 14, weight: .medium))
                    .foregroundColor(CoffeeColors.coffeeBrown)
                    .frame(maxWidth: .infinity, alignment: .leading)
            }
        }
    }
}

struct ItemsDetailListView: View {
    let items: [OrderItem]
    
    var body: some View {
        VStack(spacing: 8) {
            ForEach(items, id: \.id) { item in
                VStack(alignment: .leading, spacing: 4) {
                    HStack {
                        VStack(alignment: .leading, spacing: 2) {
                            Text("\(item.quantity)x \(item.name)")
                                .font(.system(size: 14, weight: .bold))
                                .foregroundColor(CoffeeColors.darkCoffee)
                            
                            Text("Size: \(item.size)")
                                .font(.system(size: 12))
                                .foregroundColor(CoffeeColors.coffeeBrown.opacity(0.6))
                            
                            if !item.addOns.isEmpty {
                                Text("Add-ons: \(item.addOns.joined(separator: ", "))")
                                    .font(.system(size: 12))
                                    .foregroundColor(CoffeeColors.coffeeBrown.opacity(0.6))
                            }
                        }
                        
                        Spacer()
                        
                        Text(String(format: "$%.2f", item.itemTotalPrice))
                            .font(.system(size: 14, weight: .bold))
                            .foregroundColor(CoffeeColors.darkCoffee)
                    }
                }
            }
        }
        .padding(12)
        .background(CoffeeColors.coffeeBrown.opacity(0.05))
        .cornerRadius(8)
    }
}

struct EmptyOrdersViewIOS: View {
    let hasSearchQuery: Bool
    
    var body: some View {
        VStack(spacing: 16) {
            Text("☕")
                .font(.system(size: 64))
            
            Text(hasSearchQuery ? "No orders found" : "No order history yet")
                .font(.system(size: 20, weight: .semibold))
                .foregroundColor(CoffeeColors.darkCoffee.opacity(0.6))
            
            Text(hasSearchQuery ? 
                "Try adjusting your search or filters" : 
                "Start ordering to see your history here")
                .font(.system(size: 14))
                .foregroundColor(CoffeeColors.coffeeBrown.opacity(0.4))
                .multilineTextAlignment(.center)
        }
        .padding(32)
        .frame(maxHeight: .infinity)
    }
}

enum DateFilter: CaseIterable {
    case all
    case last7Days
    case last30Days
    
    var label: String {
        switch self {
        case .all: return "All Time"
        case .last7Days: return "Last 7 Days"
        case .last30Days: return "Last 30 Days"
        }
    }
}

class OrderHistoryViewModel: ObservableObject {
    @Published var searchQuery: String = ""
    @Published var selectedDateFilter: DateFilter = .all
    @Published var allOrders: [OrderHistory] = []
    @Published var filteredOrders: [OrderHistory] = []
    
    private let presenter: OrderHistoryPresenter
    private var cancellables = Set<AnyCancellable>()
    
    init() {
        let repository = MockCoffeeRepository()
        self.presenter = OrderHistoryPresenter(repository: repository)
        loadOrders()
        
        // Observe changes to search and filter
        setupObservers()
    }
    
    private func loadOrders() {
        allOrders = presenter.getOrderHistory()
        updateFilteredOrders()
    }
    
    private func setupObservers() {
        // Using Combine to observe changes
        Publishers.CombineLatest($searchQuery, $selectedDateFilter)
            .sink { [weak self] _, _ in
                self?.updateFilteredOrders()
            }
            .store(in: &cancellables)
    }
    
    private func updateFilteredOrders() {
        let currentTime = Date().timeIntervalSince1970 * 1000
        
        // Apply date filter
        let dateFiltered: [OrderHistory]
        switch selectedDateFilter {
        case .all:
            dateFiltered = allOrders
        case .last7Days:
            let sevenDaysAgo = currentTime - (7 * 24 * 60 * 60 * 1000)
            dateFiltered = presenter.filterOrdersByDateRange(
                orders: allOrders,
                startDate: Int64(sevenDaysAgo),
                endDate: Int64(currentTime)
            )
        case .last30Days:
            let thirtyDaysAgo = currentTime - (30 * 24 * 60 * 60 * 1000)
            dateFiltered = presenter.filterOrdersByDateRange(
                orders: allOrders,
                startDate: Int64(thirtyDaysAgo),
                endDate: Int64(currentTime)
            )
        }
        
        // Apply search filter
        filteredOrders = presenter.searchOrders(orders: dateFiltered, query: searchQuery)
    }
}

func formatDate(timestamp: Int64) -> String {
    let date = Date(timeIntervalSince1970: TimeInterval(timestamp) / 1000)
    let formatter = DateFormatter()
    formatter.dateFormat = "MMM dd, yyyy 'at' hh:mm a"
    return formatter.string(from: date)
}

#Preview {
    OrderHistoryScreenView()
}
