package com.example.pharmacyapp.domain.usecases

import com.example.pharmacyapp.data.model.Appointments
import com.example.pharmacyapp.domain.repository.AppointmentsRepository
import javax.inject.Inject

class DeleteAppointmentsUseCase @Inject constructor(private val repository: AppointmentsRepository) {
    suspend fun execute(appointments: Appointments) = repository.deleteAppointments(appointments)
}
