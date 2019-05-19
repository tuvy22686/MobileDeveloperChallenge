package com.github.tuvy22686.mobiledeveloperchallenge.viewmodel

import android.app.Application
import com.github.tuvy22686.mobiledeveloperchallenge.infra.HttpClient
import com.github.tuvy22686.mobiledeveloperchallenge.model.LiveResponse
import com.google.gson.Gson

class MainViewModel(application: Application, progressBarVisibility: ProgressBarVisibility): ViewModel, HttpClient.HttpResponse {

    private var visibility: ProgressBarVisibility = progressBarVisibility
    var quotes: Map<String, Double>? = null

    override fun onCreate() {
    }

    override fun onDestroy() {
    }

    fun onRequestButtonClick() {
        val httpClient = HttpClient(this)
        httpClient.execute()
    }

    override fun onPreExecute() {
        visibility.toVisible()
    }

    override fun onPostExecute(result: String?) {
        result?.apply {
            val data = Gson().fromJson<LiveResponse>(result, LiveResponse::class.java)
            quotes = data.quotes
        }
        visibility.toGone()
    }

    interface ProgressBarVisibility {
        fun toVisible()
        fun toGone()
    }
}