package com.github.tuvy22686.mobiledeveloperchallenge.util

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class QuoteOpenHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME: String = "rate"
        const val DATABASE_VERSION: Int = 1
        const val TABLE_NAME_QUOTE: String = "quote"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val sql = "CREATE TABLE $TABLE_NAME_QUOTE ( id INTEGER PRIMARY KEY AUTOINCREMENT, source TEXT, quote TEXT, rate DOUBLE);"
        db.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val sql = "DROP TABLE IF EXISTS $TABLE_NAME_QUOTE;"
        db.execSQL(sql)
        onCreate(db)
    }

    fun insertData(db: SQLiteDatabase, source: String, quote: String, rate: Double) {
        val values = ContentValues()
        values.put("source", source)
        values.put("quote", quote)
        values.put("rate", rate)

        db.insert(TABLE_NAME_QUOTE, null, values)
    }
}