package coffeeshop.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import coffeeshop.app.navigation.CoffeeShopNavigation
import coffeeshop.app.ui.theme.CoffeeShopTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoffeeShopTheme {
                CoffeeShopNavigation()
            }
        }
    }
}
