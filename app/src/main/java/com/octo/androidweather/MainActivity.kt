package com.octo.androidweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.octo.repository.network.NetworkModule
import com.octo.repository.network.WeatherNetworkRepository
import android.util.Log
import android.content.ContentValues
import android.view.View
import com.octo.androidweather.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import okhttp3.HttpUrl

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val activity: View = binding!!.root
        setContentView(activity)
        logForecast()
    }

    private fun logForecast() {
        val networkModule = NetworkModule()
        val baseUrl = HttpUrl.get(BuildConfig.WEATHER_BASE_URL)
        val retrofit = networkModule.getRetrofit(baseUrl)
        CoroutineScope(Dispatchers.IO).launch {
            val repository = WeatherNetworkRepository(retrofit)
            val forecast = repository.loadCityWeeklyForecast("Paris")
            Log.i(ContentValues.TAG, "Forecast: $forecast")
        }
    }
}