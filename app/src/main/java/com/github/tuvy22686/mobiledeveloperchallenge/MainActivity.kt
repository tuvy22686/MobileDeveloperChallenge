package com.github.tuvy22686.mobiledeveloperchallenge

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.github.tuvy22686.mobiledeveloperchallenge.databinding.ActivityMainBinding
import com.github.tuvy22686.mobiledeveloperchallenge.infra.HttpClient

class MainActivity : AppCompatActivity(), HttpClient.HttpClientInterface {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupButton()
    }

    private fun setupButton() {
        binding.buttonRequest.setOnClickListener {
            val httpClient = HttpClient()
            httpClient.init(this@MainActivity)
            httpClient.execute()
        }
    }

    override fun onPreExecute() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun onPostExecute(result: String?) {
        binding.progressBar.visibility = View.GONE
    }
}
