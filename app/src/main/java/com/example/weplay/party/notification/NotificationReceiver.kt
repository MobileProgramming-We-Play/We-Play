package com.example.weplay.party.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val notificationHelper = NotificationHelper(context)
        val builder = notificationHelper.getNotificationBuilder()

        /*val newIntent = Intent(context, MainActivity::class.java)
        newIntent.putExtra("time", "메인")
        newIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP

        val pendingIntent =
            PendingIntent.getActivity(context, 1, newIntent,
                PendingIntent.FLAG_UPDATE_CURRENT)
        builder.setContentIntent(pendingIntent)*/

        val notification = builder.build()

        val manager = notificationHelper.getManager()
        manager.notify(10, notification)
    }

}