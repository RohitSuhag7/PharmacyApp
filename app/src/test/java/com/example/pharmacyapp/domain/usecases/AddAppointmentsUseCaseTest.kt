package com.example.pharmacyapp.domain.usecases

import com.example.pharmacyapp.data.model.Appointments
import com.example.pharmacyapp.domain.repository.AppointmentsRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class AddAppointmentsUseCaseTest {

    @get:Rule
    var mockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var mockAppointmentsRepository: AppointmentsRepository

    private lateinit var addAppointmentsUseCase: AddAppointmentsUseCase

    @Before
    fun setUp() {
        addAppointmentsUseCase = AddAppointmentsUseCase(mockAppointmentsRepository)
    }

//    @Test
//    fun executeInsertAppointmentsWithDataTest() {
//        runBlocking {
//
//            // Given
//            val appointments = Appointments(
//                id = 1,
//                date = "11 March, 2025",
//                time = "02:30",
//                reason = "Fever",
//                doctor = "Amilia",
//                status = "Confirmed"
//            )
//
//            // When
//            whenever(mockAppointmentsRepository.insertAppointments(appointments)).thenReturn(Unit)
//            addAppointmentsUseCase.execute(appointments = appointments)
//
//            // Then
//            verify(mockAppointmentsRepository).insertAppointments(appointments)
//        }
//    }

//    @Test
//    fun executeWithEmptyDataTest() {
//        runBlocking {
//
//            // Given
//            val appointments = Appointments(
//                id = 1,
//                date = "",
//                time = "",
//                reason = "",
//                doctor = "",
//                status = ""
//            )
//
//            // When
//           whenever(mockAppointmentsRepository.insertAppointments(any())).thenReturn(Unit)
//            addAppointmentsUseCase.execute(appointments = appointments)
//
//            verify(mockAppointmentsRepository).insertAppointments(appointments)
//        }
//    }
}
