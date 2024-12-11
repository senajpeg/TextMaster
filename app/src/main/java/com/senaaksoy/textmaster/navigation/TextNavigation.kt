package com.senaaksoy.textmaster.navigation

import android.window.SplashScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.senaaksoy.textmaster.textScreens.HomeScreen
import com.senaaksoy.textmaster.textScreens.SplashScreen

@Composable
fun TextNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.SPLASHSCREEN.route) {
        composable(route = Screens.SPLASHSCREEN.route){
                SplashScreen(navController = navController)
        }
        composable(route = Screens.HOMESCREEN.route){
            HomeScreen()
        }

    }
}