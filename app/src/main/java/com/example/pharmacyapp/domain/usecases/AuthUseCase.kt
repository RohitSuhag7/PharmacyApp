package com.example.pharmacyapp.domain.usecases

import android.content.Context
import com.example.pharmacyapp.data.model.UserDetails
import com.example.pharmacyapp.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class AuthUseCase @Inject constructor(private val authRepository: AuthRepository) {

    val currentUser: FirebaseUser?
        get() = authRepository.currentUser

    suspend fun invokeSignUpWithEmail(
        context: Context,
        email: String,
        password: String,
        userDetails: UserDetails
    ): FirebaseUser? {
        return authRepository.signUp(context, email, password, userDetails)
    }

    suspend fun invokeLoginWithEmail(
        email: String,
        password: String
    ): FirebaseUser? {
        return authRepository.signIn(email, password)
    }

    fun invokeSignOut() {
        authRepository.signOut()
    }

    suspend fun invokeGetUserDetails(): UserDetails? {
        return authRepository.getUserDetails()
    }

    suspend fun invokeUpdateUserDetails(updatedData: Map<String, Any>) {
        authRepository.updateUserDetails(updatedData)
    }
}
