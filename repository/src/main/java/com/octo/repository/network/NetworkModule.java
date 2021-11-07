package com.octo.repository.network;

import okhttp3.HttpUrl;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class NetworkModule {
    public Retrofit getRetrofit(final HttpUrl baseUrl) {
        return new Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build();
    }
}
