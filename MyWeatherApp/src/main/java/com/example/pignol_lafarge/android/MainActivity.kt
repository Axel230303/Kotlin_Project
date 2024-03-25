import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.pignol_lafarge.android.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    private val weatherViewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        weatherViewModel.weatherData.observe(this, Observer { data ->
            updateUI(data)
        })

        weatherViewModel.errorMessage.observe(this, Observer { message ->
            // Affichez le message d'erreur à l'utilisateur, par exemple via un Toast
        })

        val apiKey = "YOUR_API_KEY"
        val city = "Paris" // Exemple de ville

        weatherViewModel.getWeatherData(apiKey, city)
    }

    private fun updateUI(data: JSONObject?) {
        val weather = data?.getJSONArray("weather")?.getJSONObject(0)
        val main = data?.getJSONObject("main")
        val wind = data?.getJSONObject("wind")
        val uvIndex = data?.optDouble("uvIndex") // Utilisez optDouble pour éviter JSONException

        textViewWeather.text = "Weather: ${weather?.getString("description")}"
        textViewTemperature.text = "Temperature: ${main?.getInt("temp")}°C"
        textViewMinMaxTemp.text = "Min: ${main?.getInt("temp_min")}°C, Max: ${main?.getInt("temp_max")}°C"
        textViewWindSpeed.text = "Wind Speed: ${wind?.getDouble("speed")}m/s"
        // Gérez correctement uvIndex car cette donnée peut ne pas être disponible
        textViewUVIndex.text = "UV Index: ${uvIndex ?: "Not available"}"
    }
}
