package coffeeshop.shared.data.repository

import coffeeshop.shared.data.model.Banner
import coffeeshop.shared.data.model.FeaturedDrink
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
}
