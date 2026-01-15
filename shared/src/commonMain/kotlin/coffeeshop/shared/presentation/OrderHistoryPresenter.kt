package coffeeshop.shared.presentation

import coffeeshop.shared.data.model.OrderHistory
import coffeeshop.shared.data.repository.CoffeeRepository

class OrderHistoryPresenter(private val repository: CoffeeRepository) {
    
    fun getOrderHistory(): List<OrderHistory> {
        return repository.getOrderHistory()
    }
    
    fun filterOrdersByDateRange(orders: List<OrderHistory>, startDate: Long, endDate: Long): List<OrderHistory> {
        return orders.filter { it.orderDate in startDate..endDate }
    }
    
    fun searchOrders(orders: List<OrderHistory>, query: String): List<OrderHistory> {
        if (query.isBlank()) return orders
        
        val lowerQuery = query.lowercase()
        return orders.filter { order ->
            order.items.any { item -> 
                item.name.lowercase().contains(lowerQuery)
            } || order.id.lowercase().contains(lowerQuery)
        }
    }
}
