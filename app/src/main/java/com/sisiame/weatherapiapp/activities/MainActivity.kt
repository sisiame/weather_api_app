package com.sisiame.weatherapiapp.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sisiame.weatherapiapp.models.WeatherResponse
import com.sisiame.weatherapiapp.ui.theme.WeatherAPIAppTheme
import com.sisiame.weatherapiapp.viewmodels.WeatherViewModel
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherAPIAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                }
            }
        }
    }
}
