package com.nerubia.weatheapp.ui.forecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.nerubia.weatheapp.MainActivity
import com.nerubia.weatheapp.R
import com.nerubia.weatheapp.data.model.WeatherForecastModel
import com.nerubia.weatheapp.ui.base.BaseFragment
import com.nerubia.weatheapp.ui.forecast.adapter.WeatherForecastListAdapter
import kotlinx.android.synthetic.main.fragment_weather_list.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

class WeatherForecastFragment : BaseFragment() {
    override val viewModel: WeatherForecastViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_weather_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        weatherForecastSwipreRefreshLayout.setOnRefreshListener {
            viewModel.loadWeatherUpdate()
        }

        val adapter = WeatherForecastListAdapter(
            object : WeatherForecastListAdapter.WeatherForecastAdapterClickListener {
                override fun onClickItemListener(item: WeatherForecastModel) {
                    val direction = WeatherForecastFragmentDirections
                        .actionWeatherListFragmentToWeatherForecastFragment(item.id)
                    findNavController().navigate(direction)
                }
            })

        weatherForecastListRecyclerView.adapter = adapter

        viewModel.weatherForecastListLiveData.observe(viewLifecycleOwner, {
            weatherForecastSwipreRefreshLayout.isRefreshing = false
            adapter.addAll(it)
        })

        viewModel.observe()
    }
}