package com.example.pharmacyapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pharmacyapp.data.dao.AppointmentsDao
import com.example.pharmacyapp.data.dao.OrderedMedicineDetailsDao
import com.example.pharmacyapp.data.model.Appointments
import com.example.pharmacyapp.data.model.OrderedMedicineDetails

@Database(
    entities = [Appointments::class, OrderedMedicineDetails::class],
    version = 1,
    exportSchema = false
)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun appointmentsDao(): AppointmentsDao

    abstract fun orderedMedicineDetailsDao(): OrderedMedicineDetailsDao
}
