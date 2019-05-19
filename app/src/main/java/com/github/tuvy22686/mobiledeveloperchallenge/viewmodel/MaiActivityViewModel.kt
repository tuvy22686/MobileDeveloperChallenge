package com.github.tuvy22686.mobiledeveloperchallenge.viewmodel

import android.app.Application
import com.github.tuvy22686.mobiledeveloperchallenge.infra.HttpClient

class MainActivityViewModel(application: Application): ViewModel {

    override fun onCreate() {
    }

    override fun onDestroy() {
    }

    fun onRequestButtonClick(httpClientInterface: HttpClient.HttpClientInterface) {
        val httpClient = HttpClient()
        httpClient.init(httpClientInterface)
        httpClient.execute()
    }
}