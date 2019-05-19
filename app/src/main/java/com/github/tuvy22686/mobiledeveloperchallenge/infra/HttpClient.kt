package com.github.tuvy22686.mobiledeveloperchallenge.infra

import android.os.AsyncTask
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class HttpClient: AsyncTask<String, Int, String>() {

    companion object {
        const val TAG = "HttpClient"
    }

    private var httpClientInterface: HttpClientInterface? = null
    private lateinit var client: OkHttpClient

    fun init(httpClientInterface: HttpClientInterface) {
        this.httpClientInterface = httpClientInterface
    }

    override fun onPreExecute() {
        super.onPreExecute()
        httpClientInterface?.onPreExecute()
    }

    override fun doInBackground(vararg params: String?): String? {

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
        httpClientInterface?.onPostExecute(result)
    }

    interface HttpClientInterface {
        fun onPreExecute()
        fun onPostExecute(result: String?)
    }
}