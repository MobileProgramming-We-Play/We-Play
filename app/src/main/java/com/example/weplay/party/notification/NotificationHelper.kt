package com.example.weplay.party.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
import androidx.core.app.NotificationCompat
import com.example.weplay.R

class NotificationHelper(base: Context?) : ContextWrapper(base) {

    private val id = "MyChannel"
    private lateinit var manager: NotificationManager

    init {
        createChannels()
    }

    private fun createChannels() {
        val name = "TimeCheckChannel"
        val notificationChannel =
            NotificationChannel(id, name, NotificationManager.IMPORTANCE_DEFAULT)
        notificationChannel.enableVibration(true)
        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.BLUE
        notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE

        manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(notificationChannel)
    }

    public fun getManager(): NotificationManager {
        return manager
    }

    public fun getNotificationBuilder(): NotificationCompat.Builder {
        val builder = NotificationCompat.Builder(this, id)
            .setSmallIcon(R.drawable.ic_baseline_access_alarm_24)
            .setContentTitle("일정 알람")
            .setContentText("내일 모임 잊지 않으셨죠?")
            .setAutoCancel(true)

        return builder
    }
}