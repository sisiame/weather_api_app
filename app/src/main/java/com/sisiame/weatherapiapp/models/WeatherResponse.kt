package com.sisiame.weatherapiapp.models

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("location") val location: Location,
    @SerializedName("current") val current: CurrentWeather
) {
    // Override the toString for the sake of readability when debugging
    override fun toString(): String {
        return """
            City Name: ${location.name}
            Temperature: ${current.temperature}°F
            Weather Condition: ${current.condition.text}
            Weather Condition Icon URL: ${current.condition.iconUrl}
            Humidity: ${current.humidity}%
            UV Index: ${current.uvIndex}
            Feels Like: ${current.feelsLike}°F
        """.trimIndent()
    }
}

data class Location(
    @SerializedName("name") val name: String,
)

data class CurrentWeather(
    @SerializedName("temp_f") val temperature: Double,
    @SerializedName("condition") val condition: WeatherCondition,
    @SerializedName("humidity") val humidity: Int,
    @SerializedName("uv") val uvIndex: Double,
    @SerializedName("feelslike_f") val feelsLike: Double
)

data class WeatherCondition(
    @SerializedName("text") val text: String,
    @SerializedName("icon") val iconUrl: String
)