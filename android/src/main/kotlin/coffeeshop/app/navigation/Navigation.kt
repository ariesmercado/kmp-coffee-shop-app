package coffeeshop.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coffeeshop.app.ui.screen.*

/**
 * Navigation routes for the Coffee Shop app
 */
sealed class Screen(val route: String) {
    object Home : Screen("home")
    object DrinkBuilder : Screen("drink_builder")
    object Loyalty : Screen("loyalty")
    object Scanner : Screen("scanner")
    object PaymentSystem : Screen("payment_system")
}

/**
 * Main navigation host for the app
 */
@Composable
fun CoffeeShopNavigation(
    navController: NavHostController = rememberNavController()
) {
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
                onNavigateToPayment = {
                    navController.navigate(Screen.PaymentSystem.route)
                }
            )
        }
        
        // Drink Builder Screen
        composable(Screen.DrinkBuilder.route) {
            CustomDrinkBuilderScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
        
        // Loyalty/Reward Info Screen
        composable(Screen.Loyalty.route) {
            RewardInfoScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
        
        // Barcode Scanner Screen
        composable(Screen.Scanner.route) {
            BarcodeScannerScreen(
                onScanComplete = { success, points, message ->
                    // Navigate back after scan
                    navController.popBackStack()
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
        
        // Payment System Screen
        composable(Screen.PaymentSystem.route) {
            PaymentScreen(
                onPaymentSuccess = {
                    // Navigate back to home after successful payment
                    navController.popBackStack(Screen.Home.route, inclusive = false)
                },
                onCancel = {
                    navController.popBackStack()
                }
            )
        }
    }
}
