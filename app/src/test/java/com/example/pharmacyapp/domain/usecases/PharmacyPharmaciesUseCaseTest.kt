package com.example.pharmacyapp.domain.usecases

import com.example.pharmacyapp.data.model.pharmacy.Address
import com.example.pharmacyapp.data.model.pharmacy.Contact
import com.example.pharmacyapp.data.model.pharmacy.Location
import com.example.pharmacyapp.data.model.pharmacy.Pharmacy
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

class PharmacyPharmaciesUseCaseTest {

    @get:Rule
    var mockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var repository: PharmacyRepository

    private lateinit var pharmaciesUseCase: PharmacyPharmaciesUseCase

    @Before
    fun setUp() {
        pharmaciesUseCase = PharmacyPharmaciesUseCase(repository)
    }

    @Test
    fun executePharmaciesDataWithSuccessTest() {
        runBlocking {
            // Given
            val pharmaciesData = Pharmacy(
                id = "1",
                name = "ABC",
                address = Address(
                    address = "ballabgarh",
                    city = "ballabgarh",
                    country = "India",
                    state = "ballabgarh",
                    zipCode = "124001"
                ),
                contact = Contact(
                    email = "abc@gmail.com",
                    phone = "9876543210",
                    website = "abc.com"
                ),
                location = Location(latitude = 124.23, longitude = 123.24)
            )

            // When
            whenever(repository.getPharmaciesData()).thenReturn(flowOf(listOf(pharmaciesData)))

            val flow = pharmaciesUseCase.executePharmaciesData()

            // Then
            flow.collect { pharmacies ->
                assertEquals(listOf(pharmaciesData), pharmacies)
            }
        }
    }

    @Test
    fun executePharmaciesDataWithEmptyListTest() {
        runBlocking {
            // Given
            val pharmaciesData = emptyList<Pharmacy>()

            // When
            whenever(repository.getPharmaciesData()).thenReturn(flowOf(pharmaciesData))

            val flow = pharmaciesUseCase.executePharmaciesData()

            // Then
            flow.collect { pharmacies ->
                assertEquals(pharmaciesData, pharmacies)
            }
        }
    }
}