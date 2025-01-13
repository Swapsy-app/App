package com.example.freeupcopy.ui.presentation.wish_list

import com.example.freeupcopy.domain.model.BABY_AND_KIDS
import com.example.freeupcopy.domain.model.BEAUTY_AND_CARE
import com.example.freeupcopy.domain.model.BOOKS
import com.example.freeupcopy.domain.model.CATEGORY
import com.example.freeupcopy.domain.model.GADGETS
import com.example.freeupcopy.domain.model.HOME_AND_KITCHEN
import com.example.freeupcopy.domain.model.MEN
import com.example.freeupcopy.domain.model.WOMEN

data class WishListUiState(

    val isBottomPopupVisible : Boolean = false,
    val selectedOption: SelectedOption = SelectedOption.None,
    val AvailablityOption : AvailablityOptions = AvailablityOptions.None,

    val sortOptions: SortOptions = SortOptions.Recommended,

    val conditionOption: Map<ConditionOption,Boolean> = ConditionOption.entries.associateWith { true },

    val category: CATEGORY = CATEGORY.None,

    val menCategory: Map<MEN, Boolean> = MEN.entries.associateWith { false },
    val womenCategory: Map<WOMEN, Boolean> = WOMEN.entries.associateWith { false },
    val babyAndKidsCategory: Map<BABY_AND_KIDS, Boolean> = BABY_AND_KIDS.entries.associateWith { false },
    val beautyAndCareCategory: Map<BEAUTY_AND_CARE, Boolean> = BEAUTY_AND_CARE.entries.associateWith { false },
    val booksCategory: Map<BOOKS, Boolean> = BOOKS.entries.associateWith { false },
    val homeAndKitchenCategory: Map<HOME_AND_KITCHEN, Boolean> = HOME_AND_KITCHEN.entries.associateWith { false },
    val gadgetsCategory: Map<GADGETS, Boolean> = GADGETS.entries.associateWith { false },
)

enum class SelectedOption{
    Availiblity,
    Sort,
    Condition,
    Category,
    None
}

enum class AvailablityOptions{
    Available,
    SoldOut,
    None
}

enum class SortOptions{
    PriceLoToHi,
    PriceHiToLo,
    Recommended,
    SellerRating,
    NewlyArrived,
}

enum class ConditionOption{
    NewWithTags,
    LikeNew,
    Good,
    Used,
    None
}


