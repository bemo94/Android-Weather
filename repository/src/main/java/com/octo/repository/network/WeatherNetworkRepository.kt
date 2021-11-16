package com.octo.repository.network

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.IOException

class WeatherNetworkRepository(private val retrofit: Retrofit) {

    companion object {
        private const val API_KEY = "a72ffd88bc68442d7d91981af6832ca1"
        private const val UNITS = "metric"
    }

    internal interface WeatherService {
        @GET("forecast")
        fun getCityWeeklyForecast(
            @Query("q") town: String,
            @Query("appId") apiKey: String,
            @Query("units") units: String
        ): Call<JsonCityWeeklyForecast>
    }

    fun loadCityWeeklyForecast(town: String): CityWeeklyForecast? {
        try {
            val response = retrofit.create(WeatherService::class.java)
                .getCityWeeklyForecast(town, API_KEY, UNITS).execute().body()
            val transformer = ForecastTransformer()
            response?.let {
                return transformer.toCityWeeklyForecast(it)
            }
        } catch (e: IOException) {
            System.err.print(e)
        }
        return null
    }
}