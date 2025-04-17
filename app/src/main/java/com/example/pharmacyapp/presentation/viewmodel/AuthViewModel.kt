package com.example.pharmacyapp.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pharmacyapp.data.model.UserDetails
import com.example.pharmacyapp.domain.usecases.AuthUseCase
import com.example.pharmacyapp.presentation.screens.auth.FirebaseUiState
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
) : ViewModel() {

    private val _loginStateFlow = MutableStateFlow<FirebaseUser?>(null)
    val loginStateFlow: StateFlow<FirebaseUser?> = _loginStateFlow

    private val currentUser: FirebaseUser?
        get() = authUseCase.currentUser

    private val _userDetails =
        MutableStateFlow<FirebaseUiState<UserDetails>>(FirebaseUiState.Loading)
    val userDetails: StateFlow<FirebaseUiState<UserDetails>> = _userDetails

    init {
        if (authUseCase.currentUser != null) {
            _loginStateFlow.value = currentUser
        }
    }

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            val result = authUseCase.invokeLoginWithEmail(email, password)
            _loginStateFlow.value = result
        }
    }

    fun signUpUser(
        context: Context,
        email: String,
        password: String,
        userDetails: UserDetails,
        onSuccess: (FirebaseUser) -> Unit
    ) {
        viewModelScope.launch {
            val result = authUseCase.invokeSignUpWithEmail(context, email, password, userDetails)
            result?.let {
                onSuccess(it)
            }
        }
    }

    fun signOutUser() {
        viewModelScope.launch {
            authUseCase.invokeSignOut()
            _loginStateFlow.value = null
        }
    }

    fun getUserDetails() {
        viewModelScope.launch {
            val result = authUseCase.invokeGetUserDetails()
            _userDetails.value = if (result != null) {
                FirebaseUiState.Success(result)
            } else {
                FirebaseUiState.Error("Unable to fetch user details")
            }
        }
    }

    fun updateUserDetails(updatedData: Map<String, Any>) {
        viewModelScope.launch {
            authUseCase.invokeUpdateUserDetails(updatedData)

            // Refresh data
            getUserDetails()
        }
    }
}
