package com.ashish.ollama_chat_application.model

data class ChatMessage(
    val message: String,
    val sessionId: String,
    val isUser: Boolean
)
