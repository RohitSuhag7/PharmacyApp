package com.example.pharmacyapp.presentation.screens.auth

sealed class FirebaseUiState<out T> {
    data object Loading : FirebaseUiState<Nothing>()
    data class Success<T>(val data: T) : FirebaseUiState<T>()
    data class Error(val message: String) : FirebaseUiState<Nothing>()
}
