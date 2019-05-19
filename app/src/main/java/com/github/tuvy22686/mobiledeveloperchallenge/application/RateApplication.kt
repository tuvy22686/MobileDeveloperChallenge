package com.github.tuvy22686.mobiledeveloperchallenge.application

import android.app.Application
import android.util.Log

class RateApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("RateApplication", "onCreate")
    }
}