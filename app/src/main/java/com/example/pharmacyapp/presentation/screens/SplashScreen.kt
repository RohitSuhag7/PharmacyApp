package com.example.pharmacyapp.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pharmacyapp.R
import com.example.pharmacyapp.presentation.theme.LightBlue
import com.example.pharmacyapp.presentation.theme.LightGreen
import com.example.pharmacyapp.presentation.viewmodel.AuthViewModel
import com.example.pharmacyapp.utils.Constants
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {

    val authViewModel: AuthViewModel = hiltViewModel()
    val loginUser by authViewModel.loginStateFlow.collectAsState()

    LaunchedEffect(Unit) {
        delay(timeMillis = 3000)
        if (loginUser != null) {
            navController.navigate(Constants.BOTTOM_NAV) {
                popUpTo(Constants.SPLASH_SCREEN) {
                    inclusive = true
                }
            }
        } else {
            navController.navigate(Constants.LOGIN_SCREEN) {
                popUpTo(Constants.SPLASH_SCREEN) {
                    inclusive = true
                }
            }
        }

//        navController.navigate(Constants.BOTTOM_NAV) {
//            popUpTo(Constants.SPLASH_SCREEN) {
//                inclusive = true
//            }
//        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = LightBlue),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = stringResource(R.string.pharmacy),
            color = Color.White,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(32.dp))

        Image(
            painter = painterResource(R.drawable.pharmacy_splash_img),
            contentDescription = "Splash screen image",
        )

        Spacer(modifier = Modifier.height(100.dp))

        ElevatedButton(
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = LightGreen),
        ) {
            Text(
                text = stringResource(R.string.get_started),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
