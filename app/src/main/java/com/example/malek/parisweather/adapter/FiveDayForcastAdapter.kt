package com.example.malek.parisweather.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.malek.parisweather.R
import com.example.malek.parisweather.models.InfoWeather
import com.squareup.picasso.Picasso
import java.util.*


class FiveDayForcastAdapter(val onInfoClickListener: OnInfoClickListener) : RecyclerView.Adapter<ViewHolder>() {
    var infos: ArrayList<InfoWeather> = ArrayList()

    fun addInfo(infoWeather: InfoWeather) {
        infos.add(infoWeather)
        notifyDataSetChanged()
    }

    fun clear() {
        infos = ArrayList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) = ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_weather, parent, false))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        infos.get(position).let {
            viewHolder.day.text = it.date
            it.weather?.let { list ->
                if (list.isNotEmpty()) {
                    viewHolder.mainCondition.text = list.get(0).main
                    viewHolder.description.text = list.get(0).description
                    viewHolder.temperature.text = String.format("%d°", it.temp?.day?.toInt())
                    Picasso.with(onInfoClickListener as Context)
                            .load("http://openweathermap.org/img/w/" + list.get(0).icon + ".png")
                            .into(viewHolder.icon)
                }

            }
            viewHolder.itemView.setOnClickListener {
                onInfoClickListener.onInfoClickListener(infos.get(position))
            }
        }

    }


    override fun getItemCount() = infos.size
}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val day = itemView.findViewById<TextView>(R.id.day)
    val mainCondition = itemView.findViewById<TextView>(R.id.mainCondition)
    val description = itemView.findViewById<TextView>(R.id.description)
    val temperature = itemView.findViewById<TextView>(R.id.temperature)
    val icon = itemView.findViewById<ImageView>(R.id.icon)

}

interface OnInfoClickListener {
    fun onInfoClickListener(infoWeather: InfoWeather)
}
