package com.example.pharmacyapp.domain.usecases

import com.example.pharmacyapp.data.model.Appointments
import com.example.pharmacyapp.domain.repository.AppointmentsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllAppointmentsUseCase @Inject constructor(private val repository: AppointmentsRepository) {
    fun execute(): Flow<List<Appointments>> = repository.getAllAppointments()
}
