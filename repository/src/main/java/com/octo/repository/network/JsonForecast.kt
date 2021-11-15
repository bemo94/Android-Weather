package com.octo.repository.network

import com.google.gson.annotations.SerializedName
import java.util.*

data class JsonForecast(
    @SerializedName("main")
    val main: JsonMain,
    @SerializedName("dt_txt")
    val date: Date
)