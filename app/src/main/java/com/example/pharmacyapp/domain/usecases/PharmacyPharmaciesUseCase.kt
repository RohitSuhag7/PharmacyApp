package com.example.pharmacyapp.domain.usecases

import com.example.pharmacyapp.data.model.pharmacy.Pharmacy
import com.example.pharmacyapp.domain.repository.PharmacyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PharmacyPharmaciesUseCase @Inject constructor(private val repository: PharmacyRepository) {

    suspend fun executePharmaciesData(): Flow<List<Pharmacy>> = repository.getPharmaciesData()
}
