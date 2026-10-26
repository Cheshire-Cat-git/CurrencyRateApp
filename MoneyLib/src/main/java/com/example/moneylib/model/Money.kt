package com.example.moneylib.model

import java.math.BigDecimal

data class Money(
    val amount: BigDecimal,
    val currency: Currency
){
    init {
        require(amount >= BigDecimal.ZERO) { "Money amount cannot be negative" }
    }
}
