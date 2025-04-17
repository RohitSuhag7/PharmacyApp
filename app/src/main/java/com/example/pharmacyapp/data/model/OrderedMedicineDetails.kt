package com.example.pharmacyapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pharmacyapp.utils.Constants.ORDERED_MEDICINE_TABLE

@Entity(tableName = ORDERED_MEDICINE_TABLE)
data class OrderedMedicineDetails(
    @PrimaryKey
    val id: String = "",
    val medicineName: String? = "",
    val medicinePillsCount: String? = "",
    val medicineCategory: String? = "",
    val medicineDescription: String? = "",
    val medicinePrice: String? = "",
    val medicineManufacturer: String? = "",
    val selectedPharmacy: String? = ""
)
