package com.example.pharmacyapp.domain.usecases

import com.example.pharmacyapp.data.model.Doctor
import com.example.pharmacyapp.domain.repository.PharmacyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PharmacyDoctorsUseCase @Inject constructor(private val repository: PharmacyRepository) {

    suspend fun executeDoctorsData(searchQuery: String): Flow<List<Doctor>> {
        return repository.getDoctorsData().map { doctors ->
            doctors.filter { doctor ->
                doctor.firstName.contains(searchQuery, ignoreCase = true) ||
                        doctor.lastName.contains(searchQuery, ignoreCase = true) ||
                        doctor.specialization.contains(searchQuery, ignoreCase = true)
            }
        }
    }
}
