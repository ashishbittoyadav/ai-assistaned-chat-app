package com.ashish.ollama_chat_application.network

import retrofit2.Call
import retrofit2.http.GET

interface DragonBallZApi {

    @GET("characters/")
    suspend fun getCharacters(): DragonBallZApiResponse
}