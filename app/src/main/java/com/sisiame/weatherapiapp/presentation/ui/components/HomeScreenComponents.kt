package com.sisiame.weatherapiapp.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.sisiame.weatherapiapp.R
import com.sisiame.weatherapiapp.domain.models.WeatherResponse
import com.sisiame.weatherapiapp.presentation.ui.theme.WeatherAPIAppTheme
import com.sisiame.weatherapiapp.presentation.ui.theme.gray
import com.sisiame.weatherapiapp.presentation.ui.theme.ink
import com.sisiame.weatherapiapp.presentation.ui.theme.lightGray
import com.sisiame.weatherapiapp.presentation.viewmodels.HomeViewModel
import kotlin.math.roundToInt

@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel()) {
    val weatherState by viewModel.weatherState.observeAsState()
    val apiKey = stringResource(R.string.api_key)
    val focusRequester = remember { FocusRequester() }
    var searchQuery by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(top = 44.dp)
            .focusRequester(focusRequester)
    ) {
        SearchBar(
            onSearch = { city ->
                viewModel.searchCity(city.trim(), apiKey)
            },
            searchQuery = searchQuery,
            onSearchQueryChanged = {
                searchQuery = it
                viewModel.searchCity(searchQuery.trim(), apiKey)
            },
            focusRequester = focusRequester
        )
        Spacer(Modifier.height(32.dp))
        when (val state = weatherState) {
            is HomeScreenState.NoCitySelected -> {
                NoCitySelectedUI()
            }
            is HomeScreenState.CityFound -> {
                CityFoundUI(onCitySelected = {
                    viewModel.selectCity(state.weatherResponse)
                    searchQuery = "" // Clear the search bar
                    focusRequester.freeFocus() // Remove focus
                    keyboardController?.hide() // Close the keyboard
                }, response = state.weatherResponse)
            }
            is HomeScreenState.CitySelected -> {
                Spacer(Modifier.height(32.dp))
                CitySelectedUI(state.weatherResponse)
            }
            is HomeScreenState.CityNotFound -> {
                // Display an empty screen when there is no city found
                Spacer(modifier = Modifier.fillMaxSize())
            }

            null -> NoCitySelectedUI()
        }
    }
}

@Composable
fun NoCitySelectedUI() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "No City Selected",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "Please Search For A City",
            style = MaterialTheme.typography.headlineSmall,
        )
    }
}

@Composable
fun CityFoundUI(onCitySelected: () -> Unit, response: WeatherResponse) {
    Box {
        SearchResultCard(
            cityName = response.location.name,
            temperature = response.current.tempF,
            weatherIconUrl = "https:${response.current.condition.icon}",
            onClick = onCitySelected
        )
    }
}

@Composable
fun CitySelectedUI(response: WeatherResponse) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp) // Adding padding to the edges of the screen
    ) {
        // Weather Info on top
        WeatherInfo(
            weatherIconUrl = "https:${response.current.condition.icon}",
            cityName = response.location.name,
            temperature = response.current.tempF
        )

        Spacer(modifier = Modifier.height(16.dp)) // Space between weather info and the card

        // Weather Info Card on bottom
        WeatherInfoCard(
            humidity = response.current.humidity,
            uvIndex = response.current.uvIndex,
            feelsLike = response.current.feelsLikeF
        )
    }
}

@Composable
fun SearchResultCard(
    cityName: String,
    temperature: Double,
    weatherIconUrl: String,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(117.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = lightGray,
        ),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .size(85.dp)
                    .wrapContentWidth(Alignment.Start),
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .wrapContentWidth() // Ensure text doesn't take full width
                ) {
                    Text(
                        text = cityName,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .wrapContentWidth()
                ) {
                    Text(
                        text = "${temperature.roundToInt()}",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.align(Alignment.Center)
                    )
                    Text(
                        text = "°",
                        fontSize = 16.sp,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .offset(
                                x = 4.dp,
                                y = -MaterialTheme.typography.bodyLarge.fontSize.value.dp / 2
                            ) // Position the degree symbol
                    )
                }
            }
            AsyncImage(
                model = weatherIconUrl,
                contentDescription = "Weather Condition Icon",
                modifier = Modifier
                    .size(83.dp, 67.dp)
            )
        }
    }
}

@Composable
fun WeatherInfo(
    weatherIconUrl: String,
    cityName: String,
    temperature: Double
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(11.dp)
    ) {
        // Weather condition image
        AsyncImage(
            model = weatherIconUrl,
            contentDescription = "Weather Condition Icon",
            modifier = Modifier
                .width(123.dp)
                .height(123.dp),
        )

        // City name with right arrow
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = cityName,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.width(11.dp))
            Image(
                painter = painterResource(id = R.drawable.city_arrow),
                contentDescription = "Arrow Icon",
                modifier = Modifier
                    .size(21.dp)
            )
        }

        // Temperature
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .wrapContentWidth()
        ) {
            Text(
                text = "${temperature.roundToInt()}",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.align(Alignment.Center)
            )
            Text(
                text = "°",
                fontSize = 16.sp,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 4.dp, y = -MaterialTheme.typography.bodyLarge.fontSize.value.dp / 2)
            )
        }
    }
}

@Composable
fun WeatherInfoCard(
    humidity: Int,
    uvIndex: Double,
    feelsLike: Double
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(), // Ensure the Box takes up the full screen
        contentAlignment = Alignment.Center // Align the content to the center of the Box
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(75.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = lightGray
            ),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Humidity",
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "UV",
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Feels Like",
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${humidity}%",
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "${uvIndex.roundToInt()}",
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "${feelsLike.roundToInt()}°",
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun SearchBar(
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    onSearch: (String) -> Unit,
    focusRequester: FocusRequester
) {
    val keyboardControoller = LocalSoftwareKeyboardController.current

    TextField(
        value = searchQuery,
        onValueChange = onSearchQueryChanged,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = lightGray,
            unfocusedContainerColor = lightGray,
            unfocusedTextColor = ink,
            focusedTextColor = ink,
            unfocusedPlaceholderColor = gray,
            focusedPlaceholderColor = gray,
            unfocusedTrailingIconColor = gray,
            focusedTrailingIconColor = gray,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        placeholder = {
            Text( text = "Search Location", style = MaterialTheme.typography.labelMedium)
        },
        textStyle = MaterialTheme.typography.bodySmall,
        trailingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
        },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .height(46.dp)
            .focusRequester(focusRequester),
        shape = RoundedCornerShape(16.dp),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch(searchQuery)
                keyboardControoller?.hide()
                focusRequester.freeFocus()
            }
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search
        )
    )
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    WeatherAPIAppTheme {
        Surface {
            WeatherInfoCard(
                humidity = 56,
                uvIndex = 39.6,
                feelsLike = 33.1,
            )
        }
    }
}

sealed class HomeScreenState {
    data object NoCitySelected : HomeScreenState()
    data class CityFound(val weatherResponse: WeatherResponse) : HomeScreenState()
    data object CityNotFound : HomeScreenState()
    data class CitySelected(val weatherResponse: WeatherResponse) : HomeScreenState()
}