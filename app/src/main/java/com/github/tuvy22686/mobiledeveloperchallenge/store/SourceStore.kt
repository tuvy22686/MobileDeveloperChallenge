package com.github.tuvy22686.mobiledeveloperchallenge.store

import android.app.Application
import com.github.tuvy22686.mobiledeveloperchallenge.infra.HttpClient
import com.github.tuvy22686.mobiledeveloperchallenge.model.data.Source
import com.github.tuvy22686.mobiledeveloperchallenge.model.response.ListResponse
import com.github.tuvy22686.mobiledeveloperchallenge.util.QuoteOpenHelper
import com.github.tuvy22686.mobiledeveloperchallenge.util.TransactionStatus
import com.google.gson.Gson

class SourceStore(application: Application, listStoreStatus: TransactionStatus): HttpClient.HttpResponse {

    companion object {
        const val TAG = "LIST_STORE"
        const val PREF_DB_INIT = "DATABASE_INITIALIZATION_$TAG"
    }

    private var status: TransactionStatus = listStoreStatus
    private val helper = QuoteOpenHelper(application)
    private val preferences = application.getSharedPreferences(PREF_DB_INIT, Application.MODE_PRIVATE)
    private val client: HttpClient by lazy {
        HttpClient(this)
    }

    fun startTransaction() {
        client.execute(TAG)
    }

    override fun onPreExecute() {
        status.start()
    }

    override fun onPostExecute(result: String?) {
        if (!preferences.getBoolean(PREF_DB_INIT, false)) {
            result?.apply {
                val listResponse: ListResponse = Gson().fromJson<ListResponse>(this, ListResponse::class.java)
                listResponse.currencies.forEach {
                    helper.apply {
                        insertDataToSource(
                            db = helper.writableDatabase,
                            name = it.key,
                            description = it.value)
                    }
                }
                preferences.edit()
                    .putBoolean(PREF_DB_INIT, true)
                    .apply()
            }
        }

        status.finish()
    }

    fun getSources(): List<Source> {
        return helper.getListFromSource(helper.readableDatabase)
    }
}