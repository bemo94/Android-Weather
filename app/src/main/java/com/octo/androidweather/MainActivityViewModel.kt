package com.octo.androidweather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.octo.usecases.CityForecastInteractor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivityViewModel @Inject constructor (
    private val interactor: CityForecastInteractor,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    val displayState: MutableLiveData<CityForecastInteractor.State> by lazy {
         MutableLiveData<CityForecastInteractor.State>().apply {
            MutableLiveData<CityForecastInteractor.State>()
        }
    }

    fun loadForecasts(cityName: String) = viewModelScope.launch(dispatcher) {
        displayState.postValue(interactor.getCityForecastState(cityName))
    }

}