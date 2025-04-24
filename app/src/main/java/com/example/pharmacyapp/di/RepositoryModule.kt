package com.example.pharmacyapp.di

import android.content.Context
import com.example.pharmacyapp.data.dao.AppointmentsDao
import com.example.pharmacyapp.data.dao.OrderedMedicineDetailsDao
import com.example.pharmacyapp.data.repositoryImp.AppointmentsRepositoryImp
import com.example.pharmacyapp.data.repositoryImp.AuthRepositoryImp
import com.example.pharmacyapp.utils.DataStorePreference
import com.example.pharmacyapp.data.repositoryImp.OrderedMedicineRepositoryImp
import com.example.pharmacyapp.data.repositoryImp.PharmacyRepositoryImp
import com.example.pharmacyapp.data.remote.ApiService
import com.example.pharmacyapp.data.repositoryImp.ChatRepositoryImp
import com.example.pharmacyapp.domain.repository.AppointmentsRepository
import com.example.pharmacyapp.domain.repository.AuthRepository
import com.example.pharmacyapp.domain.repository.ChatRepository
import com.example.pharmacyapp.domain.repository.OrderedMedicineRepository
import com.example.pharmacyapp.domain.repository.PharmacyRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAppointmentsRepository(appointmentsDao: AppointmentsDao): AppointmentsRepository =
        AppointmentsRepositoryImp(appointmentsDao)

    @Provides
    @Singleton
    fun provideOrderedMedicineRepository(orderedMedicineDetailsDao: OrderedMedicineDetailsDao): OrderedMedicineRepository =
        OrderedMedicineRepositoryImp(orderedMedicineDetailsDao)

    @Provides
    @Singleton
    fun providePharmacyRepository(apiService: ApiService): PharmacyRepository =
        PharmacyRepositoryImp(apiService)

    @Provides
    @Singleton
    fun provideDataStorePreference(@ApplicationContext context: Context): DataStorePreference =
        DataStorePreference(context)

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        firebaseAuth: FirebaseAuth,
        firestore: FirebaseFirestore
    ): AuthRepository = AuthRepositoryImp(firebaseAuth, firestore)

    @Provides
    @Singleton
    fun provideChatRepository(
        firebaseAuth: FirebaseAuth,
        firestore: FirebaseFirestore
    ): ChatRepository = ChatRepositoryImp(firebaseAuth, firestore)
}
