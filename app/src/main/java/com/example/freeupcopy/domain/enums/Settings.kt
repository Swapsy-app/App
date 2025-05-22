package com.example.freeupcopy.domain.enums

enum class Settings(val valueName: String) {
    ADDRESS("My Address"),
    MANAGE_TAX_INFO("Manage Tax Info"),
    BLOCKED_CONTENT("Blocked Content"),
    NEED_HELP("Need Help"),
    ACCOUNT_SETTINGS("Account Settings"),
    ABOUT_US("About Us")
}

//fun getCurrencyFromString(offeredIn: String): Currency {
//    return when (offeredIn.lowercase()) {
//        "coin" -> Currency.COIN
//        "cash" -> Currency.CASH
//        else -> throw IllegalArgumentException("Unknown currency type: $offeredIn")
//    }
//}