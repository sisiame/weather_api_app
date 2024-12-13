package com.sisiame.weatherapiapp.domain.models

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("location")
    val location: Location,

    @SerializedName("current")
    val current: WeatherCurrent
) {
    // Override toString for debugging
    override fun toString(): String {
        return """
            City Name: ${location.name}
            Temperature: ${current.tempF}°F
            Weather Condition: ${current.condition.text}
            Weather Condition Icon URL: ${current.condition.icon}
            Humidity: ${current.humidity}%
            UV Index: ${current.uvIndex}
            Feels Like: ${current.feelsLikeF}°F
        """.trimIndent()
    }
}

data class Location(
    @SerializedName("name")
    val name: String,
)

data class WeatherCurrent(
    @SerializedName("temp_f")
    val tempF: Double,

    @SerializedName("condition")
    val condition: WeatherCondition,

    @SerializedName("humidity")
    val humidity: Int,

    @SerializedName("feelslike_f")
    val feelsLikeF: Double,

    @SerializedName("uv")
    val uvIndex: Double
)

data class WeatherCondition(
    @SerializedName("text")
    val text: String,

    @SerializedName("icon")
    val icon: String
)
