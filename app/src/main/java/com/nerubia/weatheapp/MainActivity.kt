package com.nerubia.weatheapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val navController by lazy {
        findNavController(R.id.fragment)
    }

    private val appBarConfig by lazy {
        AppBarConfiguration.Builder().setFallbackOnNavigateUpListener {
            finish()
            true
        }.build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController, appBarConfig)
        setupToolbar()
    }

     fun setupToolbar() {
        navController.addOnDestinationChangedListener { _,destination, _ ->
            when(destination.id) {
                R.id.weatherForecastFragment -> {
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                }
                else -> {
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfig) || super.onSupportNavigateUp()
    }
}