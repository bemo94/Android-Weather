package com.octo.androidweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.octo.repository.network.NetworkModule
import com.octo.repository.network.WeatherNetworkRepository
import android.util.Log
import android.content.ContentValues
import android.view.View
import com.octo.androidweather.databinding.ActivityMainBinding
import com.octo.presentation.CityForecastView
import com.octo.presentation.ForecastViewModel
import kotlinx.coroutines.*
import okhttp3.HttpUrl

class MainActivity : AppCompatActivity(), CityForecastView {
    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val activity: View = binding!!.root
        setContentView(activity)
        loadForecast()
    }

    private fun loadForecast() {
        CoroutineScope(Dispatchers.IO).launch {
            CityForecastModule(this@MainActivity).controller.loadCityForecast("paris")
        }
    }

    override fun displayEmptyInput() {
        CoroutineScope(Dispatchers.Main).launch {

        }
    }

    override fun displayGenericException() {
        CoroutineScope(Dispatchers.Main).launch {

        }
    }

    override fun displayUnavailableForecasts() {
        CoroutineScope(Dispatchers.Main).launch {

        }
    }

    override fun displayViewModel(viewModel: ForecastViewModel) {
        CoroutineScope(Dispatchers.Main).launch {
            println("Test" + viewModel.city)
        }
    }
}