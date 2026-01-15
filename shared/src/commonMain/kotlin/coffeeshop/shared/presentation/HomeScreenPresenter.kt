package coffeeshop.shared.presentation

import coffeeshop.shared.data.model.Banner
import coffeeshop.shared.data.model.FeaturedDrink
import coffeeshop.shared.data.repository.CoffeeRepository
import coffeeshop.shared.utils.getPersonalizedGreeting

class HomeScreenPresenter(private val repository: CoffeeRepository) {
    
    fun getGreeting(): String {
        val user = repository.getCurrentUser()
        return getPersonalizedGreeting(user.name)
    }
    
    fun getBanners(): List<Banner> {
        return repository.getBanners()
    }
    
    fun getFeaturedDrinks(): List<FeaturedDrink> {
        return repository.getFeaturedDrinks()
    }
}
