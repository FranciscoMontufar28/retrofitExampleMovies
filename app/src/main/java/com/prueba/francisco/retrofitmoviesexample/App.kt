package com.prueba.francisco.retrofitmoviesexample

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

class App:Application() {

    companion object{
        const val CHANNEL_ID : String = "channelID"
        lateinit var INSTANCE:App
    }

    override fun onCreate() {
        super.onCreate()
        createNotification()
        INSTANCE = this
    }

    private fun createNotification(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            var notificationChannel = NotificationChannel(
                CHANNEL_ID,
                "Simple notification",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.description = "This is channel notification"
            var manager : NotificationManager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(notificationChannel)
        }
    }
}