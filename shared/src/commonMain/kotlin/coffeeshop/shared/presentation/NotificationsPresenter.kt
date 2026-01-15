package coffeeshop.shared.presentation

import coffeeshop.shared.data.model.Notification
import coffeeshop.shared.data.repository.CoffeeRepository

class NotificationsPresenter(private val repository: CoffeeRepository) {
    
    fun getNotifications(): List<Notification> {
        return repository.getNotifications()
    }
    
    fun clearNotification(notificationId: String) {
        repository.clearNotification(notificationId)
    }
    
    fun clearAllNotifications() {
        repository.clearAllNotifications()
    }
    
    fun markNotificationAsRead(notificationId: String) {
        repository.markNotificationAsRead(notificationId)
    }
    
    fun getUnreadCount(notifications: List<Notification>): Int {
        return notifications.count { !it.isRead }
    }
}
