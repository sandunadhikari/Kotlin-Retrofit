package com.example.retrofitapiproject.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitapiproject.model.ResponseObject
import com.example.retrofitapiproject.repository.DataRepository
import com.example.retrofitapiproject.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class DataViewModel(
    app: Application,
    val dataRepository: DataRepository
): AndroidViewModel(app)  {

    val memeData: MutableLiveData<Resource<ResponseObject>> = MutableLiveData()

    init {
        getmemeData()
    }

    fun getmemeData() = viewModelScope.launch {
        memeData.postValue(Resource.Loading())
        val response = dataRepository.getMemes()
        memeData.postValue(handleDataResponse(response))
    }

    private fun handleDataResponse(response: Response<ResponseObject>): Resource<ResponseObject>? {
        if (response.isSuccessful){
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}