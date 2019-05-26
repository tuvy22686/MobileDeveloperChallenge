package com.github.tuvy22686.mobiledeveloperchallenge.util

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.github.tuvy22686.mobiledeveloperchallenge.model.data.Quote
import com.github.tuvy22686.mobiledeveloperchallenge.model.data.Source

class QuoteOpenHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val TAG = "QUOTE_OPEN_HELPER"
        /**
         * 株式相場
         */
        const val DATABASE_NAME: String = "quote"
        const val DATABASE_VERSION: Int = 1

        /**
         * レート
         */
        const val TABLE_NAME_RATE: String = "rate"

        /**
         * 国名
         */
        const val TABLE_NAME_SOURCE: String = "source"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val sqlCreateTableRate = "CREATE TABLE $TABLE_NAME_RATE (id INTEGER PRIMARY KEY AUTOINCREMENT, source TEXT, quote TEXT, rate DOUBLE, timestamp LONG);"
        val sqlCreateTableSource = "CREATE TABLE $TABLE_NAME_SOURCE (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, description TEXT);"
        db.apply {
            execSQL(sqlCreateTableRate)
            execSQL(sqlCreateTableSource)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val sqlDropTableRate = "DROP TABLE IF EXISTS $TABLE_NAME_RATE;"
        val sqlDropTableSource = "DROP TABLE IF EXISTS $TABLE_NAME_SOURCE;"
        db.apply {
            execSQL(sqlDropTableRate)
            execSQL(sqlDropTableSource)
        }
        onCreate(db)
    }

    fun insertDataToRate(db: SQLiteDatabase, source: String, quote: String, rate: Double, timestamp: Long) {
        val values = ContentValues()
        values.apply {
            put("source", source)
            put("quote", quote)
            put("rate", rate)
            put("timestamp", timestamp)
        }

        db.insert(TABLE_NAME_RATE, null, values)
    }

    fun updateDataToRate(db: SQLiteDatabase, quote: String, rate: Double, timestamp: Long) {
        val values = ContentValues()
        values.apply {
            put("rate", rate)
            put("timestamp", timestamp)
        }
        db.update(TABLE_NAME_RATE, values, "quote = '$quote'", null)
    }

    fun getListFromRate(db: SQLiteDatabase, source: String): List<Quote> {
        val selection = "source=?"
        val cursor: Cursor = db.query(
            TABLE_NAME_RATE,
            arrayOf("id", "source", "quote", "rate", "timestamp"),
            selection,
            arrayOf(source),
            null,
            null,
            null)
        val quotes: ArrayList<Quote> = arrayListOf()
        while (cursor.moveToNext()) {
            quotes.add(
                Quote(
                    id = cursor.getLong(0),
                    source = cursor.getString(1),
                    name = cursor.getString(2),
                    rate = cursor.getDouble(3),
                    timestamp = cursor.getLong(4))
            )
        }
        cursor.close()
        return quotes
    }

    fun insertDataToSource(db: SQLiteDatabase, name: String, description: String) {
        val values = ContentValues()
        values.apply {
            put("name", name)
            put("description", description)
        }
        db.insert(TABLE_NAME_SOURCE, null, values)
    }

    fun getListFromSource(db: SQLiteDatabase): List<Source> {
        val cursor: Cursor = db.query(
            TABLE_NAME_SOURCE,
            arrayOf("id", "name", "description"),
            null,
            null,
            null,
            null,
            null)

        val sources: ArrayList<Source> = arrayListOf()
        while (cursor.moveToNext()) {
            sources.add(
                Source(
                    id = cursor.getLong(0),
                    name = cursor.getString(1),
                    description = cursor.getString(2))
            )
        }
        cursor.close()
        return sources.toList()
    }

    fun resetTableOfSource(db: SQLiteDatabase) {
        val dropTableSql = "DROP TABLE IF EXISTS $TABLE_NAME_SOURCE;"
        val createTableSql = "CREATE TABLE $TABLE_NAME_SOURCE (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, description TEXT);"
        db.apply {
            execSQL(dropTableSql)
            execSQL(createTableSql)
        }
    }

}