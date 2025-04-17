package com.example.pharmacyapp.presentation

import android.annotation.SuppressLint
import android.app.PictureInPictureParams
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Rational
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.pharmacyapp.presentation.navigation.AppNavigation
import com.example.pharmacyapp.presentation.navigation.PharmacyNavScreens
import com.example.pharmacyapp.presentation.theme.PharmacyAppTheme
import com.example.pharmacyapp.presentation.viewmodel.DataStorePreferenceViewModel
import com.example.pharmacyapp.utils.reminder.NotificationHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
//    var pipMode = false

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val dataStorePreferenceViewModel: DataStorePreferenceViewModel = hiltViewModel()
            val isDarkTheme = dataStorePreferenceViewModel.theme.collectAsState()

            LaunchedEffect(Unit) {
                dataStorePreferenceViewModel.loadTheme()
            }

            PharmacyAppTheme(
                darkTheme = isDarkTheme.value
            ) {
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) {
                    AppNavigation()
//                    val navigateTo = intent.getStringExtra("navigate_to")
//                    if (navigateTo == "appointments_details") {
//                        navController.navigate(PharmacyNavScreens.AppointmentsDetailsScreen.route)
//                    }
                }

                // Create Notification Channel
                NotificationHelper(this).createNotificationChannel()
            }
        }
    }

//    override fun onUserLeaveHint() {
//        super.onUserLeaveHint()
//        enterPipMode()?.let { params ->
//            enterPictureInPictureMode(params)
//        }
////        pipMode = true
//    }
//
//    private fun enterPipMode(): PictureInPictureParams? {
//        val aspectRatio = Rational(16, 9)
//        val params = PictureInPictureParams
//            .Builder()
//            .setAspectRatio(aspectRatio)
//            .build()
//        return params
//    }
}
