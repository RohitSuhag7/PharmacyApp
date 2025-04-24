package com.example.pharmacyapp.presentation.screens.doctor

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pharmacyapp.R
import com.example.pharmacyapp.data.model.Doctor
import com.example.pharmacyapp.presentation.theme.LightBlue
import com.example.pharmacyapp.presentation.theme.LightGreen
import com.example.pharmacyapp.presentation.theme.LightSkyBlue
import com.example.pharmacyapp.presentation.viewmodel.PharmacyViewModel
import com.example.pharmacyapp.utils.Constants
import com.example.pharmacyapp.utils.Constants.DOCTOR_NAV_KEY
import com.example.pharmacyapp.utils.DropDownMenuEventHandler
import com.example.pharmacyapp.utils.MyIconButtonWithText
import com.google.gson.Gson

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorProfile(navController: NavController, doctorsJsonString: String?) {

    val doctorData = Gson().fromJson(doctorsJsonString, Doctor::class.java)

    val doctorFirstName by remember { mutableStateOf(doctorData?.firstName ?: "") }
    val doctorLastName by remember { mutableStateOf(doctorData?.lastName ?: "") }
    val doctorSpecialization by remember { mutableStateOf(doctorData?.specialization ?: "") }
    val doctorPhoneNumber by remember { mutableStateOf(doctorData?.phone ?: "123456789") }
    val isAvailable by remember { mutableStateOf(true) }

    // ViewModel
    val pharmacyViewModel: PharmacyViewModel = hiltViewModel()
    val medicalReasons by pharmacyViewModel.medicalReasons.collectAsState()
    val selectedMedicalReason = remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        pharmacyViewModel.fetchMedicalReasons()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                colors = TopAppBarDefaults.topAppBarColors(containerColor = LightBlue),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .absoluteOffset(y = (60).dp)
                .zIndex(1f),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.ic_doctor),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .border(
                        width = 4.dp,
                        color = Color.White,
                        shape = CircleShape
                    )
                    .background(
                        color = LightSkyBlue,
                        shape = CircleShape
                    ),
                alignment = Alignment.Center

            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 60.dp)
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "$doctorFirstName $doctorLastName",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = doctorSpecialization,
                fontSize = 14.sp,
                color = Color.Gray
            )
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

            Spacer(modifier = Modifier.height(32.dp))

            DropDownMenuEventHandler(
                selectedValue = selectedMedicalReason.value
                    ?: stringResource(R.string.select_a_reason),
                options = medicalReasons.map {
                    it.reason
                },
                onValueChangedEvent = {
                    selectedMedicalReason.value = it
                },
                getOptionLabel = {
                    it
                },
                modifier = Modifier.padding(16.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                MyIconButtonWithText(
                    iconId = R.drawable.ic_phone,
                    iconText = "Call",
                    onClick = {
                        val intent = Intent(Intent.ACTION_DIAL)
                        intent.setData(Uri.parse("tel:$doctorPhoneNumber"))

                        if (ActivityCompat.checkSelfPermission(
                                context,
                                Manifest.permission.CALL_PHONE
                            ) == PackageManager.PERMISSION_GRANTED
                        ) {
                            context.startActivity(intent)
                        } else {
                            ActivityCompat.requestPermissions(
                                context as Activity,
                                arrayOf(Manifest.permission.CALL_PHONE),
                                1
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.width(64.dp))

                MyIconButtonWithText(
                    iconId = R.drawable.ic_sms,
                    iconText = "SMS",
                    onClick = {
                        // Open Message application from this app

//                        val smsIntent = Intent(Intent.ACTION_VIEW)
//                        smsIntent.setData(Uri.parse("sms:$doctorPhoneNumber"))
//
//                        if (ActivityCompat.checkSelfPermission(
//                                context,
//                                Manifest.permission.SEND_SMS
//                            ) == PackageManager.PERMISSION_GRANTED
//                        ) {
//                            context.startActivity(smsIntent)
//                        } else {
//                            ActivityCompat.requestPermissions(
//                                context as Activity,
//                                arrayOf(Manifest.permission.SEND_SMS),
//                                1
//                            )
//                        }

                        // Navigate to Chat Screen with doctor details
                        navController.navigate(Constants.CHAT_SCREEN + "?$DOCTOR_NAV_KEY=$doctorsJsonString")
                    }
                )

                Spacer(modifier = Modifier.width(64.dp))

                MyIconButtonWithText(
                    iconId = R.drawable.ic_privacy,
                    iconText = "Help",
                    onClick = {}
                )
            }
        }
    }
}
