package com.example.malek.parisweather.utils

import android.app.Application
import com.example.malek.parisweather.R
import com.example.malek.parisweather.models.DailyForecast
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


class ParisWeatherApplication : Application() {
    lateinit var openWeatherApi: OpenWeatherApi
    override fun onCreate() {
        super.onCreate()
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val okhttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()

        val retrofit = Retrofit.Builder()
                .client(okhttpClient)
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        openWeatherApi = retrofit.create(OpenWeatherApi::class.java)
    }


}

interface OpenWeatherApi {
    @GET("daily?q=Paris&units=metric&cnt=5&appid=43fb4eb29096504097bee7473d811b22")
    fun getFiveDailyForcast(): Observable<DailyForecast>

}