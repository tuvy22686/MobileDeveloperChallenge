package com.github.tuvy22686.mobiledeveloperchallenge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.tuvy22686.mobiledeveloperchallenge.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}
