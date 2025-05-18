package com.example.freeupcopy.domain.enums

enum class Currency(val symbol: String, val valueName: String) {
    COIN("$", "coin"),
    CASH("₹", "cash"),
}

fun getCurrencyFromString(offeredIn: String): Currency {
    return when (offeredIn.lowercase()) {
        "coin" -> Currency.COIN
        "cash" -> Currency.CASH
        else -> throw IllegalArgumentException("Unknown currency type: $offeredIn")
    }
}