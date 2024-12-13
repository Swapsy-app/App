package com.example.freeupcopy.utils

import java.util.Locale

fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val secs = seconds % 60
    return String.format(Locale.getDefault(),"%d:%02d", minutes, secs)
}