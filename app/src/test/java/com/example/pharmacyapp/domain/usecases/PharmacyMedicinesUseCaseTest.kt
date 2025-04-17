package com.example.pharmacyapp.domain.usecases

import com.example.pharmacyapp.data.model.Medicine
import com.example.pharmacyapp.domain.repository.PharmacyRepository
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.kotlin.whenever

class PharmacyMedicinesUseCaseTest {

    @get:Rule
    var mockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var repository: PharmacyRepository

    private lateinit var medicinesUseCase: PharmacyMedicinesUseCase

    @Before
    fun setUp() {
        medicinesUseCase = PharmacyMedicinesUseCase(repository)
    }

    @Test
    fun executeMedicinesDataWithSuccessTest() {
        runBlocking {
            // Given
            val medicine = Medicine(
                id = "1",
                name = "Paracetamol",
                category = "Painkiller",
                description = "for pain",
                manufacturer = "ABC",
                prescriptionRequired = true,
                price = 24.24,
                stockQuantity = 5
            )

            // When
            whenever(repository.getMedicineData()).thenReturn(flowOf(listOf(medicine)))

            val flow = medicinesUseCase.executeMedicinesData("")

            // Then
            flow.collect { medicines ->
                assertEquals(listOf(medicine), medicines)
            }
        }
    }

    @Test
    fun executeMedicinesDataWithEmptyListTest() {
        runBlocking {
            // Given
            val medicine = emptyList<Medicine>()

            // When
            whenever(repository.getMedicineData()).thenReturn(flowOf(medicine))

            val flow = medicinesUseCase.executeMedicinesData("")

            // Then
            flow.collect { medicines ->
                assertEquals(medicine, medicines)
            }
        }
    }
}
