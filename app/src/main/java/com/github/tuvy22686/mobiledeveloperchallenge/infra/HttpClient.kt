package com.github.tuvy22686.mobiledeveloperchallenge.infra

import android.net.Uri
import android.os.AsyncTask
import com.github.tuvy22686.mobiledeveloperchallenge.Constants
import okhttp3.OkHttpClient
import okhttp3.Request

class HttpClient: AsyncTask<String, Int, String>() {

    private var httpClientInterface: HttpClientInterface? = null
    private lateinit var client: OkHttpClient

    fun init(httpClientInterface: HttpClientInterface) {
        this.httpClientInterface = httpClientInterface
    }

    private val url: String by lazy {
        Uri.Builder()
            .scheme("http")
            .authority("www.apilayer.net")
            .path("/api/live")
            .appendQueryParameter("access_key",
                Constants.API_KEY
            )
            .build()
            .toString()
    }

    override fun onPreExecute() {
        super.onPreExecute()
        httpClientInterface?.onPreExecute()
    }

    override fun doInBackground(vararg params: String?): String? {

        try {
            client = OkHttpClient()
            val request = Request.Builder()
                .url(url)
                .build()
            val call = client.newCall(request)
            val response = call.execute()

            if (response.code() == 200) {
                return response.toString()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

        return null
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        httpClientInterface?.onPostExecute(result)
    }

    interface HttpClientInterface {
        fun onPreExecute()
        fun onPostExecute(result: String?)
    }
}