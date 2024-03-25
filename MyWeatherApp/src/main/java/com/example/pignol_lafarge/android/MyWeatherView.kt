package com.example.pignol_lafarge.android

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView

class MyWeatherView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    private val cityTextView: TextView
    private val temperatureTextView: TextView
    private val weatherIcon: ImageView
    private val weatherDescriptionTextView: TextView

    init {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.weather_view_layout, this)

        cityTextView = view.findViewById(R.id.city_text_view)
        temperatureTextView = view.findViewById(R.id.temperature_text_view)
        weatherIcon = view.findViewById(R.id.weather_icon)
        weatherDescriptionTextView = view.findViewById(R.id.weather_description_text_view)
    }

    fun updateWeather(weather: Weather) {
        cityTextView.text = weather.cityName
        temperatureTextView.text = formatTemperature(weather.temperature) // Implement formatting logic
        weatherDescriptionTextView.text = weather.description

        // Set weather icon based on weather description (replace with actual logic)
        val iconDrawable: Drawable? = when (weather.description) {
            "sunny" -> context.getDrawable(R.drawable.ic_sunny)
            "cloudy" -> context.getDrawable(R.drawable.ic_cloudy)
            "rainy" -> context.getDrawable(R.drawable.ic_rainy)
            else -> null
        }
        weatherIcon.setImageDrawable(iconDrawable)
    }

    private fun formatTemperature(temperature: Float): String {
        // Implement temperature formatting logic (e.g., Celsius or Fahrenheit)
        return "$temperature°C" // Replace with desired unit
    }
}