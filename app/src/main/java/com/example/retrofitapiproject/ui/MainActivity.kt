package com.example.retrofitapiproject.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofitapiproject.R
import com.example.retrofitapiproject.adapters.DataAdapters
import com.example.retrofitapiproject.repository.DataRepository
import com.example.retrofitapiproject.util.Resource
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: DataViewModel
    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = DataRepository()
        val viewModelProviderFactory = DataViewModelFactory(application, repository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(DataViewModel::class.java)
        val adapter = DataAdapters(listOf(),viewModel)
        rvDataList.layoutManager = LinearLayoutManager(this)
        rvDataList.adapter = adapter

        viewModel.memeData.observe(this, Observer {response->
            when(response){
                is Resource.Success -> {
                    hideProgessBar()
                    response.data?.let {dataResponse ->
                        adapter.memeData = dataResponse.data.memes
                        adapter.notifyDataSetChanged()
                    }
                }
                is Resource.Error -> {
                    hideProgessBar()
                    response.message?.let { message->
                        Log.e(TAG,"An error occured: $message")
                    }
                }
                is Resource.Loading -> {
                    showProgessBar()
                }
            }

        })
    }

    private fun hideProgessBar() {
        paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgessBar() {
        paginationProgressBar.visibility = View.VISIBLE
    }
}