package coffeeshop.app.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coffeeshop.app.ui.theme.*
import coffeeshop.shared.data.model.AddOn
import coffeeshop.shared.data.model.DrinkSize
import coffeeshop.shared.data.model.MenuItem
import coffeeshop.shared.data.repository.MockCoffeeRepository
import coffeeshop.shared.presentation.CustomDrinkBuilderPresenter

@Composable
fun CustomDrinkBuilderScreen(
    presenter: CustomDrinkBuilderPresenter = remember { CustomDrinkBuilderPresenter(MockCoffeeRepository()) }
) {
    val drinkSizes = remember { DrinkSize.values().toList() }
    
    var currentStep by remember { mutableStateOf(1) }
    var selectedMenuItem by remember { mutableStateOf<MenuItem?>(null) }
    var selectedSize by remember { mutableStateOf(DrinkSize.MEDIUM) }
    var selectedAddOns by remember { mutableStateOf<List<AddOn>>(emptyList()) }
    var customName by remember { mutableStateOf("") }
    var showSaveDialog by remember { mutableStateOf(false) }
    var showSuccessMessage by remember { mutableStateOf(false) }
    
    val menuItems = remember { presenter.getMenuItems() }
    val availableAddOns = remember { presenter.getAvailableAddOns() }
    
    val totalPrice = remember(selectedMenuItem, selectedSize, selectedAddOns) {
        selectedMenuItem?.let { 
            presenter.calculateTotalPrice(it, selectedSize, selectedAddOns)
        } ?: 0.0
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        // Header
        CustomDrinkBuilderHeader(currentStep)
        
        // Price Preview
        if (selectedMenuItem != null) {
            PricePreviewCard(totalPrice)
        }
        
        // Step Content
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            when (currentStep) {
                1 -> {
                    item {
                        Text(
                            text = "Step 1: Choose Your Base Drink",
                            style = MaterialTheme.typography.h2.copy(fontSize = 20.sp),
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                    items(menuItems) { item ->
                        MenuItemSelectionCard(
                            item = item,
                            isSelected = selectedMenuItem?.id == item.id,
                            onClick = { 
                                selectedMenuItem = item
                            }
                        )
                    }
                }
                2 -> {
                    item {
                        Text(
                            text = "Step 2: Select Size",
                            style = MaterialTheme.typography.h2.copy(fontSize = 20.sp),
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                    items(drinkSizes) { size ->
                        SizeSelectionCard(
                            size = size,
                            basePrice = selectedMenuItem?.price ?: 0.0,
                            isSelected = selectedSize == size,
                            onClick = { selectedSize = size }
                        )
                    }
                }
                3 -> {
                    item {
                        Text(
                            text = "Step 3: Add Extras (Optional)",
                            style = MaterialTheme.typography.h2.copy(fontSize = 20.sp),
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                    items(availableAddOns) { addOn ->
                        AddOnSelectionCard(
                            addOn = addOn,
                            isSelected = selectedAddOns.any { it.id == addOn.id },
                            onClick = {
                                selectedAddOns = if (selectedAddOns.any { it.id == addOn.id }) {
                                    selectedAddOns.filter { it.id != addOn.id }
                                } else {
                                    selectedAddOns + addOn
                                }
                            }
                        )
                    }
                }
            }
        }
        
        // Navigation Buttons
        NavigationButtons(
            currentStep = currentStep,
            canProceed = when (currentStep) {
                1 -> selectedMenuItem != null
                else -> true
            },
            onPrevious = { if (currentStep > 1) currentStep-- },
            onNext = { 
                if (currentStep < 3) {
                    currentStep++
                } else {
                    showSaveDialog = true
                }
            }
        )
    }
    
    // Save Dialog
    if (showSaveDialog) {
        SaveCustomDrinkDialog(
            customName = customName,
            onCustomNameChange = { customName = it },
            onDismiss = { showSaveDialog = false },
            onSave = {
                selectedMenuItem?.let { item ->
                    presenter.saveCustomDrink(
                        baseMenuItem = item,
                        size = selectedSize,
                        addOns = selectedAddOns,
                        customName = customName.ifBlank { null }
                    )
                    showSaveDialog = false
                    showSuccessMessage = true
                }
            }
        )
    }
    
    // Success Message
    if (showSuccessMessage) {
        AlertDialog(
            onDismissRequest = { showSuccessMessage = false },
            title = { Text("Success!") },
            text = { Text("Your custom drink has been saved for quick reordering.") },
            confirmButton = {
                Button(onClick = { showSuccessMessage = false }) {
                    Text("OK")
                }
            }
        )
    }
}

@Composable
fun CustomDrinkBuilderHeader(currentStep: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.primary)
            .padding(24.dp)
    ) {
        Text(
            text = "Custom Drink Builder",
            style = MaterialTheme.typography.h1.copy(
                color = MaterialTheme.colors.onPrimary,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Create your perfect beverage",
            style = MaterialTheme.typography.body1.copy(
                color = MaterialTheme.colors.onPrimary.copy(alpha = 0.9f),
                fontSize = 16.sp
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        StepIndicator(currentStep)
    }
}

@Composable
fun StepIndicator(currentStep: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        for (step in 1..3) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(
                            color = if (step <= currentStep) CreamyWhite else CreamyWhite.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(16.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = step.toString(),
                        style = MaterialTheme.typography.body1.copy(
                            color = if (step <= currentStep) MaterialTheme.colors.primary else MaterialTheme.colors.primary.copy(alpha = 0.5f),
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun PricePreviewCard(totalPrice: Double) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = 4.dp,
        backgroundColor = CreamyWhite
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Total Price:",
                style = MaterialTheme.typography.h3.copy(fontSize = 18.sp)
            )
            Text(
                text = "$${String.format("%.2f", totalPrice)}",
                style = MaterialTheme.typography.h2.copy(
                    fontSize = 24.sp,
                    color = MaterialTheme.colors.primary,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}

@Composable
fun MenuItemSelectionCard(
    item: MenuItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .then(
                if (isSelected) Modifier.border(
                    width = 2.dp,
                    color = MaterialTheme.colors.primary,
                    shape = RoundedCornerShape(12.dp)
                ) else Modifier
            ),
        shape = RoundedCornerShape(12.dp),
        elevation = 2.dp,
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(
                        color = CoffeeBrown.copy(alpha = 0.3f),
                        shape = RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "☕", fontSize = 32.sp)
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.h3.copy(fontSize = 18.sp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "$${String.format("%.2f", item.price)}",
                    style = MaterialTheme.typography.body1.copy(
                        color = MaterialTheme.colors.primary,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Selected",
                    tint = MaterialTheme.colors.primary,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

@Composable
fun SizeSelectionCard(
    size: DrinkSize,
    basePrice: Double,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .then(
                if (isSelected) Modifier.border(
                    width = 2.dp,
                    color = MaterialTheme.colors.primary,
                    shape = RoundedCornerShape(12.dp)
                ) else Modifier
            ),
        shape = RoundedCornerShape(12.dp),
        elevation = 2.dp,
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = size.displayName,
                    style = MaterialTheme.typography.h3.copy(fontSize = 18.sp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "$${String.format("%.2f", basePrice * size.priceMultiplier)}",
                    style = MaterialTheme.typography.body1.copy(
                        color = MaterialTheme.colors.primary,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Selected",
                    tint = MaterialTheme.colors.primary,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

@Composable
fun AddOnSelectionCard(
    addOn: AddOn,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .then(
                if (isSelected) Modifier.border(
                    width = 2.dp,
                    color = MaterialTheme.colors.primary,
                    shape = RoundedCornerShape(12.dp)
                ) else Modifier
            ),
        shape = RoundedCornerShape(12.dp),
        elevation = 2.dp,
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = addOn.name,
                    style = MaterialTheme.typography.body1.copy(fontSize = 16.sp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "+$${String.format("%.2f", addOn.price)}",
                    style = MaterialTheme.typography.body2.copy(
                        color = MaterialTheme.colors.primary
                    )
                )
            }
            
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Selected",
                    tint = MaterialTheme.colors.primary,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}

@Composable
fun NavigationButtons(
    currentStep: Int,
    canProceed: Boolean,
    onPrevious: () -> Unit,
    onNext: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.surface)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (currentStep > 1) {
            Button(
                onClick = onPrevious,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.surface,
                    contentColor = MaterialTheme.colors.primary
                ),
                modifier = Modifier.weight(1f)
            ) {
                Text("Previous")
            }
            Spacer(modifier = Modifier.width(16.dp))
        }
        
        Button(
            onClick = onNext,
            enabled = canProceed,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary
            ),
            modifier = Modifier.weight(1f)
        ) {
            Text(if (currentStep == 3) "Save Drink" else "Next")
        }
    }
}

@Composable
fun SaveCustomDrinkDialog(
    customName: String,
    onCustomNameChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onSave: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Save Custom Drink") },
        text = {
            Column {
                Text("Give your drink a custom name (optional):")
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = customName,
                    onValueChange = onCustomNameChange,
                    placeholder = { Text("e.g., My Perfect Latte") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(onClick = onSave) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
