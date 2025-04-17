package com.example.pharmacyapp.domain.repository

import com.example.pharmacyapp.data.model.Appointments
import kotlinx.coroutines.flow.Flow

interface AppointmentsRepository {

    suspend fun insertAppointments(appointments: Appointments): Long

    suspend fun deleteAppointments(appointments: Appointments)

    fun getAllAppointments(): Flow<List<Appointments>>
}
