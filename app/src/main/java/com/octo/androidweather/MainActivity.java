package com.octo.androidweather;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.octo.androidweather.databinding.ActivityMainBinding;
import com.octo.presentation.CityForecastView;
import com.octo.presentation.ForecastViewModel;

public class MainActivity extends AppCompatActivity implements CityForecastView {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View activity = binding.getRoot();
        setContentView(activity);
        final CityForecastModule module = new CityForecastModule(this);
        module.getController().loadCityForecast("Paris");
    }

    @Override
    public void displayMessage(String message) {
        Log.i(TAG, "displayMessage: " + message);
    }

    @Override
    public void displayViewModel(ForecastViewModel viewModel) {
        Log.i(TAG, "displayViewModel: " + viewModel);
        binding.myTextView.setText(viewModel.getTitle());
    }
}