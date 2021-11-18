package com.octo.androidweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.octo.androidweather.databinding.ActivityMainBinding
import com.octo.usecases.CityForecastInteractor.State
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null

    @Inject
    internal lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerMainActivityComponent.builder().build().inject(this)

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val activity: View = binding!!.root
        setContentView(activity)

        observeViewModel()
        viewModel.loadForecasts("paris")
    }

    private fun observeViewModel() {
        viewModel.displayState.observe(this, { displayState ->
            when (displayState) {
                is State.Success -> println("Test" + displayState.displayable.city)
                is State.Error -> displayState.message
                else -> {
                    // do nothing
                }
            }
        })
    }
}