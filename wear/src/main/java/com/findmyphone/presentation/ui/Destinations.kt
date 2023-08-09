package com.findmyphone.presentation.ui

sealed class Destinations(val route: String) {
    object Splash : Destinations(route = "splash_screen")
    object Home : Destinations(route = "home_screen")
    object Settings : Destinations(route = "settings_screen")
    object FindingDevice : Destinations(route = "finding_screen/{deviceId}")
}
