package com.nerubia.weatheapp.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import com.nerubia.weatheapp.MainActivity
import com.nerubia.weatheapp.R
import com.nerubia.weatheapp.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_weather_forecast.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class WeatherForecastDetailsFragment : BaseFragment() {
    private val args: WeatherForecastDetailsFragmentArgs by navArgs()

    override val viewModel: WeatherForecastDetailsViewModel by viewModel {
        parametersOf(args.argumentWeatherId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_weather_forecast, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.weatherUpdateLiveData.observe(viewLifecycleOwner, {
            weatherForecastPlaceTextView.text = it.name
            weatherForecastTemperatureTextView.text = String.format(getString(R.string.weather_temperature), it.main.temp)
            weatherForecastStatusTextView.text = it.weather.find { true }?.main
            weatherForecastHighLowTemperatureTextView.text =
                String.format(getString(R.string.weather_min_max_temperature), it.main.tempMax.toInt(), it.main.tempMin.toInt())
        })

        viewModel.isFavoriteLiveData.observe(viewLifecycleOwner, {
            weatherForecastImageView.setImageDrawable(ContextCompat.getDrawable(requireContext(),
                if(it) {
                    R.drawable.ic_star_fill
                } else {
                    R.drawable.ic_star_empty
                }))
        })

        weatherForecastImageView.setOnClickListener {
            viewModel.onClickFavorite()
        }
    }
}