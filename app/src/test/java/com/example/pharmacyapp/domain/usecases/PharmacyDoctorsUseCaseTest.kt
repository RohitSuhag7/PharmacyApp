package com.example.pharmacyapp.domain.usecases

import com.example.pharmacyapp.data.model.Doctor
import com.example.pharmacyapp.domain.repository.PharmacyRepository
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.kotlin.whenever

class PharmacyDoctorsUseCaseTest {

    @get:Rule
    var mockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var repository: PharmacyRepository

    private lateinit var doctorsUseCase: PharmacyDoctorsUseCase

    @Before
    fun setUp() {
        doctorsUseCase = PharmacyDoctorsUseCase(repository)
    }

    @Test
    fun executeDoctorsDataSuccessTest() {
        runBlocking {
            // Given
            val doctors = Doctor(
                id = "1",
                email = "doctor@email.com",
                firstName = "doctor",
                lastName = "graduated",
                phone = "8767876767",
                profilePicture = "url.com",
                specialization = "eye doctor"
            )

            // When
            whenever(repository.getDoctorsData()).thenReturn(flowOf(listOf(doctors)))

            val result = doctorsUseCase.executeDoctorsData("")

            // Then
            result.collect { doctor ->
                Assert.assertEquals(listOf(doctors), doctor)
            }
        }
    }

    @Test
    fun executeDoctorsDataWithEmptyListTest() {
        runBlocking {
            // Given
            val doctorsList = emptyList<Doctor>()

            // When
            whenever(repository.getDoctorsData()).thenReturn(flowOf(doctorsList))

            val result = doctorsUseCase.executeDoctorsData("")

            // Then
            result.collect { doctor ->
                Assert.assertEquals(doctorsList, doctor)
            }
        }
    }
}
