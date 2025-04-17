package com.example.pharmacyapp.data.repositoryImp

import com.example.pharmacyapp.data.dao.OrderedMedicineDetailsDao
import com.example.pharmacyapp.data.model.OrderedMedicineDetails
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertThrows

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

// Using Mockk
class OrderedMedicineRepositoryImpTest {

    private lateinit var orderedMedicineDetailsDao: OrderedMedicineDetailsDao
    private lateinit var orderedMedicineRepositoryImp: OrderedMedicineRepositoryImp
    private lateinit var orderedMedicineDetails: OrderedMedicineDetails

    @Before
    fun setUp() {
        orderedMedicineDetailsDao = mockk()

        orderedMedicineRepositoryImp = OrderedMedicineRepositoryImp(orderedMedicineDetailsDao)

        orderedMedicineDetails = OrderedMedicineDetails(
            id = "1",
            medicineName = "Paracetamol",
            medicinePillsCount = "2",
            medicineCategory = "Painkiller",
            medicineDescription = "Used to treat mild to moderate pain and reduce fever",
            medicinePrice = "5",
            medicineManufacturer = "PharmaInc",
            selectedPharmacy = "Pharmacy City"
        )
    }

    @Test
    fun `test orderMedicines should call insertOrderMedicineDetails`() {
        runTest {
            // When
            coEvery { orderedMedicineDetailsDao.insertOrderedMedicineDetails(orderedMedicineDetails) } returns Unit

            orderedMedicineRepositoryImp.orderedMedicines(orderedMedicineDetails)

            // Then
            coVerify { orderedMedicineDetailsDao.insertOrderedMedicineDetails(orderedMedicineDetails) }
        }
    }

    @Test
    fun `test orderedMedicines should throw an exception when insertOrderMedicineDetails fails`() {
        runTest {
            // When
            coEvery { orderedMedicineDetailsDao.insertOrderedMedicineDetails(any()) } throws Exception(
                "Error"
            )

            try {
                orderedMedicineRepositoryImp.orderedMedicines(orderedMedicineDetails)
                TestCase.fail("Expected RuntimeException but none was thrown")
            } catch (e: Exception) {
                assert(e.message == "Error")
            }

            // Then
            coVerify { orderedMedicineDetailsDao.insertOrderedMedicineDetails(orderedMedicineDetails) }
        }
    }
}


// Using Mockito

//class OrderedMedicineRepositoryImpTest {
//
//    @get:Rule
//    var mockitoRule = MockitoJUnit.rule()
//
//    @Mock
//    private lateinit var orderedMedicineDetailsDao: OrderedMedicineDetailsDao
//
//    private lateinit var orderedMedicineRepositoryImp: OrderedMedicineRepositoryImp
//
//    private lateinit var orderedMedicinesDetails: OrderedMedicineDetails
//
//    @Before
//    fun setUp() {
//        orderedMedicineRepositoryImp = OrderedMedicineRepositoryImp(orderedMedicineDetailsDao)
//
//        orderedMedicinesDetails = OrderedMedicineDetails(
//            id = "1",
//            medicineName = "Paracetamol",
//            medicinePillsCount = "2",
//            medicineCategory = "Painkiller",
//            medicineDescription = "Used to treat mild to moderate pain and reduce fever",
//            medicinePrice = "5",
//            medicineManufacturer = "PharmaInc",
//            selectedPharmacy = "Pharmacy City"
//        )
//    }
//
//    @Test
//    fun orderedMedicinesSuccessTest() {
//        runBlocking {
//
//            // When
//            whenever(orderedMedicineDetailsDao.insertOrderedMedicineDetails(orderedMedicinesDetails)).thenReturn(Unit)
//
//            orderedMedicineRepositoryImp.orderedMedicines(orderedMedicinesDetails)
//
//            // Then
//            verify(orderedMedicineDetailsDao).insertOrderedMedicineDetails(orderedMedicinesDetails)
//        }
//    }
//
//    @Test
//    fun orderedMedicinesFailTest() {
//        runBlocking {
//
//            // When
//            whenever(orderedMedicineDetailsDao.insertOrderedMedicineDetails(orderedMedicinesDetails)).thenThrow(RuntimeException("Database error"))
//            try {
//                orderedMedicineRepositoryImp.orderedMedicines(orderedMedicinesDetails)
//                TestCase.fail("Expected RuntimeException but none was thrown")
//            } catch (e: RuntimeException) {
//                assert(e.message?.contains("Database error") == true)
//            }
//
//            // Then
//            verify(orderedMedicineDetailsDao).insertOrderedMedicineDetails(orderedMedicinesDetails)
//        }
//    }
//}
