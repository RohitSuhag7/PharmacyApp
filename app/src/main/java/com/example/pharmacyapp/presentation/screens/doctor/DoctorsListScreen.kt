package com.example.pharmacyapp.presentation.screens.doctor

import android.net.Uri
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
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
import androidx.compose.ui.res.stringResource
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
import com.example.pharmacyapp.utils.Constants.DOCTOR_NAV_KEY
import com.example.pharmacyapp.utils.Constants.GALLERY_IMAGE_URI
import com.example.pharmacyapp.utils.MySearchBar
import com.google.gson.Gson

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorsListScreen(navController: NavController, sharedImageUri: String?) {

    val pharmacyViewModel: PharmacyViewModel = hiltViewModel()
    val filteredDoctors by pharmacyViewModel.doctorsData.collectAsState()

    val searchQuery = remember { mutableStateOf("") }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        pharmacyViewModel.fetchDoctors(searchQuery.value)
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
                            text = "Pharmacy Doctors",
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
                items(filteredDoctors.size) { index ->
                    DoctorsListCardView(
                        doctorName = "${filteredDoctors[index].firstName} ${filteredDoctors[index].lastName}",
                        specialization = filteredDoctors[index].specialization,
                        isAvailable = true,
                        onClick = {
                            val doctorsJsonString = Gson().toJson(filteredDoctors[index])
                            if (sharedImageUri == null) {
                                navController.navigate(Constants.DOCTOR_PROFILE + "?$DOCTOR_NAV_KEY=$doctorsJsonString")
                            } else {
                                navController.navigate(Constants.CHAT_SCREEN + "?$DOCTOR_NAV_KEY=$doctorsJsonString&$GALLERY_IMAGE_URI=$sharedImageUri")
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun DoctorsListCardView(
    doctorName: String,
    specialization: String,
    isAvailable: Boolean,
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
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .border(
                        width = 2.dp,
                        color = LightBlue,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_doctor),
                    contentDescription = "doctor image",
                    tint = LightBlue,
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(modifier = Modifier.width(32.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = doctorName,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = specialization,
                    fontSize = 14.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "available now or not",
                        tint = if (isAvailable) LightGreen else Color.Gray,
                        modifier = Modifier.size(16.dp)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = if (isAvailable)
                            stringResource(R.string.available_now)
                        else
                            stringResource(R.string.not_available),
                        fontSize = 14.sp,
                        color = if (isAvailable) LightGreen else Color.Gray
                    )
                }
            }
        }
    }
}
