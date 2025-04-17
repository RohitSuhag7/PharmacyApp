package com.example.pharmacyapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.pharmacyapp.data.model.OrderedMedicineDetails

@Dao
interface OrderedMedicineDetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderedMedicineDetails(orderedMedicineDetails: OrderedMedicineDetails)
}