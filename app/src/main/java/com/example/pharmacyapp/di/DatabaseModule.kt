package com.example.pharmacyapp.di

import android.content.Context
import androidx.room.Room
import com.example.pharmacyapp.data.database.LocalDatabase
import com.example.pharmacyapp.data.dao.AppointmentsDao
import com.example.pharmacyapp.data.dao.OrderedMedicineDetailsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideLocalDatabase(@ApplicationContext context: Context): LocalDatabase {
        return Room.databaseBuilder(
            context,
            LocalDatabase::class.java,
            "local_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideAppointmentsDao(localDatabase: LocalDatabase): AppointmentsDao =
        localDatabase.appointmentsDao()

    @Provides
    @Singleton
    fun provideOrderedMedicineDetailsDao(localDatabase: LocalDatabase): OrderedMedicineDetailsDao =
        localDatabase.orderedMedicineDetailsDao()
}
