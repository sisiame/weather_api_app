package com.sisiame.weatherapiapp.data.repositories

import com.sisiame.weatherapiapp.data.remote.services.WeatherAPIService
import com.sisiame.weatherapiapp.domain.models.WeatherResponse
import retrofit2.HttpException
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val weatherAPIService: WeatherAPIService
) {
    suspend fun fetchWeather(apiKey: String, city: String): Result<WeatherResponse> {
        return try {
            val response = weatherAPIService.getWeatherResponse(apiKey, city)

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(Throwable("Empty response body"))
                }
            } else {
                Result.failure(Throwable("HTTP error: ${response.code()} - ${response.errorBody()?.string()}"))
            }
        } catch (e: HttpException) {
            Result.failure(Throwable("HTTP exception: ${e.localizedMessage}"))
        } catch (e: Exception) {
            Result.failure(Throwable("Unexpected error: ${e.localizedMessage}"))
        }
    }
}