package com.github.tuvy22686.mobiledeveloperchallenge.infra

import android.net.Uri
import com.github.tuvy22686.mobiledeveloperchallenge.store.SourceStore
import com.github.tuvy22686.mobiledeveloperchallenge.store.QuoteStore
import com.github.tuvy22686.mobiledeveloperchallenge.util.Constants
import okhttp3.Request

object ApiRequestGenerator {

    private const val SCHEME = "http"
    private const val AUTHORITY = "www.apilayer.net"

    fun generateRequest(params: Array<out String>): Request {
        val requestUrl = when (params[0]) {
            QuoteStore.TAG -> {
                live(params[1])
            }
            SourceStore.TAG -> {
                list()
            }
            else -> {
                live()
            }
        }

        return Request.Builder()
            .url(requestUrl)
            .get()
            .build()
    }

    private fun live(): String {
        return Uri.Builder()
            .scheme(SCHEME)
            .authority(AUTHORITY)
            .path("/api/live")
            .appendQueryParameter("access_key", Constants.API_KEY)
            .build()
            .toString()
    }

    private fun live(source: String): String {
        return Uri.Builder()
            .scheme(SCHEME)
            .authority(AUTHORITY)
            .path("/api/live")
            .appendQueryParameter("access_key", Constants.API_KEY)
            .appendQueryParameter("source", source)
            .build()
            .toString()
    }

    private fun list(): String {
        return Uri.Builder()
            .scheme(SCHEME)
            .authority(AUTHORITY)
            .path("/api/list")
            .appendQueryParameter("access_key", Constants.API_KEY)
            .build()
            .toString()
    }
}