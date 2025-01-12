package com.example.freeupcopy.domain.enums

enum class SignUpStatus(val value: String) {
    CREATED("CREATED"),
    UNVERIFIED("UNVERIFIED"),
    VERIFIED("VERIFIED"),
    UNVERIFIED_OTP_RESENT("UNVERIFIED_OTP_RESENT"),
    MOBILE_EXISTS("MOBILE_EXISTS");

    companion object {
        fun fromString(value: String): SignUpStatus? =
            entries.find { it.value == value }
    }
}