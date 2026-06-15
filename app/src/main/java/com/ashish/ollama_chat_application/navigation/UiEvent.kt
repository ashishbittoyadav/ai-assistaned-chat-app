package com.ashish.ollama_chat_application.navigation

sealed class UiEvent {
    data object StartScreen: UiEvent()
    data object NavigationChat: UiEvent()
    data object NavigationChatHistory: UiEvent()
    data object NavigationDragonBallZ: UiEvent()
    data class NavigationDragonBallZDetail(val id: Int): UiEvent()
}