package ru.asmelnikov.ktor_chat_client_app.domain.model

data class Message(
    val text: String,
    val formattedTime: String,
    val username: String
)
