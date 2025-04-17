package com.example.pharmacyapp.domain.repository

import com.example.pharmacyapp.data.model.Doctor
import com.example.pharmacyapp.data.model.MedicalReason
import com.example.pharmacyapp.data.model.Medicine
import com.example.pharmacyapp.data.model.pharmacy.Pharmacy
import kotlinx.coroutines.flow.Flow

interface PharmacyRepository {

    suspend fun getDoctorsData(): Flow<List<Doctor>>

    suspend fun getPharmaciesData(): Flow<List<Pharmacy>>

    suspend fun getMedicineData(): Flow<List<Medicine>>

    suspend fun getMedicalReasonsData(): Flow<List<MedicalReason>>
}
