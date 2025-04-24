package com.example.pharmacyapp.data.model

data class ChatMessage(
    val messageId: String = "",
    val senderId: String = "",
    val text: String = "",
    val replyText: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
