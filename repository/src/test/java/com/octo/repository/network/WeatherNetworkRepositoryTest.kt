package com.octo.repository.network

import junit.framework.Assert.assertEquals
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.MockResponse
import org.junit.After
import org.junit.Before
import org.junit.Test


class WeatherNetworkRepositoryTest {

    var server = MockWebServer()
    lateinit var repository: WeatherNetworkRepository
    private var networkModule: NetworkModule = NetworkModule()

    @Before
    fun setup() {
        val retrofit = networkModule.getRetrofit(server.url("/"))
        repository = WeatherNetworkRepository(retrofit)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun loadCityWeeklyForecast() {
        // Given
        val response = MockResponse()
            .setBody(FileUtil.readFile("src/test/resources/forecast.json"))
        server.enqueue(response)

        // When
        val weeklyForecast = repository.loadCityWeeklyForecast("paris")

        // Then
        val expected = CityWeeklyForecast(
            cityName = "Paris",
            forecasts = listOf(
                Forecast(
                    temperature = 14.76f,
                    date = networkModule.getSimpleDateFormat().parse("2017-10-01 18:00:00")
                )
            )
        )
        assertEquals(weeklyForecast, expected)
    }
}