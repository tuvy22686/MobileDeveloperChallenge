package com.github.tuvy22686.mobiledeveloperchallenge.store

import android.app.Application
import android.util.Log
import com.github.tuvy22686.mobiledeveloperchallenge.infra.HttpClient
import com.github.tuvy22686.mobiledeveloperchallenge.model.business.Quote
import com.github.tuvy22686.mobiledeveloperchallenge.model.response.LiveResponse
import com.github.tuvy22686.mobiledeveloperchallenge.util.QuoteOpenHelper
import com.google.gson.Gson

class LiveStore(application: Application, liveStoreStatus: Status): HttpClient.HttpResponse {

    companion object {
        const val TAG = "LIVE_STORE"
        const val PREF_DB_INIT = "DATABASE_INITIALIZATION"
    }

    private var status: Status = liveStoreStatus
    private val helper = QuoteOpenHelper(application)
    private val preferences = application.getSharedPreferences(TAG, Application.MODE_PRIVATE)
    private val client: HttpClient by lazy {
        HttpClient(this)
    }

    fun startTransaction(source: String?) {
        client.execute(TAG, source)
    }

    override fun onPreExecute() {
        status.start()
    }

    override fun onPostExecute(result: String?) {
        result?.apply {
            val liveResponse: LiveResponse = Gson().fromJson<LiveResponse>(this, LiveResponse::class.java)
            if (!preferences.getBoolean(PREF_DB_INIT, false)) {
                liveResponse.quotes.forEach {
                    helper.insertDataToRate(
                        db = helper.writableDatabase,
                        source = liveResponse.source,
                        quote = it.key,
                        rate = it.value,
                        timestamp = liveResponse.timestamp)
                }
                preferences.edit()
                    .putBoolean(PREF_DB_INIT, true)
                    .apply()
            } else {
                liveResponse.quotes.forEach {
                    helper.updateDataToRate(
                        db = helper.writableDatabase,
                        quote = it.key,
                        rate = it.value,
                        timestamp = liveResponse.timestamp)
                }
            }

        }
        status.finish()
    }

    fun getData() {
        val quotes: List<Quote> = helper.getListFromRate(helper.readableDatabase)
        quotes.forEach {
            Log.d(TAG, "name: ${it.name}, ${it.rate}")
        }
    }

    interface Status {
        fun start()
        fun finish()
    }
}