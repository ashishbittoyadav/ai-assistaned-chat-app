package com.ashish.ollama_chat_application.network

data class DragonBallZApiResponse(
    val items: List<Item>,
    val links: Links,
    val meta: Meta
)