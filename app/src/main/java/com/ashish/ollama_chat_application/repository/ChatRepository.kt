package com.ashish.ollama_chat_application.repository

import com.ashish.ollama_chat_application.network.AIResponse
import com.ashish.ollama_chat_application.network.ChatHistory
import com.ashish.ollama_chat_application.network.ChatRequest
import com.ashish.ollama_chat_application.network.*
import retrofit2.Call
import javax.inject.Inject

class ChatRepository @Inject constructor(val retrofitInstance: RetrofitInstance) {

    suspend fun getHistory(): Call<List<ChatHistory>> {
        return retrofitInstance.getApi().getHistory()
    }

    suspend fun chatWithLlama(request: ChatRequest):Call<AIResponse>{
        return retrofitInstance.getApi().sendMessage(request)
    }

}