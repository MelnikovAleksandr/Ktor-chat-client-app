package ru.asmelnikov.ktor_chat_client_app.data.remote

import io.ktor.client.*
import io.ktor.client.features.websocket.*
import io.ktor.http.cio.websocket.*
import io.ktor.client.request.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.isActive
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import ru.asmelnikov.ktor_chat_client_app.data.remote.dto.MessageDto
import ru.asmelnikov.ktor_chat_client_app.domain.model.Message
import ru.asmelnikov.ktor_chat_client_app.util.Resource

class ChatSocketServiceImpl(
    private val client: HttpClient
) : ChatSocketService {

    private var socket: WebSocketSession? = null

    override suspend fun initSession(username: String): Resource<Unit> {
        return try {
            socket = client.webSocketSession {
                url("${ChatSocketService.Endpoints.ChatSocket.url}?username=$username")
            }
            if (socket?.isActive == true) {
                Resource.Success(Unit)
            } else Resource.Error("Couldn't establish a connection")
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.localizedMessage ?: "Unknown error")
        }
    }

    override suspend fun sendMessage(message: String) {
        try {
            socket?.send(Frame.Text(message))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun observeMessages(): Flow<Message> {
        return try {
            socket?.incoming?.receiveAsFlow()?.filter { it is Frame.Text }?.map {
                val json = (it as? Frame.Text)?.readText() ?: ""
                val messageDto = Json.decodeFromString<MessageDto>(json)
                messageDto.toMessage()
            } ?: flow {}
        } catch (e: Exception) {
            e.printStackTrace()
            flow {}
        }
    }

    override suspend fun closeSession() {
        socket?.close()
    }
}