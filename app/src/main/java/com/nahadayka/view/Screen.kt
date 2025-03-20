package com.nahadayka.view

sealed class Screen(val route : String) {
    object Login : Screen(route = "login")
    object Home : Screen(route = "home")
}