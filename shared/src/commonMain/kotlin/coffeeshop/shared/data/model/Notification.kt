package coffeeshop.shared.data.model

data class Notification(
    val id: String,
    val type: NotificationType,
    val title: String,
    val message: String,
    val timestamp: Long,
    val isRead: Boolean = false
)

enum class NotificationType {
    DEAL,
    PROMOTION,
    ORDER_ACCEPTED,
    ORDER_SHIPPED,
    ORDER_DELIVERED,
    GENERAL
}
