package com.sisiame.weatherapiapp.networking.retrofit.services

import retrofit2.http.GET;
import retrofit2.http.Query

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
    fun getWeatherDetails(
        @Query("key") apiKey: String,
        @Query("q") city: String
    ) : WeatherResponse
}