package ru.asmelnikov.ktor_chat_client_app.data.remote

import ru.asmelnikov.ktor_chat_client_app.domain.model.Message

interface MessageService {

    suspend fun getAllMessages(): List<Message>

    companion object {
        const val BASE_URL = "http://172.20.0.46:8080"
    }

    sealed class Endpoints(val url: String) {
        object GetAllMessages : Endpoints("$BASE_URL/messages")
    }

}