package com.data.store

import com.domain.model.Message
import io.reactivex.Observable

interface MessageDataStore {
    fun observeMessage(messageId: String): Observable<Message>
    fun insertMessage(message: Message)
    fun updateSeen(messageId: String, seen: Boolean)
}
