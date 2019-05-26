package com.github.tuvy22686.mobiledeveloperchallenge.model.data

data class Quote(
    val id: Long,
    val source: String,
    val name: String,
    val rate: Double,
    val timestamp: Long)