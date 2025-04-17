package com.example.pharmacyapp.domain.usecases

import com.example.pharmacyapp.data.model.OrderedMedicineDetails
import com.example.pharmacyapp.domain.repository.OrderedMedicineRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
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

// Using Mockk

class InsertOrderedMedicineUseCaseTest {

    private lateinit var orderedMedicineRepository: OrderedMedicineRepository
    private lateinit var insertOrderedMedicineUseCase: InsertOrderedMedicineUseCase

    @Before
    fun setUp() {
        orderedMedicineRepository = mockk()

        insertOrderedMedicineUseCase = InsertOrderedMedicineUseCase(orderedMedicineRepository)
    }

    @Test
    fun `test executeOrderMedicine should call OrderMedicineDetails`() {
        runTest {
            // Given
            val orderedMedicineDetails = OrderedMedicineDetails(
                id = "1",
                medicineName = "Paracetamol",
                medicinePillsCount = "2",
                medicineCategory = "Painkiller",
                medicineDescription = "Used to treat mild to moderate pain and reduce fever",
                medicinePrice = "5",
                medicineManufacturer = "PharmaInc",
                selectedPharmacy = "Pharmacy City"
            )

            // When
            coEvery { orderedMedicineRepository.orderedMedicines(orderedMedicineDetails) } returns Unit

            insertOrderedMedicineUseCase.executeOrderedMedicines(orderedMedicineDetails)

            // Then
            coVerify { orderedMedicineRepository.orderedMedicines(orderedMedicineDetails) }
        }
    }

    @Test
    fun `test executeOrderMedicine with empty OrderMedicineDetails`() {
        runTest {
            // Given
            val orderedMedicineDetails = OrderedMedicineDetails(
                id = "",
                medicineName = "",
                medicinePillsCount = "",
                medicineCategory = "",
                medicineDescription = "",
                medicinePrice = "",
                medicineManufacturer = "",
                selectedPharmacy = ""
            )

            // When
            coEvery { orderedMedicineRepository.orderedMedicines(any()) } returns Unit

            insertOrderedMedicineUseCase.executeOrderedMedicines(orderedMedicineDetails)

            // Then
            coVerify { orderedMedicineRepository.orderedMedicines(orderedMedicineDetails) }
        }
    }
}


// Using Mockito

//class InsertOrderedMedicineUseCaseTest {
//
//    @get:Rule
//    var mockitoRule = MockitoJUnit.rule()
//
//    @Mock
//    lateinit var orderedMedicineRepository: OrderedMedicineRepository
//
//    private lateinit var insertOrderedMedicineUseCase: InsertOrderedMedicineUseCase
//
//    @Before
//    fun setUp() {
//        insertOrderedMedicineUseCase = InsertOrderedMedicineUseCase(orderedMedicineRepository)
//    }
//
//    @Test
//    fun executeOrderedMedicinesWithDataTest() {
//        runBlocking {
//            // Given
//            val orderedMedicineDetails = OrderedMedicineDetails(
//                id = "1",
//                medicineName = "Paracetamol",
//                medicinePillsCount = "2",
//                medicineCategory = "Painkiller",
//                medicineDescription = "Used to treat mild to moderate pain and reduce fever",
//                medicinePrice = "5",
//                medicineManufacturer = "PharmaInc",
//                selectedPharmacy = "Pharmacy City"
//            )
//
//            // When
//            whenever(orderedMedicineRepository.orderedMedicines(orderedMedicineDetails)).thenReturn(Unit)
//
//            insertOrderedMedicineUseCase.executeOrderedMedicines(orderedMedicineDetails)
//
//            // Then
//            verify(orderedMedicineRepository).orderedMedicines(orderedMedicineDetails)
//        }
//    }
//
//    @Test
//    fun executeOrderedMedicinesWithEmptyDataTest() {
//        runBlocking {
//            // Given
//            val orderedMedicineDetails = OrderedMedicineDetails(
//                id = "",
//                medicineName = "",
//                medicinePillsCount = "",
//                medicineCategory = "",
//                medicineDescription = "",
//                medicinePrice = "",
//                medicineManufacturer = "",
//                selectedPharmacy = ""
//            )
//
//            // When
//            whenever(orderedMedicineRepository.orderedMedicines(any())).thenReturn(Unit)
//            insertOrderedMedicineUseCase.executeOrderedMedicines(orderedMedicineDetails)
//
//            // Then
//            verify(orderedMedicineRepository).orderedMedicines(orderedMedicineDetails)
//        }
//    }
//
//    @Test
//    fun executeOrderedMedicinesWithNullDataTest() {
//        runBlocking {
//            // Given
//            val orderedMedicineDetails = OrderedMedicineDetails(
//                id = "1",
//                medicineName = null,
//                medicinePillsCount = null,
//                medicineCategory = null,
//                medicineDescription = null,
//                medicinePrice = null,
//                medicineManufacturer = null,
//                selectedPharmacy = null
//            )
//
//            // When
//            whenever(orderedMedicineRepository.orderedMedicines(orderedMedicineDetails)).thenReturn(null)
//            insertOrderedMedicineUseCase.executeOrderedMedicines(orderedMedicineDetails)
//
//            // Then
//            verify(orderedMedicineRepository).orderedMedicines(orderedMedicineDetails)
//        }
//    }
//}
