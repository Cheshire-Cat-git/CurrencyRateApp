package com.example.currencyrateapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.currencyrateapp.model.CurrencyEvent
import com.example.currencyrateapp.ui.viewmodels.CurrencyViewModel
import com.example.moneylib.model.Currency

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyScreen(viewModel: CurrencyViewModel) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Currency Exchange Rates") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                state.error != null -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Error: ${state.error}",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { viewModel.onEvent(CurrencyEvent.RefreshRates) }) {
                            Text("Retry")
                        }
                    }
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(state.currencies) { currency ->
                            CurrencyItemCard(currency = currency)
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun CurrencyItemCard(currency: Currency) {
    val isPositive = currency.changesLast24Hours >= 0
    val backgroundColor = if (isPositive) Color(0xFFD4EDDA) else Color(0xFFF8D7DA)
    val textColor = if (isPositive) Color(0xFF155724) else Color(0xFF721C24)

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = currency.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = textColor
                )
                Text(
                    text = "Rate: ${String.format("%.4f", currency.currRate)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = textColor
                )
            }
            Text(
                text = "${if (isPositive) "+" else ""}${String.format("%.2f", currency.changesLast24Hours)}%",
                style = MaterialTheme.typography.titleMedium,
                color = textColor
            )
        }
    }
}
