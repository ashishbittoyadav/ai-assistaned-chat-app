package com.ashish.ollama_chat_application.network

data class Meta(
    val currentPage: Int,
    val itemCount: Int,
    val itemsPerPage: Int,
    val totalItems: Int,
    val totalPages: Int
)