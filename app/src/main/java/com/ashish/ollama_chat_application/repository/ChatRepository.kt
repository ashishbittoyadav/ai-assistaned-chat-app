package com.ashish.ollama_chat_application.repository

import com.ashish.ollama_chat_application.network.AIResponse
import com.ashish.ollama_chat_application.network.ChatHistory
import com.ashish.ollama_chat_application.network.ChatRequest
import com.ashish.ollama_chat_application.network.*
import retrofit2.Call
import javax.inject.Inject

class ChatRepository @Inject constructor(val chatApi: ChatApi) {

    suspend fun getHistory(): Call<List<ChatHistory>> {
        return chatApi.getHistory()
    }

    suspend fun chatWithLlama(request: ChatRequest):Call<AIResponse>{
        return chatApi.sendMessage(request)
    }

}