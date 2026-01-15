package coffeeshop.shared.data.repository

import coffeeshop.shared.data.model.Banner
import coffeeshop.shared.data.model.FavoriteDrink
import coffeeshop.shared.data.model.FeaturedDrink
import coffeeshop.shared.data.model.MenuCategory
import coffeeshop.shared.data.model.MenuItem
import coffeeshop.shared.data.model.OrderHistory
import coffeeshop.shared.data.model.RewardTransaction
import coffeeshop.shared.data.model.User

interface CoffeeRepository {
    fun getCurrentUser(): User
    fun getBanners(): List<Banner>
    fun getFeaturedDrinks(): List<FeaturedDrink>
    fun getMenuCategories(): List<MenuCategory>
    fun getMenuItems(): List<MenuItem>
    fun getOrderHistory(): List<OrderHistory>
    fun getFavoriteDrinks(): List<FavoriteDrink>
    fun addFavoriteDrink(drink: FavoriteDrink)
    fun removeFavoriteDrink(drinkId: String)
    fun isFavorite(drinkId: String): Boolean
    fun getRewardPointsBalance(): Int
    fun getRewardTransactions(): List<RewardTransaction>
    fun addRewardPoints(points: Int, description: String)
    fun redeemRewardPoints(points: Int, description: String): Boolean
}
