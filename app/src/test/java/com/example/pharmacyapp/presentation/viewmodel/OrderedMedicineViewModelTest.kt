package com.example.pharmacyapp.presentation.viewmodel

import com.example.pharmacyapp.data.model.OrderedMedicineDetails
import com.example.pharmacyapp.domain.usecases.InsertOrderedMedicineUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.junit.MockitoJUnit

// Using Mockk

@OptIn(ExperimentalCoroutinesApi::class)
class OrderedMedicineViewModelTest {

    @get:Rule
    var mockitoRule = MockitoJUnit.rule()

    private lateinit var orderedMedicineUseCase: InsertOrderedMedicineUseCase
    private lateinit var orderedMedicineViewModel: OrderedMedicineViewModel

    @Before
    fun setUp() {
        orderedMedicineUseCase = mockk()

        orderedMedicineViewModel = OrderedMedicineViewModel(orderedMedicineUseCase)
    }

    @Test
    fun `test insertOrderedMedicine should call orderedMedicineDetails`() {
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
            coEvery { orderedMedicineUseCase.executeOrderedMedicines(orderedMedicineDetails) } returns Unit

            orderedMedicineViewModel.insertOrderedMedicine(orderedMedicineDetails)

            // Then
            coVerify { orderedMedicineUseCase.executeOrderedMedicines(orderedMedicineDetails) }
        }
    }

    @Test
    fun `test insertOrderedMedicine should handle exception`() {
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
            coEvery { orderedMedicineUseCase.executeOrderedMedicines(any()) } throws Exception("insert error")
            orderedMedicineViewModel.insertOrderedMedicine(orderedMedicineDetails)

            advanceUntilIdle()

            // Then
            coVerify { orderedMedicineUseCase.executeOrderedMedicines(orderedMedicineDetails) }
        }
    }
}


        // Using Mockito

//@OptIn(ExperimentalCoroutinesApi::class)
//class OrderedMedicineViewModelTest {
//
//    @get:Rule
//    var mockitoRule = MockitoJUnit.rule()
//
//    @Mock
//    private lateinit var orderedMedicineUseCase: InsertOrderedMedicineUseCase
//
//    private lateinit var orderedMedicineViewModel: OrderedMedicineViewModel
//
//    @Before
//    fun setUp() {
//        orderedMedicineViewModel = OrderedMedicineViewModel(orderedMedicineUseCase)
//    }
//
//    @Test
//    fun insertOrderedMedicineSuccessTest() {
//        runTest {
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
//            whenever(orderedMedicineUseCase.executeOrderedMedicines(orderedMedicineDetails)).thenReturn(Unit)
//            orderedMedicineViewModel.insertOrderedMedicine(orderedMedicineDetails)
//
//            advanceUntilIdle()
//
//            // Then
//            verify(orderedMedicineUseCase, times(1)).executeOrderedMedicines(orderedMedicineDetails)
//        }
//    }
//
//    @Test
//    fun insertOrderedMedicineFailTest() {
//        runTest {
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
//            whenever(orderedMedicineUseCase.executeOrderedMedicines(orderedMedicineDetails)).thenThrow(RuntimeException("Some error"))
//            orderedMedicineViewModel.insertOrderedMedicine(orderedMedicineDetails)
//
//            advanceUntilIdle()
//
//            // Then
//            verify(orderedMedicineUseCase, times(1)).executeOrderedMedicines(orderedMedicineDetails)
//        }
//    }
//}
