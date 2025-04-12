package com.example.freeupcopy.ui.presentation.sell_screen.advance_setting_screen

import com.example.freeupcopy.data.remote.dto.auth.SignUpOtpVerifyResponse
import com.example.freeupcopy.data.remote.dto.sell.GstResponse

data class AdvanceSettingUiState(
    val gst: String = "",

    val gstResponse: GstResponse? = null,

    val isLoading: Boolean = false,
    val error: String = ""
)