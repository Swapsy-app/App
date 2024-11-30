package com.example.freeupcopy.domain.model

data class Weight(
    val range: String,
    val description: String
) {
    companion object {
        val predefinedWeight = Weight("", "")
    }
}