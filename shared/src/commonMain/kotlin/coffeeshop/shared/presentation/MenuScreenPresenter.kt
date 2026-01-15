package coffeeshop.shared.presentation

import coffeeshop.shared.data.model.MenuCategory
import coffeeshop.shared.data.model.MenuItem
import coffeeshop.shared.data.repository.CoffeeRepository

class MenuScreenPresenter(private val repository: CoffeeRepository) {
    
    fun getCategories(): List<MenuCategory> {
        return repository.getMenuCategories()
    }
    
    fun getMenuItems(): List<MenuItem> {
        return repository.getMenuItems()
    }
    
    fun getMenuItemsByCategory(categoryId: String): List<MenuItem> {
        return repository.getMenuItems().filter { it.categoryId == categoryId }
    }
    
    fun searchMenuItems(query: String): List<MenuItem> {
        if (query.isBlank()) {
            return repository.getMenuItems()
        }
        
        val searchQuery = query.lowercase().trim()
        return repository.getMenuItems().filter { item ->
            item.name.lowercase().contains(searchQuery) ||
            item.description.lowercase().contains(searchQuery)
        }
    }
    
    fun searchMenuItemsByCategory(query: String, categoryId: String?): List<MenuItem> {
        val items = if (categoryId != null) {
            getMenuItemsByCategory(categoryId)
        } else {
            repository.getMenuItems()
        }
        
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
