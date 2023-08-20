package ru.asmelnikov.ktor_chat_client_app.data.remote

import io.ktor.client.*
import io.ktor.client.request.*
import ru.asmelnikov.ktor_chat_client_app.data.remote.dto.MessageDto
import ru.asmelnikov.ktor_chat_client_app.domain.model.Message

class MessageServiceImpl(
    private val client: HttpClient
) : MessageService {

    override suspend fun getAllMessages(): List<Message> {
        return try {
            client.get<List<MessageDto>>(MessageService.Endpoints.GetAllMessages.url).map {
                it.toMessage()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}