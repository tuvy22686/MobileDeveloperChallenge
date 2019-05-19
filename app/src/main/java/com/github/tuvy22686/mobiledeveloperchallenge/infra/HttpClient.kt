package com.github.tuvy22686.mobiledeveloperchallenge.infra

import android.os.AsyncTask
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class HttpClient(httpResponse: HttpResponse): AsyncTask<String, Int, String>() {

    companion object {
        const val TAG = "HttpClient"
    }

    private var response: HttpResponse = httpResponse
    private lateinit var client: OkHttpClient

    override fun onPreExecute() {
        super.onPreExecute()
        response.onPreExecute()
    }

    override fun doInBackground(vararg params: String): String? {

        try {
            client = OkHttpClient()
            val request = Request.Builder()
                .url(ApiRequestGenerator.live())
                .get()
                .build()
            val call = client.newCall(request)
            val response: Response = call.execute()

            if (response.isSuccessful) {
                return response.body()?.string()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

        return null
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        response.onPostExecute(result)
    }

    interface HttpResponse {
        fun onPreExecute()
        fun onPostExecute(result: String?)
    }
}