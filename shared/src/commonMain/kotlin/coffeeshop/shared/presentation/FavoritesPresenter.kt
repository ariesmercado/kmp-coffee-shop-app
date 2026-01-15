package coffeeshop.shared.presentation

import coffeeshop.shared.data.model.FavoriteDrink
import coffeeshop.shared.data.repository.CoffeeRepository

/**
 * Presenter class for managing favorites business logic.
 * Acts as an intermediary between the UI layer and the repository layer,
 * handling all favorite-related operations and state management.
 */
class FavoritesPresenter(private val repository: CoffeeRepository) {
    
    fun getFavoriteDrinks(): List<FavoriteDrink> {
        return repository.getFavoriteDrinks()
    }
    
    fun getFavoriteIds(): Set<String> {
        return repository.getFavoriteDrinks().map { it.id }.toSet()
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
