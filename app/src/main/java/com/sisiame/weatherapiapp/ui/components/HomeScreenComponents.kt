package com.sisiame.weatherapiapp.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sisiame.weatherapiapp.models.WeatherResponse

@Composable
fun HomeScreen(
    state: HomeScreenState,
    onSearch: (String) -> Unit,
    onCitySelected: (WeatherResponse) -> Unit
) {
    when (state) {
        is HomeScreenState.NoCitySelected -> {
            NoCitySelectedUI()
        }
        is HomeScreenState.CityFound -> {
            CityFoundUI()
        }
        is HomeScreenState.CityNotFound -> {
            // Display an empty screen when there is no city found
            Spacer(modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
fun NoCitySelectedUI() {

}

@Composable
fun CityFoundUI() {

}

@Composable
fun SearchResultCard() {

}

@Composable
fun WeatherDetailsCard() {

}

@Composable
fun WeatherDetailsItem() {

}

@Composable
fun SearchBar(
    searchText: String,
    onSearchTextChanged: (String) -> Unit,
    onSearch: (String) -> Unit
) {

}

sealed class HomeScreenState {
    data object NoCitySelected : HomeScreenState()
    data class CityFound(val city : String, val weatherResponse: WeatherResponse) : HomeScreenState()
    data object CityNotFound : HomeScreenState()
}