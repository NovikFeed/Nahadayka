package com.nahadayka.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nahadayka.ui.theme.NahadaykaTheme
import com.nahadayka.viewModel.NavigationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NahadaykaTheme {
                MainContent()
            }
        }
    }
}

@Composable
fun MainContent() {
    val navigationViewModel = hiltViewModel<NavigationViewModel>()
    val navController = rememberNavController()
    val navigationState = navigationViewModel.navigationState.collectAsState().value
    LaunchedEffect(navigationState) {
        navController.popBackStack()
        navController.navigate(navigationState.screenRoute)
    }
    NavHost(modifier = Modifier.fillMaxSize(), startDestination = Screen.Login.route, navController = navController){
        composable(Screen.Login.route) {
            LoginScreen(navigationState)
        }
        composable(Screen.Home.route) {
            HomeScreen()
        }
    }
}