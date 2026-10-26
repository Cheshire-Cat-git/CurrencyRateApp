package com.example.currencyrateapp.model

sealed interface CurrencyEvent {
    object RefreshRates : CurrencyEvent
}