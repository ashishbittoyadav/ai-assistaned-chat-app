package com.ashish.ollama_chat_application.network

data class ChatMessage(
    val message: String,
    val sessionId: String,
    val isUser: Boolean
)
//data class ChatMessage(
//    val message: String="",
//    val sessionId: String="",
//    val isUser: Boolean=false
//)