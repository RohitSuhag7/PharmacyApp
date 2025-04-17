package com.example.pharmacyapp.data.repositoryImp

import com.example.pharmacyapp.data.dao.AppointmentsDao
import com.example.pharmacyapp.data.model.Appointments
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.fail
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

//          Using Mockk
class AppointmentsRepositoryImpTest {

    private lateinit var appointmentsDao: AppointmentsDao
    private lateinit var appointmentsRepositoryImp: AppointmentsRepositoryImp

    @Before
    fun setUp() {
        appointmentsDao = mockk()

        appointmentsRepositoryImp = AppointmentsRepositoryImp(appointmentsDao)
    }

//    @Test
//    fun `test insertAppointments calls dao insertAppointments`() {
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
//            coEvery { appointmentsDao.insertAppointments(appointments) } returns Unit
//            appointmentsRepositoryImp.insertAppointments(appointments)
//
//            // Then
//            coVerify { appointmentsDao.insertAppointments(appointments) }
//        }
//    }

    @Test
    fun `test getAllAppointments returns flow of appointments`() {
        runTest {
            // Given
            val appointmentsList = listOf(
                Appointments(
                    id = 1,
                    date = "11 March, 2025",
                    time = "02:30",
                    reason = "Fever",
                    doctor = "Amilia",
                    status = "Confirmed"
                )
            )
            coEvery { appointmentsDao.getAllAppointments() } returns flowOf(appointmentsList)

            // When
            val resultFlow = appointmentsRepositoryImp.getAllAppointments()

            // Collect the flow and verify the result
            resultFlow.collect { result ->
                // Then
                assertNotNull(result)
                assertEquals(appointmentsList, result)
            }

            // Verify that the dao method was called
            coVerify { appointmentsDao.getAllAppointments() }
        }
    }
}


//          Using Mockito

//class AppointmentsRepositoryImpTest {
//
//    @get:Rule
//    var mockitoRule = MockitoJUnit.rule()
//
//
//    @Mock
//    private lateinit var appointmentsDao: AppointmentsDao
//
//    private lateinit var appointmentsRepositoryImp: AppointmentsRepositoryImp
//
//    @Before
//    fun setUp() {
//        appointmentsRepositoryImp = AppointmentsRepositoryImp(appointmentsDao)
//    }
//
//    @Test
//    fun insertAppointmentsSuccessTest() {
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
//            whenever(appointmentsDao.insertAppointments(appointments)).thenReturn(Unit)
//            appointmentsRepositoryImp.insertAppointments(appointments)
//
//            // Then
//            verify(appointmentsDao).insertAppointments(appointments)
//        }
//    }
//
//    @Test
//    fun insertAppointmentsWithEmptyDataTest() {
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
//            whenever(appointmentsDao.insertAppointments(any())).thenReturn(Unit)
//            appointmentsRepositoryImp.insertAppointments(appointments)
//
//            // Then
//            verify(appointmentsDao).insertAppointments(appointments)
//        }
//    }
//
//    @Test
//    fun insertAppointmentsWithDatabaseFailTest() = runBlocking {
//        // Given
//        val appointments = Appointments(
//            id = 1,
//            date = "",
//            time = "",
//            reason = "",
//            doctor = "",
//            status = ""
//        )
//
//        // When
//        whenever(appointmentsDao.insertAppointments(any())).thenThrow(RuntimeException("Database error"))
//        try {
//            appointmentsRepositoryImp.insertAppointments(appointments)
//            fail("Expected RuntimeException but none was thrown")
//        } catch (e: RuntimeException) {
//            assert(e.message?.contains("Database error") == true)
//        }
//
//        // Then
//        verify(appointmentsDao).insertAppointments(appointments)
//
//    }
//
//    @Test
//    fun getAllAppointmentsTest() {
//        runBlocking {
//            // Given
//            val appointments = listOf(
//                Appointments(
//                    id = 1,
//                    date = "11 March, 2025",
//                    time = "02:30",
//                    reason = "Fever",
//                    doctor = "Amilia",
//                    status = "Confirmed"
//                )
//            )
//
//            // When
//            whenever(appointmentsDao.getAllAppointments()).thenReturn(flowOf(appointments))
//
//            val result = appointmentsRepositoryImp.getAllAppointments().toList()
//
//            // Then
//            Assert.assertEquals(listOf(appointments), result)
//            verify(appointmentsDao).getAllAppointments()
//        }
//    }
//}
