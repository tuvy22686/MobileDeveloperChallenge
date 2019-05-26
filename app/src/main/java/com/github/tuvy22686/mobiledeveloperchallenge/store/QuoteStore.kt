package com.github.tuvy22686.mobiledeveloperchallenge.store

import android.app.Application
import com.github.tuvy22686.mobiledeveloperchallenge.infra.HttpClient
import com.github.tuvy22686.mobiledeveloperchallenge.model.data.Quote
import com.github.tuvy22686.mobiledeveloperchallenge.model.response.LiveResponse
import com.github.tuvy22686.mobiledeveloperchallenge.util.QuoteOpenHelper
import com.github.tuvy22686.mobiledeveloperchallenge.util.TransactionStatus
import com.google.gson.Gson

class QuoteStore(application: Application, liveStoreStatus: TransactionStatus): HttpClient.HttpResponse {

    companion object {
        const val TAG = "LIVE_STORE"
        const val PREF_DB_INIT = "DATABASE_INITIALIZATION_$TAG"
    }

    private var status: TransactionStatus = liveStoreStatus
    private val helper = QuoteOpenHelper(application)
    private val preferences = application.getSharedPreferences(TAG, Application.MODE_PRIVATE)

    fun startTransaction(source: String?) {
        HttpClient(this)
            .execute(TAG, source)
    }

    override fun onPreExecute() {
        status.start()
    }

    override fun onPostExecute(result: String?) {
        result?.apply {
            val liveResponse: LiveResponse = Gson().fromJson<LiveResponse>(this, LiveResponse::class.java)
            if (!preferences.getBoolean("${PREF_DB_INIT}_${liveResponse.source}", false)) {
                liveResponse.quotes.forEach {
                    helper.insertDataToRate(
                        db = helper.writableDatabase,
                        source = liveResponse.source,
                        quote = it.key,
                        rate = it.value,
                        timestamp = liveResponse.timestamp)
                }
                preferences.edit()
                    .putBoolean("${PREF_DB_INIT}_${liveResponse.source}", true)
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

    fun getQuotes(source: String): List<Quote> {
        return helper.getListFromRate(helper.readableDatabase, source)
    }
}