package com.octo.repository.network

import com.google.gson.annotations.SerializedName

data class JsonCity(
    @SerializedName("name")
    val name: String
)