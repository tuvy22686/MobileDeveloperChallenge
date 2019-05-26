package com.github.tuvy22686.mobiledeveloperchallenge.model.response

data class ListResponse(
    val success: Boolean,
    val terms: String,
    val privacy: String,
    val currencies: Map<String, String>)