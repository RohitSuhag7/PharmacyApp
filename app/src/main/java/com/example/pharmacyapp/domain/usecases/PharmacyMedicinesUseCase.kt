package com.example.pharmacyapp.domain.usecases

import com.example.pharmacyapp.data.model.Medicine
import com.example.pharmacyapp.domain.repository.PharmacyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PharmacyMedicinesUseCase @Inject constructor(private val repository: PharmacyRepository){

    suspend fun executeMedicinesData(searchQuery: String): Flow<List<Medicine>> {
        return repository.getMedicineData().map { medicines ->
            medicines.filter { medicine ->
                medicine.name.contains(searchQuery, ignoreCase = true) ||
                        medicine.category.contains(searchQuery, ignoreCase = true)
            }
        }
    }
}
