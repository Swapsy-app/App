package com.example.freeupcopy.data.remote.dto.sell

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

data class ProductRequest(
    val category: Category,
    val images: List<String>,
    val video: String?,
    val title: String,
    val description: String,
    val pickupAddress: String?, // Assuming this is the ObjectId as a String
    val condition: String,
    val manufacturingCountry: String,
    val weight: Double,
    val brand: String?,
    val occasion: String?,
    val color: String?,
    val shape: String?,
    val fabric: String?,
    val quantity: Int = 1,
    val shippingMethod: String,
//    val gstNumber: String?,
//    val sellerId: String, // Assuming this is the ObjectId as a String
    val size: Size?,
    val price: Price
)

data class Category(
    val primaryCategory: String,
    val secondaryCategory: String?,
    val tertiaryCategory: String?
)


@Serializable
data class Size(
    val attributes: List<SizeAttribute>?,
    val freeSize: Boolean = false,
    val sizeString: String?
)

@Serializable
data class SizeAttribute(
    val name: String,
    val value: String
)

data class Price(
    val mrp: Double,
    val cash: Cash?,
    val coin: Coin?,
    val mix: Mix?
)

data class Cash(
    val enteredAmount: Double?,
    val sellerReceivesCash: Double?
)

data class Coin(
    val enteredAmount: Double?,
    val sellerReceivesCoin: Double?
)

data class Mix(
    val enteredCash: Double?,
    val enteredCoin: Double?,
    val sellerReceivesCash: Double?,
    val sellerReceivesCoin: Double?
)

fun Size.toSizeString(): String {
    return when {
        attributes?.isNotEmpty() == true ->
            attributes.joinToString(" | ") { "${it.name} ${it.value.substringBefore("/").trim()}" }
        sizeString?.isNotEmpty() == true -> sizeString
        else -> ""
    }
}