package com.example.pignol_lafarge.android

import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*

class WeatherApi {
    private val httpClient = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
    }
    private val apiKey = "7515a0aee27e9f71154316a53da362ba"
    private val baseUrl = "https://api.openweathermap.org/data/2.5"

    suspend fun getWeatherByCityName(cityName: String): WeatherResponse {
        return httpClient.get("$baseUrl/weather") {
            parameter("q", cityName)
            parameter("appid", apiKey)
        }
    }
}
