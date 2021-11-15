package com.octo.repository.network

import retrofit2.Retrofit
import com.google.gson.GsonBuilder
import okhttp3.HttpUrl
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Locale

class NetworkModule {

    companion object {
        private const val DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
    }

    fun getRetrofit(baseUrl: HttpUrl): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setDateFormat(DATE_FORMAT).create()))
        .build()

    fun getSimpleDateFormat() = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())

}