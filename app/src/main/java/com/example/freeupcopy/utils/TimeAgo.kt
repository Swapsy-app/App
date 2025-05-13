package com.example.freeupcopy.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Duration
import java.time.OffsetDateTime
import java.time.ZoneOffset


@RequiresApi(Build.VERSION_CODES.O)
fun getTimeAgo(isoDateTime: String): String {
    val createdTime = OffsetDateTime.parse(isoDateTime)
    val now = OffsetDateTime.now(ZoneOffset.UTC)
    val duration = Duration.between(createdTime, now)

    val seconds = duration.seconds

    return when {
        seconds < 60 -> "${seconds}s ago"
        seconds < 3600 -> "${seconds / 60}min ago"
        seconds < 86400 -> "${seconds / 3600}h ago"
        seconds < 604800 -> "${seconds / 86400}d ago"
        seconds < 2592000 -> "${seconds / 604800}w ago"
        seconds < 31536000 -> "${seconds / 2592000}m ago"
        else -> "${seconds / 31536000}yr ago"
    }
}