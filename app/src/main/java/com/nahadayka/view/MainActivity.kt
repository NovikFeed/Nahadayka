package com.nahadayka.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nahadayka.ui.theme.NahadaykaTheme
import com.nahadayka.viewModel.NavigationViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.Alignment
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

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
    val currentScreen = navController.currentBackStackEntry?.destination?.route
    LaunchedEffect(navigationState) {
        if(navigationState.screenRoute != currentScreen){
        navController.popBackStack()
        navController.navigate(navigationState.screenRoute)}
    }
    Box(modifier = Modifier.fillMaxSize()) {
        NavHost(
            modifier = Modifier.fillMaxSize(),
            startDestination = Screen.Login.route,
            navController = navController
        ) {
            composable(Screen.Login.route) {
                LoginScreen(navigationState, navigationViewModel)
            }
            composable(Screen.Home.route) {
                HomeScreen(navigationViewModel)
            }
        }
        if (navigationState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .pointerInput(Unit) {})
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                CircularProgressIndicator(modifier = Modifier.size(70.dp), color = Color.Black)
            }
        }

    }

}