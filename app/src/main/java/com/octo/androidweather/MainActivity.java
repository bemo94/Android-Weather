package com.octo.androidweather;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.octo.androidweather.databinding.ActivityMainBinding;
import com.octo.repository.CityWeeklyForecast;
import com.octo.repository.network.NetworkModule;
import com.octo.repository.network.WeatherNetworkRepository;

import okhttp3.HttpUrl;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View activity = binding.getRoot();
        setContentView(activity);
        logForecast();
    }

    private void logForecast() {
        final NetworkModule networkModule = new NetworkModule();
        final HttpUrl httpUrl = HttpUrl.parse(BuildConfig.WEATHER_BASE_URL);
        final Retrofit retrofit = networkModule.getRetrofit(httpUrl);
        final WeatherNetworkRepository repository = new WeatherNetworkRepository(retrofit);
        new Thread() {
            @Override
            public void run() {
                final CityWeeklyForecast forecast = repository.loadCityWeeklyForecast("Paris");
                Log.i(TAG, "Forecast: " + forecast);
            }
        }.start();
    }
}