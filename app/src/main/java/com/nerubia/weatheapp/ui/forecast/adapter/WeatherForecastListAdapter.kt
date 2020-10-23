package com.nerubia.weatheapp.ui.forecast.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nerubia.weatheapp.R
import com.nerubia.weatheapp.data.model.WeatherForecastModel
import kotlinx.android.synthetic.main.layout_weather_forecast.view.*

class WeatherForecastListAdapter(
    private val listener: WeatherForecastAdapterClickListener
) : RecyclerView.Adapter<WeatherForecastListAdapter.WeatherForecastViewHolder>() {
    private var list: List<WeatherForecastModel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherForecastViewHolder =
        WeatherForecastViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_weather_forecast, parent, false)
        )

    override fun onBindViewHolder(holder: WeatherForecastViewHolder, position: Int) {
        holder.itemView.weatherForecastTemperatureTextView.text =
            String.format(
                holder.itemView.resources.getString(R.string.weather_temperature),
                list[position].main.temp
            )
        holder.itemView.weatherForecastPlaceTextView.text = list[position].name
        holder.itemView.weatherForecastStatusTextView.text =
            list[position].weather.find { true }?.main

        val backgroundColor = if (list[position].main.temp <= 0) {
            R.color.colorFreezing
        } else if (list[position].main.temp > 0 && list[position].main.temp <= 15) {
            R.color.colorCold
        } else if (list[position].main.temp > 15 && list[position].main.temp <= 30) {
            R.color.colorWarm
        } else {
            R.color.colorHot
        }

        holder.itemView.setBackgroundColor(
            ContextCompat.getColor(
                holder.itemView.context,
                backgroundColor
            )
        )

        holder.itemView.weatherForecastImageView.visibility = if(list[position].favorite) {
            View.VISIBLE
        } else {
            View.GONE
        }

        holder.itemView.setOnClickListener {
            listener.onClickItemListener(list[position])
        }
    }

    fun addAll(itemList: List<WeatherForecastModel>) {
        dispatchUpdate(itemList, list)
        list = itemList
    }

    private fun dispatchUpdate(
        expectedList: List<WeatherForecastModel>,
        oldList: List<WeatherForecastModel>
    ) {
        val result = DiffUtil.calculateDiff(DiffUtils(expectedList, oldList))
        result.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int = list.size

    class WeatherForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private class DiffUtils<T>(
        var newData: List<T>,
        var oldData: List<T>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldData.size

        override fun getNewListSize(): Int = newData.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int):
                Boolean = oldData[oldItemPosition].toString() == newData[newItemPosition].toString()

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int):
                Boolean = oldData[oldItemPosition].toString() == newData[newItemPosition].toString()
    }

    interface WeatherForecastAdapterClickListener {
        fun onClickItemListener(item: WeatherForecastModel)
    }
}