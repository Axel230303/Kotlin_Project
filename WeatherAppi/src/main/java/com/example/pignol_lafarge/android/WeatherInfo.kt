
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pignol_lafarge.android.WeatherResponse

@Composable
fun WeatherInfo(weather: WeatherResponse) {
    Column {
        Text(text = "City: ${weather.name}")
        // Affiche la température en degrés Celsius. Assurez-vous que votre API renvoie déjà les températures en Celsius.
        Text(text = "Temperature: ${weather.main.temp}°C")
        // Affiche une description du temps (par exemple, "Clear sky")
        Text(text = "Weather: ${weather.weather.first().description}")
        // Vous pouvez ajouter plus d'informations ici selon les données disponibles dans votre modèle WeatherResponse.
        // Par exemple, vitesse du vent, humidité, etc.
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Wind Speed: ${weather.wind.speed} m/s")
        Text(text = "Humidity: ${weather.main.humidity}%")
    }
}
