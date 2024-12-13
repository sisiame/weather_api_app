package com.sisiame.weatherapiapp.data.repositories

import com.sisiame.weatherapiapp.domain.models.WeatherResponse
import com.sisiame.weatherapiapp.domain.models.WeatherCurrent
import com.sisiame.weatherapiapp.domain.models.WeatherCondition
import com.sisiame.weatherapiapp.domain.models.Location
import kotlinx.coroutines.delay
import javax.inject.Inject

class MockWeatherRepository @Inject constructor() {
    suspend fun fetchWeather(apiKey: String, city: String): Result<WeatherResponse> {
        // Simulating network delay for realism
        delay(1000)

        // Mock data to simulate a successful response
        val mockWeatherResponse = WeatherResponse(
            location = Location(
                name = "London",
            ),
            current = WeatherCurrent(
                tempF = 39.6,
                condition = WeatherCondition(
                    text = "Patchy light rain",
                    icon = "//cdn.weatherapi.com/weather/64x64/night/293.png"
                ),
                humidity = 100,
                feelsLikeF = 38.9,
                uvIndex = 0.0
            )
        )

        // Simulating a successful result
        return Result.success(mockWeatherResponse)
    }
}