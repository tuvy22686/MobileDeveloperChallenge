package com.github.tuvy22686.mobiledeveloperchallenge.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.github.tuvy22686.mobiledeveloperchallenge.databinding.ActivityMainBinding
import com.github.tuvy22686.mobiledeveloperchallenge.infra.HttpClient
import com.github.tuvy22686.mobiledeveloperchallenge.model.LiveResponse
import com.github.tuvy22686.mobiledeveloperchallenge.viewmodel.MainActivityViewModel
import com.google.gson.Gson

class MainActivity : AppCompatActivity(), HttpClient.HttpClientInterface {

    companion object {
        const val TAG = "MainActivity"
    }

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
        viewModel.onCreate()
        setContentView(binding.root)
        setupButton()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
    }

    private fun setupViewModel() {
        viewModel = MainActivityViewModel(application)
    }

    private fun setupButton() {
        binding.buttonRequest.setOnClickListener {
            viewModel.onRequestButtonClick(this@MainActivity)
        }
    }

    override fun onPreExecute() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun onPostExecute(result: String?) {
        binding.progressBar.visibility = View.GONE
        val jsonData = if (result != null) {
            Gson().fromJson<LiveResponse>(result, LiveResponse::class.java)
        } else {
            null
        }
        jsonData?.apply {
            Log.d(TAG, jsonData.toString())
        }
    }
}
