package com.sisiame.weatherapiapp.data.remote.services

import com.sisiame.weatherapiapp.domain.models.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface WeatherAPIService {
    /**
     * Get the current weather details from the queried city.
     *
     * @param apiKey The API key provided by weatherapi.com.
     * @param city The city being queried.
     *
     * @return The response from the weather API.
     */
    @GET("current.json")
    suspend fun getWeatherResponse(
        @Query("key") apiKey: String,
        @Query("q") city: String,
    ) : Response<WeatherResponse>

    @GET
    suspend fun getWeather(
        @Url url: String
    ) : Response<WeatherResponse>
}