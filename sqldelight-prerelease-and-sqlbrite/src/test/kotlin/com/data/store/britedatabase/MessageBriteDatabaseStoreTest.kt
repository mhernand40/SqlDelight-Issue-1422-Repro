package com.data.store.britedatabase

import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.data.store.MessageDataStore
import com.data.store.MessageDataStoreTest
import com.database.test.rules.TestInMemoryDatabase
import com.sqldelightissuerepros.MessageModel
import com.squareup.sqlbrite3.SqlBrite
import io.reactivex.schedulers.Schedulers
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(manifest = Config.NONE)
class MessageBriteDatabaseStoreTest : MessageDataStoreTest() {
    @get:Rule
    val testDatabase = TestInMemoryDatabase(
        context = ApplicationProvider.getApplicationContext<Context>(),
        callback = TestInMemorySQLiteOpenHelperCallback()
    )

    override fun provideDataStore(): MessageDataStore {
        return MessageBriteDatabaseStore(
            db = SqlBrite.Builder()
                .build()
                .wrapDatabaseHelper(
                    testDatabase.sqliteOpenHelper,
                    Schedulers.trampoline()
                )
        )
    }
}

private class TestInMemorySQLiteOpenHelperCallback : SupportSQLiteOpenHelper.Callback(1) {
    override fun onCreate(db: SupportSQLiteDatabase) {
        db.execSQL(MessageModel.CREATE_TABLE)
    }

    override fun onUpgrade(db: SupportSQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Do nothing
    }
}
