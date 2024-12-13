package com.sisiame.weatherapiapp.services

import com.sisiame.weatherapiapp.models.WeatherResponse
import retrofit2.Response
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
    fun getWeatherResponse(
        @Query("key") apiKey: String,
        @Query("q") city: String
    ) : Response<WeatherResponse>
}