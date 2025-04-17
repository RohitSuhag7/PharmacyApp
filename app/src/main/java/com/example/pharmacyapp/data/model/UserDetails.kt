package com.example.pharmacyapp.data.model

import com.google.gson.annotations.SerializedName

data class UserDetails(
    @SerializedName("id")
    val id: String ?= "",
    @SerializedName("image")
    val img: String ?= "",
    @SerializedName("name")
    val name: String ?= "",
    @SerializedName("mobile")
    val mobileNumber: String ?= "",
    @SerializedName("address")
    val address: String ?= "",
    @SerializedName("email")
    val email: String ?= "",
    @SerializedName("dob")
    val dob: String ?= "",
)
