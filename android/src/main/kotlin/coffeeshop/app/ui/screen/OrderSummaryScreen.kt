package coffeeshop.app.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coffeeshop.app.ui.theme.*
import coffeeshop.shared.data.model.Order
import coffeeshop.shared.data.model.OrderItem
import coffeeshop.shared.presentation.OrderSummaryPresenter

@Composable
fun OrderSummaryScreen(
    presenter: OrderSummaryPresenter = remember { OrderSummaryPresenter() },
    onEditOrder: () -> Unit = {},
    onProceedToPayment: () -> Unit = {}
) {
    val order = remember { presenter.getSampleOrder() }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        // Header
        OrderSummaryHeader()
        
        // Content with order items and total
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Order Items Section
            item {
                Text(
                    text = "Order Items",
                    style = MaterialTheme.typography.h2.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colors.onBackground,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            
            items(order.items) { orderItem ->
                OrderItemCard(orderItem)
            }
            
            // Cost Breakdown Section
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Cost Breakdown",
                    style = MaterialTheme.typography.h2.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colors.onBackground,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                CostBreakdownCard(order)
            }
        }
        
        // Bottom Action Buttons
        OrderActionButtons(
            onEditOrder = onEditOrder,
            onProceedToPayment = onProceedToPayment
        )
    }
}

@Composable
fun OrderSummaryHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.primary)
            .padding(24.dp)
    ) {
        Text(
            text = "Order Summary",
            style = MaterialTheme.typography.h1.copy(
                color = MaterialTheme.colors.onPrimary,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Review your order before checkout",
            style = MaterialTheme.typography.body1.copy(
                color = MaterialTheme.colors.onPrimary.copy(alpha = 0.9f),
                fontSize = 16.sp
            )
        )
    }
}

@Composable
fun OrderItemCard(orderItem: OrderItem) {
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
            // Item Name and Quantity
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = orderItem.name,
                    style = MaterialTheme.typography.h3.copy(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colors.onSurface,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "×${orderItem.quantity}",
                    style = MaterialTheme.typography.body1.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colors.primary
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Size
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Size: ",
                    style = MaterialTheme.typography.body2.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                )
                Text(
                    text = orderItem.size,
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onSurface
                )
            }
            
            // Add-ons
            if (orderItem.addOns.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = "Add-ons: ",
                        style = MaterialTheme.typography.body2.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                    )
                    Column {
                        orderItem.addOns.forEach { addOn ->
                            Text(
                                text = "• $addOn",
                                style = MaterialTheme.typography.body2,
                                color = MaterialTheme.colors.onSurface
                            )
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Price
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Base Price: $${String.format("%.2f", orderItem.basePrice)}",
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                )
                Text(
                    text = "$${String.format("%.2f", orderItem.itemTotalPrice)}",
                    style = MaterialTheme.typography.h3.copy(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colors.primary
                )
            }
        }
    }
}

@Composable
fun CostBreakdownCard(order: Order) {
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
            // Subtotal
            CostBreakdownRow(
                label = "Subtotal",
                amount = order.subtotal,
                isTotal = false
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Tax
            CostBreakdownRow(
                label = "Tax (${(order.taxRate * 100).toInt()}%)",
                amount = order.tax,
                isTotal = false
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Divider(
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f),
                thickness = 1.dp
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Total
            CostBreakdownRow(
                label = "Total",
                amount = order.total,
                isTotal = true
            )
        }
    }
}

@Composable
fun CostBreakdownRow(
    label: String,
    amount: Double,
    isTotal: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.body1.copy(
                fontSize = if (isTotal) 20.sp else 16.sp,
                fontWeight = if (isTotal) FontWeight.Bold else FontWeight.Normal
            ),
            color = MaterialTheme.colors.onSurface
        )
        Text(
            text = "$${String.format("%.2f", amount)}",
            style = MaterialTheme.typography.body1.copy(
                fontSize = if (isTotal) 20.sp else 16.sp,
                fontWeight = if (isTotal) FontWeight.Bold else FontWeight.Normal
            ),
            color = if (isTotal) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface
        )
    }
}

@Composable
fun OrderActionButtons(
    onEditOrder: () -> Unit,
    onProceedToPayment: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.surface)
            .padding(16.dp)
    ) {
        // Proceed to Payment Button (Primary)
        Button(
            onClick = onProceedToPayment,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary
            ),
            elevation = ButtonDefaults.elevation(
                defaultElevation = 4.dp,
                pressedElevation = 8.dp
            )
        ) {
            Text(
                text = "Proceed to Payment",
                style = MaterialTheme.typography.button.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        
        // Edit Order Button (Secondary)
        OutlinedButton(
            onClick = onEditOrder,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                backgroundColor = Color.Transparent,
                contentColor = MaterialTheme.colors.primary
            ),
            border = ButtonDefaults.outlinedBorder.copy(
                width = 2.dp
            )
        ) {
            Text(
                text = "Edit Order",
                style = MaterialTheme.typography.button.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}
