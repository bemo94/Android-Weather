package com.octo.androidweather

import com.octo.repository.network.NetworkModule
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.HttpUrl
import retrofit2.Retrofit

@Module
class MainActivityModule {

    @Provides
    fun provideRetrofit(): Retrofit = NetworkModule().getRetrofit(HttpUrl.get(BuildConfig.WEATHER_BASE_URL))

    @Provides
    fun provideDispatcher(): CoroutineDispatcher = Dispatchers.IO

}