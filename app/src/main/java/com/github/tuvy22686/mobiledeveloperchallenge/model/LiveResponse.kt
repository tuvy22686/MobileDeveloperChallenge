package com.github.tuvy22686.mobiledeveloperchallenge.model

data class LiveResponse(
    val success: Boolean,
    val terms: String,
    val privacy: String,
    val timestamp: Long,
    val source: String,
    val quotes: Map<String, Double>)