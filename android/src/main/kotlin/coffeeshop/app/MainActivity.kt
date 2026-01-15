package coffeeshop.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
                // To view the Custom Drink Builder Screen, uncomment the line below
                CustomDrinkBuilderScreen()
                // To view the Notifications Screen, uncomment the line below and comment out CustomDrinkBuilderScreen()
                // NotificationsScreen()
                // To view the Profile Screen, uncomment the line below and comment out CustomDrinkBuilderScreen()
                // ProfileScreen()
                // To view the Menu Screen with favorites, uncomment the line below and comment out CustomDrinkBuilderScreen()
                // MenuScreen()
                // To view the Favorites Screen, uncomment the line below and comment out CustomDrinkBuilderScreen()
                // FavoritesScreen()
                // To view the Order History Screen, uncomment the line below and comment out CustomDrinkBuilderScreen()
                // OrderHistoryScreen()
                // To view the Payment Screen, uncomment the line below and comment out CustomDrinkBuilderScreen()
                // PaymentScreen()
                // To view the Order Summary Screen, uncomment the line below and comment out CustomDrinkBuilderScreen()
                // OrderSummaryScreen()
                // HomeScreen()
            }
        }
    }
}
