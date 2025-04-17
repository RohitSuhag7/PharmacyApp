package com.example.pharmacyapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pharmacyapp.data.model.Doctor
import com.example.pharmacyapp.data.model.MedicalReason
import com.example.pharmacyapp.data.model.Medicine
import com.example.pharmacyapp.data.model.pharmacy.Pharmacy
import com.example.pharmacyapp.domain.usecases.PharmacyDoctorsUseCase
import com.example.pharmacyapp.domain.usecases.PharmacyMedicalReasonsUseCase
import com.example.pharmacyapp.domain.usecases.PharmacyMedicinesUseCase
import com.example.pharmacyapp.domain.usecases.PharmacyPharmaciesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PharmacyViewModel @Inject constructor(
    private val pharmacyDoctorsUseCase: PharmacyDoctorsUseCase,
    private val pharmacyPharmaciesUseCase: PharmacyPharmaciesUseCase,
    private val pharmacyMedicinesUseCase: PharmacyMedicinesUseCase,
    private val pharmacyMedicalReasonsUseCase: PharmacyMedicalReasonsUseCase
) : ViewModel() {

    private val _doctorsData = MutableStateFlow<List<Doctor>>(emptyList())
    val doctorsData: StateFlow<List<Doctor>> = _doctorsData

    private val _pharmaciesData = MutableStateFlow<List<Pharmacy>>(emptyList())
    val pharmaciesData: StateFlow<List<Pharmacy>> = _pharmaciesData

    private val _medicinesData = MutableStateFlow<List<Medicine>>(emptyList())
    val medicinesData: StateFlow<List<Medicine>> = _medicinesData

    private val _medicalReasons = MutableStateFlow<List<MedicalReason>>(emptyList())
    val medicalReasons: StateFlow<List<MedicalReason>> = _medicalReasons

    fun fetchDoctors(searchQuery: String) {
        viewModelScope.launch(Dispatchers.IO) {
            pharmacyDoctorsUseCase.executeDoctorsData(searchQuery).collect {
                _doctorsData.value = it
            }
        }
    }

    fun fetchPharmacies() {
        viewModelScope.launch(Dispatchers.IO) {
            pharmacyPharmaciesUseCase.executePharmaciesData().collect {
                _pharmaciesData.value = it
            }
        }
    }

    fun fetchMedicines(searchQuery: String) {
        viewModelScope.launch(Dispatchers.IO) {
            pharmacyMedicinesUseCase.executeMedicinesData(searchQuery).collect {
                _medicinesData.value = it
            }
        }
    }

    fun fetchMedicalReasons() {
        viewModelScope.launch(Dispatchers.IO) {
            pharmacyMedicalReasonsUseCase.executeMedicalReasons().collect {
                _medicalReasons.value = it
            }
        }
    }
}
