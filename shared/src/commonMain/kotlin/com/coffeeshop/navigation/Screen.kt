package com.coffeeshop.navigation

/**
 * Sealed class representing all navigation routes in the Coffee Shop App
 */
sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object DrinkBuilder : Screen("drink_builder")
    data object Loyalty : Screen("loyalty")
    data object Scanner : Screen("scanner")
    data object PaymentSystem : Screen("payment_system")
}

/**
 * List of all available screens for navigation
 */
val allScreens = listOf(
    Screen.Home,
    Screen.DrinkBuilder,
    Screen.Loyalty,
    Screen.Scanner,
    Screen.PaymentSystem
)
