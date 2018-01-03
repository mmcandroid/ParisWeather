package com.example.malek.parisweather.models

import android.os.Parcel
import android.os.Parcelable


class City(var geonameId: Int?,
           var name: String?,
           var lat: Double?,
           var lon: Double?,
           var country: String?,
           var iso2: String?,
           var type: String?,
           var population: Int?) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readString(),
            parcel.readValue(Double::class.java.classLoader) as? Double,
            parcel.readValue(Double::class.java.classLoader) as? Double,
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readValue(Int::class.java.classLoader) as? Int) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(geonameId)
        parcel.writeString(name)
        parcel.writeValue(lat)
        parcel.writeValue(lon)
        parcel.writeString(country)
        parcel.writeString(iso2)
        parcel.writeString(type)
        parcel.writeValue(population)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<City> {
        override fun createFromParcel(parcel: Parcel): City {
            return City(parcel)
        }

        override fun newArray(size: Int): Array<City?> {
            return arrayOfNulls(size)
        }
    }
}

class Temperature(var day: Double?,
                  var min: Double?,
                  var max: Double?,
                  var night: Double?,
                  var eve: Double?,
                  var morn: Double?) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readValue(Double::class.java.classLoader) as? Double,
            parcel.readValue(Double::class.java.classLoader) as? Double,
            parcel.readValue(Double::class.java.classLoader) as? Double,
            parcel.readValue(Double::class.java.classLoader) as? Double,
            parcel.readValue(Double::class.java.classLoader) as? Double,
            parcel.readValue(Double::class.java.classLoader) as? Double) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(day)
        parcel.writeValue(min)
        parcel.writeValue(max)
        parcel.writeValue(night)
        parcel.writeValue(eve)
        parcel.writeValue(morn)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Temperature> {
        override fun createFromParcel(parcel: Parcel): Temperature {
            return Temperature(parcel)
        }

        override fun newArray(size: Int): Array<Temperature?> {
            return arrayOfNulls(size)
        }
    }
}

class Weather(var id: Int?,
              var main: String?,
              var description: String?,
              var icon: String?) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(main)
        parcel.writeString(description)
        parcel.writeString(icon)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Weather> {
        override fun createFromParcel(parcel: Parcel): Weather {
            return Weather(parcel)
        }

        override fun newArray(size: Int): Array<Weather?> {
            return arrayOfNulls(size)
        }
    }
}


class InfoWeather(var dt: Int?,
                  var temp: Temperature?,
                  var pressure: Double?,
                  var humidity: Double?,
                  var weather: List<Weather>?,
                  var speed: Double?,
                  var deg: Double?,
                  var clouds: Double?,
                  var rain: Double?,
                  var snow: Double?,
                  var date: String) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readParcelable(Temperature::class.java.classLoader),
            parcel.readValue(Double::class.java.classLoader) as? Double,
            parcel.readValue(Double::class.java.classLoader) as? Double,
            parcel.createTypedArrayList(Weather),
            parcel.readValue(Double::class.java.classLoader) as? Double,
            parcel.readValue(Double::class.java.classLoader) as? Double,
            parcel.readValue(Double::class.java.classLoader) as? Double,
            parcel.readValue(Double::class.java.classLoader) as? Double,
            parcel.readValue(Double::class.java.classLoader) as? Double,
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(dt)
        parcel.writeParcelable(temp, flags)
        parcel.writeValue(pressure)
        parcel.writeValue(humidity)
        parcel.writeTypedList(weather)
        parcel.writeValue(speed)
        parcel.writeValue(deg)
        parcel.writeValue(clouds)
        parcel.writeValue(rain)
        parcel.writeValue(snow)
        parcel.writeString(date)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<InfoWeather> {
        override fun createFromParcel(parcel: Parcel): InfoWeather {
            return InfoWeather(parcel)
        }

        override fun newArray(size: Int): Array<InfoWeather?> {
            return arrayOfNulls(size)
        }
    }
}

class DailyForecast(var cod: String?,
                    var message: Double?,
                    var city: City?,
                    var cnt: Int?,
                    var list: List<InfoWeather>) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readValue(Double::class.java.classLoader) as? Double,
            parcel.readParcelable(City::class.java.classLoader),
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.createTypedArrayList(InfoWeather)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(cod)
        parcel.writeValue(message)
        parcel.writeParcelable(city, flags)
        parcel.writeValue(cnt)
        parcel.writeTypedList(list)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DailyForecast> {
        override fun createFromParcel(parcel: Parcel): DailyForecast {
            return DailyForecast(parcel)
        }

        override fun newArray(size: Int): Array<DailyForecast?> {
            return arrayOfNulls(size)
        }
    }
}




