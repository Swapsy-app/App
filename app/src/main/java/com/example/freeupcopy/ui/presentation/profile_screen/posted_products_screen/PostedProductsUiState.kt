package com.example.freeupcopy.ui.presentation.profile_screen.posted_products_screen

import com.example.freeupcopy.ui.presentation.profile_screen.componants.TabData

data class PostedProductsUiState(
    val isSearching: Boolean = false,
    val currentTabIndex: Int = 0,
    val listingFilterIndex: Int? = null,
    val pendingFilterIndex: Int? = null,
    val deliveredFilterIndex: Int? = null,

    val tabData: TabData = TabData(
        listedTotalCount = "12",
        pendingTotalCount = "5",
        deliveredTotalCount = "3",
        listedSubCounts = mapOf(
            "active" to "8",
            "drafts" to "1",
            "under_review" to "3",
            "unavailable" to "1"
        ),
        pendingSubCounts = mapOf(
            "order_received" to "3",
            "shipped" to "1",
            "issues" to "1"
        ),
        deliveredSubCounts = mapOf(
            "completed" to "2",
            "cancelled" to "1"
        )
    ),

    val isLoading: Boolean = false,
    val error: String = ""
)