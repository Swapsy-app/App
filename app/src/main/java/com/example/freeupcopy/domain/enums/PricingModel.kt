package com.example.freeupcopy.domain.enums

enum class PricingModel(val displayValue: String) {
    CASH("Cash"),
    COINS("Coins"),
    CASH_AND_COINS("Cash\n+\nCoins");

//    companion object {
//        fun fromDisplayValue(value: String): PricingModel? {
//            return values().find { it.displayValue == value }
//        }
//    }
}