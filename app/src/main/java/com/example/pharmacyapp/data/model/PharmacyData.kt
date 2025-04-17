package com.example.pharmacyapp.data.model

import com.example.pharmacyapp.data.model.pharmacy.Pharmacy
import com.google.gson.annotations.SerializedName

data class PharmacyData(
    @SerializedName("user")
    val user: User,
    @SerializedName("doctors")
    val doctors: List<Doctor>,
    @SerializedName("medicines")
    val medicines: List<Medicine>,
    @SerializedName("pharmacies")
    val pharmacies: List<Pharmacy>,
    @SerializedName("medical_reasons")
    val medicalReasons: List<MedicalReason>
)
