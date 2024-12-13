package com.sisiame.weatherapiapp.data.modules

import com.sisiame.weatherapiapp.data.remote.RetrofitClient
import com.sisiame.weatherapiapp.data.remote.services.WeatherAPIService
import com.sisiame.weatherapiapp.data.repositories.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideWeatherAPIService(): WeatherAPIService {
        return RetrofitClient.createService(WeatherAPIService::class.java)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(weatherAPIService: WeatherAPIService): WeatherRepository {
        return WeatherRepository(weatherAPIService)
    }
}