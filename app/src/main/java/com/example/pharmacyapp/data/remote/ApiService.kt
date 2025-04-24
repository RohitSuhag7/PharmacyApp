package com.example.pharmacyapp.data.remote

import com.example.pharmacyapp.data.model.PharmacyData
import retrofit2.http.GET

interface ApiService {

    @GET("8899b7e4-4cef-459f-a2d2-19ed2fe2c9c2")
    suspend fun getPharmacyData(): PharmacyData
}
