package com.github.tuvy22686.mobiledeveloperchallenge.viewmodel

import android.app.Application
import com.github.tuvy22686.mobiledeveloperchallenge.model.data.Quote
import com.github.tuvy22686.mobiledeveloperchallenge.store.SourceStore
import com.github.tuvy22686.mobiledeveloperchallenge.store.QuoteStore
import com.github.tuvy22686.mobiledeveloperchallenge.util.TransactionStatus

class MainViewModel(application: Application,
                    progressBarVisibility: ProgressBarVisibility): ViewModel, TransactionStatus {

    private var visibility: ProgressBarVisibility = progressBarVisibility
    private val quoteStore = QuoteStore(application, this)
    private val sourceStore = SourceStore(application, this)
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
        quoteStore.startTransaction(selectedSource)
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