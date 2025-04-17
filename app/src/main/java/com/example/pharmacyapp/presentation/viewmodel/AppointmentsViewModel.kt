package com.example.pharmacyapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pharmacyapp.data.model.Appointments
import com.example.pharmacyapp.domain.usecases.AddAppointmentsUseCase
import com.example.pharmacyapp.domain.usecases.DeleteAppointmentsUseCase
import com.example.pharmacyapp.domain.usecases.GetAllAppointmentsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AppointmentsViewModel @Inject constructor(
    private val addAppointmentsUseCase: AddAppointmentsUseCase,
    private val getAllAppointmentsUseCase: GetAllAppointmentsUseCase,
    private val deleteAppointmentsUseCase: DeleteAppointmentsUseCase
) : ViewModel() {

    private val _appointments = MutableStateFlow<List<Appointments>>(emptyList())
    val appointments: StateFlow<List<Appointments>> = _appointments

    fun getAllAppointments() {
        viewModelScope.launch {
            getAllAppointmentsUseCase.execute().collect { appointmentsList ->
                _appointments.value = appointmentsList
            }
        }
    }

    fun deleteAppointment(appointments: Appointments) {
        viewModelScope.launch {
            deleteAppointmentsUseCase.execute(appointments = appointments)
        }
    }

    fun addAppointments(appointments: Appointments, onResult: ((Long) -> Unit)? = null) {
        viewModelScope.launch {
            try {
                val insertedId = addAppointmentsUseCase.execute(appointments = appointments)
                withContext(Dispatchers.Main) {
                    onResult?.invoke(insertedId)
                }
                getAllAppointments()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
