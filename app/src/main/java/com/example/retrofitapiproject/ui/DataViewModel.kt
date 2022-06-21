package com.example.retrofitapiproject.ui

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitapiproject.DataApplication
import com.example.retrofitapiproject.model.ResponseObject
import com.example.retrofitapiproject.repository.DataRepository
import com.example.retrofitapiproject.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class DataViewModel(
    app: Application,
    val dataRepository: DataRepository
): AndroidViewModel(app)  {

    val memeData: MutableLiveData<Resource<ResponseObject>> = MutableLiveData()

    init {
        getmemeData()
    }

    fun getmemeData() = viewModelScope.launch {
        safeMemeDataCall()
    }

    private fun handleDataResponse(response: Response<ResponseObject>): Resource<ResponseObject>? {
        if (response.isSuccessful){
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private suspend fun safeMemeDataCall(){
        memeData.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()){
                val response = dataRepository.getMemes()
                memeData.postValue(handleDataResponse(response))
            }else{
                memeData.postValue(Resource.Error("No internet connection"))
            }
        }catch (t: Throwable){
            when(t){
                is IOException -> memeData.postValue(Resource.Error("Network Failure"))
                else -> memeData.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private fun hasInternetConnection(): Boolean{
        val connectivityManager = getApplication<DataApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when{
                capabilities.hasTransport(TRANSPORT_WIFI) -> true
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                else -> false
            }
        }else{
            connectivityManager.activeNetworkInfo?.run {
                return when(type){
                    TYPE_WIFI -> true
                    TYPE_MOBILE -> true
                    TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false

    }
}