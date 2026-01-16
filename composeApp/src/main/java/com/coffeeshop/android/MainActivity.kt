package com.coffeeshop.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.coffeeshop.navigation.Screen
import com.coffeeshop.ui.screens.*

/**
 * Main Activity for Android app
 * Sets up the navigation graph using Jetpack Compose Navigation
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                
                NavHost(
                    navController = navController,
                    startDestination = Screen.Home.route
                ) {
                    // Home Screen
                    composable(Screen.Home.route) {
                        HomeScreen(
                            onNavigateToDrinkBuilder = {
                                navController.navigate(Screen.DrinkBuilder.route)
                            },
                            onNavigateToLoyalty = {
                                navController.navigate(Screen.Loyalty.route)
                            },
                            onNavigateToScanner = {
                                navController.navigate(Screen.Scanner.route)
                            },
                            onNavigateToPaymentSystem = {
                                navController.navigate(Screen.PaymentSystem.route)
                            }
                        )
                    }
                    
                    // Drink Builder Screen
                    composable(Screen.DrinkBuilder.route) {
                        DrinkBuilderScreen(
                            onNavigateBack = {
                                navController.popBackStack()
                            }
                        )
                    }
                    
                    // Loyalty Screen
                    composable(Screen.Loyalty.route) {
                        LoyaltyScreen(
                            onNavigateBack = {
                                navController.popBackStack()
                            }
                        )
                    }
                    
                    // Scanner Screen
                    composable(Screen.Scanner.route) {
                        ScannerScreen(
                            onNavigateBack = {
                                navController.popBackStack()
                            }
                        )
                    }
                    
                    // Payment System Screen
                    composable(Screen.PaymentSystem.route) {
                        PaymentSystemScreen(
                            onNavigateBack = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}
