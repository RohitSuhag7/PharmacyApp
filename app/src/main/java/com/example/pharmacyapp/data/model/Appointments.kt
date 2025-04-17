package com.example.pharmacyapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pharmacyapp.utils.Constants.APPOINTMENTS_TABLE
import java.io.Serializable

@Entity(tableName = APPOINTMENTS_TABLE)
data class Appointments(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: String? = "",
    val time: String? = "",
    val reason: String? = "",
    val doctor: String? = "",
    val status: String? = "Confirmed"
): Serializable
