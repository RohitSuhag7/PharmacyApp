package com.example.pharmacyapp.data.model

import com.google.gson.annotations.SerializedName

data class Doctor(
    @SerializedName("doctor_id")
    val id: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("profile_picture")
    val profilePicture: String,
    @SerializedName("specialization")
    val specialization: String
)