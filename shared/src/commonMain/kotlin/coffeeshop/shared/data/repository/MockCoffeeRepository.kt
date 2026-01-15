package coffeeshop.shared.data.repository

import coffeeshop.shared.data.model.Banner
import coffeeshop.shared.data.model.FavoriteDrink
import coffeeshop.shared.data.model.FeaturedDrink
import coffeeshop.shared.data.model.MenuCategory
import coffeeshop.shared.data.model.MenuItem
import coffeeshop.shared.data.model.Notification
import coffeeshop.shared.data.model.NotificationType
import coffeeshop.shared.data.model.OrderHistory
import coffeeshop.shared.data.model.OrderItem
import coffeeshop.shared.data.model.RewardTransaction
import coffeeshop.shared.data.model.RewardTransactionType
import coffeeshop.shared.data.model.User

class MockCoffeeRepository : CoffeeRepository {
    
    private val favoriteDrinks = mutableListOf<FavoriteDrink>()
    private val favoriteIds = mutableSetOf<String>()
    private var rewardPointsBalance = 325 // Starting with some points for demo
    private val rewardTransactions = mutableListOf<RewardTransaction>()
    private val notifications = mutableListOf<Notification>()
    
    init {
        // Add some sample favorite drinks for testing
        val sampleFavorites = listOf(
            FavoriteDrink(
                id = "fav_1",
                name = "Caramel Macchiato",
                description = "Espresso with vanilla syrup and steamed milk, topped with caramel",
                price = 4.95,
                imageUrl = "caramel_macchiato.jpg",
                rating = 4.8
            ),
            FavoriteDrink(
                id = "fav_2",
                name = "Mocha Latte",
                description = "Chocolate and espresso with steamed milk and whipped cream",
                price = 5.25,
                imageUrl = "mocha_latte.jpg",
                rating = 4.9
            ),
            FavoriteDrink(
                id = "fav_3",
                name = "Cold Brew",
                description = "Smooth cold-steeped coffee served over ice",
                price = 4.50,
                imageUrl = "cold_brew.jpg",
                rating = 4.6
            )
        )
        favoriteDrinks.addAll(sampleFavorites)
        favoriteIds.addAll(sampleFavorites.map { it.id })
        
        // Add some sample reward transactions
        val currentTime = System.currentTimeMillis()
        val oneDayMs = 24 * 60 * 60 * 1000L
        
        rewardTransactions.addAll(listOf(
            RewardTransaction(
                id = "reward_1",
                type = RewardTransactionType.EARNED,
                points = 85,
                timestamp = currentTime - (1 * oneDayMs),
                details = "Purchase at Coffee Shop - $16.90"
            ),
            RewardTransaction(
                id = "reward_2",
                type = RewardTransactionType.EARNED,
                points = 25,
                timestamp = currentTime - (3 * oneDayMs),
                details = "Purchase at Coffee Shop - $4.86"
            ),
            RewardTransaction(
                id = "reward_3",
                type = RewardTransactionType.REDEEMED,
                points = -100,
                timestamp = currentTime - (5 * oneDayMs),
                details = "Redeemed for $5 discount"
            ),
            RewardTransaction(
                id = "reward_4",
                type = RewardTransactionType.EARNED,
                points = 115,
                timestamp = currentTime - (7 * oneDayMs),
                details = "Purchase at Coffee Shop - $23.38"
            ),
            RewardTransaction(
                id = "reward_5",
                type = RewardTransactionType.EARNED,
                points = 200,
                timestamp = currentTime - (10 * oneDayMs),
                details = "Welcome Bonus"
            )
        ))
        
        // Add some sample notifications
        notifications.addAll(listOf(
            Notification(
                id = "notif_1",
                type = NotificationType.DEAL,
                title = "☕ Happy Hour Deal!",
                message = "Get 20% off all Frappuccinos from 2-5 PM today. Don't miss out on this refreshing deal!",
                timestamp = currentTime - (2 * 60 * 60 * 1000L), // 2 hours ago
                isRead = false
            ),
            Notification(
                id = "notif_2",
                type = NotificationType.ORDER_DELIVERED,
                title = "Order Delivered",
                message = "Your order #ORD123 has been delivered. Enjoy your coffee!",
                timestamp = currentTime - (5 * 60 * 60 * 1000L), // 5 hours ago
                isRead = false
            ),
            Notification(
                id = "notif_3",
                type = NotificationType.PROMOTION,
                title = "🎉 New Menu Items!",
                message = "Try our new Pumpkin Spice Latte and Maple Pecan Macchiato. Limited time only!",
                timestamp = currentTime - (1 * oneDayMs),
                isRead = true
            ),
            Notification(
                id = "notif_4",
                type = NotificationType.ORDER_SHIPPED,
                title = "Order On The Way",
                message = "Your order #ORD122 is on its way. Expected delivery in 15 minutes.",
                timestamp = currentTime - (2 * oneDayMs),
                isRead = true
            ),
            Notification(
                id = "notif_5",
                type = NotificationType.ORDER_ACCEPTED,
                title = "Order Confirmed",
                message = "We've received your order #ORD124. It will be ready soon!",
                timestamp = currentTime - (3 * oneDayMs),
                isRead = true
            ),
            Notification(
                id = "notif_6",
                type = NotificationType.DEAL,
                title = "☕ Weekend Special",
                message = "Buy one coffee, get one 50% off this weekend. Valid for all drinks!",
                timestamp = currentTime - (4 * oneDayMs),
                isRead = true
            )
        ))
    }
    
    override fun getCurrentUser(): User {
        return User(name = "Coffee Lover", id = "user_001", rewardPoints = rewardPointsBalance)
    }

    override fun getBanners(): List<Banner> {
        return listOf(
            Banner(
                id = "banner_1",
                title = "New Season Specials",
                subtitle = "Try our winter warmers collection",
                imageUrl = "banner_1.jpg",
                backgroundColor = "#8B4513"
            ),
            Banner(
                id = "banner_2",
                title = "Buy 2 Get 1 Free",
                subtitle = "On all espresso drinks today",
                imageUrl = "banner_2.jpg",
                backgroundColor = "#A0522D"
            ),
            Banner(
                id = "banner_3",
                title = "Loyalty Rewards",
                subtitle = "Earn points with every purchase",
                imageUrl = "banner_3.jpg",
                backgroundColor = "#6F4E37"
            )
        )
    }

    override fun getFeaturedDrinks(): List<FeaturedDrink> {
        return listOf(
            FeaturedDrink(
                id = "drink_1",
                name = "Caramel Macchiato",
                description = "Espresso with vanilla syrup and steamed milk, topped with caramel",
                price = 4.95,
                imageUrl = "caramel_macchiato.jpg",
                rating = 4.8
            ),
            FeaturedDrink(
                id = "drink_2",
                name = "Cappuccino",
                description = "Rich espresso with frothed milk and a dusting of cocoa",
                price = 3.95,
                imageUrl = "cappuccino.jpg",
                rating = 4.7
            ),
            FeaturedDrink(
                id = "drink_3",
                name = "Mocha Latte",
                description = "Chocolate and espresso with steamed milk and whipped cream",
                price = 5.25,
                imageUrl = "mocha_latte.jpg",
                rating = 4.9
            ),
            FeaturedDrink(
                id = "drink_4",
                name = "Cold Brew",
                description = "Smooth cold-steeped coffee served over ice",
                price = 4.50,
                imageUrl = "cold_brew.jpg",
                rating = 4.6
            ),
            FeaturedDrink(
                id = "drink_5",
                name = "Vanilla Latte",
                description = "Espresso with vanilla syrup and steamed milk",
                price = 4.45,
                imageUrl = "vanilla_latte.jpg",
                rating = 4.5
            ),
            FeaturedDrink(
                id = "drink_6",
                name = "Americano",
                description = "Espresso with hot water for a rich, full-bodied taste",
                price = 3.25,
                imageUrl = "americano.jpg",
                rating = 4.4
            )
        )
    }

    override fun getMenuCategories(): List<MenuCategory> {
        return listOf(
            MenuCategory(
                id = "espresso",
                name = "Espresso",
                description = "Classic espresso-based drinks"
            ),
            MenuCategory(
                id = "blended",
                name = "Blended",
                description = "Smooth blended beverages"
            ),
            MenuCategory(
                id = "hot",
                name = "Hot",
                description = "Warm and comforting drinks"
            ),
            MenuCategory(
                id = "iced",
                name = "Iced",
                description = "Refreshing cold beverages"
            )
        )
    }

    override fun getMenuItems(): List<MenuItem> {
        return listOf(
            // Espresso Category
            MenuItem(
                id = "menu_1",
                name = "Espresso",
                description = "Rich and bold single shot of espresso",
                price = 2.50,
                imageUrl = "espresso.jpg",
                rating = 4.7,
                categoryId = "espresso"
            ),
            MenuItem(
                id = "menu_2",
                name = "Americano",
                description = "Espresso with hot water for a rich, full-bodied taste",
                price = 3.25,
                imageUrl = "americano.jpg",
                rating = 4.4,
                categoryId = "espresso"
            ),
            MenuItem(
                id = "menu_3",
                name = "Cappuccino",
                description = "Rich espresso with frothed milk and a dusting of cocoa",
                price = 3.95,
                imageUrl = "cappuccino.jpg",
                rating = 4.7,
                categoryId = "espresso"
            ),
            MenuItem(
                id = "menu_4",
                name = "Caramel Macchiato",
                description = "Espresso with vanilla syrup and steamed milk, topped with caramel",
                price = 4.95,
                imageUrl = "caramel_macchiato.jpg",
                rating = 4.8,
                categoryId = "espresso"
            ),
            MenuItem(
                id = "menu_5",
                name = "Flat White",
                description = "Velvety smooth espresso with microfoam milk",
                price = 4.25,
                imageUrl = "flat_white.jpg",
                rating = 4.6,
                categoryId = "espresso"
            ),
            // Blended Category
            MenuItem(
                id = "menu_6",
                name = "Mocha Frappuccino",
                description = "Blended coffee with chocolate and topped with whipped cream",
                price = 5.50,
                imageUrl = "mocha_frappuccino.jpg",
                rating = 4.9,
                categoryId = "blended"
            ),
            MenuItem(
                id = "menu_7",
                name = "Caramel Frappuccino",
                description = "Blended coffee with caramel and whipped cream topping",
                price = 5.50,
                imageUrl = "caramel_frappuccino.jpg",
                rating = 4.8,
                categoryId = "blended"
            ),
            MenuItem(
                id = "menu_8",
                name = "Vanilla Bean Frappuccino",
                description = "Creamy blended vanilla drink with whipped cream",
                price = 5.25,
                imageUrl = "vanilla_frappuccino.jpg",
                rating = 4.7,
                categoryId = "blended"
            ),
            MenuItem(
                id = "menu_9",
                name = "Java Chip Frappuccino",
                description = "Blended coffee with chocolate chips and mocha sauce",
                price = 5.75,
                imageUrl = "java_chip_frappuccino.jpg",
                rating = 4.9,
                categoryId = "blended"
            ),
            // Hot Category
            MenuItem(
                id = "menu_10",
                name = "Vanilla Latte",
                description = "Espresso with vanilla syrup and steamed milk",
                price = 4.45,
                imageUrl = "vanilla_latte.jpg",
                rating = 4.5,
                categoryId = "hot"
            ),
            MenuItem(
                id = "menu_11",
                name = "Mocha Latte",
                description = "Chocolate and espresso with steamed milk and whipped cream",
                price = 5.25,
                imageUrl = "mocha_latte.jpg",
                rating = 4.9,
                categoryId = "hot"
            ),
            MenuItem(
                id = "menu_12",
                name = "Hot Chocolate",
                description = "Rich chocolate drink with steamed milk and whipped cream",
                price = 3.75,
                imageUrl = "hot_chocolate.jpg",
                rating = 4.6,
                categoryId = "hot"
            ),
            MenuItem(
                id = "menu_13",
                name = "Chai Tea Latte",
                description = "Spiced black tea with steamed milk and honey",
                price = 4.25,
                imageUrl = "chai_latte.jpg",
                rating = 4.5,
                categoryId = "hot"
            ),
            MenuItem(
                id = "menu_14",
                name = "Green Tea Latte",
                description = "Matcha green tea with steamed milk",
                price = 4.50,
                imageUrl = "green_tea_latte.jpg",
                rating = 4.4,
                categoryId = "hot"
            ),
            // Iced Category
            MenuItem(
                id = "menu_15",
                name = "Iced Americano",
                description = "Chilled espresso with cold water over ice",
                price = 3.50,
                imageUrl = "iced_americano.jpg",
                rating = 4.5,
                categoryId = "iced"
            ),
            MenuItem(
                id = "menu_16",
                name = "Iced Latte",
                description = "Espresso with cold milk over ice",
                price = 4.25,
                imageUrl = "iced_latte.jpg",
                rating = 4.6,
                categoryId = "iced"
            ),
            MenuItem(
                id = "menu_17",
                name = "Cold Brew",
                description = "Smooth cold-steeped coffee served over ice",
                price = 4.50,
                imageUrl = "cold_brew.jpg",
                rating = 4.6,
                categoryId = "iced"
            ),
            MenuItem(
                id = "menu_18",
                name = "Iced Caramel Macchiato",
                description = "Iced espresso with vanilla, milk and caramel drizzle",
                price = 5.25,
                imageUrl = "iced_caramel_macchiato.jpg",
                rating = 4.8,
                categoryId = "iced"
            ),
            MenuItem(
                id = "menu_19",
                name = "Iced Mocha",
                description = "Chilled chocolate espresso with cold milk",
                price = 4.95,
                imageUrl = "iced_mocha.jpg",
                rating = 4.7,
                categoryId = "iced"
            ),
            MenuItem(
                id = "menu_20",
                name = "Iced Chai Latte",
                description = "Spiced tea with cold milk over ice",
                price = 4.50,
                imageUrl = "iced_chai_latte.jpg",
                rating = 4.5,
                categoryId = "iced"
            )
        )
    }

    override fun getOrderHistory(): List<OrderHistory> {
        // Creating timestamps for the past 30 days
        val currentTime = System.currentTimeMillis()
        val oneDayMs = 24 * 60 * 60 * 1000L
        
        // Note: Add-ons pricing: Extra Shot = $0.75, Whipped Cream = $0.50, Almond Milk = $0.50, Extra Caramel = $0.50
        
        return listOf(
            OrderHistory(
                id = "order_001",
                orderDate = currentTime - (1 * oneDayMs), // 1 day ago
                items = listOf(
                    OrderItem(
                        id = "item_001",
                        menuItemId = "menu_4",
                        name = "Caramel Macchiato",
                        size = "Large",
                        addOns = listOf("Extra Shot"),
                        quantity = 2,
                        basePrice = 4.95,
                        itemTotalPrice = 11.40 // (4.95 + 0.75) * 2
                    ),
                    OrderItem(
                        id = "item_002",
                        menuItemId = "menu_12",
                        name = "Hot Chocolate",
                        size = "Medium",
                        addOns = listOf("Whipped Cream"),
                        quantity = 1,
                        basePrice = 3.75,
                        itemTotalPrice = 4.25
                    )
                ),
                subtotal = 15.65,
                tax = 1.25,
                total = 16.90,
                status = "Completed"
            ),
            OrderHistory(
                id = "order_002",
                orderDate = currentTime - (3 * oneDayMs), // 3 days ago
                items = listOf(
                    OrderItem(
                        id = "item_003",
                        menuItemId = "menu_17",
                        name = "Cold Brew",
                        size = "Large",
                        addOns = emptyList(),
                        quantity = 1,
                        basePrice = 4.50,
                        itemTotalPrice = 4.50
                    )
                ),
                subtotal = 4.50,
                tax = 0.36,
                total = 4.86,
                status = "Completed"
            ),
            OrderHistory(
                id = "order_003",
                orderDate = currentTime - (5 * oneDayMs), // 5 days ago
                items = listOf(
                    OrderItem(
                        id = "item_004",
                        menuItemId = "menu_3",
                        name = "Cappuccino",
                        size = "Medium",
                        addOns = emptyList(),
                        quantity = 1,
                        basePrice = 3.95,
                        itemTotalPrice = 3.95
                    ),
                    OrderItem(
                        id = "item_005",
                        menuItemId = "menu_10",
                        name = "Vanilla Latte",
                        size = "Large",
                        addOns = listOf("Extra Shot", "Almond Milk"),
                        quantity = 1,
                        basePrice = 4.45,
                        itemTotalPrice = 5.70 // 4.45 + 0.75 + 0.50
                    ),
                    OrderItem(
                        id = "item_006",
                        menuItemId = "menu_6",
                        name = "Mocha Frappuccino",
                        size = "Large",
                        addOns = listOf("Whipped Cream"),
                        quantity = 2,
                        basePrice = 5.50,
                        itemTotalPrice = 12.00 // (5.50 + 0.50) * 2
                    )
                ),
                subtotal = 21.65,
                tax = 1.73,
                total = 23.38,
                status = "Completed"
            ),
            OrderHistory(
                id = "order_004",
                orderDate = currentTime - (7 * oneDayMs), // 7 days ago
                items = listOf(
                    OrderItem(
                        id = "item_007",
                        menuItemId = "menu_2",
                        name = "Americano",
                        size = "Medium",
                        addOns = emptyList(),
                        quantity = 1,
                        basePrice = 3.25,
                        itemTotalPrice = 3.25
                    )
                ),
                subtotal = 3.25,
                tax = 0.26,
                total = 3.51,
                status = "Completed"
            ),
            OrderHistory(
                id = "order_005",
                orderDate = currentTime - (10 * oneDayMs), // 10 days ago
                items = listOf(
                    OrderItem(
                        id = "item_008",
                        menuItemId = "menu_11",
                        name = "Mocha Latte",
                        size = "Large",
                        addOns = listOf("Whipped Cream"),
                        quantity = 1,
                        basePrice = 5.25,
                        itemTotalPrice = 5.75
                    ),
                    OrderItem(
                        id = "item_009",
                        menuItemId = "menu_13",
                        name = "Chai Tea Latte",
                        size = "Medium",
                        addOns = emptyList(),
                        quantity = 1,
                        basePrice = 4.25,
                        itemTotalPrice = 4.25
                    )
                ),
                subtotal = 10.00,
                tax = 0.80,
                total = 10.80,
                status = "Completed"
            ),
            OrderHistory(
                id = "order_006",
                orderDate = currentTime - (14 * oneDayMs), // 14 days ago
                items = listOf(
                    OrderItem(
                        id = "item_010",
                        menuItemId = "menu_18",
                        name = "Iced Caramel Macchiato",
                        size = "Large",
                        addOns = listOf("Extra Shot"),
                        quantity = 1,
                        basePrice = 5.25,
                        itemTotalPrice = 6.00
                    )
                ),
                subtotal = 6.00,
                tax = 0.48,
                total = 6.48,
                status = "Completed"
            ),
            OrderHistory(
                id = "order_007",
                orderDate = currentTime - (20 * oneDayMs), // 20 days ago
                items = listOf(
                    OrderItem(
                        id = "item_011",
                        menuItemId = "menu_4",
                        name = "Caramel Macchiato",
                        size = "Medium",
                        addOns = emptyList(),
                        quantity = 1,
                        basePrice = 4.95,
                        itemTotalPrice = 4.95
                    ),
                    OrderItem(
                        id = "item_012",
                        menuItemId = "menu_7",
                        name = "Caramel Frappuccino",
                        size = "Large",
                        addOns = listOf("Whipped Cream", "Extra Caramel"),
                        quantity = 1,
                        basePrice = 5.50,
                        itemTotalPrice = 6.50
                    )
                ),
                subtotal = 11.45,
                tax = 0.92,
                total = 12.37,
                status = "Completed"
            ),
            OrderHistory(
                id = "order_008",
                orderDate = currentTime - (25 * oneDayMs), // 25 days ago
                items = listOf(
                    OrderItem(
                        id = "item_013",
                        menuItemId = "menu_15",
                        name = "Iced Americano",
                        size = "Large",
                        addOns = emptyList(),
                        quantity = 2,
                        basePrice = 3.50,
                        itemTotalPrice = 7.00
                    )
                ),
                subtotal = 7.00,
                tax = 0.56,
                total = 7.56,
                status = "Completed"
            )
        ).sortedByDescending { it.orderDate } // Most recent orders first
    }
    
    override fun getFavoriteDrinks(): List<FavoriteDrink> {
        return favoriteDrinks.toList()
    }
    
    override fun addFavoriteDrink(drink: FavoriteDrink) {
        favoriteDrinks.add(drink)
        favoriteIds.add(drink.id)
    }
    
    override fun removeFavoriteDrink(drinkId: String) {
        favoriteDrinks.removeAll { it.id == drinkId }
        favoriteIds.remove(drinkId)
    }
    
    override fun isFavorite(drinkId: String): Boolean {
        return favoriteIds.contains(drinkId)
    }
    
    override fun getRewardPointsBalance(): Int {
        return rewardPointsBalance
    }
    
    override fun getRewardTransactions(): List<RewardTransaction> {
        return rewardTransactions.sortedByDescending { it.timestamp }
    }
    
    override fun addRewardPoints(points: Int, description: String) {
        rewardPointsBalance += points
        rewardTransactions.add(
            RewardTransaction(
                id = "reward_${System.currentTimeMillis()}",
                type = RewardTransactionType.EARNED,
                points = points,
                timestamp = System.currentTimeMillis(),
                details = description
            )
        )
    }
    
    override fun redeemRewardPoints(points: Int, description: String): Boolean {
        if (rewardPointsBalance < points) {
            return false
        }
        rewardPointsBalance -= points
        rewardTransactions.add(
            RewardTransaction(
                id = "reward_${System.currentTimeMillis()}",
                type = RewardTransactionType.REDEEMED,
                points = -points,
                timestamp = System.currentTimeMillis(),
                details = description
            )
        )
        return true
    }
    
    override fun getNotifications(): List<Notification> {
        return notifications.sortedByDescending { it.timestamp }
    }
    
    override fun clearNotification(notificationId: String) {
        notifications.removeAll { it.id == notificationId }
    }
    
    override fun clearAllNotifications() {
        notifications.clear()
    }
    
    override fun markNotificationAsRead(notificationId: String) {
        val index = notifications.indexOfFirst { it.id == notificationId }
        if (index != -1) {
            notifications[index] = notifications[index].copy(isRead = true)
        }
    }
}
