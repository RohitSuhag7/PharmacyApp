package com.example.pharmacyapp.domain.repository

import com.example.pharmacyapp.data.model.OrderedMedicineDetails

interface OrderedMedicineRepository {

    suspend fun orderedMedicines(orderedMedicineDetails: OrderedMedicineDetails)
}
