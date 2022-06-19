package com.example.retrofitapiproject.repository

import com.example.retrofitapiproject.api.RetrofitInstance

class DataRepository() {
    suspend fun getMemes() = RetrofitInstance.api.getMemes()
}