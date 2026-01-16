package com.coffeeshop.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Drink Builder Screen - Allows users to customize their coffee drinks
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrinkBuilderScreen(
    onNavigateBack: () -> Unit
) {
    var drinkSize by remember { mutableStateOf("Medium") }
    var coffeeType by remember { mutableStateOf("Latte") }
    var milkType by remember { mutableStateOf("Whole Milk") }
    var shots by remember { mutableStateOf(2) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Build Your Drink") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Text("←", fontSize = 24.sp)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "🍵",
                fontSize = 64.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            
            Text(
                text = "Customize Your Coffee",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            
            // Coffee Type
            OptionCard(
                title = "Coffee Type",
                selectedValue = coffeeType,
                options = listOf("Espresso", "Americano", "Latte", "Cappuccino", "Mocha"),
                onValueChange = { coffeeType = it }
            )
            
            // Size
            OptionCard(
                title = "Size",
                selectedValue = drinkSize,
                options = listOf("Small", "Medium", "Large", "Extra Large"),
                onValueChange = { drinkSize = it }
            )
            
            // Milk Type
            OptionCard(
                title = "Milk Type",
                selectedValue = milkType,
                options = listOf("Whole Milk", "Skim Milk", "Oat Milk", "Almond Milk", "Soy Milk"),
                onValueChange = { milkType = it }
            )
            
            // Espresso Shots
            Card {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Espresso Shots: $shots",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { if (shots > 1) shots-- },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("-")
                        }
                        Button(
                            onClick = { if (shots < 5) shots++ },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("+")
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = { /* Add to cart logic */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text("Add to Order", fontSize = 18.sp)
            }
        }
    }
}

@Composable
private fun OptionCard(
    title: String,
    selectedValue: String,
    options: List<String>,
    onValueChange: (String) -> Unit
) {
    Card {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))
            options.forEach { option ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedValue == option,
                        onClick = { onValueChange(option) }
                    )
                    Text(
                        text = option,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
    }
}
