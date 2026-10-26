package com.example.currencyrateapp.data

import com.example.moneylib.model.Currency
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CurrencyRepository(
    private val apiService: CurrencyApiService
) {
    suspend fun fetchCurrencies(): List<Currency> {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        val latestResponse = apiService.getLatestRates("USD")
        val latestRates = latestResponse.rates

        val latestDate = latestResponse.date?.let {
            try {
                LocalDate.parse(it, formatter)
            } catch (e: Exception) {
                null
            }
        } ?: LocalDate.now()

        var prevDate = latestDate.minusDays(1)
        while (prevDate.dayOfWeek == java.time.DayOfWeek.SATURDAY || prevDate.dayOfWeek == java.time.DayOfWeek.SUNDAY) {
            prevDate = prevDate.minusDays(1)
        }

        val yesterdayResponse = try {
            apiService.getHistoricalRates(prevDate.format(formatter), "USD")
        } catch (e: Exception) {
            null
        }

        val yesterdayRates = yesterdayResponse?.rates ?: emptyMap()

        return latestRates.map { (name, currentRate) ->
            val prevRate = yesterdayRates[name] ?: currentRate
            val percentageChange = if (prevRate != 0.0) {
                ((currentRate - prevRate) / prevRate) * 100.0
            } else {
                0.0
            }

            Currency(
                name = name,
                currRate = currentRate,
                changesLast24Hours = percentageChange
            )
        }
    }
}