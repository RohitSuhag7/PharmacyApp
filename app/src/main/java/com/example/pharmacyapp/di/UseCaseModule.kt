package com.example.pharmacyapp.di

import com.example.pharmacyapp.domain.repository.AppointmentsRepository
import com.example.pharmacyapp.domain.repository.AuthRepository
import com.example.pharmacyapp.domain.repository.ChatRepository
import com.example.pharmacyapp.domain.repository.OrderedMedicineRepository
import com.example.pharmacyapp.domain.repository.PharmacyRepository
import com.example.pharmacyapp.domain.usecases.AddAppointmentsUseCase
import com.example.pharmacyapp.domain.usecases.AuthUseCase
import com.example.pharmacyapp.domain.usecases.ChatUseCase
import com.example.pharmacyapp.domain.usecases.GetAllAppointmentsUseCase
import com.example.pharmacyapp.domain.usecases.InsertOrderedMedicineUseCase
import com.example.pharmacyapp.domain.usecases.PharmacyDoctorsUseCase
import com.example.pharmacyapp.domain.usecases.PharmacyMedicalReasonsUseCase
import com.example.pharmacyapp.domain.usecases.PharmacyMedicinesUseCase
import com.example.pharmacyapp.domain.usecases.PharmacyPharmaciesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideAddAppointmentsUseCase(repository: AppointmentsRepository): AddAppointmentsUseCase =
        AddAppointmentsUseCase(repository)

    @Provides
    @Singleton
    fun provideGetAppointmentsUseCase(repository: AppointmentsRepository): GetAllAppointmentsUseCase =
        GetAllAppointmentsUseCase(repository)

    @Provides
    @Singleton
    fun provideInsertOrderedMedicineUseCase(repository: OrderedMedicineRepository): InsertOrderedMedicineUseCase =
        InsertOrderedMedicineUseCase(repository)

    @Provides
    @Singleton
    fun providePharmacyDoctorsUseCase(repository: PharmacyRepository): PharmacyDoctorsUseCase =
        PharmacyDoctorsUseCase(repository)

    @Provides
    @Singleton
    fun providePharmacyPharmaciesUseCase(repository: PharmacyRepository): PharmacyPharmaciesUseCase =
        PharmacyPharmaciesUseCase(repository)

    @Provides
    @Singleton
    fun providePharmacyMedicinesUseCase(repository: PharmacyRepository): PharmacyMedicinesUseCase =
        PharmacyMedicinesUseCase(repository)

    @Provides
    @Singleton
    fun providePharmacyMedicalReasonsUseCase(repository: PharmacyRepository): PharmacyMedicalReasonsUseCase =
        PharmacyMedicalReasonsUseCase(repository)

    @Provides
    @Singleton
    fun provideAuthUseCase(repository: AuthRepository): AuthUseCase = AuthUseCase(repository)

    @Provides
    @Singleton
    fun provideChatUseCase(repository: ChatRepository): ChatUseCase = ChatUseCase(repository)
}
