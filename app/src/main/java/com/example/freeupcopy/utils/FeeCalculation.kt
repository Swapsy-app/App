package com.example.freeupcopy.utils

import kotlin.math.ceil

fun calculateDeliveryFee(weightCategory: String, price: Long): Long {
    return when (weightCategory) {
        "cat0" -> 25L
        "cat1" -> 30L
        "cat2" -> 100L
        "cat3" -> 150L
        else -> throw IllegalArgumentException("Invalid weight category: $weightCategory")
    }
}

fun calculatePlatformFee(price: Long, commissionRate: Double = 5.0): Long {
    return ceil(price * (commissionRate / 100)).toLong()
}

fun calculateTotalEarnings(price: Long, weightCategory: String): Long {
    val deliveryFee = calculateDeliveryFee(weightCategory, price)
    val commission = calculatePlatformFee(price)
    return price - deliveryFee - commission
}

fun validateCash(cashValue: String?): Boolean {
    if (!cashValue.isNullOrEmpty()) {
        val tempCalculate = calculateTotalEarnings(cashValue.toLong(), "cat0")
        return tempCalculate >= 10
    }
    return false
}