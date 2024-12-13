package com.sisiame.weatherapiapp.local_storage_persistence.data

import android.content.Context
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.sisiame.weatherapiapp.models.WeatherResponse
import kotlinx.coroutines.flow.map

class WeatherDataStore {
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
     *
     * @param context The context where the dataStore is being edited.
     * 
     */
    suspend fun saveWeatherDetails(context: Context, weatherDetails : WeatherResponse) {
        context.dataStore.edit {  preferences ->
            preferences[CITY_KEY] = weatherDetails.location.name
            preferences[TEMPERATURE_KEY] = weatherDetails.current.temperature
            preferences[WEATHER_CONDITION_KEY] = weatherDetails.current.condition.text
            preferences[WEATHER_CONDITION_ICON_KEY] = weatherDetails.current.condition.iconUrl
            preferences[HUMIDITY_KEY] = weatherDetails.current.humidity
            preferences[UV_INDEX_KEY] = weatherDetails.current.uvIndex
            preferences[FEELS_LIKE_KEY] = weatherDetails.current.feelsLike
        }
    }

    /**
     * Retrieves the weather data from the data store.
     *
     * @param context The context where the dataStore is being retrieved from.
     */
    fun getWeatherDetails(context: Context) = context.dataStore.data.map { preferences ->
        preferences[CITY_KEY] ?: "City"
        preferences[TEMPERATURE_KEY] ?: 999
        preferences[WEATHER_CONDITION_KEY] ?: "Weather Condition"
        preferences[WEATHER_CONDITION_ICON_KEY] ?: "Weather Condition Icon"
        preferences[HUMIDITY_KEY] ?: 999
        preferences[UV_INDEX_KEY] ?: 999
        preferences[FEELS_LIKE_KEY] ?: 999
    }
}