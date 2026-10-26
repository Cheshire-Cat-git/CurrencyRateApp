package com.example.currencyrateapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyrateapp.data.CurrencyRepository
import com.example.currencyrateapp.model.CurrencyEvent
import com.example.currencyrateapp.model.CurrencyUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val repository: CurrencyRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(CurrencyUiState())
    val uiState: StateFlow<CurrencyUiState>
        get() = _uiState.asStateFlow()

    init {
        onEvent(CurrencyEvent.RefreshRates)
    }

    fun onEvent(event: CurrencyEvent) {
        when (event) {
            is CurrencyEvent.RefreshRates -> loadCurrencies()
        }
    }

    private fun loadCurrencies() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val currencies = repository.fetchCurrencies()
                _uiState.update { it.copy(isLoading = false, currencies = currencies) }
            }catch (e: Exception){
                _uiState.update {
                    it.copy(isLoading = false, error = e.localizedMessage ?: "Unknown error")
                }
            }
        }
    }
}