package com.example.pharmacyapp.presentation.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.pharmacyapp.presentation.screens.appointments.BookAppointmentsScreen
import com.example.pharmacyapp.presentation.screens.appointments.AppointmentsDetailsScreen
import com.example.pharmacyapp.presentation.screens.ChartScreen
import com.example.pharmacyapp.presentation.screens.ExoPlayerScreen
import com.example.pharmacyapp.presentation.screens.MoreScreen
import com.example.pharmacyapp.presentation.screens.ProfileScreen
import com.example.pharmacyapp.presentation.screens.SettingsScreen
import com.example.pharmacyapp.presentation.screens.PDFViewerScreen
import com.example.pharmacyapp.presentation.screens.PharmaciesLocationMapScreen
import com.example.pharmacyapp.presentation.screens.auth.LoginScreen
import com.example.pharmacyapp.presentation.screens.doctor.ChatScreen
import com.example.pharmacyapp.presentation.screens.doctor.DoctorProfile
import com.example.pharmacyapp.presentation.screens.doctor.DoctorsListScreen
import com.example.pharmacyapp.presentation.screens.medicines.MedicineDetailsScreen
import com.example.pharmacyapp.presentation.screens.medicines.MedicinesListScreen
import com.example.pharmacyapp.utils.Constants
import com.example.pharmacyapp.utils.Constants.DOCTOR_NAV_KEY
import com.example.pharmacyapp.utils.Constants.GALLERY_IMAGE_URI
import com.example.pharmacyapp.utils.Constants.MEDICINE_NAV_KEY
import com.example.pharmacyapp.utils.Constants.PDF_FILE_PATH
import java.io.File

@Composable
fun BottomNavigation(navController: NavHostController, sharedImageUri: Uri?) {

    LaunchedEffect(sharedImageUri) {
        if (sharedImageUri != null) {
            navController.navigate(Constants.DOCTORS_LIST_SCREEN + "?$GALLERY_IMAGE_URI=$sharedImageUri")
        }
    }

    NavHost(
        navController = navController,
        startDestination = PharmacyNavScreens.ProfileScreen.route
    ) {
        composable(route = PharmacyNavScreens.AppointmentsDetailsScreen.route) {
            AppointmentsDetailsScreen()
        }
        composable(route = PharmacyNavScreens.AppointmentsScreen.route) {
            BookAppointmentsScreen(navController = navController)
        }
        composable(route = PharmacyNavScreens.ProfileScreen.route) {
            ProfileScreen(navController = navController)
        }
        composable(route = PharmacyNavScreens.ChartScreen.route) {
            ChartScreen()
        }
        composable(route = PharmacyNavScreens.MoreScreen.route) {
            MoreScreen(navController = navController)
        }
        composable(
            route = Constants.DOCTORS_LIST_SCREEN + "?$GALLERY_IMAGE_URI={$GALLERY_IMAGE_URI}",
            arguments = listOf(
                navArgument(GALLERY_IMAGE_URI) {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) { navBackStackEntry ->
            DoctorsListScreen(
                navController = navController,
                sharedImageUri = navBackStackEntry.arguments?.getString(GALLERY_IMAGE_URI)
            )
        }
        composable(
            route = Constants.DOCTOR_PROFILE + "?$DOCTOR_NAV_KEY={$DOCTOR_NAV_KEY}",
            arguments = listOf(
                navArgument(DOCTOR_NAV_KEY) {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) { navBackStackEntry ->
            DoctorProfile(
                navController = navController,
                doctorsJsonString = navBackStackEntry.arguments?.getString(DOCTOR_NAV_KEY)
            )
        }
        composable(route = Constants.MEDICINES_LIST_SCREEN) {
            MedicinesListScreen(navController = navController)
        }
        composable(
            route = Constants.MEDICINES_DETAILS_SCREEN + "?$MEDICINE_NAV_KEY={$MEDICINE_NAV_KEY}",
            arguments = listOf(
                navArgument(MEDICINE_NAV_KEY) {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) { navBackStackEntry ->
            MedicineDetailsScreen(
                navController = navController,
                medicineJsonString = navBackStackEntry.arguments?.getString(MEDICINE_NAV_KEY)
            )
        }
        composable(route = Constants.PHARMACIES_LOCATION_MAP_SCREEN) {
            PharmaciesLocationMapScreen()
        }
        composable(route = Constants.LOGIN_SCREEN) {
            LoginScreen(navController)
        }
        composable(
            route = Constants.PDF_VIEWER_SCREEN + "?$PDF_FILE_PATH={$PDF_FILE_PATH}",
            arguments = listOf(navArgument(PDF_FILE_PATH) {
                type = NavType.StringType
                nullable = true
            }),
        ) { backStackEntry ->
            val pdfFilePath = backStackEntry.arguments?.getString(PDF_FILE_PATH)
            if (pdfFilePath != null) {
                PDFViewerScreen(navController = navController, pdfFile = File(pdfFilePath))
            }
        }
        composable(route = Constants.EXOPLAYER_SCREEN) {
            ExoPlayerScreen(navController = navController)
        }
        composable(route = Constants.SETTINGS_SCREEN) {
            SettingsScreen(navController = navController)
        }
        composable(
            route = Constants.CHAT_SCREEN + "?$DOCTOR_NAV_KEY={$DOCTOR_NAV_KEY}&$GALLERY_IMAGE_URI={$GALLERY_IMAGE_URI}",
            arguments = listOf(
                navArgument(DOCTOR_NAV_KEY) {
                    type = NavType.StringType
                    nullable = true
                },
                navArgument(GALLERY_IMAGE_URI) {
                    type = NavType.StringType
                    nullable = true
                }
            )) { navBackStackEntry ->
            ChatScreen(
                navController = navController,
                doctorsJsonString = navBackStackEntry.arguments?.getString(DOCTOR_NAV_KEY),
                sharedImageUri = navBackStackEntry.arguments?.getString(GALLERY_IMAGE_URI)
            )
        }
    }
}
