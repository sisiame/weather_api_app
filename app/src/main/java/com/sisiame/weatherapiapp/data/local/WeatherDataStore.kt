package com.sisiame.weatherapiapp.data.local

import android.content.Context
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.sisiame.weatherapiapp.domain.models.WeatherCurrent
import com.sisiame.weatherapiapp.domain.models.Location
import com.sisiame.weatherapiapp.domain.models.WeatherCondition
import com.sisiame.weatherapiapp.domain.models.WeatherResponse
import kotlinx.coroutines.flow.map

class WeatherDataStore(private val context: Context) {
    // define dataStore instance
    private val Context.dataStore by preferencesDataStore(name = "weather_data")

    // define keys for storing weather data
    private val CITY_KEY = stringPreferencesKey("city")
    private val TEMPERATURE_KEY = doublePreferencesKey("temperature")
    private val WEATHER_CONDITION_KEY = stringPreferencesKey("weather_condition")
    private val WEATHER_CONDITION_ICON_KEY = stringPreferencesKey("weather_condition_icon")
    private val HUMIDITY_KEY = intPreferencesKey("humidity")
    private val UV_INDEX_KEY = doublePreferencesKey("uv_index")
    private val FEELS_LIKE_KEY = doublePreferencesKey("feels_like")

    /**
     * Saves the weather data to the dataStore.
     */
    suspend fun saveWeatherDetails(weatherDetails : WeatherResponse) {
        context.dataStore.edit {  preferences ->
            preferences[CITY_KEY] = weatherDetails.location.name
            preferences[TEMPERATURE_KEY] = weatherDetails.current.tempF
            preferences[WEATHER_CONDITION_KEY] = weatherDetails.current.condition.text
            preferences[WEATHER_CONDITION_ICON_KEY] = weatherDetails.current.condition.icon
            preferences[HUMIDITY_KEY] = weatherDetails.current.humidity
            preferences[UV_INDEX_KEY] = weatherDetails.current.uvIndex
            preferences[FEELS_LIKE_KEY] = weatherDetails.current.feelsLikeF
        }
    }

    /**
     * Retrieves the weather data from the data store as a [WeatherResponse] object.
     */
    fun getWeatherDetails() = context.dataStore.data.map { preferences ->
        WeatherResponse(
            location = Location(
                name = preferences[CITY_KEY] ?: "",
            ),
            current = WeatherCurrent(
                tempF = preferences[TEMPERATURE_KEY] ?: 0.0,
                condition = WeatherCondition(
                    text = preferences[WEATHER_CONDITION_KEY] ?: "",
                    icon = preferences[WEATHER_CONDITION_ICON_KEY] ?: ""
                ),
                humidity = preferences[HUMIDITY_KEY] ?: 0,
                uvIndex = preferences[UV_INDEX_KEY] ?: 0.0,
                feelsLikeF = preferences[FEELS_LIKE_KEY] ?: 0.0,
            )
        )
    }
}