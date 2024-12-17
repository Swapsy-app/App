package com.example.freeupcopy.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Weight(
    val type: String,
    val range: String,
    val description: String
)