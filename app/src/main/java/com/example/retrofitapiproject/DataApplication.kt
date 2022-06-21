package com.example.retrofitapiproject

import android.app.Application
import com.example.retrofitapiproject.repository.DataRepository
import com.example.retrofitapiproject.ui.DataViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class DataApplication: Application(),KodeinAware{
    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@DataApplication))
        bind() from singleton { DataRepository() }

        bind() from provider {
            DataViewModelFactory(this@DataApplication,instance())
        }
    }
}