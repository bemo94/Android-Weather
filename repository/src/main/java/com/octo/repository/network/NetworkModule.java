package com.octo.repository.network;

import java.text.SimpleDateFormat;
import java.util.Locale;

import okhttp3.HttpUrl;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class NetworkModule {
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public Retrofit getRetrofit(final HttpUrl baseUrl) {
        return new Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build();
    }

    public SimpleDateFormat getDateFormat() {
        return new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
    }
}
