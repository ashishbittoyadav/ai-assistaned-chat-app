package com.ashish.ollama_chat_application.view_model

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashish.ollama_chat_application.model.ChatMessage
import com.ashish.ollama_chat_application.network.AIResponse
import com.ashish.ollama_chat_application.network.ApiResponse
import com.ashish.ollama_chat_application.network.ChatHistory
import com.ashish.ollama_chat_application.network.ChatRequest
import com.ashish.ollama_chat_application.repository.ChatRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

sealed interface ChatUiState {
    data class ChatResponse(val data: ApiResponse) : ChatUiState
    data class HistoryState(val history: List<ChatHistory>): ChatUiState
    object Loading : ChatUiState
    data class Error(val error: String) : ChatUiState

    object Initial : ChatUiState
}

class ChatViewModel() : ViewModel() {

    private val chatRepository: ChatRepository= ChatRepository();

    var chatUiState = MutableStateFlow<ChatUiState>(ChatUiState.Initial)

    val messages =  mutableStateListOf<ChatMessage>()

    fun sendMessageToAi(request: ChatRequest) {
        viewModelScope.launch {
            chatUiState.value = ChatUiState.Loading
            chatRepository.chatWithLlama(request)
                .enqueue(object : retrofit2.Callback<AIResponse> {
                    override fun onResponse(call: Call<AIResponse>, response: Response<AIResponse>) {
                        response.body().let { response ->
                            if (response != null) {
                                messages.add(ChatMessage(response.response, "ashish",false))
                                chatUiState.value = ChatUiState.ChatResponse(response)
                            } else {
                                chatUiState.value = ChatUiState.Error("response is null")
                            }
                        }
                    }

                    override fun onFailure(call: Call<AIResponse>, t: Throwable) {
                        chatUiState.value =
                            ChatUiState.Error(t.message ?: t.cause?.message ?: "some thing went wrong.")
                    }
                })
        }
    }

    fun getChatHistory() {
        viewModelScope.launch {
            chatUiState.value = ChatUiState.Loading
            chatRepository.getHistory()
                .enqueue(
                    object : retrofit2.Callback<List<ChatHistory>> {
                        override fun onResponse(
                            call: Call<List<ChatHistory>?>,
                            response: Response<List<ChatHistory>?>
                        ) {
                            response.body().let { response ->
                                if (response != null) {
                                    chatUiState.value = ChatUiState.HistoryState(response)
                                } else {
                                    chatUiState.value = ChatUiState.Error("response is null")
                                }
                            }
                        }

                        override fun onFailure(
                            call: Call<List<ChatHistory>?>,
                            t: Throwable
                        ) {
                            Log.d("UiState.TAG", "onResponse result: $t")
                            chatUiState.value = ChatUiState.Error(
                                t.message ?: t.cause?.message ?: "something went wrong."
                            )
                        }

                    }
                )
        }
    }

}