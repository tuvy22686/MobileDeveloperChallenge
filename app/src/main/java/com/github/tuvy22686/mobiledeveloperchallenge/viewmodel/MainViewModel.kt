package com.github.tuvy22686.mobiledeveloperchallenge.viewmodel

import android.app.Application
import com.github.tuvy22686.mobiledeveloperchallenge.store.LiveStore

class MainViewModel(private val application: Application,
                    progressBarVisibility: ProgressBarVisibility): ViewModel, LiveStore.Status {

    private var visibility: ProgressBarVisibility = progressBarVisibility

    var quotes: Map<String, Double>? = null

    override fun onCreate() {
    }

    override fun onDestroy() {
    }

    fun onRequestButtonClick() {
        val liveStore = LiveStore(application, this)
        liveStore.startTransaction(null)
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