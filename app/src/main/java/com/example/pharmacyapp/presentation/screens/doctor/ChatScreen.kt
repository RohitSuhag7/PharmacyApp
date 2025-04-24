package com.example.pharmacyapp.presentation.screens.doctor

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pharmacyapp.data.model.ChatMessage
import com.example.pharmacyapp.data.model.Doctor
import com.example.pharmacyapp.presentation.theme.LightBlue
import com.example.pharmacyapp.presentation.viewmodel.ChatViewModel
import com.google.gson.Gson

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(navController: NavController, doctorsJsonString: String?) {

    val doctorData = Gson().fromJson(doctorsJsonString, Doctor::class.java)

    val doctorId by remember { mutableStateOf(doctorData?.id ?: "") }
    val doctorFirstName by remember { mutableStateOf(doctorData?.firstName ?: "") }
    val doctorLastName by remember { mutableStateOf(doctorData?.lastName ?: "") }

    val viewModel: ChatViewModel = hiltViewModel()

    val messages by viewModel.messages.collectAsState()
    val messageText by viewModel.messageText.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getMessages(doctorId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "$doctorFirstName $doctorLastName",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back Screen Icon",
                        tint = Color.White,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .clickable {
                                navController.popBackStack()
                            }
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = LightBlue)
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                reverseLayout = false
            ) {
                items(messages.size) { message ->
                    ChatBubble(
                        message = messages[message],
                        isOwn = messages[message].senderId != doctorId
                    )
                    ChatBubble(
                        message = messages[message],
                        isOwn = messages[message].senderId == doctorId
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = messageText,
                    onValueChange = viewModel::onMessageChange,
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Type your message") }
                )

                Spacer(modifier = Modifier.width(4.dp))

                Button(onClick = {
                    if (messageText.isNotBlank()) {
                        viewModel.sendMessage(doctorId)
                    }
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = "send message"
                    )
                }
            }
        }
    }
}

@Composable
fun ChatBubble(message: ChatMessage, isOwn: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isOwn) Arrangement.End else Arrangement.Start
    ) {
        Text(
            text = if (isOwn) message.text else message.replyText,
            modifier = Modifier
                .padding(8.dp)
                .background(
                    if (isOwn) Color.Blue else Color.LightGray,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(10.dp),
            color = if (isOwn) Color.White else Color.Black
        )
    }
}
