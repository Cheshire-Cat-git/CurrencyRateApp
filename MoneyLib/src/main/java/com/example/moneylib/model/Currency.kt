package com.example.moneylib.model

data class Currency (
    val name: String,
    val currRate: Double,
    val changesLast24Hours: Double
){
    init {
        require(name.isNotBlank()) { "Currency name cannot be blank" }
        require(currRate >= 0) { "Currency rate must be more than zero" }
    }
}