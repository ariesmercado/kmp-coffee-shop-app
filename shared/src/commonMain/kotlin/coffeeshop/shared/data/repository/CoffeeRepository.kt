package coffeeshop.shared.data.repository

import coffeeshop.shared.data.model.Banner
import coffeeshop.shared.data.model.FeaturedDrink
import coffeeshop.shared.data.model.User

interface CoffeeRepository {
    fun getCurrentUser(): User
    fun getBanners(): List<Banner>
    fun getFeaturedDrinks(): List<FeaturedDrink>
}
