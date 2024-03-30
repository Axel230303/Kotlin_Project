package com.example.pignol_lafarge.android
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherApp()
        }
    }
}

@Composable
fun WeatherApp() {
    val weatherApi = WeatherApi() // Assurez-vous d'initialiser ceci correctement, potentiellement en dehors de la composable pour respecter les meilleures pratiques
    val coroutineScope = rememberCoroutineScope()
    var cityName by remember { mutableStateOf("Limoges") }
    var weatherResponse by remember { mutableStateOf<WeatherResponse?>(null) }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = cityName,
            onValueChange = { cityName = it },
            label = { Text("Enter City Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                coroutineScope.launch {
                    weatherResponse = weatherApi.getWeatherByCityName(cityName)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Get Weather")
        }
        Spacer(modifier = Modifier.height(16.dp))
        weatherResponse?.let { weather ->
            Text("City: ${weather.name}")
            Text("Temperature: ${weather.main.temp} K")
            Text("Weather: ${weather.weather.first().main}")
            // Ajoutez plus de détails météo ici si vous le souhaitez
        }
    }
}
