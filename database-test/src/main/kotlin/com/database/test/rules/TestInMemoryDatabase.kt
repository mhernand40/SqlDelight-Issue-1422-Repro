package com.database.test.rules

import android.content.Context
import androidx.sqlite.db.SupportSQLiteOpenHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import org.junit.rules.ExternalResource

class TestInMemoryDatabase(
    private val context: Context,
    private val callback: SupportSQLiteOpenHelper.Callback
) : ExternalResource() {

    lateinit var sqliteOpenHelper: SupportSQLiteOpenHelper
        private set

    override fun before() {
        super.before()
        sqliteOpenHelper = FrameworkSQLiteOpenHelperFactory().create(
            SupportSQLiteOpenHelper.Configuration.builder(context)
                .name(null)
                .callback(callback)
                .build()
        )
    }

    override fun after() {
        super.after()
        sqliteOpenHelper.close()
    }
}