package com.example.pharmacyapp.data.repositoryImp

import com.example.pharmacyapp.data.model.ChatMessage
import com.example.pharmacyapp.domain.repository.ChatRepository
import com.example.pharmacyapp.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class ChatRepositoryImp @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : ChatRepository {

    private val userId = firebaseAuth.currentUser?.uid
        ?: throw IllegalStateException("User is not authenticated")

    private val userRef = firestore.collection(Constants.FIRESTORE_COLLECTION).document(userId)
    private val chatCollection = userRef.collection(Constants.FIRESTORE_CHATS_COLLECTION)

    override suspend fun sendMessage(doctorId: String, chatMessage: ChatMessage) {
        val chatRef = chatCollection.document(doctorId)
        val messageRef = chatRef.collection(Constants.FIRESTORE_MESSAGE_COLLECTION)
            .document(chatMessage.messageId)

        firestore.runBatch { batch ->
            batch.set(messageRef, chatMessage)
            batch.set(
                chatRef, mapOf(
                    "userId" to userId,
                    "doctorId" to doctorId,
                    "lastMessage" to chatMessage.text,
                    "lastReplyMessage" to chatMessage.replyText,
                    "lastTimestamp" to chatMessage.timestamp
                ), SetOptions.merge()
            )
        }
    }

    override fun getMessages(doctorId: String): Flow<List<ChatMessage>> = callbackFlow {

        val messagesRef = chatCollection.document(doctorId)
            .collection(Constants.FIRESTORE_MESSAGE_COLLECTION)
            .orderBy("timestamp")

        val listener = messagesRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }

            val messages = snapshot?.documents?.mapNotNull {
                it.toObject(ChatMessage::class.java)
            } ?: emptyList()

            trySend(messages)
        }

        awaitClose { listener.remove() }
    }
}
