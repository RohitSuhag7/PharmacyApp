package com.example.pharmacyapp.data.model.pharmacy

import com.google.gson.annotations.SerializedName

data class Pharmacy(
    @SerializedName("pharmacy_id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("address")
    val address: Address,
    @SerializedName("contact")
    val contact: Contact,
    @SerializedName("location")
    val location: Location
)
