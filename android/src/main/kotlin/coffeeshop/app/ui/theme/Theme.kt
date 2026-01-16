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
    secondary = GoldenAccent,
    secondaryVariant = CaramelBrown,
    background = SoftBeige,
    surface = WarmIvory,
    onPrimary = CreamyWhite,
    onSecondary = DarkCoffee,
    onBackground = DarkCoffee,
    onSurface = DarkCoffee,
    error = Color(0xFFB00020)
)

private val DarkColorPalette = darkColors(
    primary = CinnamonBrown,
    primaryVariant = CoffeeBrown,
    secondary = GoldenAccent,
    secondaryVariant = CaramelBrown,
    background = EspressoBlack,
    surface = ChocolateBrown,
    onPrimary = CreamyWhite,
    onSecondary = DarkCoffee,
    onBackground = CreamyWhite,
    onSurface = CreamyWhite,
    error = Color(0xFFCF6679)
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
