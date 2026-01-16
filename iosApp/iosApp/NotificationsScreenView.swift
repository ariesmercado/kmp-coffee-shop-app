import SwiftUI
import shared
import Combine

struct NotificationsScreenView: View {
    @StateObject private var viewModel = NotificationsViewModel()
    @State private var showClearAllAlert = false
    
    var body: some View {
        VStack(spacing: 0) {
            NotificationsHeaderView(
                unreadCount: viewModel.unreadCount,
                onClearAll: { showClearAllAlert = true }
            )
            
            // Notifications List
            if viewModel.notifications.isEmpty {
                EmptyNotificationsViewIOS()
            } else {
                ScrollView {
                    LazyVStack(spacing: 12) {
                        ForEach(viewModel.notifications, id: \.id) { notification in
                            NotificationCardView(
                                notification: notification,
                                onClear: {
                                    viewModel.clearNotification(notificationId: notification.id)
                                }
                            )
                        }
                    }
                    .padding(.horizontal, 16)
                    .padding(.vertical, 8)
                }
            }
        }
        .background(CoffeeColors.creamyWhite)
        .edgesIgnoringSafeArea(.top)
        .alert("Clear All Notifications", isPresented: $showClearAllAlert) {
            Button("Cancel", role: .cancel) { }
            Button("Clear All", role: .destructive) {
                viewModel.clearAllNotifications()
            }
        } message: {
            Text("Are you sure you want to clear all notifications? This action cannot be undone.")
        }
    }
}

struct NotificationsHeaderView: View {
    let unreadCount: Int
    let onClearAll: () -> Void
    
    var body: some View {
        HStack(alignment: .center) {
            VStack(alignment: .leading, spacing: 4) {
                Text("Notifications")
                    .font(.system(size: 28, weight: .bold))
                    .foregroundColor(.white)
                
                Text(unreadCount > 0 
                     ? "\(unreadCount) new notification\(unreadCount > 1 ? "s" : "")"
                     : "No new notifications")
                    .font(.system(size: 16))
                    .foregroundColor(.white.opacity(0.9))
            }
            
            Spacer()
            
            // Clear All Button
            Button(action: onClearAll) {
                Image(systemName: "trash")
                    .foregroundColor(.white)
                    .font(.system(size: 18))
                    .padding(12)
                    .background(Color.white.opacity(0.2))
                    .clipShape(Circle())
            }
        }
        .padding(24)
        .background(CoffeeColors.coffeeBrown)
    }
}

struct NotificationCardView: View {
    let notification: Notification
    let onClear: () -> Void
    
    var body: some View {
        HStack(alignment: .top, spacing: 12) {
            VStack(alignment: .leading, spacing: 8) {
                // Type Badge and Timestamp
                HStack {
                    NotificationTypeBadgeView(type: notification.type)
                    
                    Spacer()
                    
                    Text(formatTimestamp(timestamp: notification.timestamp))
                        .font(.system(size: 12))
                        .foregroundColor(CoffeeColors.coffeeBrown.opacity(0.5))
                }
                
                // Title with unread indicator
                HStack(alignment: .center, spacing: 8) {
                    if !notification.isRead {
                        Circle()
                            .fill(CoffeeColors.coffeeBrown)
                            .frame(width: 8, height: 8)
                    }
                    
                    Text(notification.title)
                        .font(.system(size: 16, weight: .bold))
                        .foregroundColor(CoffeeColors.darkCoffee)
                }
                
                // Message
                Text(notification.message)
                    .font(.system(size: 14))
                    .foregroundColor(CoffeeColors.darkCoffee.opacity(0.7))
                    .lineSpacing(4)
            }
            
            // Clear Button
            Button(action: onClear) {
                Image(systemName: "xmark")
                    .foregroundColor(CoffeeColors.coffeeBrown.opacity(0.5))
                    .font(.system(size: 14))
                    .frame(width: 32, height: 32)
            }
        }
        .padding(16)
        .background(notification.isRead ? CoffeeColors.latteFoam : CoffeeColors.coffeeBrown.opacity(0.05))
        .cornerRadius(12)
        .shadow(color: Color.black.opacity(0.1), radius: 2, x: 0, y: 2)
    }
}

struct NotificationTypeBadgeView: View {
    let type: NotificationType
    
    var badgeInfo: (Color, Color, String) {
        switch type {
        case .deal:
            return (Color.orange.opacity(0.2), Color.orange.opacity(0.8), "Deal")
        case .promotion:
            return (Color.purple.opacity(0.2), Color.purple.opacity(0.8), "Promotion")
        case .orderAccepted:
            return (Color.blue.opacity(0.2), Color.blue.opacity(0.8), "Order")
        case .orderShipped:
            return (Color.orange.opacity(0.2), Color.orange.opacity(0.8), "Shipping")
        case .orderDelivered:
            return (Color.green.opacity(0.2), Color.green.opacity(0.8), "Delivered")
        case .tierUpgrade:
            return (Color.yellow.opacity(0.2), Color.yellow.opacity(0.8), "Tier Upgrade")
        case .general:
            return (Color.gray.opacity(0.2), Color.gray.opacity(0.8), "Info")
        @unknown default:
            return (Color.gray.opacity(0.2), Color.gray.opacity(0.8), "Info")
        }
    }
    
    var body: some View {
        let (bgColor, textColor, label) = badgeInfo
        
        Text(label)
            .font(.system(size: 11, weight: .bold))
            .foregroundColor(textColor)
            .padding(.horizontal, 8)
            .padding(.vertical, 4)
            .background(bgColor)
            .cornerRadius(12)
    }
}

struct EmptyNotificationsViewIOS: View {
    var body: some View {
        VStack(spacing: 16) {
            Text("🔔")
                .font(.system(size: 64))
            
            Text("No notifications yet")
                .font(.system(size: 20, weight: .semibold))
                .foregroundColor(CoffeeColors.darkCoffee.opacity(0.6))
            
            Text("We'll notify you about deals, promotions, and order updates")
                .font(.system(size: 14))
                .foregroundColor(CoffeeColors.coffeeBrown.opacity(0.4))
                .multilineTextAlignment(.center)
                .padding(.horizontal, 32)
        }
        .padding(32)
        .frame(maxHeight: .infinity)
    }
}

class NotificationsViewModel: ObservableObject {
    @Published var notifications: [Notification] = []
    @Published var unreadCount: Int = 0
    
    private let presenter: NotificationsPresenter
    
    init() {
        let repository = MockCoffeeRepository()
        self.presenter = NotificationsPresenter(repository: repository)
        loadNotifications()
    }
    
    func loadNotifications() {
        notifications = presenter.getNotifications()
        unreadCount = Int(presenter.getUnreadCount(notifications: notifications))
    }
    
    func clearNotification(notificationId: String) {
        presenter.clearNotification(notificationId: notificationId)
        loadNotifications()
    }
    
    func clearAllNotifications() {
        presenter.clearAllNotifications()
        loadNotifications()
    }
    
    func markAsRead(notificationId: String) {
        presenter.markNotificationAsRead(notificationId: notificationId)
        loadNotifications()
    }
}

func formatTimestamp(timestamp: Int64) -> String {
    let now = Date().timeIntervalSince1970 * 1000
    let diff = now - Double(timestamp)
    
    switch diff {
    case 0..<(60 * 1000):
        return "Just now"
    case (60 * 1000)..<(60 * 60 * 1000):
        return "\(Int(diff / (60 * 1000)))m ago"
    case (60 * 60 * 1000)..<(24 * 60 * 60 * 1000):
        return "\(Int(diff / (60 * 60 * 1000)))h ago"
    case (24 * 60 * 60 * 1000)..<(7 * 24 * 60 * 60 * 1000):
        return "\(Int(diff / (24 * 60 * 60 * 1000)))d ago"
    default:
        let date = Date(timeIntervalSince1970: TimeInterval(timestamp) / 1000)
        let formatter = DateFormatter()
        formatter.dateFormat = "MMM dd"
        return formatter.string(from: date)
    }
}

#Preview {
    NotificationsScreenView()
}
