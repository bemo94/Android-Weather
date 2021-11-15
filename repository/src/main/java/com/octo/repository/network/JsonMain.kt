package com.octo.repository.network

import com.google.gson.annotations.SerializedName

data class JsonMain(
    @SerializedName("temp")
    val temperature: Float
)