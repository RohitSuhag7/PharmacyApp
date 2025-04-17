package com.example.pharmacyapp.data.repositoryImp

import com.example.pharmacyapp.data.dao.AppointmentsDao
import com.example.pharmacyapp.data.model.Appointments
import com.example.pharmacyapp.domain.repository.AppointmentsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppointmentsRepositoryImp @Inject constructor(private val appointmentsDao: AppointmentsDao) :
    AppointmentsRepository {

    override suspend fun insertAppointments(appointments: Appointments): Long = appointmentsDao.insertAppointments(appointments)

    override suspend fun deleteAppointments(appointments: Appointments) = appointmentsDao.deleteAppointments(appointments)

    override fun getAllAppointments(): Flow<List<Appointments>> = appointmentsDao.getAllAppointments()
}
