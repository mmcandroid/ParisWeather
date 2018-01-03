package com.example.malek.parisweather.activities

import android.os.Build
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import com.example.malek.parisweather.R
import com.example.malek.parisweather.models.InfoWeather
import kotlinx.android.synthetic.main.activity_detail_forcast_activty.*

class DetailForcastActivty : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_forcast_activty)
        setSupportActionBar(toolbarDetails as Toolbar)
        if (intent != null) {
            if (intent.getParcelableExtra<InfoWeather>(FiveDayForcastActivtiy.WEATHER_INFO) != null) {
               // get info from intent
                val infoWeather = intent.getParcelableExtra<InfoWeather>(FiveDayForcastActivtiy.WEATHER_INFO)
                if (infoWeather.weather != null) {
                    if (infoWeather.weather!!.isNotEmpty()) {
                        mainCondition.text = infoWeather.weather!!.get(0).main
                        description.text = infoWeather.weather!!.get(0).description
                    }
                }
                // show details
                day.text = String.format(getString(R.string.temp_format), infoWeather.temp?.day?.toInt())
                max.text = String.format(getString(R.string.min), infoWeather.temp?.max?.toInt())
                min.text = String.format(getString(R.string.min), infoWeather.temp?.min?.toInt())
                if (infoWeather.speed != null) {
                    wind.visibility = View.VISIBLE
                    wind.text = String.format(getString(R.string.wind), infoWeather.speed.toString())
                }
                if (infoWeather.pressure != null) {
                    pressure.visibility = View.VISIBLE
                    pressure.text = String.format(getString(R.string.pressure), infoWeather.pressure.toString())

                }
                if (infoWeather.humidity != null) {
                    humidity.visibility = View.VISIBLE
                    humidity.text = String.format(getString(R.string.humidity), infoWeather.humidity.toString())

                }
                if (infoWeather.clouds != null) {
                    clouds.visibility = View.VISIBLE
                    clouds.text = String.format(getString(R.string.cloudiness), infoWeather.clouds.toString())
                }
                if (infoWeather.rain != null) {
                    rain.visibility = View.VISIBLE
                    rain.text = String.format(getString(R.string.rain_volume), infoWeather.rain.toString())
                }
                if (infoWeather.snow != null) {
                    snow.visibility = View.VISIBLE
                    snow.text = String.format(getString(R.string.snow_volume), infoWeather.snow.toString())
                }
                setupToolbar(infoWeather.date)
            }
        }

    }

    fun setupToolbar(date: String) {
        (toolbarDetails as Toolbar).setTitle(date)
        setSupportActionBar((toolbarDetails as Toolbar))
        if (supportActionBar != null) {
            (supportActionBar as ActionBar).setDisplayHomeAsUpEnabled(true)
            (supportActionBar as ActionBar).setDisplayShowHomeEnabled(true)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                (supportActionBar as ActionBar).setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp, null))
            } else {
                (supportActionBar as ActionBar).setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp))
            }

        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return true
    }
}
