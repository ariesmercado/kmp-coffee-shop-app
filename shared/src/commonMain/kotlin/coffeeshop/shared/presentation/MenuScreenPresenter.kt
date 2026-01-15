package coffeeshop.shared.presentation

import coffeeshop.shared.data.model.MenuCategory
import coffeeshop.shared.data.model.MenuItem
import coffeeshop.shared.data.repository.CoffeeRepository

class MenuScreenPresenter(private val repository: CoffeeRepository) {
    
    private val allMenuItems: List<MenuItem> by lazy {
        repository.getMenuItems()
    }
    
    fun getCategories(): List<MenuCategory> {
        return repository.getMenuCategories()
    }
    
    fun getMenuItems(): List<MenuItem> {
        return allMenuItems
    }
    
    fun getMenuItemsByCategory(categoryId: String): List<MenuItem> {
        return allMenuItems.filter { it.categoryId == categoryId }
    }
    
    fun searchMenuItems(query: String): List<MenuItem> {
        return filterMenuItemsBySearch(allMenuItems, query)
    }
    
    fun searchMenuItemsByCategory(query: String, categoryId: String?): List<MenuItem> {
        val items = if (categoryId != null) {
            getMenuItemsByCategory(categoryId)
        } else {
            allMenuItems
        }
        
        return filterMenuItemsBySearch(items, query)
    }
    
    private fun filterMenuItemsBySearch(items: List<MenuItem>, query: String): List<MenuItem> {
        if (query.isBlank()) {
            return items
        }
        
        val searchQuery = query.lowercase().trim()
        return items.filter { item ->
            item.name.lowercase().contains(searchQuery) ||
            item.description.lowercase().contains(searchQuery)
        }
    }
}
