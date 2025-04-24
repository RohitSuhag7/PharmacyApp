package com.example.pharmacyapp.domain.repository

import com.example.pharmacyapp.data.model.ChatMessage
import kotlinx.coroutines.flow.Flow

interface ChatRepository {

    suspend fun sendMessage(doctorId: String, chatMessage: ChatMessage)

    fun getMessages(doctorId: String): Flow<List<ChatMessage>>
}
