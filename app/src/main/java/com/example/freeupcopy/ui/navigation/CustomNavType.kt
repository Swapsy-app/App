package com.example.freeupcopy.ui.navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.example.freeupcopy.data.remote.dto.cart.Seller
import com.example.freeupcopy.domain.model.Price
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object CustomNavType {

    val PriceType = object : NavType<Price?> (
        isNullableAllowed = true
    ) {
        override fun get(bundle: Bundle, key: String): Price? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): Price? {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun serializeAsValue(value: Price?): String {
            return Uri.encode(Json.encodeToString(value))
        }

        override fun put(bundle: Bundle, key: String, value: Price?) {
            bundle.putString(key, Json.encodeToString(value))
        }
    }

    val SizeType = object : NavType<com.example.freeupcopy.data.remote.dto.sell.Size?>(
        isNullableAllowed = true
    ) {
        override fun get(bundle: Bundle, key: String): com.example.freeupcopy.data.remote.dto.sell.Size? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): com.example.freeupcopy.data.remote.dto.sell.Size? {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun serializeAsValue(value: com.example.freeupcopy.data.remote.dto.sell.Size?): String {
            return Uri.encode(Json.encodeToString(value))
        }

        override fun put(bundle: Bundle, key: String, value: com.example.freeupcopy.data.remote.dto.sell.Size?) {
            bundle.putString(key, Json.encodeToString(value))
        }
    }

    val SellerType = object : NavType<Seller>(
        isNullableAllowed = true
    ) {
        override fun get(bundle: Bundle, key: String): Seller {
            return Json.decodeFromString(bundle.getString(key) ?: return Seller("", "", ""))
        }

        override fun parseValue(value: String): Seller {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun serializeAsValue(value: Seller): String {
            return Uri.encode(Json.encodeToString(value))
        }

        override fun put(bundle: Bundle, key: String, value: Seller) {
            bundle.putString(key, Json.encodeToString(value))
        }
    }
}