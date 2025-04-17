package com.example.pharmacyapp.presentation.screens.appointments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pharmacyapp.data.model.Appointments
import com.example.pharmacyapp.presentation.theme.LightGreen
import com.example.pharmacyapp.presentation.viewmodel.AppointmentsViewModel
import com.example.pharmacyapp.utils.SwipeToDeleteContainer

@Composable
fun AppointmentsDetailsScreen() {

    // viewmodel
    val appointmentsViewModel: AppointmentsViewModel = hiltViewModel()
    val getAllAppointments: List<Appointments> by appointmentsViewModel.appointments.collectAsState(
        initial = emptyList()
    )

    LaunchedEffect(Unit) {
        appointmentsViewModel.getAllAppointments()
    }

    if (getAllAppointments.isNotEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn {
                items(
                    items = getAllAppointments,
                    key = { it }
                ) { appointment ->
                    SwipeToDeleteContainer(
                        item = appointment,
                        onDelete = {
                            appointmentsViewModel.deleteAppointment(appointment)
                        }
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            elevation = CardDefaults.elevatedCardElevation(4.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    modifier = Modifier
                                        .weight(0.3f)
                                        .padding(16.dp)
                                ) {
                                    Text(
                                        text = appointment.time.toString(),
                                        fontSize = 25.sp,
                                        fontWeight = FontWeight.Bold
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Text(
                                        text = appointment.date.toString(),
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                VerticalDivider(
                                    modifier = Modifier
                                        .height(120.dp)
                                        .background(Color.LightGray),
                                    thickness = 5.dp
                                )

                                Column(
                                    modifier = Modifier
                                        .weight(0.5f)
                                        .padding(16.dp)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.CheckCircle,
                                            contentDescription = null,
                                            tint = LightGreen,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Text(
                                            text = appointment.reason.toString(),
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier
                                                .padding(horizontal = 8.dp)
                                        )
                                    }
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.CheckCircle,
                                            contentDescription = null,
                                            tint = LightGreen,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Text(
                                            text = appointment.doctor.toString(),
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier
                                                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                                        )
                                    }
                                    Text(
                                        text = appointment.status.toString(),
                                        modifier = Modifier
                                            .background(color = LightGreen)
                                            .padding(horizontal = 16.dp, vertical = 4.dp),
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        style = TextStyle(color = Color.White)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "No Appointments",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
