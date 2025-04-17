package com.example.pharmacyapp.data.model.pharmacy

import com.google.gson.annotations.SerializedName

data class Contact(
    @SerializedName("email")
    val email: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("website")
    val website: String
)