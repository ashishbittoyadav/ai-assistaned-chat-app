package com.ashish.ollama_chat_application.uiState

import com.ashish.ollama_chat_application.network.DragonBallZApiResponse

sealed interface DragonBallUiState {

    object Loading : DragonBallUiState

    data class Error(val error: String) : DragonBallUiState

    data class Characters(val characters: DragonBallZApiResponse) : DragonBallUiState

}