package com.example.pharmacyapp.domain.usecases

import com.example.pharmacyapp.data.model.ChatMessage
import com.example.pharmacyapp.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChatUseCase @Inject constructor(private val chatRepository: ChatRepository) {

    suspend fun sendMessage(doctorId: String, chatMessage: ChatMessage) {
        chatRepository.sendMessage(doctorId, chatMessage)
    }

    fun getMessages(doctorId: String): Flow<List<ChatMessage>> {
        return chatRepository.getMessages(doctorId)
    }
}
