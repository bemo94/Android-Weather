package com.octo.androidweather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.os.Bundle;
import android.view.View;

import com.octo.androidweather.databinding.ActivityMainBinding;
import com.octo.presentation.CityForecastView;
import com.octo.presentation.ForecastViewModel;

public class MainActivity extends AppCompatActivity implements CityForecastView {

    private ActivityMainBinding binding;
    private static final int DISPLAY_MESSAGE = 0;
    private static final int DISPLAY_LOADING = 1;
    private static final int DISPLAY_VIEW_MODEL = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final CityForecastModule module = new CityForecastModule(this);
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View activity = binding.getRoot();
        setContentView(activity);
        binding.searchView.setOnQueryTextListener(
            new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    binding.viewFlipper.setDisplayedChild(DISPLAY_LOADING);
                    module.getController().loadCityForecast(query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            }
        );
    }

    @Override
    public void displayMessage(String message) {
        binding.messageTextView.setText(message);
        binding.viewFlipper.setDisplayedChild(DISPLAY_MESSAGE);
    }

    @Override
    public void displayViewModel(ForecastViewModel viewModel) {
        binding.viewFlipper.setDisplayedChild(DISPLAY_VIEW_MODEL);
        binding.bestTemperatureTextView.setText(viewModel.getBestTemperatureLabel());
        binding.bestForecastTextView.setText(viewModel.getBestForecastLabel());
        binding.worstTemperatureTextView.setText(viewModel.getWorstTemperatureLabel());
        binding.worstForecastTextView.setText(viewModel.getWorstForecastLabel());
    }
}