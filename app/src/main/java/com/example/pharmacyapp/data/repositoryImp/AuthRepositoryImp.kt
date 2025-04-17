package com.example.pharmacyapp.data.repositoryImp

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.example.pharmacyapp.data.model.UserDetails
import com.example.pharmacyapp.domain.repository.AuthRepository
import com.example.pharmacyapp.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class AuthRepositoryImp @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepository {

    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override suspend fun signIn(email: String, password: String): FirebaseUser? {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            result.user
        } catch (e: Exception) {
            Log.e("AuthRepository", "Sign-in failed: ${e.message}")
            null
        }
    }

    override suspend fun signUp(
        context: Context,
        email: String,
        password: String,
        userDetails: UserDetails
    ): FirebaseUser? {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            result.user?.updateProfile(
                UserProfileChangeRequest.Builder().setDisplayName(userDetails.name).build()
            )?.await()

            val userData = userDetails.copy(id = result.user?.uid)

            result.user?.uid?.let { uid ->
                firestore.collection(Constants.FIRESTORE_COLLECTION)
                    .document(uid)
                    .set(userData)
                    .await()
            }
            result.user
        } catch (e: Exception) {
            Log.e("AuthRepository", "Sign-up failed: ${e.message}")
            Toast.makeText(context, "SingUp Failed: ${e.message}", Toast.LENGTH_LONG).show()
            null
        }
    }

    override fun signOut() {
        firebaseAuth.signOut()
    }

    override suspend fun getUserDetails(): UserDetails? {
        val uid = currentUser?.uid ?: return null
        return try {
            val snapshot = firestore.collection(Constants.FIRESTORE_COLLECTION)
                .document(uid)
                .get()
                .await()

            snapshot.toObject(UserDetails::class.java)
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun updateUserDetails(updatedData: Map<String, Any>) {
        val uid = currentUser?.uid ?: ""
        firestore.collection(Constants.FIRESTORE_COLLECTION)
            .document(uid)
            .update(updatedData)
            .await()
    }
}
