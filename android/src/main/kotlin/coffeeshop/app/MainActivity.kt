package coffeeshop.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import coffeeshop.app.ui.screen.HomeScreen
import coffeeshop.app.ui.screen.OrderHistoryScreen
import coffeeshop.app.ui.screen.OrderSummaryScreen
import coffeeshop.app.ui.screen.PaymentScreen
import coffeeshop.app.ui.theme.CoffeeShopTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoffeeShopTheme {
                // To view the Order History Screen, uncomment the line below
                OrderHistoryScreen()
                // To view the Payment Screen, uncomment the line below and comment out OrderHistoryScreen()
                // PaymentScreen()
                // To view the Order Summary Screen, uncomment the line below and comment out OrderHistoryScreen()
                // OrderSummaryScreen()
                // HomeScreen()
            }
        }
    }
}
