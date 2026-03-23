package com.ashish.ollama_chat_application.repository

import com.ashish.ollama_chat_application.network.AIResponse
import com.ashish.ollama_chat_application.network.ChatHistory
import com.ashish.ollama_chat_application.network.ChatRequest
import com.ashish.ollama_chat_application.network.RetrofitInstance
import retrofit2.Call

class ChatRepository {

    suspend fun getHistory(): Call<List<ChatHistory>> {
        return RetrofitInstance.api.getHistory()
    }

    suspend fun chatWithLlama(request: ChatRequest):Call<AIResponse>{
        return RetrofitInstance.api.sendMessage(request)
    }

}