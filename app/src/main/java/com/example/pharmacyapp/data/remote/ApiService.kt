package com.example.pharmacyapp.data.remote

import com.example.pharmacyapp.data.model.PharmacyData
import retrofit2.http.GET

interface ApiService {

    @GET("7dac0757-c5b4-449d-9695-2da9b0d6fc77")                 //f473f7f5-6fd8-4179-aeb6-2e4c27addfe1, //ff7af803-aee5-4bbe-9cc4-1906bfc3177f
    suspend fun getPharmacyData(): PharmacyData
}
