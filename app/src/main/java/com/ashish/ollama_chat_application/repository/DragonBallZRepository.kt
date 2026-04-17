package com.ashish.ollama_chat_application.repository

import com.ashish.ollama_chat_application.network.DragonBallZApi
import com.ashish.ollama_chat_application.network.DragonBallZApiResponse
import retrofit2.Call
import javax.inject.Inject

class DragonBallZRepository @Inject constructor(val dragonBallZApi: DragonBallZApi) {

    suspend fun getCharacters(): DragonBallZApiResponse {
        return dragonBallZApi.getCharacters()
    }

}