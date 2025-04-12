package com.example.freeupcopy.data.remote.dto.sell.address

data class AddressesResponse(
    val addresses: List<Address>,
    val pagination: Pagination,
)