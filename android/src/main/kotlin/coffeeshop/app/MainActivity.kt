package coffeeshop.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import coffeeshop.app.ui.screen.BarcodeScannerScreen
import coffeeshop.app.ui.screen.CustomDrinkBuilderScreen
import coffeeshop.app.ui.screen.FavoritesScreen
import coffeeshop.app.ui.screen.HomeScreen
import coffeeshop.app.ui.screen.MenuScreen
import coffeeshop.app.ui.screen.NotificationsScreen
import coffeeshop.app.ui.screen.OrderHistoryScreen
import coffeeshop.app.ui.screen.OrderSummaryScreen
import coffeeshop.app.ui.screen.PaymentScreen
import coffeeshop.app.ui.screen.ProfileScreen
import coffeeshop.app.ui.theme.CoffeeShopTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoffeeShopTheme {
                var showScanner by remember { mutableStateOf(false) }
                
                if (showScanner) {
                    BarcodeScannerScreen(
                        onScanComplete = { success, points, message ->
                            // In a real app, this would show a notification or update the UI
                            showScanner = false
                        },
                        onBackClick = { showScanner = false }
                    )
                } else {
                    ProfileScreen(
                        onScanBarcodeClick = { showScanner = true }
                    )
                }
                
                // To view the Custom Drink Builder Screen, uncomment the line below
                // CustomDrinkBuilderScreen()
                // To view the Notifications Screen, uncomment the line below and comment out ProfileScreen()
                // NotificationsScreen()
                // To view the Menu Screen with favorites, uncomment the line below and comment out ProfileScreen()
                // MenuScreen()
                // To view the Favorites Screen, uncomment the line below and comment out ProfileScreen()
                // FavoritesScreen()
                // To view the Order History Screen, uncomment the line below and comment out ProfileScreen()
                // OrderHistoryScreen()
                // To view the Payment Screen, uncomment the line below and comment out ProfileScreen()
                // PaymentScreen()
                // To view the Order Summary Screen, uncomment the line below and comment out ProfileScreen()
                // OrderSummaryScreen()
                // HomeScreen()
            }
        }
    }
}
