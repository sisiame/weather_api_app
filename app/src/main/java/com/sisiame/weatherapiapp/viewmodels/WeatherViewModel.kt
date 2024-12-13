package com.sisiame.weatherapiapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sisiame.weatherapiapp.services.RetrofitClient
import com.sisiame.weatherapiapp.services.WeatherAPIService
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    private val _weatherData = MutableLiveData("No data")
    val weatherData: LiveData<String> get() = _weatherData

    init {
        viewModelScope.launch {
            getWeatherData()
        }
    }

    private fun getWeatherData() {
        _weatherData.value = RetrofitClient
            .createService(WeatherAPIService::class.java)
            .getWeatherResponse(
                apiKey = "0cc9130147974ce3a48210657240912",
                city = "New York"
            )
            .body()
            .toString()
    }
}