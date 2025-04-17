package com.example.pharmacyapp.domain.usecases

import com.example.pharmacyapp.data.model.OrderedMedicineDetails
import com.example.pharmacyapp.domain.repository.OrderedMedicineRepository
import javax.inject.Inject

class InsertOrderedMedicineUseCase @Inject constructor(private val orderedMedicineRepository: OrderedMedicineRepository) {

    suspend fun executeOrderedMedicines(orderedMedicineDetails: OrderedMedicineDetails) {
        orderedMedicineRepository.orderedMedicines(orderedMedicineDetails)
    }
}
