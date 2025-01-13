package com.example.freeupcopy.ui.presentation.wish_list

import androidx.lifecycle.ViewModel
import com.example.freeupcopy.domain.model.BABY_AND_KIDS
import com.example.freeupcopy.domain.model.BEAUTY_AND_CARE
import com.example.freeupcopy.domain.model.BOOKS
import com.example.freeupcopy.domain.model.CATEGORY
import com.example.freeupcopy.domain.model.GADGETS
import com.example.freeupcopy.domain.model.HOME_AND_KITCHEN
import com.example.freeupcopy.domain.model.MEN
import com.example.freeupcopy.domain.model.WOMEN
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class WishListViewModel : ViewModel() {
    private val _state = MutableStateFlow(WishListUiState())
    val state = _state.asStateFlow()

    fun onOptionClicked(options: String) {
        _state.value = when (options) {
            "Availability" -> _state.value.copy(selectedOption = SelectedOption.Availiblity)
            "Sort" -> _state.value.copy(selectedOption = SelectedOption.Sort)
            "Condition" -> _state.value.copy(selectedOption = SelectedOption.Condition)
            "Category" -> _state.value.copy(selectedOption = SelectedOption.Category)
            else -> _state.value
        }
    }

    fun onOpenBottomPopup() {
        _state.update {
            it.copy(isBottomPopupVisible = true)
        }
    }

    fun onCloseBottomPopup() {
        _state.update {
            it.copy(isBottomPopupVisible = false)
        }
    }

    fun isOptionSelected(options: String): Boolean {
        return when (options) {
            "Availability" -> _state.value.selectedOption == SelectedOption.Availiblity
            "Sort" -> _state.value.selectedOption == SelectedOption.Sort
            "Condition" -> _state.value.selectedOption == SelectedOption.Condition
            "Category" -> _state.value.selectedOption == SelectedOption.Category
            else -> false
        }
    }

    // Availability Selection
    fun selectCurrentlyAvailable() {
        _state.update { it.copy(AvailablityOption = AvailablityOptions.Available) }
    }

    fun selectSoldOut() {
        _state.update { it.copy(AvailablityOption = AvailablityOptions.SoldOut) }
    }

    // Sort Options Selection
    fun selectRecommended() {
        _state.update { it.copy(sortOptions = SortOptions.Recommended) }
    }

    fun selectNewlyArrived() {
        _state.update { it.copy(sortOptions = SortOptions.NewlyArrived) }
    }

    fun selectSellerRating() {
        _state.update { it.copy(sortOptions = SortOptions.SellerRating) }
    }

    fun selectPriceHiToLo() {
        _state.update { it.copy(sortOptions = SortOptions.PriceHiToLo) }
    }

    fun selectPriceLoToHi() {
        _state.update { it.copy(sortOptions = SortOptions.PriceLoToHi) }
    }

    // Condition Options Selection
    fun selectGood() {
        _state.update { currentState ->
            currentState.copy(
                conditionOption = currentState.conditionOption.mapValues { (key, value) ->
                    if (key == ConditionOption.Good) !value else value
                }
            )
        }
    }

    fun selectUsed() {
        _state.update { currentState ->
            currentState.copy(
                conditionOption = currentState.conditionOption.mapValues { (key, value) ->
                    if (key == ConditionOption.Used) !value else value
                }
            )
        }
    }

    fun selectLikeNew() {
        _state.update { currentState ->
            currentState.copy(
                conditionOption = currentState.conditionOption.mapValues { (key, value) ->
                    if (key == ConditionOption.LikeNew) !value else value
                }
            )
        }
    }

    fun selectNewWithTags() {
        _state.update { currentState ->
            currentState.copy(
                conditionOption = currentState.conditionOption.mapValues { (key, value) ->
                    if (key == ConditionOption.NewWithTags) !value else value
                }
            )
        }
    }

    fun selectBackCategory(){
        _state.update {
            it.copy(
                category = CATEGORY.None
            )
        }
    }

    // Category Selection
    fun selectMen() {
        _state.update { currentState ->
            currentState.copy(
                category = CATEGORY.MEN,
            )
        }
    }

    fun selectWomen() {
        _state.update { currentState ->
            currentState.copy(
                category = CATEGORY.WOMEN,
            )
        }
    }

    fun selectBabyAndKids() {
        _state.update { currentState ->
            currentState.copy(
                category = CATEGORY.BABY_AND_KIDS,
            )
        }
    }

    fun selectBeautyAndCare() {
        _state.update { currentState ->
            currentState.copy(
                category = CATEGORY.BEAUTY_AND_CARE,
            )
        }
    }

    fun selectBooks() {
        _state.update { currentState ->
            currentState.copy(
                category = CATEGORY.BOOKS,
            )
        }
    }

    fun selectHomeAndKitchen() {
        _state.update { currentState ->
            currentState.copy(
                category = CATEGORY.HOME_AND_KITCHEN,
            )
        }
    }

    fun selectGadgets() {
        _state.update { currentState ->
            currentState.copy(
                category = CATEGORY.GADGETS,
            )
        }
    }

    // Category Sub-Selections (for MEN)
    // Category Sub-Selections (for MEN)
    fun selectTshirtAndShirts() {
        _state.update { currentState ->
            currentState.copy(
                menCategory = currentState.menCategory.toMutableMap().apply {
                    this[MEN.T_SHIRTS_AND_SHIRTS] = !this[MEN.T_SHIRTS_AND_SHIRTS]!!
                }
            )
        }
    }

    fun selectSweatsAndHoodies() {
        _state.update { currentState ->
            currentState.copy(
                menCategory = currentState.menCategory.toMutableMap().apply {
                    this[MEN.SWEATS_AND_HOODIES] = !this[MEN.SWEATS_AND_HOODIES]!!
                }
            )
        }
    }

    fun selectSweaters() {
        _state.update { currentState ->
            currentState.copy(
                menCategory = currentState.menCategory.toMutableMap().apply {
                    this[MEN.SWEATERS] = !this[MEN.SWEATERS]!!
                }
            )
        }
    }

    fun selectJeansAndPants() {
        _state.update { currentState ->
            currentState.copy(
                menCategory = currentState.menCategory.toMutableMap().apply {
                    this[MEN.JEANS_AND_PANTS] = !this[MEN.JEANS_AND_PANTS]!!
                }
            )
        }
    }

    fun selectShorts() {
        _state.update { currentState ->
            currentState.copy(
                menCategory = currentState.menCategory.toMutableMap().apply {
                    this[MEN.SHORTS] = !this[MEN.SHORTS]!!
                }
            )
        }
    }

    fun selectCoatsAndJackets() {
        _state.update { currentState ->
            currentState.copy(
                menCategory = currentState.menCategory.toMutableMap().apply {
                    this[MEN.COATS_AND_JACKETS] = !this[MEN.COATS_AND_JACKETS]!!
                }
            )
        }
    }

    fun selectSuitsAndBlazers() {
        _state.update { currentState ->
            currentState.copy(
                menCategory = currentState.menCategory.toMutableMap().apply {
                    this[MEN.SUITS_AND_BLAZERS] = !this[MEN.SUITS_AND_BLAZERS]!!
                }
            )
        }
    }

    fun selectEthnicWear() {
        _state.update { currentState ->
            currentState.copy(
                menCategory = currentState.menCategory.toMutableMap().apply {
                    this[MEN.ETHNIC_WEAR] = !this[MEN.ETHNIC_WEAR]!!
                }
            )
        }
    }

    fun selectFootwear() {
        _state.update { currentState ->
            currentState.copy(
                menCategory = currentState.menCategory.toMutableMap().apply {
                    this[MEN.FOOTWEAR] = !this[MEN.FOOTWEAR]!!
                }
            )
        }
    }

    fun selectBagsAndBackpacks() {
        _state.update { currentState ->
            currentState.copy(
                menCategory = currentState.menCategory.toMutableMap().apply {
                    this[MEN.BAGS_AND_BACKPACKS] = !this[MEN.BAGS_AND_BACKPACKS]!!
                }
            )
        }
    }

    fun selectAccessories() {
        _state.update { currentState ->
            currentState.copy(
                menCategory = currentState.menCategory.toMutableMap().apply {
                    this[MEN.ACCESSORIES] = !this[MEN.ACCESSORIES]!!
                }
            )
        }
    }

    fun selectAthleticWear() {
        _state.update { currentState ->
            currentState.copy(
                menCategory = currentState.menCategory.toMutableMap().apply {
                    this[MEN.ATHLETIC_WEAR] = !this[MEN.ATHLETIC_WEAR]!!
                }
            )
        }
    }

    fun selectOther() {
        _state.update { currentState ->
            currentState.copy(
                menCategory = currentState.menCategory.toMutableMap().apply {
                    this[MEN.OTHER] = !this[MEN.OTHER]!!
                }
            )
        }
    }

    // Category Sub-Selections (for WOMEN)
    fun selectWomenEthnic() {
        _state.update { currentState ->
            currentState.copy(
                womenCategory = currentState.womenCategory.toMutableMap().apply {
                    this[WOMEN.ETHNIC] = !this[WOMEN.ETHNIC]!!
                }
            )
        }
    }

    fun selectWomenWestern() {
        _state.update { currentState ->
            currentState.copy(
                womenCategory = currentState.womenCategory.toMutableMap().apply {
                    this[WOMEN.WESTERN] = !this[WOMEN.WESTERN]!!
                }
            )
        }
    }

    fun selectWomenJewellery() {
        _state.update { currentState ->
            currentState.copy(
                womenCategory = currentState.womenCategory.toMutableMap().apply {
                    this[WOMEN.JEWELLERY] = !this[WOMEN.JEWELLERY]!!
                }
            )
        }
    }

    fun selectWomenAccessories() {
        _state.update { currentState ->
            currentState.copy(
                womenCategory = currentState.womenCategory.toMutableMap().apply {
                    this[WOMEN.ACCESSORIES] = !this[WOMEN.ACCESSORIES]!!
                }
            )
        }
    }

    fun selectWomenBags() {
        _state.update { currentState ->
            currentState.copy(
                womenCategory = currentState.womenCategory.toMutableMap().apply {
                    this[WOMEN.BAGS] = !this[WOMEN.BAGS]!!
                }
            )
        }
    }

    fun selectWomenFootwear() {
        _state.update { currentState ->
            currentState.copy(
                womenCategory = currentState.womenCategory.toMutableMap().apply {
                    this[WOMEN.FOOTWEAR] = !this[WOMEN.FOOTWEAR]!!
                }
            )
        }
    }

    fun selectWomenInnerWearAndSleepwear() {
        _state.update { currentState ->
            currentState.copy(
                womenCategory = currentState.womenCategory.toMutableMap().apply {
                    this[WOMEN.INNER_WEAR_AND_SLEEPWEAR] = !this[WOMEN.INNER_WEAR_AND_SLEEPWEAR]!!
                }
            )
        }
    }

    // Category Sub-Selections (for BABY_AND_KIDS)
    fun selectBabyAndKidsBoysClothing() {
        _state.update { currentState ->
            currentState.copy(
                babyAndKidsCategory = currentState.babyAndKidsCategory.toMutableMap().apply {
                    this[BABY_AND_KIDS.BOYS_CLOTHING] = !this[BABY_AND_KIDS.BOYS_CLOTHING]!!
                }
            )
        }
    }

    fun selectBabyAndKidsGirlsClothing() {
        _state.update { currentState ->
            currentState.copy(
                babyAndKidsCategory = currentState.babyAndKidsCategory.toMutableMap().apply {
                    this[BABY_AND_KIDS.GIRLS_CLOTHING] = !this[BABY_AND_KIDS.GIRLS_CLOTHING]!!
                }
            )
        }
    }

    fun selectBabyAndKidsBoysFootwear() {
        _state.update { currentState ->
            currentState.copy(
                babyAndKidsCategory = currentState.babyAndKidsCategory.toMutableMap().apply {
                    this[BABY_AND_KIDS.BOYS_FOOTWEAR] = !this[BABY_AND_KIDS.BOYS_FOOTWEAR]!!
                }
            )
        }
    }

    fun selectBabyAndKidsGirlsFootwear() {
        _state.update { currentState ->
            currentState.copy(
                babyAndKidsCategory = currentState.babyAndKidsCategory.toMutableMap().apply {
                    this[BABY_AND_KIDS.GIRLS_FOOTWEAR] = !this[BABY_AND_KIDS.GIRLS_FOOTWEAR]!!
                }
            )
        }
    }

    fun selectBabyAndKidsBathAndSkinCare() {
        _state.update { currentState ->
            currentState.copy(
                babyAndKidsCategory = currentState.babyAndKidsCategory.toMutableMap().apply {
                    this[BABY_AND_KIDS.BATH_AND_SKIN_CARE] = !this[BABY_AND_KIDS.BATH_AND_SKIN_CARE]!!
                }
            )
        }
    }

    fun selectBabyAndKidsAccessories() {
        _state.update { currentState ->
            currentState.copy(
                babyAndKidsCategory = currentState.babyAndKidsCategory.toMutableMap().apply {
                    this[BABY_AND_KIDS.ACCESSORIES] = !this[BABY_AND_KIDS.ACCESSORIES]!!
                }
            )
        }
    }

    fun selectBabyAndKidsToysAndGames() {
        _state.update { currentState ->
            currentState.copy(
                babyAndKidsCategory = currentState.babyAndKidsCategory.toMutableMap().apply {
                    this[BABY_AND_KIDS.TOYS_AND_GAMES] = !this[BABY_AND_KIDS.TOYS_AND_GAMES]!!
                }
            )
        }
    }

    fun selectBabyAndKidsOther() {
        _state.update { currentState ->
            currentState.copy(
                babyAndKidsCategory = currentState.babyAndKidsCategory.toMutableMap().apply {
                    this[BABY_AND_KIDS.OTHER] = !this[BABY_AND_KIDS.OTHER]!!
                }
            )
        }
    }

    // Category Sub-Selections (for BEAUTY_AND_CARE)
    fun selectBeautyAndCareSkinCare() {
        _state.update { currentState ->
            currentState.copy(
                beautyAndCareCategory = currentState.beautyAndCareCategory.toMutableMap().apply {
                    this[BEAUTY_AND_CARE.SKIN_CARE] = !this[BEAUTY_AND_CARE.SKIN_CARE]!!
                }
            )
        }
    }

    fun selectBeautyAndCareHairCare() {
        _state.update { currentState ->
            currentState.copy(
                beautyAndCareCategory = currentState.beautyAndCareCategory.toMutableMap().apply {
                    this[BEAUTY_AND_CARE.HAIR_CARE] = !this[BEAUTY_AND_CARE.HAIR_CARE]!!
                }
            )
        }
    }

    fun selectBeautyAndCareMakeupAndNails() {
        _state.update { currentState ->
            currentState.copy(
                beautyAndCareCategory = currentState.beautyAndCareCategory.toMutableMap().apply {
                    this[BEAUTY_AND_CARE.MAKEUP_AND_NAILS] = !this[BEAUTY_AND_CARE.MAKEUP_AND_NAILS]!!
                }
            )
        }
    }

    fun selectBeautyAndCareOther() {
        _state.update { currentState ->
            currentState.copy(
                beautyAndCareCategory = currentState.beautyAndCareCategory.toMutableMap().apply {
                    this[BEAUTY_AND_CARE.OTHER] = !this[BEAUTY_AND_CARE.OTHER]!!
                }
            )
        }
    }

    // Category Sub-Selections (for BOOKS)
    fun selectBooksFiction() {
        _state.update { currentState ->
            currentState.copy(
                booksCategory = currentState.booksCategory.toMutableMap().apply {
                    this[BOOKS.FICTION] = !this[BOOKS.FICTION]!!
                }
            )
        }
    }

    fun selectBooksNonFiction() {
        _state.update { currentState ->
            currentState.copy(
                booksCategory = currentState.booksCategory.toMutableMap().apply {
                    this[BOOKS.NON_FICTION] = !this[BOOKS.NON_FICTION]!!
                }
            )
        }
    }

    fun selectBooksAcademic() {
        _state.update { currentState ->
            currentState.copy(
                booksCategory = currentState.booksCategory.toMutableMap().apply {
                    this[BOOKS.ACADEMIC] = !this[BOOKS.ACADEMIC]!!
                }
            )
        }
    }

    fun selectBooksChildren() {
        _state.update { currentState ->
            currentState.copy(
                booksCategory = currentState.booksCategory.toMutableMap().apply {
                    this[BOOKS.CHILDREN] = !this[BOOKS.CHILDREN]!!
                }
            )
        }
    }

    fun selectBooksComicsAndGraphicNovels() {
        _state.update { currentState ->
            currentState.copy(
                booksCategory = currentState.booksCategory.toMutableMap().apply {
                    this[BOOKS.COMICS_AND_GRAPHIC_NOVELS] = !this[BOOKS.COMICS_AND_GRAPHIC_NOVELS]!!
                }
            )
        }
    }

    fun selectBooksOther() {
        _state.update { currentState ->
            currentState.copy(
                booksCategory = currentState.booksCategory.toMutableMap().apply {
                    this[BOOKS.OTHER] = !this[BOOKS.OTHER]!!
                }
            )
        }
    }

    // Category Sub-Selections (for HOME_AND_KITCHEN)
    fun selectHomeAndKitchenFurniture() {
        _state.update { currentState ->
            currentState.copy(
                homeAndKitchenCategory = currentState.homeAndKitchenCategory.toMutableMap().apply {
                    this[HOME_AND_KITCHEN.FURNITURE] = !this[HOME_AND_KITCHEN.FURNITURE]!!
                }
            )
        }
    }

    fun selectHomeAndKitchenDecor() {
        _state.update { currentState ->
            currentState.copy(
                homeAndKitchenCategory = currentState.homeAndKitchenCategory.toMutableMap().apply {
                    this[HOME_AND_KITCHEN.DECOR] = !this[HOME_AND_KITCHEN.DECOR]!!
                }
            )
        }
    }

    fun selectHomeAndKitchenKitchenware() {
        _state.update { currentState ->
            currentState.copy(
                homeAndKitchenCategory = currentState.homeAndKitchenCategory.toMutableMap().apply {
                    this[HOME_AND_KITCHEN.KITCHENWARE] = !this[HOME_AND_KITCHEN.KITCHENWARE]!!
                }
            )
        }
    }

    fun selectHomeAndKitchenBedding() {
        _state.update { currentState ->
            currentState.copy(
                homeAndKitchenCategory = currentState.homeAndKitchenCategory.toMutableMap().apply {
                    this[HOME_AND_KITCHEN.BEDDING] = !this[HOME_AND_KITCHEN.BEDDING]!!
                }
            )
        }
    }

    fun selectHomeAndKitchenBath() {
        _state.update { currentState ->
            currentState.copy(
                homeAndKitchenCategory = currentState.homeAndKitchenCategory.toMutableMap().apply {
                    this[HOME_AND_KITCHEN.BATH] = !this[HOME_AND_KITCHEN.BATH]!!
                }
            )
        }
    }

    fun selectHomeAndKitchenAppliances() {
        _state.update { currentState ->
            currentState.copy(
                homeAndKitchenCategory = currentState.homeAndKitchenCategory.toMutableMap().apply {
                    this[HOME_AND_KITCHEN.APPLIANCES] = !this[HOME_AND_KITCHEN.APPLIANCES]!!
                }
            )
        }
    }

    fun selectHomeAndKitchenOther() {
        _state.update { currentState ->
            currentState.copy(
                homeAndKitchenCategory = currentState.homeAndKitchenCategory.toMutableMap().apply {
                    this[HOME_AND_KITCHEN.OTHER] = !this[HOME_AND_KITCHEN.OTHER]!!
                }
            )
        }
    }

    fun selectGadgetsMobilePhones() {
        _state.update { currentState ->
            currentState.copy(
                gadgetsCategory = currentState.gadgetsCategory.mapValues { (key, value) ->
                    if (key == GADGETS.MOBILE_PHONES) !value else value
                }
            )
        }
    }

    fun selectGadgetsLaptops() {
        _state.update { currentState ->
            currentState.copy(
                gadgetsCategory = currentState.gadgetsCategory.mapValues { (key, value) ->
                    if (key == GADGETS.LAPTOPS) !value else value
                }
            )
        }
    }

    fun selectGadgetsTablets() {
        _state.update { currentState ->
            currentState.copy(
                gadgetsCategory = currentState.gadgetsCategory.mapValues { (key, value) ->
                    if (key == GADGETS.TABLETS) !value else value
                }
            )
        }
    }

    fun selectGadgetsCameras() {
        _state.update { currentState ->
            currentState.copy(
                gadgetsCategory = currentState.gadgetsCategory.mapValues { (key, value) ->
                    if (key == GADGETS.CAMERAS) !value else value
                }
            )
        }
    }

    fun selectGadgetsWearables() {
        _state.update { currentState ->
            currentState.copy(
                gadgetsCategory = currentState.gadgetsCategory.mapValues { (key, value) ->
                    if (key == GADGETS.WEARABLES) !value else value
                }
            )
        }
    }

    fun selectGadgetsAccessories() {
        _state.update { currentState ->
            currentState.copy(
                gadgetsCategory = currentState.gadgetsCategory.mapValues { (key, value) ->
                    if (key == GADGETS.ACCESSORIES) !value else value
                }
            )
        }
    }

    fun selectGadgetsOther() {
        _state.update { currentState ->
            currentState.copy(
                gadgetsCategory = currentState.gadgetsCategory.mapValues { (key, value) ->
                    if (key == GADGETS.OTHER) !value else value
                }
            )
        }
    }



}
