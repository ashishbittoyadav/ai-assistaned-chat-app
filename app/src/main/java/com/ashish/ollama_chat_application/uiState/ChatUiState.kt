package com.ashish.ollama_chat_application.uiState

import com.ashish.ollama_chat_application.network.ApiResponse
import com.ashish.ollama_chat_application.network.ChatHistory

sealed interface ChatUiState {
    data class ChatResponse(val data: ApiResponse) : ChatUiState
    data class HistoryState(val history: List<ChatHistory>): ChatUiState
    object Loading : ChatUiState
    data class Error(val error: String) : ChatUiState

    object Initial : ChatUiState
}
