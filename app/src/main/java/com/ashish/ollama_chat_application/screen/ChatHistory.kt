package com.ashish.ollama_chat_application.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ashish.ollama_chat_application.view_model.ChatUiState
import com.ashish.ollama_chat_application.view_model.ChatViewModel


@Composable
fun ChatHistory(innerPadding: PaddingValues, viewModel: ChatViewModel) {

    val uiState by viewModel.chatUiState.collectAsState()

    // Trigger API once
    LaunchedEffect(Unit) {
        viewModel.getChatHistory()
    }

    when (uiState) {

        is ChatUiState.Loading -> {
            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize()
            )
        }

        is ChatUiState.HistoryState -> {

            val history = (uiState as ChatUiState.HistoryState).history.reversed()

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.onPrimaryFixedVariant)
                    .padding(innerPadding)
                    .padding(start = 8.dp, end = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(history) { chatHistory ->

                    Spacer(modifier = Modifier.padding(8.dp))

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.primary)
                            .padding(8.dp)
                    ) {
                        Column(modifier = Modifier.fillMaxWidth()) {

                            var expanded by remember { mutableStateOf(false) }

                            Text(
                                text = "Question: ${chatHistory.question}",
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.inversePrimary
                            )

                            Spacer(modifier = Modifier.padding(top = 8.dp))

                            Row {
                                Text(
                                    text = "AI Response: ",
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.inversePrimary
                                )

                                Text(
                                    modifier = Modifier.clickable {
                                        expanded = !expanded
                                    },
                                    maxLines = if (expanded) Int.MAX_VALUE else 1,
                                    text = chatHistory.response,
                                    color = MaterialTheme.colorScheme.inversePrimary
                                )
                            }
                        }
                    }
                }
            }
        }

        is ChatUiState.Error -> {
            Text("Error loading chat")
        }

        else -> {}
    }
}