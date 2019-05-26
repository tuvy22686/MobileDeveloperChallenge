package com.github.tuvy22686.mobiledeveloperchallenge.ui

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.tuvy22686.mobiledeveloperchallenge.databinding.ActivityMainBinding
import com.github.tuvy22686.mobiledeveloperchallenge.ui.adapter.QuoteViewAdapter
import com.github.tuvy22686.mobiledeveloperchallenge.ui.viewholder.QuoteViewHolder
import com.github.tuvy22686.mobiledeveloperchallenge.viewmodel.MainViewModel

class MainActivity : AppCompatActivity(), MainViewModel.ProgressBarVisibility, QuoteViewHolder.ItemClickListener {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
        viewModel.onCreate()
        setContentView(binding.root)
        setupSourceButton()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
    }

    private fun setupViewModel() {
        viewModel = MainViewModel(application, this)
    }

    private fun setupSourceButton() {
        binding.source.setOnClickListener {
            val sources = viewModel.getSourcesName()
            AlertDialog.Builder(this).apply {
                setTitle("Source")
                setNegativeButton("Cancel", null)
                setItems(sources, DialogInterface.OnClickListener { dialog, which ->
                    viewModel.requestOfQuotes(sources[which].toString())
                    dialog.dismiss()
                })
                show()
            }
        }
    }

    override fun toVisible() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun toGone() {
        binding.progressBar.visibility = View.GONE
        binding.source.text = viewModel.selectedSource
        binding.recyclerView.adapter = QuoteViewAdapter(this, viewModel.getQuotes(viewModel.selectedSource), this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onItemClick(rate: Double) {
        binding.rate.text = rate.toString()
    }
}
