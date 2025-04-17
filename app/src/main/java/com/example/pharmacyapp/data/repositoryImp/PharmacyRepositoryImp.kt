package com.example.pharmacyapp.data.repositoryImp

import com.example.pharmacyapp.data.model.Doctor
import com.example.pharmacyapp.data.model.MedicalReason
import com.example.pharmacyapp.data.model.Medicine
import com.example.pharmacyapp.data.model.pharmacy.Pharmacy
import com.example.pharmacyapp.data.remote.ApiService
import com.example.pharmacyapp.domain.repository.PharmacyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class PharmacyRepositoryImp @Inject constructor(private val apiService: ApiService) :
    PharmacyRepository {

    override suspend fun getDoctorsData(): Flow<List<Doctor>> {
        val response = apiService.getPharmacyData()
        return flowOf(response.doctors)
    }

    override suspend fun getPharmaciesData(): Flow<List<Pharmacy>> {
        val response = apiService.getPharmacyData()
        return flowOf(response.pharmacies)
    }

    override suspend fun getMedicineData(): Flow<List<Medicine>> {
        val response = apiService.getPharmacyData()
        return flowOf(response.medicines)
    }

    override suspend fun getMedicalReasonsData(): Flow<List<MedicalReason>> {
        val response = apiService.getPharmacyData()
        return flowOf(response.medicalReasons)
    }
}
