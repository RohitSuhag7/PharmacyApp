package com.example.pharmacyapp.domain.repository

import android.content.Context
import com.example.pharmacyapp.data.model.UserDetails
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {

    val currentUser: FirebaseUser?

    suspend fun signIn(email: String, password: String): FirebaseUser?

    suspend fun signUp(context: Context, email: String, password: String, userDetails: UserDetails): FirebaseUser?

    fun signOut()

    suspend fun getUserDetails(): UserDetails?

    suspend fun updateUserDetails(updatedData: Map<String, Any>)
}
