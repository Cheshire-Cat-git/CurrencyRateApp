package com.example.currencyrateapp.model

data class ApiResponse(
    val amount: Double? = 1.0,
    val base: String? = "USD",
    val date: String? = null,
    val rates: Map<String, Double> = emptyMap()
)