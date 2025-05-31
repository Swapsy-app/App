package com.example.freeupcopy.data.remote.dto.product


data class AcceptedOfferResponse(
    val hasAcceptedOffer: Boolean,
    val acceptedOffer: BargainOffer?
)

data class BargainOffer(
    val _id: String,
    val offeredPrice: Double,
    val offeredIn: String, // "cash", "coin", "mix"
    val status: String
)