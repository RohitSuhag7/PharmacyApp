package com.example.pharmacyapp.data.model

import com.google.gson.annotations.SerializedName

data class MedicalReason(
    @SerializedName("medicine_id")
    val medicineId: String,
    @SerializedName("reason")
    val reason: String
)
