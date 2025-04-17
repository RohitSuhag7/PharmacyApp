package com.example.pharmacyapp.presentation.viewmodel

import com.example.pharmacyapp.data.model.Appointments
import com.example.pharmacyapp.domain.usecases.AddAppointmentsUseCase
import com.example.pharmacyapp.domain.usecases.DeleteAppointmentsUseCase
import com.example.pharmacyapp.domain.usecases.GetAllAppointmentsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class AppointmentsViewModelTest {

    @get:Rule
    var mockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var addAppointmentsUseCase: AddAppointmentsUseCase

    @Mock
    private lateinit var getAllAppointmentsUseCase: GetAllAppointmentsUseCase

    private lateinit var deleteAppointmentsUseCase: DeleteAppointmentsUseCase

    private lateinit var appointmentsViewModel: AppointmentsViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        appointmentsViewModel = AppointmentsViewModel(addAppointmentsUseCase, getAllAppointmentsUseCase, deleteAppointmentsUseCase)

        Dispatchers.setMain(testDispatcher)
    }

//    @Test
//    fun addAppointmentsSuccessTest() {
//        runTest {
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
//            advanceUntilIdle()
//
//            whenever(addAppointmentsUseCase.execute(appointments)).thenReturn(Unit)
//            appointmentsViewModel.addAppointments(appointments)
//
//            // Then
//            verify(addAppointmentsUseCase, times(1)).execute(appointments)
//        }
//    }

    @Test
    fun addAppointmentsFailTest() {
        runTest {
            // Given
            val appointments = Appointments(
                id = 1,
                date = "11 March, 2025",
                time = "02:30",
                reason = "Fever",
                doctor = "Amilia",
                status = "Confirmed"
            )

            // When
            whenever(addAppointmentsUseCase.execute(appointments)).thenThrow(RuntimeException("Some error"))
            appointmentsViewModel.addAppointments(appointments)

            advanceUntilIdle()

            // Then
            verify(addAppointmentsUseCase, times(1)).execute(appointments)
        }
    }

    @Test
    fun getAppointmentsSuccessTest() {
        runTest {
            // Given
            val appointments = listOf(Appointments(
                id = 1,
                date = "11 March, 2025",
                time = "02:30",
                reason = "Fever",
                doctor = "Amilia",
                status = "Confirmed"
            ))

            // When
            advanceUntilIdle()

            whenever(getAllAppointmentsUseCase.execute()).thenReturn(flowOf(appointments))

            appointmentsViewModel.getAllAppointments()

            // Then
//            assertEquals(appointments, appointmentsViewModel.appointments.first())
            verify(getAllAppointmentsUseCase, times(1)).execute()
        }
    }

    @Test
    fun getAppointmentsWithEmptyListTest() {
        runTest {
            // Given
            val appointments = emptyList<Appointments>()

            // When
            advanceUntilIdle()

            whenever(getAllAppointmentsUseCase.execute()).thenReturn(flowOf(appointments))

            appointmentsViewModel.getAllAppointments()

            // Then
//            assertTrue(appointmentsViewModel.appointments.first().isEmpty())
            verify(getAllAppointmentsUseCase, times(1)).execute()
        }
    }
}
