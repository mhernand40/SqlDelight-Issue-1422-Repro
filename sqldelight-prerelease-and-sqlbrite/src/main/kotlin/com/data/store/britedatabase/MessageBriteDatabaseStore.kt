package com.data.store.britedatabase

import com.data.store.MessageDataStore
import com.domain.model.Message
import com.sqldelightissuerepros.MessageModel
import com.squareup.sqlbrite3.BriteDatabase
import io.reactivex.Observable

class MessageBriteDatabaseStore(private val db: BriteDatabase): MessageDataStore {

    override fun observeMessage(messageId: String): Observable<Message> {
        val statement = FACTORY.select_message_by_id(messageId)
        return db.createQuery(statement.tables, statement)
            .mapToOne(FACTORY.select_message_by_idMapper()::map)
            .map { messageDataModel ->
                Message(
                    id = messageDataModel.id(),
                    text = messageDataModel.text(),
                    seen = messageDataModel.seen()
                )
            }
    }

    override fun insertMessage(message: Message) {
        val statement = MessageModel.Insert_message(db.writableDatabase)
        statement.bind(message.id, message.text, message.seen)
        db.executeInsert(statement.table, statement)
    }

    override fun updateSeen(messageId: String, seen: Boolean) {
        val statement = MessageModel.Update_message_seen(db.writableDatabase)
        statement.bind(seen, messageId)
        db.executeUpdateDelete(statement.table, statement)
    }
}

private val FACTORY: MessageModel.Factory<MessageModel> = MessageModel.Factory(::MessageDataModel)

private class MessageDataModel(
    val id: String,
    val text: String,
    val seen: Boolean
): MessageModel {
    override fun id(): String = id
    override fun text(): String = text
    override fun seen(): Boolean = seen
}
