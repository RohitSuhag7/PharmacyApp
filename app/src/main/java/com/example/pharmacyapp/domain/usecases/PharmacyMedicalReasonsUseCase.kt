package com.example.pharmacyapp.domain.usecases

import com.example.pharmacyapp.data.model.MedicalReason
import com.example.pharmacyapp.domain.repository.PharmacyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PharmacyMedicalReasonsUseCase @Inject constructor(private val repository: PharmacyRepository) {

    suspend fun executeMedicalReasons(): Flow<List<MedicalReason>> = repository.getMedicalReasonsData()
}
