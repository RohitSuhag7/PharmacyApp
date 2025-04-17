package com.example.pharmacyapp.utils.reminder

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.pharmacyapp.presentation.MainActivity
import com.example.pharmacyapp.utils.Constants

class NotificationHelper(private val context: Context) {

    private val notificationManager = context.getSystemService(NotificationManager::class.java)

    private val intent = Intent(context, MainActivity::class.java).apply {
        putExtra("navigate_to", "appointments_details")
    }

    // Create a PendingIntent to wrap the intent
    private val pendingIntent: PendingIntent = PendingIntent.getActivity(
        context,
        0,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )


    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotificationChannel() {
        val notificationChannel = NotificationChannel(
            Constants.NOTIFICATION_CHANNEL_ID,
            Constants.NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(notificationChannel)
    }

    fun showNotification(id: Int, title: String, contentText: String, icon: Int) {
        val notification = NotificationCompat.Builder(context, Constants.NOTIFICATION_CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(contentText)
            .setSmallIcon(icon)
//            .setContentIntent(pendingIntent)
            .setPriority(NotificationManager.IMPORTANCE_DEFAULT)
            .setAutoCancel(true)
            .build()

        Log.d("Alarm", "showNotification: $id")

        notificationManager.notify(id, notification)
    }
}
