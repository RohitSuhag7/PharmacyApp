package com.example.pharmacyapp.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pharmacyapp.R
import com.example.pharmacyapp.presentation.theme.LightBlue
import com.example.pharmacyapp.utils.Constants

@Composable
fun ProfileScreen(navController: NavController) {

    val itemsList = mutableListOf(
        Item(icon = R.drawable.ic_home, text = stringResource(R.string.pharmacies)),
        Item(icon = R.drawable.ic_doctor, text = stringResource(R.string.doctors)),
        Item(icon = R.drawable.ic_medicine, text = stringResource(R.string.medicines)),
        Item(icon = R.drawable.ic_appointments, text = stringResource(R.string.appointments))
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LazyColumn {
            items(itemsList.size) { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    elevation = CardDefaults.elevatedCardElevation(4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    onClick = {
                        when (itemsList[item].text) {
                            "Pharmacies" -> navController.navigate(Constants.PHARMACIES_LOCATION_MAP_SCREEN)
                            "Doctors" -> navController.navigate(Constants.DOCTORS_LIST_SCREEN)
                            "Medicines" -> navController.navigate(Constants.MEDICINES_LIST_SCREEN)
                            "Appointments" -> navController.navigate(Constants.APPOINTMENTS_DETAILS_SCREEN)
                        }
                    }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(itemsList[item].icon),
                            contentDescription = null,
                            tint = LightBlue,
                            modifier = Modifier
                                .size(48.dp)
                        )
                        Spacer(modifier = Modifier.width(22.dp))
                        Text(
                            text = itemsList[item].text,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}

data class Item(val icon: Int, val text: String)
