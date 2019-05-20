package com.github.tuvy22686.mobiledeveloperchallenge.viewmodel

import android.app.Application
import com.github.tuvy22686.mobiledeveloperchallenge.model.business.Quote
import com.github.tuvy22686.mobiledeveloperchallenge.store.LiveStore

class MainViewModel(private val application: Application,
                    progressBarVisibility: ProgressBarVisibility): ViewModel, LiveStore.Status {

    private var visibility: ProgressBarVisibility = progressBarVisibility
    private val liveStore = LiveStore(application, this)

    override fun onCreate() {
        liveStore.startTransaction(null)
    }

    override fun onDestroy() {
    }

    fun onRequestButtonClick() {
//        return liveStore.getQuotes()
    }

    fun getQuotes(): List<Quote> {
        return liveStore.getQuotes()
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