package com.github.tuvy22686.mobiledeveloperchallenge.viewmodel

import android.app.Application
import com.github.tuvy22686.mobiledeveloperchallenge.model.data.Quote
import com.github.tuvy22686.mobiledeveloperchallenge.store.QuoteStore
import com.github.tuvy22686.mobiledeveloperchallenge.store.SourceStore
import com.github.tuvy22686.mobiledeveloperchallenge.util.TransactionStatus
import com.github.tuvy22686.mobiledeveloperchallenge.util.UnixTimeCalculatorUtils

class MainViewModel(application: Application,
                    progressBarVisibility: ProgressBarVisibility): ViewModel, TransactionStatus {

    private var visibility: ProgressBarVisibility = progressBarVisibility
    private val quoteStore = QuoteStore(application, this)
    private val sourceStore = SourceStore(application, this)
    private val quotePreference = application.getSharedPreferences(QuoteStore.TAG, Application.MODE_PRIVATE)
    var selectedSource: String = "USD"

    override fun onCreate() {
        quoteStore.startTransaction(selectedSource)
        sourceStore.startTransaction()
    }

    override fun onDestroy() {
    }

    fun getQuotes(source: String): List<Quote> {
        return quoteStore.getQuotes(source)
    }

    fun getSourcesName(): Array<CharSequence> {
        return sourceStore.getSources()
            .map {
                it.name as CharSequence
            }
            .toTypedArray()
    }

    fun requestOfQuotes(source: String) {
        selectedSource = source
        if (!quotePreference.getBoolean("${QuoteStore.PREF_DB_INIT}_$selectedSource", false)
            ||
            UnixTimeCalculatorUtils.isMoreThan30Minutes(
                now = System.currentTimeMillis()/1000,
                recordedTime = quoteStore.getQuotes(selectedSource).first().timestamp)) {
            quoteStore.startTransaction(selectedSource)
        }
    }

    override fun start() {
        visibility.toVisible()
    }

    override fun finish() {
        visibility.toGone()
    }

    interface ProgressBarVisibility {
        fun toVisible()
        fun toGone()
    }
}