package com.example.pharmacyapp.presentation.screens

import android.location.Address
import android.location.Geocoder
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pharmacyapp.presentation.theme.LightGreen
import com.example.pharmacyapp.presentation.viewmodel.PharmacyViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PharmaciesLocationMapScreen() {

    // viewmodel
    val pharmacyViewModel: PharmacyViewModel = hiltViewModel()
    val pharmaciesData by pharmacyViewModel.pharmaciesData.collectAsState()

    val location1 = LatLng(40.7128, -74.006)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(location1, 10f)
    }

    val context = LocalContext.current
    val geocoder = Geocoder(context, Locale.getDefault())

    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        pharmacyViewModel.fetchPharmacies()
    }


    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState
            ) {
                pharmaciesData.forEach { pharmacy ->
                    val location = LatLng(pharmacy.location.latitude, pharmacy.location.longitude)
                    Marker(
                        state = MarkerState(position = location),
                        title = pharmacy.name
                    )
                }
            }

            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = {
                        showBottomSheet = false
                    },
                    sheetState = sheetState,
                    containerColor = Color.White,
                    tonalElevation = 16.dp
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .padding(
                                vertical = 16.dp,
                                horizontal = 25.dp
                            )
                    ) {
                        items(pharmaciesData.size) { index ->
                            val addresses: MutableList<Address>? = geocoder.getFromLocation(
                                pharmaciesData[index].location.latitude,
                                pharmaciesData[index].location.longitude,
                                1
                            )
                            val address = addresses?.get(0)

                            val street =
                                address?.thoroughfare ?: address?.getAddressLine(0)?.split(",")
                                    ?.get(0) ?: "Unknown street"
                            val city = address?.adminArea ?: "New York"



                            Spacer(modifier = Modifier.height(8.dp))

                            ModalSheetContent(
                                pharmacyName = pharmaciesData[index].name,
                                pharmacyLocation = "$street, $city"
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            HorizontalDivider(
                                thickness = 2.dp,
                                color = LightGreen
                            )

                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ModalSheetContent(
    pharmacyName: String,
    pharmacyLocation: String
) {
    Text(
        text = pharmacyName,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black
    )
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = "location",
            tint = LightGreen,
            modifier = Modifier.size(16.dp)
        )

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = pharmacyLocation,
            fontSize = 14.sp,
            color = Color.Gray
        )
    }
}
