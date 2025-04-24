package com.example.pharmacyapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pharmacyapp.data.model.ChatMessage
import com.example.pharmacyapp.domain.usecases.ChatUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(private val chatUseCase: ChatUseCase) : ViewModel() {

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages

    private val _messageText = MutableStateFlow("")
    val messageText: StateFlow<String> = _messageText

    // Update the message text as the user types
    fun onMessageChange(newText: String) {
        _messageText.value = newText
    }

    private val currentUserId: String? = FirebaseAuth.getInstance().currentUser?.uid

    fun sendMessage(doctorId: String) {
        val userId = currentUserId ?: return

        val messageText = _messageText.value
        if (messageText.isBlank()) return

        val message = ChatMessage(
            messageId = UUID.randomUUID().toString(),
            senderId = userId,
            text = messageText,
            replyText = replayMessage(messageText),
            timestamp = System.currentTimeMillis()
        )

        viewModelScope.launch {
            try {
                chatUseCase.sendMessage(doctorId, message)
                _messageText.value = ""
            } catch (e: Exception) {
                Log.e("ChatViewModel", "Error sending message: ${e.message}")
            }
        }
    }

    private fun replayMessage(text: String): String {
        if (text.lowercase().contains("hi") || text.lowercase().contains("hello")) {
            return "Hello! How can I help you?"
        } else if (text.lowercase().contains("fever") || text.lowercase().contains("cold") || text.lowercase().contains("flu")) {
            return "Please take Ibuprofen(Anti-inflammatory)."
        } else if (text.lowercase().contains("headache") || text.lowercase().contains("pain")) {
            return "Please take Aspirin or Paracetamol. \n Take any one of them."
        } else if (text.lowercase().contains("diabetes")) {
            return "Please take Metformin."
        } else if (text.lowercase().contains("infection")) {
            return "Please take Amoxicillin antibiotic."
        } else if (text.lowercase().contains("high blood pressure")) {
            return "Please take Hydrochlorothiazide and visit clinic."
        }
        return "Please visit Clinic"
    }

    fun getMessages(doctorId: String) {
        viewModelScope.launch {
            chatUseCase.getMessages(doctorId)
                .collect { messages ->
                    _messages.value = messages.sortedBy { it.timestamp }
                }
        }
    }
}
