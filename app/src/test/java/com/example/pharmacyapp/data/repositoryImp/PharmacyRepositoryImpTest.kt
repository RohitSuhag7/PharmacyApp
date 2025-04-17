package com.example.pharmacyapp.data.repositoryImp

import com.example.pharmacyapp.data.model.Doctor
import com.example.pharmacyapp.data.model.MedicalReason
import com.example.pharmacyapp.data.model.Medicine
import com.example.pharmacyapp.data.model.PharmacyData
import com.example.pharmacyapp.data.model.User
import com.example.pharmacyapp.data.model.pharmacy.Address
import com.example.pharmacyapp.data.model.pharmacy.Contact
import com.example.pharmacyapp.data.model.pharmacy.Location
import com.example.pharmacyapp.data.model.pharmacy.Pharmacy
import com.example.pharmacyapp.data.remote.ApiService
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.kotlin.whenever

class PharmacyRepositoryImpTest {

    @get:Rule
    var mockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var apiService: ApiService

    private lateinit var pharmacyRepositoryImp: PharmacyRepositoryImp

    @Before
    fun setUp() {
        pharmacyRepositoryImp = PharmacyRepositoryImp(apiService)
    }

    @Test
    fun getDoctorsDataSuccessTest() {
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
            val mockPharmacyData = PharmacyData(
                user = User("","","","","","",""),
                doctors = listOf(doctors),
                medicines = emptyList(),
                pharmacies = emptyList(),
                medicalReasons = emptyList()
            )

            // When
            whenever(apiService.getPharmacyData()).thenReturn(mockPharmacyData)

            val result = pharmacyRepositoryImp.getDoctorsData().first()

            // Then
            assertEquals(result, listOf(doctors))
        }
    }

    @Test
    fun getDoctorsDataWithNoDoctorsTest() {
        runBlocking {
            // Given
            val mockPharmacyData = PharmacyData(
                user = User("","","","","","",""),
                doctors = emptyList(),
                medicines = emptyList(),
                pharmacies = emptyList(),
                medicalReasons = emptyList()
            )

            // When
            whenever(apiService.getPharmacyData()).thenReturn(mockPharmacyData)

            val result = pharmacyRepositoryImp.getDoctorsData().first()

            // Then
            assert(result.isEmpty())
        }
    }

    @Test
    fun getPharmaciesDataSuccessTest() {
        runBlocking {
            // Given
            val pharmacies = Pharmacy(
                id = "1",
                name = "ABC",
                address = Address(address = "ballabgarh", city = "ballabgarh", country = "India", state = "ballabgarh", zipCode = "124001"),
                contact = Contact(email = "abc@gmail.com", phone = "9876543210", website = "abc.com"),
                location = Location(latitude = 124.23, longitude = 123.24)
            )
            val mockPharmacyData = PharmacyData(
                user = User("","","","","","",""),
                doctors = emptyList(),
                medicines = emptyList(),
                pharmacies = listOf(pharmacies),
                medicalReasons = emptyList()
            )

            // When
            whenever(apiService.getPharmacyData()).thenReturn(mockPharmacyData)

            val result = pharmacyRepositoryImp.getPharmaciesData().first()

            // Then
            assertEquals(result, listOf(pharmacies))
        }
    }

    @Test
    fun getPharmaciesDataWithNoPharmaciesTest() {
        runBlocking {
            // Given
            val mockPharmacyData = PharmacyData(
                user = User("","","","","","",""),
                doctors = emptyList(),
                medicines = emptyList(),
                pharmacies = emptyList(),
                medicalReasons = emptyList()
            )

            // When
            whenever(apiService.getPharmacyData()).thenReturn(mockPharmacyData)

            val result = pharmacyRepositoryImp.getPharmaciesData().first()

            // Then
            assert(result.isEmpty())
        }
    }

    @Test
    fun getMedicineDataSuccessTest() {
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
            val mockPharmacyData = PharmacyData(
                user = User("","","","","","",""),
                doctors = emptyList(),
                medicines = listOf(medicine),
                pharmacies = emptyList(),
                medicalReasons = emptyList()
            )

            // When
            whenever(apiService.getPharmacyData()).thenReturn(mockPharmacyData)

            val result = pharmacyRepositoryImp.getMedicineData().first()

            // Then
            assertEquals(result, listOf(medicine))
        }
    }

    @Test
    fun getMedicineDataWithNoMedicineTest() {
        runBlocking {
            // Given
            val mockPharmacyData = PharmacyData(
                user = User("","","","","","",""),
                doctors = emptyList(),
                medicines = emptyList(),
                pharmacies = emptyList(),
                medicalReasons = emptyList()
            )

            // When
            whenever(apiService.getPharmacyData()).thenReturn(mockPharmacyData)

            val result = pharmacyRepositoryImp.getMedicineData().first()

            // Then
            assert(result.isEmpty())
        }
    }

    @Test
    fun getMedicalReasonsDataSuccessTest() {
        runBlocking {
            // Given
            val medicalReason = MedicalReason(
                medicineId = "1",
                reason = "Fever"
            )
            val mockPharmacyData = PharmacyData(
                user = User("","","","","","",""),
                doctors = emptyList(),
                medicines = emptyList(),
                pharmacies = emptyList(),
                medicalReasons = listOf(medicalReason)
            )

            // When
            whenever(apiService.getPharmacyData()).thenReturn(mockPharmacyData)

            val result = pharmacyRepositoryImp.getMedicalReasonsData().first()

            // Then
            assertEquals(result, listOf(medicalReason))
        }
    }

    @Test
    fun getMedicalReasonsDataWithNoMedicalReasonTest() {
        runBlocking {
            // Given
            val mockPharmacyData = PharmacyData(
                user = User("","","","","","",""),
                doctors = emptyList(),
                medicines = emptyList(),
                pharmacies = emptyList(),
                medicalReasons = emptyList()
            )

            // When
            whenever(apiService.getPharmacyData()).thenReturn(mockPharmacyData)

            val result = pharmacyRepositoryImp.getMedicalReasonsData().first()

            // Then
            assert(result.isEmpty())
        }
    }
}