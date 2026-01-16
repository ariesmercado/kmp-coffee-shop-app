package coffeeshop.app.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coffeeshop.app.ui.theme.*
import coffeeshop.shared.data.model.Order
import coffeeshop.shared.data.model.PaymentMethod
import coffeeshop.shared.data.model.PaymentStatus
import coffeeshop.shared.presentation.OrderSummaryPresenter
import coffeeshop.shared.presentation.PaymentPresenter
import kotlinx.coroutines.launch

private const val SUCCESS_DELAY_MS = 2000L

@Composable
fun PaymentScreen(
    paymentPresenter: PaymentPresenter = remember { PaymentPresenter() },
    orderSummaryPresenter: OrderSummaryPresenter = remember { OrderSummaryPresenter() },
    onPaymentSuccess: () -> Unit = {},
    onCancel: () -> Unit = {}
) {
    val order = remember { orderSummaryPresenter.getSampleOrder() }
    
    // Payment method state
    var selectedPaymentMethod by remember { mutableStateOf(PaymentMethod.CREDIT_CARD) }
    
    // Billing address state
    var fullName by remember { mutableStateOf("") }
    var addressLine1 by remember { mutableStateOf("") }
    var addressLine2 by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("") }
    var zipCode by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("United States") }
    
    // Validation error state
    var errorMessage by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    
    // Processing state
    var isProcessing by remember { mutableStateOf(false) }
    
    // Payment result dialog state
    var showPaymentResultDialog by remember { mutableStateOf(false) }
    var paymentResultSuccess by remember { mutableStateOf(false) }
    var paymentResultMessage by remember { mutableStateOf("") }
    var paymentTransactionId by remember { mutableStateOf("") }
    
    // Coroutine scope for async operations
    val coroutineScope = rememberCoroutineScope()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        // Header
        PaymentHeader()
        
        // Content
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Total Amount Section
            item {
                TotalAmountCard(order)
            }
            
            // Payment Method Section
            item {
                Text(
                    text = "Payment Method",
                    style = MaterialTheme.typography.h2.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colors.onBackground,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            
            items(paymentPresenter.getAvailablePaymentMethods()) { method ->
                PaymentMethodCard(
                    paymentMethod = method,
                    isSelected = selectedPaymentMethod == method,
                    onSelect = { selectedPaymentMethod = method }
                )
            }
            
            // Billing Address Section
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Billing Address",
                    style = MaterialTheme.typography.h2.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colors.onBackground,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                
                BillingAddressForm(
                    fullName = fullName,
                    onFullNameChange = { fullName = it },
                    addressLine1 = addressLine1,
                    onAddressLine1Change = { addressLine1 = it },
                    addressLine2 = addressLine2,
                    onAddressLine2Change = { addressLine2 = it },
                    city = city,
                    onCityChange = { city = it },
                    state = state,
                    onStateChange = { state = it },
                    zipCode = zipCode,
                    onZipCodeChange = { zipCode = it },
                    country = country,
                    onCountryChange = { country = it }
                )
            }
            
            // Error Message
            if (showError) {
                item {
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        }
        
        // Bottom Action Buttons
        PaymentActionButtons(
            onConfirmPayment = {
                // Validate billing address
                val validationResult = paymentPresenter.validateBillingAddress(
                    fullName = fullName,
                    addressLine1 = addressLine1,
                    city = city,
                    state = state,
                    zipCode = zipCode,
                    country = country
                )
                
                if (validationResult.isValid) {
                    isProcessing = true
                    showError = false
                    
                    // Create payment info
                    val paymentInfo = paymentPresenter.createPaymentInfo(
                        orderId = order.id,
                        paymentMethod = selectedPaymentMethod,
                        fullName = fullName,
                        addressLine1 = addressLine1,
                        addressLine2 = addressLine2,
                        city = city,
                        state = state,
                        zipCode = zipCode,
                        country = country,
                        totalAmount = order.total
                    )
                    
                    // Process payment asynchronously
                    coroutineScope.launch {
                        try {
                            val result = paymentPresenter.processPayment(paymentInfo)
                            isProcessing = false
                            
                            // Show result dialog
                            paymentResultSuccess = result.status == PaymentStatus.SUCCESS
                            paymentResultMessage = result.message
                            paymentTransactionId = result.transactionId
                            showPaymentResultDialog = true
                            
                            // Call success callback after short delay
                            if (paymentResultSuccess) {
                                kotlinx.coroutines.delay(SUCCESS_DELAY_MS)
                                onPaymentSuccess()
                            }
                        } catch (e: Exception) {
                            isProcessing = false
                            paymentResultSuccess = false
                            paymentResultMessage = "Payment failed: ${e.message}"
                            paymentTransactionId = ""
                            showPaymentResultDialog = true
                        }
                    }
                } else {
                    errorMessage = validationResult.errorMessage
                    showError = true
                }
            },
            onCancel = onCancel,
            isProcessing = isProcessing
        )
    }
    
    // Payment Result Dialog
    if (showPaymentResultDialog) {
        PaymentResultDialog(
            isSuccess = paymentResultSuccess,
            message = paymentResultMessage,
            transactionId = paymentTransactionId,
            onDismiss = { 
                showPaymentResultDialog = false
                if (paymentResultSuccess) {
                    onPaymentSuccess()
                }
            }
        )
    }
}

@Composable
fun PaymentHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.primary)
            .padding(24.dp)
    ) {
        Text(
            text = "Payment",
            style = MaterialTheme.typography.h1.copy(
                color = MaterialTheme.colors.onPrimary,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Complete your purchase securely",
            style = MaterialTheme.typography.body1.copy(
                color = MaterialTheme.colors.onPrimary.copy(alpha = 0.9f),
                fontSize = 16.sp
            )
        )
    }
}

@Composable
fun TotalAmountCard(order: Order) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = 8.dp,
        backgroundColor = CoffeeBrown.copy(alpha = 0.08f)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Text(
                text = "Total Amount",
                style = MaterialTheme.typography.h5.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                ),
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "$${String.format("%.2f", order.total)}",
                style = MaterialTheme.typography.h1.copy(
                    fontSize = 42.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = GoldenAccent
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Including tax: $${String.format("%.2f", order.tax)}",
                style = MaterialTheme.typography.body2.copy(fontSize = 13.sp),
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
fun PaymentMethodCard(
    paymentMethod: PaymentMethod,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelect() }
            .then(
                if (isSelected) {
                    Modifier.border(
                        width = 2.dp,
                        color = GoldenAccent,
                        shape = RoundedCornerShape(16.dp)
                    )
                } else {
                    Modifier
                }
            ),
        shape = RoundedCornerShape(16.dp),
        elevation = if (isSelected) 8.dp else 3.dp,
        backgroundColor = if (isSelected) {
            GoldenAccent.copy(alpha = 0.08f)
        } else {
            MaterialTheme.colors.surface
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = paymentMethod.displayName,
                style = MaterialTheme.typography.body1.copy(
                    fontSize = 17.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                ),
                color = MaterialTheme.colors.onSurface
            )
            
            RadioButton(
                selected = isSelected,
                onClick = onSelect,
                colors = RadioButtonDefaults.colors(
                    selectedColor = GoldenAccent,
                    unselectedColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
                )
            )
        }
    }
}

@Composable
fun BillingAddressForm(
    fullName: String,
    onFullNameChange: (String) -> Unit,
    addressLine1: String,
    onAddressLine1Change: (String) -> Unit,
    addressLine2: String,
    onAddressLine2Change: (String) -> Unit,
    city: String,
    onCityChange: (String) -> Unit,
    state: String,
    onStateChange: (String) -> Unit,
    zipCode: String,
    onZipCodeChange: (String) -> Unit,
    country: String,
    onCountryChange: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = 4.dp,
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Full Name
            OutlinedTextField(
                value = fullName,
                onValueChange = onFullNameChange,
                label = { Text("Full Name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = GoldenAccent,
                    focusedLabelColor = GoldenAccent,
                    cursorColor = GoldenAccent
                )
            )
            
            // Address Line 1
            OutlinedTextField(
                value = addressLine1,
                onValueChange = onAddressLine1Change,
                label = { Text("Address Line 1") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = GoldenAccent,
                    focusedLabelColor = GoldenAccent,
                    cursorColor = GoldenAccent
                )
            )
            
            // Address Line 2
            OutlinedTextField(
                value = addressLine2,
                onValueChange = onAddressLine2Change,
                label = { Text("Address Line 2 (Optional)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = GoldenAccent,
                    focusedLabelColor = GoldenAccent,
                    cursorColor = GoldenAccent
                )
            )
            
            // City
            OutlinedTextField(
                value = city,
                onValueChange = onCityChange,
                label = { Text("City") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = GoldenAccent,
                    focusedLabelColor = GoldenAccent,
                    cursorColor = GoldenAccent
                )
            )
            
            // State and ZIP Code
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedTextField(
                    value = state,
                    onValueChange = onStateChange,
                    label = { Text("State") },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = GoldenAccent,
                        focusedLabelColor = GoldenAccent,
                        cursorColor = GoldenAccent
                    )
                )
                
                OutlinedTextField(
                    value = zipCode,
                    onValueChange = onZipCodeChange,
                    label = { Text("ZIP Code") },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = GoldenAccent,
                        focusedLabelColor = GoldenAccent,
                        cursorColor = GoldenAccent
                    )
                )
            }
            
            // Country
            OutlinedTextField(
                value = country,
                onValueChange = onCountryChange,
                label = { Text("Country") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = GoldenAccent,
                    focusedLabelColor = GoldenAccent,
                    cursorColor = GoldenAccent
                )
            )
        }
    }
}

@Composable
fun PaymentActionButtons(
    onConfirmPayment: () -> Unit,
    onCancel: () -> Unit,
    isProcessing: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.surface)
            .padding(20.dp)
    ) {
        // Confirm Payment Button
        Button(
            onClick = onConfirmPayment,
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = CoffeeBrown,
                contentColor = CreamyWhite
            ),
            elevation = ButtonDefaults.elevation(
                defaultElevation = 8.dp,
                pressedElevation = 12.dp
            ),
            enabled = !isProcessing
        ) {
            if (isProcessing) {
                CircularProgressIndicator(
                    color = CreamyWhite,
                    modifier = Modifier.size(26.dp),
                    strokeWidth = 3.dp
                )
            } else {
                Text(
                    text = "Confirm Payment",
                    style = MaterialTheme.typography.button.copy(
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                )
            }
        }
        
        Spacer(modifier = Modifier.height(14.dp))
        
        // Cancel Button
        OutlinedButton(
            onClick = onCancel,
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                backgroundColor = Color.Transparent,
                contentColor = CoffeeBrown
            ),
            border = ButtonDefaults.outlinedBorder.copy(
                width = 2.dp
            ),
            enabled = !isProcessing
        ) {
            Text(
                text = "Cancel",
                style = MaterialTheme.typography.button.copy(
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )
            )
        }
    }
}

@Composable
fun PaymentResultDialog(
    isSuccess: Boolean,
    message: String,
    transactionId: String,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = 8.dp,
            backgroundColor = MaterialTheme.colors.surface
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Icon
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .background(
                            color = if (isSuccess) 
                                Color(0xFF4CAF50).copy(alpha = 0.2f)
                            else 
                                Color(0xFFF44336).copy(alpha = 0.2f),
                            shape = RoundedCornerShape(32.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (isSuccess) "✓" else "✗",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isSuccess) Color(0xFF4CAF50) else Color(0xFFF44336)
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Title
                Text(
                    text = if (isSuccess) "Payment Successful!" else "Payment Failed",
                    style = MaterialTheme.typography.h2.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colors.onSurface
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Message
                Text(
                    text = message,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                )
                
                if (isSuccess && transactionId.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    // Transaction ID
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = MaterialTheme.colors.primary.copy(alpha = 0.1f),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(12.dp)
                    ) {
                        Text(
                            text = "Transaction ID",
                            style = MaterialTheme.typography.caption,
                            color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                        )
                        Text(
                            text = transactionId,
                            style = MaterialTheme.typography.body2.copy(
                                fontWeight = FontWeight.Medium
                            ),
                            color = MaterialTheme.colors.onSurface
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // OK Button
                Button(
                    onClick = onDismiss,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.primary
                    )
                ) {
                    Text(
                        text = "OK",
                        style = MaterialTheme.typography.button.copy(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        color = MaterialTheme.colors.onPrimary
                    )
                }
            }
        }
    }
}
