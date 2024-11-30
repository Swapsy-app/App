package com.example.freeupcopy.domain.model

data class Price(
    val mrp: String,
    val coin: String
) {
    companion object {
        val predefinedPrice = Price("", "")
    }
}