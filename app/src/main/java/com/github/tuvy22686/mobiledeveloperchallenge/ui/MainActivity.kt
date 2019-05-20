package com.github.tuvy22686.mobiledeveloperchallenge.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.github.tuvy22686.mobiledeveloperchallenge.databinding.ActivityMainBinding
import com.github.tuvy22686.mobiledeveloperchallenge.viewmodel.MainViewModel

class MainActivity : AppCompatActivity(), MainViewModel.ProgressBarVisibility {

    companion object {
        const val TAG = "MainActivity"
    }

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var viewModel: MainViewModel

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
        viewModel = MainViewModel(application, this)
    }

    private fun setupButton() {
        binding.buttonRequest.setOnClickListener {
            viewModel.onRequestButtonClick()
        }
    }

    override fun toVisible() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun toGone() {
        binding.progressBar.visibility = View.GONE
        viewModel.quotes?.apply {
            val key = this.keys.first()
            binding.quote.text = key
            binding.rate.text = this[key].toString()
        }
    }
}
