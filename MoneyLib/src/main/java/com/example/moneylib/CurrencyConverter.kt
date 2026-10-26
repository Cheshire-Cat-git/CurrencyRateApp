package com.example.moneylib

import com.example.moneylib.model.Currency
import com.example.moneylib.model.Money
import java.math.BigDecimal
import java.math.RoundingMode

class CurrencyConverter {
    fun convert(money: Money, targetCurrency: Currency): Money {
        val baseAmount = money.amount.divide(
            BigDecimal
            .valueOf(money.currency.currRate),
            6,
            RoundingMode.HALF_UP
        )

        val targetAmount = baseAmount.multiply(
            BigDecimal.valueOf(targetCurrency.currRate)
        ).setScale(2, RoundingMode.HALF_UP)

        return Money(targetAmount, targetCurrency)
    }
}