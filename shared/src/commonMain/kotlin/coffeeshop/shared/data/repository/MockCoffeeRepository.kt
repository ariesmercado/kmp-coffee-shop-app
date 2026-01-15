package coffeeshop.shared.data.repository

import coffeeshop.shared.data.model.Banner
import coffeeshop.shared.data.model.FeaturedDrink
import coffeeshop.shared.data.model.MenuCategory
import coffeeshop.shared.data.model.MenuItem
import coffeeshop.shared.data.model.User

class MockCoffeeRepository : CoffeeRepository {
    override fun getCurrentUser(): User {
        return User(name = "Coffee Lover", id = "user_001")
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
}
