package com.example.pharmacyapp.presentation.screens.medicines

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pharmacyapp.R
import com.example.pharmacyapp.presentation.theme.LightBlue
import com.example.pharmacyapp.presentation.theme.LightGreen
import com.example.pharmacyapp.presentation.viewmodel.PharmacyViewModel
import com.example.pharmacyapp.utils.Constants
import com.example.pharmacyapp.utils.Constants.MEDICINE_NAV_KEY
import com.example.pharmacyapp.utils.MySearchBar
import com.google.gson.Gson

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicinesListScreen(navController: NavController) {

    val pharmacyViewModel: PharmacyViewModel = hiltViewModel()
    val filterMedicines by pharmacyViewModel.medicinesData.collectAsState()

    val searchQuery = remember { mutableStateOf("") }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        pharmacyViewModel.fetchMedicines(searchQuery.value)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
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
                            text = "Available Medicines",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = LightBlue),
                modifier = Modifier.height(120.dp)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MySearchBar(
                query = searchQuery.value,
                onQueryChange = {
                    searchQuery.value = it
                },
                onKeyboardSearchClick = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                }
            )

            LazyColumn {
                items(filterMedicines.size) { index ->
                    MedicineListCardView(
                        img = R.drawable.ic_medicine,
                        name = filterMedicines[index].name,
                        category = filterMedicines[index].category,
                        price = "₹${filterMedicines[index].price}",
                        onClick = {
                            val medicineJsonString = Gson().toJson(filterMedicines[index])
                            navController.navigate(Constants.MEDICINES_DETAILS_SCREEN + "?$MEDICINE_NAV_KEY=$medicineJsonString")
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun MedicineListCardView(
    img: Int,
    name: String,
    category: String,
    price: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.elevatedCardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(img),
                    contentDescription = "doctor image",
                    tint = LightBlue,
                    modifier = Modifier.size(50.dp)
                )
            }

            Spacer(modifier = Modifier.width(32.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    maxLines = 1
                )
                Text(
                    text = category,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = price,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = LightGreen
            )
        }
    }
}
