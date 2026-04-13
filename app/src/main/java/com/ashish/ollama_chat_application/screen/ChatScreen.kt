package com.ashish.ollama_chat_application.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ashish.ollama_chat_application.network.ChatMessage
import com.ashish.ollama_chat_application.network.ChatRequest
import com.ashish.ollama_chat_application.view_model.ChatUiState
import com.ashish.ollama_chat_application.view_model.ChatViewModel

@Composable
fun ChatScreen(innerPadding: PaddingValues, viewModel: ChatViewModel) {
    var text by remember { mutableStateOf("") }
    val listState = rememberLazyListState()

    val uiState by viewModel.chatUiState.collectAsState()

    Column(modifier = Modifier
        .background(MaterialTheme.colorScheme.onPrimaryFixedVariant)
        .fillMaxSize()
        .padding(innerPadding)) {
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
        ) {
            items(items = viewModel.messages){
                    msg ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = if (msg.isUser) Arrangement.End else Arrangement.Start
                ) {
                    Text(
                        text = msg.message,
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                if (msg.isUser) Color(0xFFBBDEFB) else Color(
                                    0xFFE0E0E0
                                )
                            )
                            .padding(8.dp),
                    )
                }
            }
        }
        LaunchedEffect(viewModel.messages.size) {
            if (viewModel.messages.isNotEmpty()) {
                listState.animateScrollToItem(viewModel.messages.size - 1)
            }
        }
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            BasicTextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
                    .background(Color(0xFFF0F0F0))
                    .padding(8.dp)
            )
            Button(onClick = {
                if (text.isNotBlank()) {
                    val userMsg = ChatMessage(text, "ashish",true)
                    viewModel.messages.add(userMsg)
                    viewModel.sendMessageToAi(ChatRequest(text,userMsg.sessionId))
                    text = ""
                }
            }) {
                Text("Send")
            }
        }
    }

    when(uiState){
        is ChatUiState.ChatResponse -> {}
        is ChatUiState.Error -> {}
        is ChatUiState.HistoryState -> {}
        ChatUiState.Initial -> {}
        ChatUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}