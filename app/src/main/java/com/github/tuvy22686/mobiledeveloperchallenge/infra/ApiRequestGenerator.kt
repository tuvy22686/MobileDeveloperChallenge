package com.github.tuvy22686.mobiledeveloperchallenge.infra

import android.net.Uri
import com.github.tuvy22686.mobiledeveloperchallenge.store.LiveStore
import com.github.tuvy22686.mobiledeveloperchallenge.util.Constants
import okhttp3.Request

object ApiRequestGenerator {

    private const val SCHEME = "http"
    private const val AUTHORITY = "www.apilayer.net"

    fun generateRequest(params: Array<out String>): Request {
        val requestUrl = when (params[0]) {
            LiveStore.TAG -> {
                if (params[1].isNullOrEmpty()) {
                    live()
                } else {
                    live(params[1])
                }
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

    fun list(): String {
        return Uri.Builder()
            .scheme(SCHEME)
            .authority(AUTHORITY)
            .path("/api/list")
            .appendQueryParameter("access_key", Constants.API_KEY)
            .build()
            .toString()
    }
}