package com.data.store

import com.domain.model.Message
import org.junit.Test

abstract class MessageDataStoreTest {

    private val messageDataStore by lazy {
        provideDataStore()
    }

    abstract fun provideDataStore(): MessageDataStore

    @Test
    fun `no-op UPDATE should not trigger emission`() {
        val messageId = "message-id"
        val message = Message(id = messageId, text = "Hey", seen = false)
        messageDataStore.insertMessage(message)

        val observer = messageDataStore.observeMessage(messageId).test()

        // Message has been inserted.
        observer.assertValue(message)

        messageDataStore.updateSeen(messageId, seen = true)

        // Original message plus updated message
        observer.assertValues(
            message,
            message.copy(seen = true)
        )

        // No-op UPDATE since message already marked with seen = true
        messageDataStore.updateSeen(messageId, seen = true)

        // We should not receive a 3rd emission.
        observer.assertValues(
            message,
            message.copy(seen = true)
        )
    }
}
