package com.prueba.francisco.retrofitmoviesexample

import android.content.Context
import android.content.IntentFilter
import android.net.wifi.WifiManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.prueba.francisco.retrofitmoviesexample.util.ConnectivityDetection
import kotlinx.android.synthetic.main.activity_movies.*


class MoviesActivity : AppCompatActivity() {
    private val connectivityDetention = ConnectivityDetection()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)
        val navController = this.findNavController(R.id.navHostFragment)
        NavigationUI.setupActionBarWithNavController(this, navController)
        bnvMovies.setupWithNavController(navController)

    }

    override fun onResume() {
        super.onResume()
        val filter = IntentFilter(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION)
        this.registerReceiver(connectivityDetention, filter)
    }

    override fun onPause() {
        this.unregisterReceiver(connectivityDetention)
        super.onPause()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.navHostFragment)
        return navController.navigateUp()
    }
}