package io.music_assistant.client

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import io.music_assistant.client.di.androidModule
import org.koin.android.ext.koin.androidContext
import io.music_assistant.client.di.initKoin
import io.music_assistant.client.services.MediaNotificationManager

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin(androidModule()) {
            androidContext(this@MyApplication)
        }
        createNotificationChannel(this)
    }

    private fun createNotificationChannel(context: Context) {
        val channel = NotificationChannel(
            MediaNotificationManager.CHANNEL_ID,
            "Media Playback",
            NotificationManager.IMPORTANCE_LOW
        )
        val manager = context.getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }
}