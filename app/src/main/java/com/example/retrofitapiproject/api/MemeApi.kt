package com.example.retrofitapiproject.api

import com.example.retrofitapiproject.model.ResponseObject
import retrofit2.Response
import retrofit2.http.GET

interface MemeApi {
    @GET("get_memes")
    suspend fun getMemes():Response<ResponseObject>
}