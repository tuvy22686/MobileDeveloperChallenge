package com.github.tuvy22686.mobiledeveloperchallenge.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.tuvy22686.mobiledeveloperchallenge.databinding.ActivityMainBinding
import com.github.tuvy22686.mobiledeveloperchallenge.ui.adapter.QuoteViewAdapter
import com.github.tuvy22686.mobiledeveloperchallenge.ui.viewholder.QuoteViewHolder
import com.github.tuvy22686.mobiledeveloperchallenge.viewmodel.MainViewModel

class MainActivity : AppCompatActivity(), MainViewModel.ProgressBarVisibility, QuoteViewHolder.ItemClickListener {

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
        binding.source.text = "USD"
        binding.recyclerView.adapter = QuoteViewAdapter(this, viewModel.getQuotes(), this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onItemClick(rate: Double) {
        binding.rate.text = rate.toString()
    }
}
