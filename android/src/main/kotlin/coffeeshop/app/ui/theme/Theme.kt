package coffeeshop.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorPalette = lightColors(
    primary = CoffeeBrown,
    primaryVariant = DarkCoffee,
    secondary = CaramelBrown,
    secondaryVariant = MochaBrown,
    background = CreamyWhite,
    surface = LatteFoam,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = DarkCoffee,
    onSurface = DarkCoffee
)

private val DarkColorPalette = darkColors(
    primary = LightCoffee,
    primaryVariant = CoffeeBrown,
    secondary = CaramelBrown,
    secondaryVariant = MochaBrown,
    background = EspressoBlack,
    surface = DarkCoffee,
    onPrimary = CreamyWhite,
    onSecondary = CreamyWhite,
    onBackground = CreamyWhite,
    onSurface = CreamyWhite
)

@Composable
fun CoffeeShopTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        content = content
    )
}
