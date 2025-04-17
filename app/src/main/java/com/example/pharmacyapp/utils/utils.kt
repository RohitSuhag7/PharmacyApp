package com.example.pharmacyapp.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun timeFormatter(time: Long): String {
    val date = Date(time)
    return SimpleDateFormat("HH:mm", Locale.ROOT).format(date)
}
