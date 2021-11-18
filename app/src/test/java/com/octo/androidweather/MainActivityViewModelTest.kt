package com.octo.androidweather

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.octo.presentation.ForecastDisplayable
import com.octo.usecases.CityForecastInteractor
import kotlinx.coroutines.Dispatchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainActivityViewModelTest {

    @Mock
    lateinit var interactor: CityForecastInteractor

    private lateinit var viewModel: MainActivityViewModel

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var observer: Observer<CityForecastInteractor.State>

    @Before
    fun setUp() {
        val dispatcher = Dispatchers.Unconfined
        viewModel = MainActivityViewModel(interactor = interactor, dispatcher)
        viewModel.displayState.observeForever(observer)
    }

    @Test
    fun loadForecasts() {
        // Given
        val state = CityForecastInteractor.State.Success(mock(ForecastDisplayable::class.java))
        given(interactor.getCityForecastState("paris")).willReturn(state)

        // When
        viewModel.loadForecasts("paris")

        // Then
        then(observer).should().onChanged(state)
    }
}