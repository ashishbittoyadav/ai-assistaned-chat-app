package com.ashish.ollama_chat_application.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashish.ollama_chat_application.network.DragonBallZApi
import com.ashish.ollama_chat_application.network.DragonBallZApiResponse
import com.ashish.ollama_chat_application.network.Item
import com.ashish.ollama_chat_application.uiState.DragonBallUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DragonBallZViewModel @Inject constructor(private val dragonBallZApi: DragonBallZApi): ViewModel() {

    var dragonBallUiState = MutableStateFlow<DragonBallUiState>(DragonBallUiState.Loading)

    private lateinit var dragonBallZApiResponse : DragonBallZApiResponse

    val selectedCharacter = MutableStateFlow<Item?>(null)
    fun getDragonBallZCharacters() {
        viewModelScope.launch {
            dragonBallZApiResponse = dragonBallZApi.getCharacters()
            dragonBallUiState.value = DragonBallUiState.Characters(dragonBallZApiResponse)
        }
    }

    fun selectCharacter(itemIndex: Int) {
        selectedCharacter.value = dragonBallZApiResponse.items.find {  it.id == itemIndex }
    }


}