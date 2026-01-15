package coffeeshop.shared.presentation

import coffeeshop.shared.data.model.Order
import coffeeshop.shared.data.model.OrderItem

class OrderSummaryPresenter(private val defaultTaxRate: Double = 0.08) {
    
    /**
     * Creates an order with calculated subtotal, tax, and total
     */
    fun createOrder(orderId: String, items: List<OrderItem>, taxRate: Double = defaultTaxRate): Order {
        val subtotal = calculateSubtotal(items)
        val tax = calculateTax(subtotal, taxRate)
        val total = subtotal + tax
        
        return Order(
            id = orderId,
            items = items,
            subtotal = subtotal,
            taxRate = taxRate,
            tax = tax,
            total = total
        )
    }
    
    /**
     * Calculates the subtotal from a list of order items
     */
    fun calculateSubtotal(items: List<OrderItem>): Double {
        return items.sumOf { it.itemTotalPrice }
    }
    
    /**
     * Calculates tax based on subtotal and tax rate
     */
    fun calculateTax(subtotal: Double, taxRate: Double): Double {
        return subtotal * taxRate
    }
    
    /**
     * Gets a sample order for demonstration
     */
    fun getSampleOrder(): Order {
        val sampleItems = listOf(
            OrderItem(
                id = "order_item_1",
                menuItemId = "menu_4",
                name = "Caramel Macchiato",
                size = "Medium",
                addOns = listOf("Extra Shot", "Whipped Cream"),
                quantity = 2,
                basePrice = 4.95,
                itemTotalPrice = 11.90
            ),
            OrderItem(
                id = "order_item_2",
                menuItemId = "menu_3",
                name = "Cappuccino",
                size = "Large",
                addOns = listOf(),
                quantity = 1,
                basePrice = 4.45,
                itemTotalPrice = 4.45
            ),
            OrderItem(
                id = "order_item_3",
                menuItemId = "menu_17",
                name = "Cold Brew",
                size = "Medium",
                addOns = listOf("Vanilla Syrup"),
                quantity = 1,
                basePrice = 4.50,
                itemTotalPrice = 5.00
            )
        )
        
        return createOrder("order_001", sampleItems)
    }
}
