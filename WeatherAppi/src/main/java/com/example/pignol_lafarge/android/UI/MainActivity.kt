package com.example.pignol_lafarge.android.UI

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pignol_lafarge.android.R
import com.example.pignol_lafarge.android.WeatherViewModel.WeatherResponse
import com.example.pignol_lafarge.android.WeatherViewModel.WeatherViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import java.time.LocalDateTime

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherApp()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherApp(weatherViewModel: WeatherViewModel = viewModel()) {
    var cityName by remember { mutableStateOf("") }
    val weatherResponse by weatherViewModel.weatherState.observeAsState()
    Box {
        val current = LocalDateTime.now()
        val hour = current.hour

        val background = when (hour) {
            in 5..11 -> R.drawable.background_morning
            in 12..17 -> R.drawable.background_afternoon
            in 18..20 -> R.drawable.background_evening
            else -> R.drawable.background_night
        }

        Image(
            painter = painterResource(id = background),
            contentDescription = "Background fonction of the hour",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .size(100.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            TextField(
                value = cityName,
                onValueChange = { cityName = it },
                label = { Text("Entrer le nom de la ville :", color = Color.DarkGray)},
                modifier = Modifier
                    .fillMaxWidth()
                    .border(2.dp, Color.Black),
                placeholder = {
                    Text("Paris,Marseille,...", color = Color.Gray)
                },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Gray,
                    unfocusedIndicatorColor = Color.Transparent,
                )
            )

            Spacer(modifier = Modifier.height(4.dp))

            Button(
                onClick = { weatherViewModel.getWeatherForCity(cityName) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.DarkGray,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text("Trouver la ville")
            }

            Spacer(modifier = Modifier.size(16.dp))

            weatherResponse?.let { weather ->
                WeatherDisplay(weather = weather)
            }
        }
    }
}

@Composable
fun WeatherDisplay(weather: WeatherResponse) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "${weather.name}",
            color = Color(0xFF58AAEB),
            fontSize = 52.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = androidx.compose.ui.text.font.FontFamily.Cursive
        )

        weather.weather.forEach { weatherDetail ->
            val weatherText = when (weatherDetail.main) {
                "Clouds" -> "Nuageux"
                "Clear" -> "Ensoleillé"
                "Rain" -> "Pluie"
                else -> "Inconnu"
            }

            Text(
                text = "${weather.main.temp}°C",
                color = Color(0xFF58AAEB),
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = androidx.compose.ui.text.font.FontFamily.Cursive
            )
            Spacer(modifier = Modifier.height(16.dp))

            var temp = weather.main.temp

            if (temp <= 5) {
                Image(
                    painter = painterResource(id = R.drawable.cold),
                    contentDescription = "Cold",
                    modifier = Modifier
                        .height(60.dp)
                        .align(Alignment.CenterHorizontally)
                )
            } else if (temp > 5 && temp <= 15) {
                Image(
                    painter = painterResource(id = R.drawable.tepid),
                    contentDescription = "Tepid",
                    modifier = Modifier
                        .height(75.dp)
                        .align(Alignment.CenterHorizontally)
                )
            } else if (temp > 15) {
                Image(
                    painter = painterResource(id = R.drawable.hot),
                    contentDescription = "Hot",
                    modifier = Modifier
                        .height(60.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            weather.weather.forEach { weatherDetail ->

                Text(
                    text = "$weatherText",
                    style = TextStyle(color = Color(0xFF58AAEB), fontSize = 26.sp),
                    fontWeight = FontWeight.Bold,
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                )
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
                } else if (weatherDetail.main == "Rain") {
                    Image(
                        painter = painterResource(id = R.drawable.rain),
                        contentDescription = "Rain",
                        modifier = Modifier
                            .height(120.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                } else if (weatherDetail.main == "Thunderstorm") {
                    Image(
                        painter = painterResource(id = R.drawable.thunderstorm),
                        contentDescription = "Rain",
                        modifier = Modifier
                            .height(200.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                } else if (weatherDetail.main == "Snowy") {
                    Image(
                        painter = painterResource(id = R.drawable.snowy),
                        contentDescription = "Snow",
                        modifier = Modifier
                            .height(200.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.height(50.dp))
                    Image(
                        painter = painterResource(id = R.drawable.wind),
                        contentDescription = "Rain",
                        modifier = Modifier
                            .height(35.dp)
                    )
                    Text(
                        text = "${weather.wind.speed}m/s",
                        color = Color(0xFF58AAEB),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = androidx.compose.ui.text.font.FontFamily.Cursive
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                }

                Spacer(modifier =Modifier.height(32.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Min : ${weather.main.temp_min}°C",
                        color = Color(0xFF58AAEB),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = androidx.compose.ui.text.font.FontFamily.Cursive
                    )

                    Text(
                        text = "Max : ${weather.main.temp_max}°C",
                        color = Color(0xFF58AAEB),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = androidx.compose.ui.text.font.FontFamily.Cursive
                    )

                    Spacer(modifier =Modifier.height(16.dp))
                }
            }
            Spacer(modifier =Modifier.height(85.dp))
            Text(text = "Application développé par Baptiste Lafarge et Axel Pignol", color = Color.Black, fontSize = 12.sp)
        }
    }


}