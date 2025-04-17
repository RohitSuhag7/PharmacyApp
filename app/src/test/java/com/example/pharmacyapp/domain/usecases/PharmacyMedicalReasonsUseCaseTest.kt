package com.example.pharmacyapp.domain.usecases

import com.example.pharmacyapp.data.model.MedicalReason
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

class PharmacyMedicalReasonsUseCaseTest {

    @get:Rule
    var mockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var repository: PharmacyRepository

    private lateinit var medicalReasonsUseCase: PharmacyMedicalReasonsUseCase

    @Before
    fun setUp() {
        medicalReasonsUseCase = PharmacyMedicalReasonsUseCase(repository)
    }

    @Test
    fun executeMedicalReasonsSuccessTest() {
        runBlocking {
            // Given
            val medicalReason = MedicalReason(
                medicineId = "1",
                reason = "Fever"
            )

            // When
            whenever(repository.getMedicalReasonsData()).thenReturn(flowOf(listOf(medicalReason)))

            val flow = medicalReasonsUseCase.executeMedicalReasons()

            // Then
            flow.collect { medicalReasons ->
                assertEquals(listOf(medicalReason), medicalReasons)
            }
        }
    }

    @Test
    fun executeMedicalReasonsWithEmptyListTest() {
        runBlocking {
            // Given
            val medicalReason = emptyList<MedicalReason>()

            // When
            whenever(repository.getMedicalReasonsData()).thenReturn(flowOf(medicalReason))

            val flow = medicalReasonsUseCase.executeMedicalReasons()

            // Then
            flow.collect { medicalReasons ->
                assertEquals(medicalReason, medicalReasons)
            }
        }
    }
}
