package com.ashish.ollama_chat_application.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

open class AIResponse(
    open val response: String
): ApiResponse()

data class ChatHistory(val sessionId: String, val question: String, val response: String):
    ApiResponse()

data class ChatRequest(val question: String, val sessionId: String)

interface ChatApi {
    @POST("ai/chat")
    fun sendMessage(@Body request: ChatRequest): Call<AIResponse>

    @GET("ai/history")
    fun getHistory(): Call<List<ChatHistory>>
}