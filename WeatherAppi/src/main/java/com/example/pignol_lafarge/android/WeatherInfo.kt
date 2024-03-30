package com.example.pignol_lafarge.android

data class WeatherInfo(
    val main: String,
    val description: String,
    val temp: Double,
    val temp_min: Double,
    val temp_max: Double,
    val wind_speed: Double,
    val uv_index: Double,
)
