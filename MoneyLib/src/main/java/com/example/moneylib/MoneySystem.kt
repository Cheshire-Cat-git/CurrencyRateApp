package com.example.moneylib

import com.example.moneylib.model.Currency
import com.example.moneylib.model.Money
import java.math.BigDecimal

class MoneySystem {
    private val converter = CurrencyConverter()

    fun exchange(amount: Double, from: Currency, to: Currency): Money {
        val sourceMoney = Money(BigDecimal.valueOf(amount), from)
        return converter.convert(sourceMoney, to)
    }
}