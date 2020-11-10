package com.nerubia.weatheapp.ui.forecast.adapter

import android.annotation.SuppressLint
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

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: WeatherForecastViewHolder, position: Int) {
        holder.itemView.apply {
            weatherForecastTemperatureTextView.text = "${list[position].main.temp} °C"
            weatherForecastPlaceTextView.text = list[position].name
            weatherForecastStatusTextView.text =
                list[position].weather.find { true }?.main

            setBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    getTempColor(position)
                )
            )

            weatherForecastImageView.visibility = if (list[position].favorite) {
                View.VISIBLE
            } else {
                View.GONE
            }

            setOnClickListener {
                listener.onClickItemListener(list[position])
            }
        }
    }

    private fun getTempColor(position: Int): Int = if (list[position].main.temp <= 0) {
        R.color.freezing
    } else if (list[position].main.temp > 0 && list[position].main.temp <= 15) {
        R.color.cold
    } else if (list[position].main.temp > 15 && list[position].main.temp <= 30) {
        R.color.warm
    } else {
        R.color.hot
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