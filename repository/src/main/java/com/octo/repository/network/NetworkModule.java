package com.octo.repository.network;

import okhttp3.HttpUrl;
import retrofit2.Retrofit;

public class NetworkModule {
    Retrofit getRetrofit(final HttpUrl baseUrl) {
        return new Retrofit.Builder()
            .baseUrl(baseUrl)
            .build();
    }
}
