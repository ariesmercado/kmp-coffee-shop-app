package coffeeshop.shared.data.repository

import coffeeshop.shared.data.model.SavedPaymentMethod

/**
 * Payment Methods Repository
 * 
 * Manages saved payment methods for users.
 * In production, this should:
 * - Encrypt payment method data before storage
 * - Store tokenized card information only
 * - Sync with backend API
 * - Implement proper authentication
 */
class PaymentMethodsRepository {
    
    private val savedMethods = mutableListOf<SavedPaymentMethod>()
    
    /**
     * Get all saved payment methods for the user
     */
    fun getSavedPaymentMethods(): List<SavedPaymentMethod> {
        return savedMethods.toList()
    }
    
    /**
     * Save a new payment method
     */
    fun savePaymentMethod(method: SavedPaymentMethod) {
        // Remove existing default if this is set as default
        if (method.isDefault) {
            savedMethods.forEachIndexed { index, savedMethod ->
                if (savedMethod.isDefault) {
                    savedMethods[index] = savedMethod.copy(isDefault = false)
                }
            }
        }
        savedMethods.add(method)
    }
    
    /**
     * Remove a saved payment method
     */
    fun removePaymentMethod(methodId: String) {
        savedMethods.removeAll { it.id == methodId }
    }
    
    /**
     * Get default payment method
     */
    fun getDefaultPaymentMethod(): SavedPaymentMethod? {
        return savedMethods.firstOrNull { it.isDefault }
    }
    
    /**
     * Set a payment method as default
     */
    fun setDefaultPaymentMethod(methodId: String) {
        savedMethods.forEachIndexed { index, savedMethod ->
            savedMethods[index] = savedMethod.copy(isDefault = savedMethod.id == methodId)
        }
    }
}
