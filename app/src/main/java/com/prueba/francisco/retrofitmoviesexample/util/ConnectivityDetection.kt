package com.prueba.francisco.retrofitmoviesexample.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.widget.Toast

class ConnectivityDetection: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        Toast.makeText(context, "The app is connected $isConnected", Toast.LENGTH_SHORT).show()
        when(activeNetwork?.type){
            ConnectivityManager.TYPE_WIFI -> {Toast.makeText(context, "Wifi", Toast.LENGTH_SHORT).show()}
            ConnectivityManager.TYPE_MOBILE -> {Toast.makeText(context, "Mobile", Toast.LENGTH_SHORT).show()}
            else -> {Toast.makeText(context, "Not Connected", Toast.LENGTH_SHORT).show()}
        }
    }
}