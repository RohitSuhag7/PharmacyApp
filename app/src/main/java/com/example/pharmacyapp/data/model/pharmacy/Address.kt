package com.example.pharmacyapp.data.model.pharmacy

import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("address")
    val address: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("state")
    val country: String,
    @SerializedName("zip_code")
    val state: String,
    @SerializedName("country")
    val zipCode: String
)