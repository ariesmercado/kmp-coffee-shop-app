package coffeeshop.shared.presentation

import coffeeshop.shared.data.model.FavoriteDrink
import coffeeshop.shared.data.repository.CoffeeRepository

class FavoritesPresenter(private val repository: CoffeeRepository) {
    
    fun getFavoriteDrinks(): List<FavoriteDrink> {
        return repository.getFavoriteDrinks()
    }
    
    fun addFavoriteDrink(drink: FavoriteDrink) {
        repository.addFavoriteDrink(drink)
    }
    
    fun removeFavoriteDrink(drinkId: String) {
        repository.removeFavoriteDrink(drinkId)
    }
    
    fun toggleFavorite(drink: FavoriteDrink) {
        if (repository.isFavorite(drink.id)) {
            repository.removeFavoriteDrink(drink.id)
        } else {
            repository.addFavoriteDrink(drink)
        }
    }
    
    fun isFavorite(drinkId: String): Boolean {
        return repository.isFavorite(drinkId)
    }
}
