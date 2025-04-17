package com.example.pharmacyapp.data.repositoryImp

import com.example.pharmacyapp.data.dao.OrderedMedicineDetailsDao
import com.example.pharmacyapp.data.model.OrderedMedicineDetails
import com.example.pharmacyapp.domain.repository.OrderedMedicineRepository
import javax.inject.Inject

class OrderedMedicineRepositoryImp @Inject constructor(private val orderedMedicineDetailsDao: OrderedMedicineDetailsDao) :
    OrderedMedicineRepository {
    override suspend fun orderedMedicines(orderedMedicineDetails: OrderedMedicineDetails) {
        orderedMedicineDetailsDao.insertOrderedMedicineDetails(orderedMedicineDetails)
    }
}
