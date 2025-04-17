package com.example.pharmacyapp.presentation.viewmodel

import com.example.pharmacyapp.data.model.Doctor
import com.example.pharmacyapp.data.model.MedicalReason
import com.example.pharmacyapp.data.model.Medicine
import com.example.pharmacyapp.data.model.pharmacy.Address
import com.example.pharmacyapp.data.model.pharmacy.Contact
import com.example.pharmacyapp.data.model.pharmacy.Location
import com.example.pharmacyapp.data.model.pharmacy.Pharmacy
import com.example.pharmacyapp.domain.usecases.PharmacyDoctorsUseCase
import com.example.pharmacyapp.domain.usecases.PharmacyMedicalReasonsUseCase
import com.example.pharmacyapp.domain.usecases.PharmacyMedicinesUseCase
import com.example.pharmacyapp.domain.usecases.PharmacyPharmaciesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class PharmacyViewModelTest {

    @get:Rule
    var mockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var pharmacyDoctorsUseCase: PharmacyDoctorsUseCase

    @Mock
    private lateinit var pharmacyPharmaciesUseCase: PharmacyPharmaciesUseCase

    @Mock
    private lateinit var pharmacyMedicinesUseCase: PharmacyMedicinesUseCase

    @Mock
    private lateinit var pharmacyMedicalReasonsUseCase: PharmacyMedicalReasonsUseCase


    private lateinit var pharmacyViewModel: PharmacyViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        pharmacyViewModel = PharmacyViewModel(
            pharmacyDoctorsUseCase,
            pharmacyPharmaciesUseCase,
            pharmacyMedicinesUseCase,
            pharmacyMedicalReasonsUseCase
        )

        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun fetchDoctorsSuccessTest() {
        runTest {
            // Given
            val doctors = listOf(
                Doctor(
                    id = "1",
                    email = "doctor@email.com",
                    firstName = "doctor",
                    lastName = "graduated",
                    phone = "8767876767",
                    profilePicture = "url.com",
                    specialization = "eye doctor"
                )
            )

            // When
            whenever(pharmacyDoctorsUseCase.executeDoctorsData("")).thenReturn(flowOf(doctors))

            pharmacyViewModel.fetchDoctors("")

            advanceUntilIdle()

            // Then
            verify(pharmacyDoctorsUseCase, times(1)).executeDoctorsData("")
        }
    }

    @Test
    fun fetchDoctorsWithEmptyListTest() {
        runTest {
            // Given
            val doctorList = emptyList<Doctor>()

            // When
            whenever(pharmacyDoctorsUseCase.executeDoctorsData("")).thenReturn(flowOf(doctorList))
            pharmacyViewModel.fetchDoctors("")

            advanceUntilIdle()

            // Then
            assertTrue(pharmacyViewModel.doctorsData.first().isEmpty())
            verify(pharmacyDoctorsUseCase, times(1)).executeDoctorsData("")
        }
    }

    @Test
    fun fetchPharmaciesSuccessTest() {
        runTest {
            // Given
            val pharmaciesList = listOf(
                Pharmacy(
                    id = "1",
                    name = "ABC",
                    address = Address(address = "ballabgarh", city = "ballabgarh", country = "India", state = "ballabgarh", zipCode = "124001"),
                    contact = Contact(email = "abc@gmail.com", phone = "9876543210", website = "abc.com"),
                    location = Location(latitude = 124.23, longitude = 123.24)
                )
            )

            // When
            whenever(pharmacyPharmaciesUseCase.executePharmaciesData()).thenReturn(flowOf(pharmaciesList))

            pharmacyViewModel.fetchPharmacies()

            advanceUntilIdle()

            // Then
            verify(pharmacyPharmaciesUseCase, times(1)).executePharmaciesData()
        }
    }

    @Test
    fun fetchPharmaciesWithEmptyListTest() {
        runTest {
            // Given
            val pharmaciesList = emptyList<Pharmacy>()

            // When
            whenever(pharmacyPharmaciesUseCase.executePharmaciesData()).thenReturn(flowOf(pharmaciesList))

            pharmacyViewModel.fetchPharmacies()

            advanceUntilIdle()

            // Then
            assertTrue(pharmacyViewModel.pharmaciesData.first().isEmpty())
            verify(pharmacyPharmaciesUseCase, times(1)).executePharmaciesData()
        }
    }

    @Test
    fun fetchMedicinesSuccessTest() {
        runTest {
            // Given
            val medicine = listOf(
                Medicine(
                    id = "1",
                    name = "Paracetamol",
                    category = "Painkiller",
                    description = "for pain",
                    manufacturer = "ABC",
                    prescriptionRequired = true,
                    price = 24.24,
                    stockQuantity = 5
                )
            )

            // When
            whenever(pharmacyMedicinesUseCase.executeMedicinesData("")).thenReturn(flowOf(medicine))

            pharmacyViewModel.fetchMedicines("")

            advanceUntilIdle()

            // Then
            verify(pharmacyMedicinesUseCase, times(1)).executeMedicinesData("")
        }
    }

    @Test
    fun fetchMedicinesWithEmptyListTest() {
        runTest {
            // Given
            val medicineList = emptyList<Medicine>()

            // When
            whenever(pharmacyMedicinesUseCase.executeMedicinesData("")).thenReturn(flowOf(medicineList))

            pharmacyViewModel.fetchMedicines("")

            advanceUntilIdle()

            // Then
            assertTrue(pharmacyViewModel.medicinesData.first().isEmpty())
            verify(pharmacyMedicinesUseCase, times(1)).executeMedicinesData("")
        }
    }

    @Test
    fun fetchMedicalReasonsSuccessTest() {
        runTest {
            // Given
            val medicalReason = listOf(
                MedicalReason(
                    medicineId = "1",
                    reason = "Fever"
                )
            )

            // When
            whenever(pharmacyMedicalReasonsUseCase.executeMedicalReasons()).thenReturn(flowOf(medicalReason))

            pharmacyViewModel.fetchMedicalReasons()

            advanceUntilIdle()

            // Then
            verify(pharmacyMedicalReasonsUseCase, times(1)).executeMedicalReasons()
        }
    }

    @Test
    fun fetchMedicalReasonsWithEmptyListTest() {
        runTest {
            // Given
            val medicalReason = emptyList<MedicalReason>()

            // When
            whenever(pharmacyMedicalReasonsUseCase.executeMedicalReasons()).thenReturn(flowOf(medicalReason))

            pharmacyViewModel.fetchMedicalReasons()

            advanceUntilIdle()

            // Then
            assertTrue(pharmacyViewModel.medicalReasons.first().isEmpty())
            verify(pharmacyMedicalReasonsUseCase, times(1)).executeMedicalReasons()
        }
    }
}
