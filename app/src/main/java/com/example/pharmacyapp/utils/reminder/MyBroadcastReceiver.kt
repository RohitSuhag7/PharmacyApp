package com.example.pharmacyapp.utils.reminder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.pharmacyapp.R
import com.example.pharmacyapp.utils.Constants

class MyBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        val id = p1?.getIntExtra(Constants.ID_KEY, 1)
        val title = p1?.getStringExtra(Constants.TITLE_KEY)

        NotificationHelper(p0!!).showNotification(
            id = id ?: Constants.NOTIFICATION_ID,
            title = "Pharmacy",
            contentText = title ?: Constants.TITLE_KEY,
            icon = R.drawable.ic_notifications
        )
    }
}
