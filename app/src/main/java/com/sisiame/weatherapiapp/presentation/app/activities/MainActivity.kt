package com.sisiame.weatherapiapp.presentation.app.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.sisiame.weatherapiapp.presentation.ui.components.HomeScreen
import com.sisiame.weatherapiapp.presentation.ui.theme.WeatherAPIAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAPIAppTheme {
                HomeScreen()
            }
        }
    }
}
