package com.sisiame.weatherapiapp

import com.sisiame.weatherapiapp.data.remote.services.WeatherAPIService
import com.sisiame.weatherapiapp.data.repositories.WeatherRepository
import com.sisiame.weatherapiapp.domain.models.Location
import com.sisiame.weatherapiapp.domain.models.WeatherCondition
import com.sisiame.weatherapiapp.domain.models.WeatherCurrent
import com.sisiame.weatherapiapp.domain.models.WeatherResponse
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import retrofit2.Response

class WeatherRepositoryTest {

    private lateinit var weatherAPIService: WeatherAPIService
    private lateinit var weatherRepository: WeatherRepository

    @Before
    fun setUp() {
        weatherAPIService = Mockito.mock(WeatherAPIService::class.java)
        weatherRepository = WeatherRepository(weatherAPIService)
    }

    @Test
    fun `test fetchWeather success`() = runBlocking {
        val mockWeatherResponse = WeatherResponse(
            location = Location("London"),
            current = WeatherCurrent(
                39.4,
                WeatherCondition("Patchy light rain", "//cdn.weatherapi.com/weather/64x64/night/293.png"),
                100,
                38.7,
                0.0
            )
        )
        val mockResponse = Response.success(mockWeatherResponse)

        Mockito.`when`(weatherAPIService.getWeatherResponse(Mockito.anyString(), Mockito.anyString()))
            .thenReturn(mockResponse)

        val result = weatherRepository.fetchWeather("fake_api_key", "London")

        assert(result.isSuccess) { "Expected successful response" }
        assertEquals(mockWeatherResponse, result.getOrNull())
    }

    @Test
    fun `test fetchWeather failure`() = runBlocking {
        val mockErrorResponse = Response.error<WeatherResponse>(403, "Error".toResponseBody(null))
        Mockito.`when`(weatherAPIService.getWeatherResponse(Mockito.anyString(), Mockito.anyString()))
            .thenReturn(mockErrorResponse)

        val result = weatherRepository.fetchWeather("fake_api_key", "London")

        assert(result.isFailure) { "Expected failure response" }
    }

    @Test
    fun `test fetchWeather exception handling`(): Unit = runBlocking {
        Mockito.`when`(weatherAPIService.getWeatherResponse(Mockito.anyString(), Mockito.anyString()))
            .thenThrow(RuntimeException("Network error"))

        val result = weatherRepository.fetchWeather("fake_api_key", "London")

        assertTrue(result.isFailure)

        val exception = result.exceptionOrNull()
        assertNotNull(exception)
        assertEquals("Unexpected error: Network error", exception?.message)
    }
}