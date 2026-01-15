package coffeeshop.app.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coffeeshop.app.ui.theme.*
import coffeeshop.shared.data.model.Notification
import coffeeshop.shared.data.model.NotificationType
import coffeeshop.shared.data.repository.MockCoffeeRepository
import coffeeshop.shared.presentation.NotificationsPresenter
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun NotificationsScreen(
    presenter: NotificationsPresenter = remember { NotificationsPresenter(MockCoffeeRepository()) }
) {
    var notifications by remember { mutableStateOf(presenter.getNotifications()) }
    var showClearAllDialog by remember { mutableStateOf(false) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        // Header
        NotificationsHeader(
            unreadCount = presenter.getUnreadCount(notifications),
            onClearAll = { showClearAllDialog = true }
        )
        
        // Notifications List
        if (notifications.isEmpty()) {
            EmptyNotificationsView()
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(notifications, key = { it.id }) { notification ->
                    NotificationCard(
                        notification = notification,
                        onClear = {
                            presenter.clearNotification(notification.id)
                            notifications = presenter.getNotifications()
                        }
                    )
                }
            }
        }
    }
    
    // Clear All Dialog
    if (showClearAllDialog) {
        AlertDialog(
            onDismissRequest = { showClearAllDialog = false },
            title = { Text("Clear All Notifications") },
            text = { Text("Are you sure you want to clear all notifications? This action cannot be undone.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        presenter.clearAllNotifications()
                        notifications = presenter.getNotifications()
                        showClearAllDialog = false
                    }
                ) {
                    Text("Clear All", color = MaterialTheme.colors.primary)
                }
            },
            dismissButton = {
                TextButton(onClick = { showClearAllDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun NotificationsHeader(
    unreadCount: Int,
    onClearAll: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.primary)
            .padding(24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Notifications",
                    style = MaterialTheme.typography.h1.copy(
                        color = MaterialTheme.colors.onPrimary,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = if (unreadCount > 0) "$unreadCount new notification${if (unreadCount > 1) "s" else ""}" 
                           else "No new notifications",
                    style = MaterialTheme.typography.body1.copy(
                        color = MaterialTheme.colors.onPrimary.copy(alpha = 0.9f),
                        fontSize = 16.sp
                    )
                )
            }
            
            // Clear All Button
            IconButton(
                onClick = onClearAll,
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colors.onPrimary.copy(alpha = 0.2f),
                        shape = CircleShape
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Clear All",
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        }
    }
}

@Composable
fun NotificationCard(
    notification: Notification,
    onClear: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = 2.dp,
        backgroundColor = if (notification.isRead) 
            MaterialTheme.colors.surface 
        else 
            MaterialTheme.colors.primary.copy(alpha = 0.05f)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                // Type Badge and Timestamp
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    NotificationTypeBadge(type = notification.type)
                    
                    Text(
                        text = formatTimestamp(notification.timestamp),
                        style = MaterialTheme.typography.caption.copy(
                            color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
                            fontSize = 12.sp
                        )
                    )
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Title
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (!notification.isRead) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(
                                    color = MaterialTheme.colors.primary,
                                    shape = CircleShape
                                )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    Text(
                        text = notification.title,
                        style = MaterialTheme.typography.h3.copy(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
                
                Spacer(modifier = Modifier.height(4.dp))
                
                // Message
                Text(
                    text = notification.message,
                    style = MaterialTheme.typography.body2.copy(
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f),
                        lineHeight = 20.sp
                    )
                )
            }
            
            // Clear Button
            IconButton(
                onClick = onClear,
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "Clear notification",
                    tint = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
fun NotificationTypeBadge(type: NotificationType) {
    val (backgroundColor, textColor, label) = when (type) {
        NotificationType.DEAL -> Triple(
            Color(0xFFFF9800).copy(alpha = 0.2f),
            Color(0xFFE65100),
            "Deal"
        )
        NotificationType.PROMOTION -> Triple(
            Color(0xFF9C27B0).copy(alpha = 0.2f),
            Color(0xFF6A1B9A),
            "Promotion"
        )
        NotificationType.ORDER_ACCEPTED -> Triple(
            Color(0xFF2196F3).copy(alpha = 0.2f),
            Color(0xFF1565C0),
            "Order"
        )
        NotificationType.ORDER_SHIPPED -> Triple(
            Color(0xFFFF9800).copy(alpha = 0.2f),
            Color(0xFFE65100),
            "Shipping"
        )
        NotificationType.ORDER_DELIVERED -> Triple(
            Color(0xFF4CAF50).copy(alpha = 0.2f),
            Color(0xFF2E7D32),
            "Delivered"
        )
        NotificationType.GENERAL -> Triple(
            Color(0xFF607D8B).copy(alpha = 0.2f),
            Color(0xFF37474F),
            "Info"
        )
    }
    
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = backgroundColor
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.caption.copy(
                color = textColor,
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp
            )
        )
    }
}

@Composable
fun EmptyNotificationsView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "🔔",
            fontSize = 64.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "No notifications yet",
            style = MaterialTheme.typography.h3,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "We'll notify you about deals, promotions, and order updates",
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.4f)
        )
    }
}

private fun formatTimestamp(timestamp: Long): String {
    val now = System.currentTimeMillis()
    val diff = now - timestamp
    
    return when {
        diff < 60 * 1000 -> "Just now"
        diff < 60 * 60 * 1000 -> "${diff / (60 * 1000)}m ago"
        diff < 24 * 60 * 60 * 1000 -> "${diff / (60 * 60 * 1000)}h ago"
        diff < 7 * 24 * 60 * 60 * 1000 -> "${diff / (24 * 60 * 60 * 1000)}d ago"
        else -> {
            val sdf = SimpleDateFormat("MMM dd", Locale.getDefault())
            sdf.format(Date(timestamp))
        }
    }
}
