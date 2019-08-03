package com.data.store.sqldelight1

import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.data.store.MessageDataStore
import com.data.store.MessageDataStoreTest
import com.database.test.rules.TestInMemoryDatabase
import com.sqldelight1.Database
import com.squareup.sqldelight.android.AndroidSqliteDriver
import io.reactivex.schedulers.Schedulers
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(manifest = Config.NONE)
class MessageSqlDelightDatabaseStoreTest : MessageDataStoreTest() {
    @get:Rule
    val testDatabase = TestInMemoryDatabase(
        context = ApplicationProvider.getApplicationContext<Context>(),
        callback = TestInMemorySQLiteOpenHelperCallback()
    )

    override fun provideDataStore(): MessageDataStore {
        return MessageSqlDelightDatabaseStore(
            db = Database(driver = AndroidSqliteDriver(openHelper = testDatabase.sqliteOpenHelper)),
            scheduler = Schedulers.trampoline()
        )
    }
}

private class TestInMemorySQLiteOpenHelperCallback : SupportSQLiteOpenHelper.Callback(1) {
    override fun onCreate(db: SupportSQLiteDatabase) {
        Database.Schema.create(driver = AndroidSqliteDriver(db))
    }

    override fun onUpgrade(db: SupportSQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Do nothing
    }
}
