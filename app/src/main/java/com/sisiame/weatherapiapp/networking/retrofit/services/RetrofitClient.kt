package com.sisiame.weatherapiapp.networking.retrofit.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://api.weatherapi.com/v1"
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun <WeatherAPIService> createService(weatherAPIService: Class<WeatherAPIService>): WeatherAPIService {
        return retrofit.create(weatherAPIService);
    }
}