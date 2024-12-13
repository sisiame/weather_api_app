package com.sisiame.weatherapiapp.networking.models

data class Current(
    val condition: Condition,
    val feelslike_c: Double,
    val feelslike_f: Double,
    val humidity: Int,
    val temp_c: Double,
    val temp_f: Double,
    val uv: Int
)