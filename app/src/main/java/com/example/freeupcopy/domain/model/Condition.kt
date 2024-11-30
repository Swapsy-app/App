package com.example.freeupcopy.domain.model

data class Condition(
    val tag: String,
    val description: String
) {
    companion object {
        val predefinedCondition = Weight("", "")
    }
}