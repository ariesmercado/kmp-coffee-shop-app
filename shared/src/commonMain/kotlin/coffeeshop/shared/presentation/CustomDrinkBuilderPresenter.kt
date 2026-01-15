package coffeeshop.shared.presentation

import coffeeshop.shared.data.model.AddOn
import coffeeshop.shared.data.model.CustomDrink
import coffeeshop.shared.data.model.DrinkSize
import coffeeshop.shared.data.model.MenuItem
import coffeeshop.shared.data.repository.CoffeeRepository

class CustomDrinkBuilderPresenter(private val repository: CoffeeRepository) {
    
    fun getMenuItems(): List<MenuItem> {
        return repository.getMenuItems()
    }
    
    fun getAvailableAddOns(): List<AddOn> {
        return repository.getAvailableAddOns()
    }
    
    fun calculateTotalPrice(baseMenuItem: MenuItem, size: DrinkSize, addOns: List<AddOn>): Double {
        val basePrice = baseMenuItem.price * size.priceMultiplier
        val addOnsTotal = addOns.sumOf { it.price }
        return basePrice + addOnsTotal
    }
    
    fun saveCustomDrink(
        baseMenuItem: MenuItem,
        size: DrinkSize,
        addOns: List<AddOn>,
        customName: String? = null
    ): CustomDrink {
        val totalPrice = calculateTotalPrice(baseMenuItem, size, addOns)
        val drink = CustomDrink(
            id = "custom_${System.currentTimeMillis()}",
            baseMenuItem = baseMenuItem,
            size = size,
            addOns = addOns,
            totalPrice = totalPrice,
            customName = customName
        )
        repository.saveCustomDrink(drink)
        return drink
    }
    
    fun getSavedCustomDrinks(): List<CustomDrink> {
        return repository.getSavedCustomDrinks()
    }
    
    fun removeCustomDrink(drinkId: String) {
        repository.removeCustomDrink(drinkId)
    }
}
