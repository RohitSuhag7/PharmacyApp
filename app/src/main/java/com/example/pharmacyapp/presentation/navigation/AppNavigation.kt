package com.example.pharmacyapp.presentation.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pharmacyapp.presentation.screens.auth.LoginScreen
import com.example.pharmacyapp.presentation.screens.PharmacyBottomBar
import com.example.pharmacyapp.presentation.screens.SplashScreen
import com.example.pharmacyapp.presentation.screens.auth.SignUpScreen
import com.example.pharmacyapp.utils.Constants

@Composable
fun AppNavigation(sharedImageUri: Uri?) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Constants.SPLASH_SCREEN
    ) {
        composable(route = Constants.SPLASH_SCREEN) {
            SplashScreen(navController = navController)
        }
        composable(route = Constants.LOGIN_SCREEN) {
            LoginScreen(navController = navController)
        }
        composable(route = Constants.SIGNUP_SCREEN) {
            SignUpScreen(navController = navController)
        }
        composable(route = Constants.BOTTOM_NAV) {
            PharmacyBottomBar(sharedImageUri)
        }
    }
}
