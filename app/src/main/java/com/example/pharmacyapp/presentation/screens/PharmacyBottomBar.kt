package com.example.pharmacyapp.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pharmacyapp.R
import com.example.pharmacyapp.presentation.navigation.BottomNavigation
import com.example.pharmacyapp.presentation.navigation.PharmacyNavScreens
import com.example.pharmacyapp.presentation.screens.auth.FirebaseUiState
import com.example.pharmacyapp.presentation.theme.LightBlue
import com.example.pharmacyapp.presentation.viewmodel.AuthViewModel
import com.example.pharmacyapp.utils.Constants
import com.example.pharmacyapp.utils.Constants.DOCTOR_NAV_KEY
import com.example.pharmacyapp.utils.Constants.MEDICINE_NAV_KEY
import com.example.pharmacyapp.utils.Constants.PDF_FILE_PATH

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PharmacyBottomBar() {
    val navController = rememberNavController()

    val viewModel: AuthViewModel = hiltViewModel()

    val userDetailsState by viewModel.userDetails.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getUserDetails()
    }

    var userName: String? = null

    when (val state = userDetailsState) {
        is FirebaseUiState.Success -> {
            userName = state.data.name
        }

        is FirebaseUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is FirebaseUiState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = state.message,
                    color = Color.Red
                )
            }
        }
    }

    val screens = mutableListOf(
        PharmacyNavScreens.AppointmentsDetailsScreen,
        PharmacyNavScreens.AppointmentsScreen,
        PharmacyNavScreens.ProfileScreen,
        PharmacyNavScreens.ChartScreen,
        PharmacyNavScreens.MoreScreen
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val listOfRoutes = listOf(
        Constants.DOCTORS_LIST_SCREEN,
        Constants.DOCTOR_PROFILE + "?$DOCTOR_NAV_KEY={$DOCTOR_NAV_KEY}",
        Constants.MEDICINES_LIST_SCREEN,
        Constants.MEDICINES_DETAILS_SCREEN + "?$MEDICINE_NAV_KEY={$MEDICINE_NAV_KEY}",
        Constants.PHARMACIES_LOCATION_MAP_SCREEN,
        Constants.LOGIN_SCREEN,
        Constants.PDF_VIEWER_SCREEN + "?$PDF_FILE_PATH={$PDF_FILE_PATH}",
        Constants.EXOPLAYER_SCREEN,
        Constants.SETTINGS_SCREEN,
        Constants.CHAT_SCREEN + "?$DOCTOR_NAV_KEY={$DOCTOR_NAV_KEY}"
    )
    val showBottomBar = listOfRoutes.contains(currentRoute).not()

    if (showBottomBar) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                if (currentRoute?.contains(Constants.MORE_SCREEN) == true) {
                    TopAppBar(
                        title = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                            ) {
                                Text(
                                    text = stringResource(R.string.account_info),
                                    fontSize = 25.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    style = MaterialTheme.typography.titleMedium,
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(containerColor = LightBlue),
                        modifier = Modifier.height(120.dp)
                    )
                } else {
                    TopAppBar(
                        title = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.profile_img_back),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(60.dp)
                                        .clip(CircleShape),
                                    contentScale = ContentScale.Crop
                                )

                                Spacer(modifier = Modifier.width(16.dp))

                                Text(
                                    text = userName ?: stringResource(R.string.jai_singh),
                                    fontSize = 25.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    style = MaterialTheme.typography.titleMedium,
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(containerColor = LightBlue),
                        modifier = Modifier.height(120.dp)
                    )
                }
            },

            bottomBar = {
                BottomAppBar(
                    containerColor = Color.White
                ) {

                    screens.forEach { item ->
                        NavigationBarItem(
                            selected = currentRoute == item.route,
                            onClick = {
                                navController.navigate(item.route)
                            }, icon = {
                                Icon(
                                    painter = painterResource(id = item.icons),
                                    contentDescription = null,
                                    tint = LightBlue,
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                        )
                    }
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
            ) {
                BottomNavigation(navController = navController)
            }
        }
    } else {
        BottomNavigation(navController = navController)
    }
}
