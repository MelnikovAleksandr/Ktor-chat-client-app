package ru.asmelnikov.ktor_chat_client_app.data.remote

import kotlinx.coroutines.flow.Flow
import ru.asmelnikov.ktor_chat_client_app.domain.model.Message
import ru.asmelnikov.ktor_chat_client_app.util.Resource

interface ChatSocketService {

    suspend fun initSession(
        username: String
    ): Resource<Unit>

    suspend fun sendMessage(
        message: String
    )

    fun observeMessages(): Flow<Message>

    suspend fun closeSession()

    companion object {
        const val BASE_URL = "ws://172.20.0.46:8080"
    }

    sealed class Endpoints(val url: String) {
        object ChatSocket : Endpoints("$BASE_URL/chat-socket")
    }

}