package ru.asmelnikov.ktor_chat_client_app.presentation.chat

import ru.asmelnikov.ktor_chat_client_app.domain.model.Message

data class ChatState(
    val messages: List<Message> = emptyList(),
    val isLoading: Boolean = false
)
