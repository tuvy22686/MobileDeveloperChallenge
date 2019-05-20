package com.github.tuvy22686.mobiledeveloperchallenge.application

import android.app.Application
import android.util.Log

class QuoteApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        Log.d("QuoteApplication", "onCreate")
    }
}