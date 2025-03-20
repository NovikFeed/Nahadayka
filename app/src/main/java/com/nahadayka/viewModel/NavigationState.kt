package com.nahadayka.viewModel

import com.nahadayka.view.Screen

data class NavigationState(
    val isLoading : Boolean = false,
    val screenRoute : String = Screen.Login.route,
    val message : String = "ok",
    val viewMessage : Boolean = false,
    val isLogin : Boolean = true,
){
}