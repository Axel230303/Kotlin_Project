import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class WeatherViewModel : ViewModel() {
    val weatherData = MutableLiveData<JSONObject?>()
    val errorMessage = MutableLiveData<String>()

    fun getWeatherData(apiKey: String, city: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val url = URL("https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$apiKey")
                (url.openConnection() as? HttpURLConnection)?.run {
                    requestMethod = "GET"
                    inputStream.bufferedReader().use { reader ->
                        val response = StringBuilder()
                        var line: String?
                        while (reader.readLine().also { line = it } != null) {
                            response.append(line)
                        }
                        weatherData.postValue(JSONObject(response.toString()))
                    }
                }
            } catch (e: Exception) {
                errorMessage.postValue("Error while getting weather data: ${e.message}")
            }
        }
    }
}
