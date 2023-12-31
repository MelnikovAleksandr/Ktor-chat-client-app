package ru.asmelnikov.ktor_chat_client_app.data.remote.dto

import kotlinx.serialization.Serializable
import ru.asmelnikov.ktor_chat_client_app.domain.model.Message
import java.text.DateFormat
import java.util.Date

@Serializable
data class MessageDto(
    val text: String,
    val timestamp: Long,
    val username: String,
    val id: String
) {
    fun toMessage(): Message {
        val date = Date(timestamp)
        val formattedDate = DateFormat.getDateInstance(DateFormat.DEFAULT).format(date)
        return Message(
            text = text,
            username = username,
            formattedTime = formattedDate
        )
    }
}
