package com.example.pharmacyapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pharmacyapp.data.model.Appointments
import kotlinx.coroutines.flow.Flow

@Dao
interface AppointmentsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppointments(appointments: Appointments): Long

    @Delete
    suspend fun deleteAppointments(appointments: Appointments)

    @Query("SELECT * FROM APPOINTMENTS_TABLE")
    fun getAllAppointments(): Flow<List<Appointments>>
}
