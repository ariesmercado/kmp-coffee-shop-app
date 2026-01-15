package coffeeshop.app.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coffeeshop.app.ui.theme.*
import coffeeshop.shared.data.model.OrderHistory
import coffeeshop.shared.data.model.OrderItem
import coffeeshop.shared.data.repository.MockCoffeeRepository
import coffeeshop.shared.presentation.OrderHistoryPresenter
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun OrderHistoryScreen(
    presenter: OrderHistoryPresenter = remember { OrderHistoryPresenter(MockCoffeeRepository()) }
) {
    val allOrders = remember { presenter.getOrderHistory() }
    var searchQuery by remember { mutableStateOf("") }
    var selectedDateFilter by remember { mutableStateOf(DateFilter.ALL) }
    
    val filteredOrders = remember(searchQuery, selectedDateFilter, allOrders) {
        val dateFiltered = when (selectedDateFilter) {
            DateFilter.ALL -> allOrders
            DateFilter.LAST_7_DAYS -> {
                val sevenDaysAgo = System.currentTimeMillis() - (7 * 24 * 60 * 60 * 1000L)
                presenter.filterOrdersByDateRange(allOrders, sevenDaysAgo, System.currentTimeMillis())
            }
            DateFilter.LAST_30_DAYS -> {
                val thirtyDaysAgo = System.currentTimeMillis() - (30 * 24 * 60 * 60 * 1000L)
                presenter.filterOrdersByDateRange(allOrders, thirtyDaysAgo, System.currentTimeMillis())
            }
        }
        presenter.searchOrders(dateFiltered, searchQuery)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        // Header
        OrderHistoryHeader()
        
        // Search and Filter Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search by item or order ID") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = MaterialTheme.colors.surface,
                    focusedBorderColor = MaterialTheme.colors.primary,
                    unfocusedBorderColor = MaterialTheme.colors.onSurface.copy(alpha = 0.3f)
                ),
                shape = RoundedCornerShape(12.dp)
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Date Filter Chips
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                DateFilter.values().forEach { filter ->
                    FilterChip(
                        selected = selectedDateFilter == filter,
                        onClick = { selectedDateFilter = filter },
                        label = filter.label
                    )
                }
            }
        }
        
        // Orders List
        if (filteredOrders.isEmpty()) {
            EmptyOrdersView(hasSearchQuery = searchQuery.isNotEmpty())
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredOrders) { order ->
                    OrderHistoryCard(order)
                }
            }
        }
    }
}

@Composable
fun OrderHistoryHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.primary)
            .padding(24.dp)
    ) {
        Text(
            text = "Order History",
            style = MaterialTheme.typography.h1.copy(
                color = MaterialTheme.colors.onPrimary,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "View your past orders",
            style = MaterialTheme.typography.body1.copy(
                color = MaterialTheme.colors.onPrimary.copy(alpha = 0.9f),
                fontSize = 16.sp
            )
        )
    }
}

@Composable
fun FilterChip(
    selected: Boolean,
    onClick: () -> Unit,
    label: String
) {
    Surface(
        modifier = Modifier.clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        color = if (selected) MaterialTheme.colors.primary else MaterialTheme.colors.surface,
        elevation = if (selected) 4.dp else 2.dp
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            color = if (selected) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSurface,
            style = MaterialTheme.typography.body2.copy(fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal)
        )
    }
}

@Composable
fun OrderHistoryCard(order: OrderHistory) {
    var isExpanded by remember { mutableStateOf(false) }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = 2.dp,
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Order Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Order #${order.id.takeLast(6)}",
                        style = MaterialTheme.typography.h3.copy(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = formatDate(order.orderDate),
                        style = MaterialTheme.typography.body2.copy(
                            color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                        )
                    )
                }
                
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "$${String.format("%.2f", order.total)}",
                        style = MaterialTheme.typography.h3.copy(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colors.primary
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    StatusBadge(status = order.status)
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Items Summary
            if (!isExpanded) {
                ItemsSummary(order.items)
            } else {
                ItemsDetailList(order.items)
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // View Details Button
            TextButton(
                onClick = { isExpanded = !isExpanded },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(
                    text = if (isExpanded) "Hide Details" else "View Details",
                    color = MaterialTheme.colors.primary,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun StatusBadge(status: String) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = when (status) {
            "Completed" -> Color(0xFF4CAF50).copy(alpha = 0.2f)
            "In Progress" -> Color(0xFFFF9800).copy(alpha = 0.2f)
            else -> Color(0xFFF44336).copy(alpha = 0.2f)
        }
    ) {
        Text(
            text = status,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.caption.copy(
                color = when (status) {
                    "Completed" -> Color(0xFF2E7D32)
                    "In Progress" -> Color(0xFFE65100)
                    else -> Color(0xFFC62828)
                },
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
        )
    }
}

@Composable
fun ItemsSummary(items: List<OrderItem>) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        val displayItems = items.take(2)
        displayItems.forEach { item ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${item.quantity}x ${item.name} (${item.size})",
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "$${String.format("%.2f", item.itemTotalPrice)}",
                    style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Medium)
                )
            }
        }
        if (items.size > 2) {
            Text(
                text = "+${items.size - 2} more item${if (items.size - 2 > 1) "s" else ""}",
                style = MaterialTheme.typography.body2.copy(
                    color = MaterialTheme.colors.primary,
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}

@Composable
fun ItemsDetailList(items: List<OrderItem>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.05f),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items.forEach { item ->
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "${item.quantity}x ${item.name}",
                            style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = "Size: ${item.size}",
                            style = MaterialTheme.typography.caption.copy(
                                color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                            )
                        )
                        if (item.addOns.isNotEmpty()) {
                            Text(
                                text = "Add-ons: ${item.addOns.joinToString(", ")}",
                                style = MaterialTheme.typography.caption.copy(
                                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                                )
                            )
                        }
                    }
                    Text(
                        text = "$${String.format("%.2f", item.itemTotalPrice)}",
                        style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }
        }
    }
}

@Composable
fun EmptyOrdersView(hasSearchQuery: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "☕",
            fontSize = 64.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = if (hasSearchQuery) "No orders found" else "No order history yet",
            style = MaterialTheme.typography.h3,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = if (hasSearchQuery) 
                "Try adjusting your search or filters" 
            else 
                "Start ordering to see your history here",
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.4f)
        )
    }
}

enum class DateFilter(val label: String) {
    ALL("All Time"),
    LAST_7_DAYS("Last 7 Days"),
    LAST_30_DAYS("Last 30 Days")
}

fun formatDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("MMM dd, yyyy 'at' hh:mm a", Locale.getDefault())
    return sdf.format(Date(timestamp))
}
