package com.octo.repository.network

import com.google.gson.annotations.SerializedName

data class JsonCityWeeklyForecast(
    @SerializedName("city")
    val city: JsonCity,
    @SerializedName("list")
    val forecasts: List<JsonForecast>
)