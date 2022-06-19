package com.example.retrofitapiproject.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitapiproject.repository.DataRepository

class DataViewModelFactory(
    val app: Application,
    private val repository: DataRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DataViewModel(app, repository) as T
    }

}