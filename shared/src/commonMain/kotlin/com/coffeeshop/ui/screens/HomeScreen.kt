package com.coffeeshop.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Home Screen - Main entry point of the Coffee Shop App
 * Provides navigation buttons to all major features
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToDrinkBuilder: () -> Unit,
    onNavigateToLoyalty: () -> Unit,
    onNavigateToScanner: () -> Unit,
    onNavigateToPaymentSystem: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Coffee Shop") },
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                text = "☕",
                fontSize = 72.sp,
                textAlign = TextAlign.Center
            )
            
            Text(
                text = "Welcome to Coffee Shop",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            
            Text(
                text = "Your favorite coffee, just a tap away",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Navigation Buttons
            NavigationButton(
                text = "Build Your Drink",
                description = "Customize your perfect coffee",
                icon = "🍵",
                onClick = onNavigateToDrinkBuilder
            )
            
            NavigationButton(
                text = "Loyalty Rewards",
                description = "Check your points and rewards",
                icon = "⭐",
                onClick = onNavigateToLoyalty
            )
            
            NavigationButton(
                text = "QR Scanner",
                description = "Scan to earn points or pay",
                icon = "📱",
                onClick = onNavigateToScanner
            )
            
            NavigationButton(
                text = "Payment System",
                description = "Manage payment methods",
                icon = "💳",
                onClick = onNavigateToPaymentSystem
            )
            
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun NavigationButton(
    text: String,
    description: String,
    icon: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        onClick = onClick,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = icon,
                fontSize = 40.sp,
                modifier = Modifier.padding(end = 16.dp)
            )
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
