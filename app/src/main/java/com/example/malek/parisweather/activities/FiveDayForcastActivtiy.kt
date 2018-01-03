package com.example.malek.parisweather.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.malek.parisweather.R
import com.example.malek.parisweather.adapter.FiveDayForcastAdapter
import com.example.malek.parisweather.adapter.OnInfoClickListener
import com.example.malek.parisweather.models.DailyForecast
import com.example.malek.parisweather.models.InfoWeather
import com.example.malek.parisweather.utils.ParisWeatherApplication
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*


class FiveDayForcastActivtiy : AppCompatActivity(), OnInfoClickListener {

    companion object {
        val WEATHER_INFO = "weather_info"
    }

    val TAG: String = FiveDayForcastActivtiy::class.java.simpleName
    val adapterForcast = FiveDayForcastAdapter(this)
    val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE)
    val filename = "weather.json"
    var disposableApi: Disposable? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (toolbar as Toolbar).title = getString(R.string.screen_home_title)
        setSupportActionBar(toolbar as Toolbar)
        fetchForForcastFromApi(false)
        refresh.setOnRefreshListener {
            fetchForForcastFromApi(true)
        }
        listForcast.adapter = adapterForcast

    }


    fun fetchForForcastFromApi(isRefresh: Boolean) {
        disposableApi = (application as ParisWeatherApplication).openWeatherApi
                .getFiveDailyForcast()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap {
                    // save forcast to file
                    saveForcast(it)
                    Observable.fromIterable(it.list)
                }
                .onErrorResumeNext(
                        // on error while geting forecast from api return local file
                        getForecastFromLocal()
                )
                .map {
                    // reformat date
                    it.date = simpleDateFormat.format(
                            it.dt?.toLong()?.let
                            { timeStamp -> Date(timeStamp * 1000) })
                    it
                }
                .doOnSubscribe {
                    if (isRefresh) {
                        adapterForcast.clear()
                    }
                }
                .subscribe({
                    if (loading != null) {
                        loading.visibility = View.GONE
                    }
                    if (refresh != null) {
                        refresh.visibility = View.VISIBLE
                        refresh.isRefreshing = false
                    }
                    // add info weather to adapter
                    adapterForcast.addInfo(it)
                }, { throwable ->
                    // onError
                    Log.e(TAG, throwable.toString())
                    Toast.makeText(this, getString(R.string.error_data), Toast.LENGTH_LONG).show()
                    if (loading != null) {
                        loading.visibility = View.GONE
                    }
                    if (refresh != null) {
                        refresh.isRefreshing = false
                    }

                })


    }

    fun getForecastFromLocal(): Observable<InfoWeather> {
        Log.e(TAG, "fromFile")
        return Observable.fromCallable {
            val file = File(filesDir, filename)
            val fileInputStream = openFileInput(filename)
            val fileContent = ByteArray(file.length().toInt())
            fileInputStream.read(fileContent)
            val json = String(fileContent)
            Log.e(TAG, "file" + json)
            fileInputStream.close()
            return@fromCallable Gson().fromJson(json, DailyForecast::class.java)
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap {
                    Log.e(TAG, "from file")
                    Observable.fromIterable(it.list)
                }
    }

    fun saveForcast(dailyForecast: DailyForecast) {
        Observable.fromCallable {
            val file = File(filesDir, filename)
            val json = Gson().toJson(dailyForecast)
            val outputStream: FileOutputStream
            outputStream = openFileOutput(filename, MODE_PRIVATE)
            outputStream.write(json.toByteArray())
            outputStream.close()
            
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.e(TAG, "file saved")
                }, { t ->
                    Log.e(TAG, t.toString())
                })

    }

    override fun onInfoClickListener(infoWeather: InfoWeather) {
        val toDetails = Intent(this, DetailForcastActivty::class.java)
        toDetails.putExtra(WEATHER_INFO, infoWeather)
        startActivity(toDetails)

    }

    override fun onDestroy() {
        super.onDestroy()
        if (disposableApi != null) {
            if (disposableApi!!.isDisposed) {
                disposableApi!!.dispose()
            }
        }
    }
}
