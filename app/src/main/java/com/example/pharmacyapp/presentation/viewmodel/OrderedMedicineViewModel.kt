package com.example.pharmacyapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pharmacyapp.data.model.OrderedMedicineDetails
import com.example.pharmacyapp.domain.usecases.InsertOrderedMedicineUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderedMedicineViewModel @Inject constructor(private val orderedMedicineUseCase: InsertOrderedMedicineUseCase) :
    ViewModel() {

    fun insertOrderedMedicine(orderedMedicineDetails: OrderedMedicineDetails) {
        viewModelScope.launch {
            try {
                orderedMedicineUseCase.executeOrderedMedicines(orderedMedicineDetails = orderedMedicineDetails)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
