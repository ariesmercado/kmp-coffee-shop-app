package com.coffeeshop.navigation

/**
 * Navigation events that can be triggered from UI
 */
sealed class NavigationEvent {
    data class NavigateTo(val screen: Screen) : NavigationEvent()
    data object NavigateBack : NavigationEvent()
}
