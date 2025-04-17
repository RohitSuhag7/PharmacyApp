package com.example.pharmacyapp.domain.usecases

import com.example.pharmacyapp.data.model.Appointments
import com.example.pharmacyapp.domain.repository.AppointmentsRepository
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.kotlin.whenever

class GetAllAppointmentsUseCaseTest {

    @get:Rule
    var mockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var mockAppointmentsRepository: AppointmentsRepository

    private lateinit var getAllAppointmentsUseCase: GetAllAppointmentsUseCase

    @Before
    fun setUp() {
        getAllAppointmentsUseCase = GetAllAppointmentsUseCase(mockAppointmentsRepository)
    }

    @Test
    fun executeGetAllAppointmentsWithDataTest() {
        runBlocking {
            // Given
            val appointments = listOf(
                Appointments(
                    id = 1,
                    date = "11 March, 2025",
                    time = "02:30",
                    reason = "Fever",
                    doctor = "Amilia",
                    status = "Confirmed"
                )
            )

            // When
            whenever(mockAppointmentsRepository.getAllAppointments()).thenReturn(flowOf(appointments))

            val result = getAllAppointmentsUseCase.execute().toList()

            // Then
            assertEquals(listOf(appointments), result)
        }
    }

    @Test
    fun executeGetAllAppointmentsWithEmptyDataTest() {
        runBlocking {
            // Given
            val appointments = listOf(
                Appointments(
                    id = 1,
                    date = "",
                    time = "",
                    reason = "",
                    doctor = "",
                    status = ""
                )
            )

            // When
            whenever(mockAppointmentsRepository.getAllAppointments()).thenReturn(flowOf(appointments))

            val result = getAllAppointmentsUseCase.execute().toList()

            // Then
            assertEquals(listOf(appointments), result)
        }
    }

    @Test
    fun executeGetAllAppointmentsWithNullDataTest() {
        runBlocking {
            // Given
            val appointments = listOf(
                Appointments(
                    id = 1,
                    date = null,
                    time = null,
                    reason = null,
                    doctor = null,
                    status = null
                )
            )

            // When
            whenever(mockAppointmentsRepository.getAllAppointments()).thenReturn(flowOf(appointments))

            val result = getAllAppointmentsUseCase.execute().toList()

            // Then
            assertEquals(listOf(appointments), result)
        }
    }
}