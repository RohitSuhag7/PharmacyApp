package com.example.pharmacyapp.data.model

import com.google.gson.annotations.SerializedName

data class Medicine(
    @SerializedName("medicine_id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("category")
    val category: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("manufacturer")
    val manufacturer: String,
    @SerializedName("prescription_required")
    val prescriptionRequired: Boolean,
    @SerializedName("price")
    val price: Double,
    @SerializedName("stock_quantity")
    val stockQuantity: Int
)