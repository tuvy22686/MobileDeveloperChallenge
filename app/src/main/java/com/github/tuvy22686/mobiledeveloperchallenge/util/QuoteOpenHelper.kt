package com.github.tuvy22686.mobiledeveloperchallenge.util

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.github.tuvy22686.mobiledeveloperchallenge.model.business.Quote

class QuoteOpenHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME: String = "quote"
        const val DATABASE_VERSION: Int = 1
        const val TABLE_NAME_RATE: String = "rate"
        const val TABLE_NAME_SOURCE: String = "source"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val sqlCreateTableRate = "CREATE TABLE $TABLE_NAME_RATE (id INTEGER PRIMARY KEY AUTOINCREMENT, source TEXT, quote TEXT, rate DOUBLE, timestamp LONG);"
        val sqlCreateTableSource = "CREATE TABLE $TABLE_NAME_SOURCE (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, timestamp LONG);"
        db.apply {
            execSQL(sqlCreateTableRate)
            execSQL(sqlCreateTableSource)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val sql = "DROP TABLE IF EXISTS $TABLE_NAME_RATE;"
        db.execSQL(sql)
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

    fun getListFromRate(db: SQLiteDatabase): List<Quote> {
        val cursor: Cursor = db.query(
            TABLE_NAME_RATE,
            arrayOf("id", "quote", "rate", "timestamp"),
            null,
            null,
            null,
            null,
            null)

        val quotes: ArrayList<Quote> = arrayListOf()
        while (cursor.moveToNext()) {
            val quote = Quote(
                id = cursor.getLong(0),
                name = cursor.getString(1),
                rate = cursor.getDouble(2),
                timestamp = cursor.getLong(3))
            quotes.add(quote)
        }

        cursor.close()

        return quotes.toList()
    }

    fun insertDataToSource(db: SQLiteDatabase, source: String) {
        val values = ContentValues()
        values.put("source", source)
        db.insert(TABLE_NAME_SOURCE, null, values)
    }
}