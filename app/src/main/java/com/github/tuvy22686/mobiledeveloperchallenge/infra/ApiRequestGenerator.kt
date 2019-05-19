package com.github.tuvy22686.mobiledeveloperchallenge.infra

import android.net.Uri
import com.github.tuvy22686.mobiledeveloperchallenge.util.Constants

object ApiRequestGenerator {

    private const val SCHEME = "http"
    private const val AUTHORITY = "www.apilayer.net"

    fun live(): String {
        return Uri.Builder()
            .scheme(SCHEME)
            .authority(AUTHORITY)
            .path("/api/live")
            .appendQueryParameter("access_key", Constants.API_KEY)
            .build()
            .toString()
    }
}