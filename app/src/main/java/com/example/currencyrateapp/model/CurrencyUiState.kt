package com.example.currencyrateapp.model

import com.example.moneylib.model.Currency

data class CurrencyUiState(
    val currencies: List<Currency> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
