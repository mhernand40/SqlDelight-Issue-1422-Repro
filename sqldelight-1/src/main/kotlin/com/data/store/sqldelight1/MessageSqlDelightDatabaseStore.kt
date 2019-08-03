package com.data.store.sqldelight1

import com.data.store.MessageDataStore
import com.domain.model.Message
import com.sqldelight1.Database
import com.squareup.sqldelight.runtime.rx.asObservable
import com.squareup.sqldelight.runtime.rx.mapToOne
import io.reactivex.Observable
import io.reactivex.Scheduler

class MessageSqlDelightDatabaseStore(
    private val db: Database,
    private val scheduler: Scheduler
): MessageDataStore {

    override fun observeMessage(messageId: String): Observable<Message> {
        return db.messageQueries.select_message_by_id(id = messageId)
            .asObservable(scheduler)
            .mapToOne()
            .map {
                Message(
                    id = it.id,
                    text = it.text,
                    seen = it.seen
                )
            }
    }

    override fun insertMessage(message: Message) {
        db.messageQueries.insert_message(
            id = message.id,
            text = message.text,
            seen = message.seen
        )
    }

    override fun updateSeen(messageId: String, seen: Boolean) {
        db.messageQueries.update_message_seen(id = messageId, seen = seen)
    }
}
