package com.example.pignol_lafarge.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.InternalCoroutinesApi

class MainActivity : ComponentActivity() {
    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherApp()
        }
    }
}

@Composable
fun WeatherApp(weatherViewModel: WeatherViewModel = viewModel()) {
    var cityName by remember { mutableStateOf("") }
    val weatherResponse by weatherViewModel.weatherState.observeAsState()
    val SkyBlue = Color(0xFF87CEEB)

    Column(modifier = Modifier
        .fillMaxSize()
        .background(SkyBlue)
        .padding(16.dp)){

        OutlinedTextField(
            value = cityName,
            onValueChange = { cityName = it },
            label = { Text("Entrer le nom de la ville :") },
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { weatherViewModel.getWeatherForCity(cityName) },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFFA500),
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text("Trouver la ville")
        }

        Spacer(modifier = Modifier.height(8.dp))

        weatherResponse?.let { weather ->
            WeatherDisplay(weather = weather)
        }
    }
}

@Composable
fun WeatherDisplay(weather: WeatherResponse) {
    val SkyBlue = Color(0xFF87CEEB)
    Column(modifier = Modifier
        .fillMaxSize()
        .background(SkyBlue)
        .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        weather.weather.forEach { weatherDetail ->
            val weatherText = when (weatherDetail.main) {
                "Clouds" -> "Nuageux"
                "Clear" -> "Ensoleillé"
                "Rain" -> "Pluie"
            else -> "Inconnu"
            }

        Text(text = "Ville choisie : ${weather.name}", style = MaterialTheme.typography.headlineMedium, color = Color.White, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "${weather.main.temp}°C",
            color = Color.White,
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(id = R.drawable.celsius),
            contentDescription = "Rain",
            modifier = Modifier
                .height(60.dp)
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        weather.weather.forEach { weatherDetail ->

            Text(text = "Météo actuelle : $weatherText", style = TextStyle(color = Color.White, fontSize = 18.sp))
            Spacer(modifier = Modifier.height(16.dp))

            if (weatherDetail.main == "Clouds") {
                Image(
                    painter = painterResource(id = R.drawable.cloud),
                    contentDescription = "Cloudy",
                    modifier = Modifier
                        .height(120.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(16.dp))
            } else if (weatherDetail.main == "Clear") {
                Image(
                    painter = painterResource(id = R.drawable.sunny),
                    contentDescription = "Sunny",
                    modifier = Modifier
                        .height(120.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            else if (weatherDetail.main == "Rain") {
                Image(
                    painter = painterResource(id = R.drawable.rain),
                    contentDescription = "Rain",
                    modifier = Modifier
                        .height(120.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            else if (weatherDetail.main == "Thunderstorm") {
                Image(
                    painter = painterResource(id = R.drawable.thunderstorm),
                    contentDescription = "Rain",
                    modifier = Modifier
                        .height(120.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Vitesse du vent : ${weather.wind.speed}m/s",style = TextStyle(color = Color.White, fontSize = 18.sp))
        }
    }
}
}