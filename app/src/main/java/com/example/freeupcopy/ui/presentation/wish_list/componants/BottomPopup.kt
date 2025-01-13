package com.example.freeupcopy.ui.presentation.wish_list.componants

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.freeupcopy.domain.model.BABY_AND_KIDS
import com.example.freeupcopy.domain.model.BEAUTY_AND_CARE
import com.example.freeupcopy.domain.model.BOOKS
import com.example.freeupcopy.domain.model.CATEGORY
import com.example.freeupcopy.domain.model.GADGETS
import com.example.freeupcopy.domain.model.HOME_AND_KITCHEN
import com.example.freeupcopy.domain.model.MEN
import com.example.freeupcopy.domain.model.WOMEN
import com.example.freeupcopy.ui.presentation.wish_list.AvailablityOptions
import com.example.freeupcopy.ui.presentation.wish_list.ConditionOption
import com.example.freeupcopy.ui.presentation.wish_list.SelectedOption
import com.example.freeupcopy.ui.presentation.wish_list.SortOptions
import com.example.freeupcopy.ui.presentation.wish_list.WishListViewModel
import com.example.freeupcopy.ui.theme.CardShape
import com.example.freeupcopy.ui.theme.SwapsyTheme


@Composable
fun BottomPopup(
    selectedOption: SelectedOption,

    availablityOptions: AvailablityOptions,
    sortOptions: SortOptions,
    conditionOption: Map<ConditionOption, Boolean>,
    category: CATEGORY,

    men: Map<MEN, Boolean>,
    women: Map<WOMEN,Boolean>,
    babyAndKids: Map<BABY_AND_KIDS,Boolean>,
    books: Map<BOOKS,Boolean>,
    homeAndKitchen: Map<HOME_AND_KITCHEN,Boolean>,
    beautyAndCare: Map<BEAUTY_AND_CARE,Boolean>,
    gadgets: Map<GADGETS,Boolean>,

    // Availability selection
    selectCurrentlyAvailable: () -> Unit,
    selectSoldOut: () -> Unit,

    // Sorting selection
    selectRecommended: () -> Unit,
    selectNewlyArrived: () -> Unit,
    selectSellerRating: () -> Unit,
    selectPriceLoToHi: () -> Unit,
    selectPriceHiToLo: () -> Unit,

    // Condition selection
    selectNewWithTags: () -> Unit,
    selectLikeNew: () -> Unit,
    selectGood: () -> Unit,
    selectUsed: () -> Unit,

    // Category selection
    selectMen: () -> Unit,
    selectWomen: () -> Unit,
    selectBabyAndKids: () -> Unit,
    selectBeautyAndCare: () -> Unit,
    selectBooks: () -> Unit,
    selectHomeAndKitchen: () -> Unit,
    selectGadgets: () -> Unit,
    selectBackCategory : () -> Unit,

    // Specific Men category selection
    selectTshirtAndShirts: () -> Unit,
    selectSweatsAndHoodies: () -> Unit,
    selectSweaters: () -> Unit,
    selectJeansAndPants: () -> Unit,
    selectShorts: () -> Unit,
    selectCoatsAndJackets: () -> Unit,
    selectSuitsAndBlazers: () -> Unit,
    selectEthnicWear: () -> Unit,
    selectFootwear: () -> Unit,
    selectBagsAndBackpacks: () -> Unit,
    selectAccessories: () -> Unit,
    selectAthleticWear: () -> Unit,
    selectOther: () -> Unit,

    selectWomenAccessories: () -> Unit,
    selectWomenBags: () -> Unit,
    selectWomenFootwear: () -> Unit,
    selectWomenJewellery: () -> Unit,
    selectWomenEthnic: () -> Unit,
    selectWomenWestern: () -> Unit,
    selectWomenInnerWearAndSleepwear: () -> Unit,

    selectBabyAndKidsOther: () -> Unit,
    selectBabyAndKidsAccessories: () -> Unit,
    selectBabyAndKidsBoysClothing: () -> Unit,
    selectBabyAndKidsGirlsClothing: () -> Unit,
    selectBabyAndKidsToysAndGames: () -> Unit,
    selectBabyAndKidsBoysFootwear: () -> Unit,
    selectBabyAndKidsGirlsFootwear: () -> Unit,
    selectBabyAndKidsBathAndSkinCare: () -> Unit,

    selectBeautyAndCareHairCare: () -> Unit,
    selectBeautyAndCareSkinCare: () -> Unit,
    selectBeautyAndCareMakeupAndNails: () -> Unit,
    selectBeautyAndCareOther: () -> Unit,

    selectBooksNonFiction: () -> Unit,
    selectBooksComicsAndGraphicNovels: () -> Unit,
    selectBooksAcademic: () -> Unit,
    selectBooksChildren: () -> Unit,
    selectBooksOther: () -> Unit,
    selectBooksFiction: () -> Unit,

    selectHomeAndKitchenDecor: () -> Unit,
    selectHomeAndKitchenOther: () -> Unit,
    selectHomeAndKitchenFurniture: () -> Unit,
    selectHomeAndKitchenBedding: () -> Unit,
    selectHomeAndKitchenAppliances: () -> Unit,
    selectHomeAndKitchenKitchenware: () -> Unit,
    selectHomeAndKitchenBath: () -> Unit,

    selectGadgetsMobilePhones: () -> Unit,
    selectGadgetsWearables: () -> Unit,
    selectGadgetsAccessories: () -> Unit,
    selectGadgetsCameras: () -> Unit,
    selectGadgetsLaptops: () -> Unit,
    selectGadgetsTablets: () -> Unit,
    selectGadgetsOther: () -> Unit,
)
{

        Column(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Text(
                text = selectedOption.name,
                fontSize = 24.sp,
                modifier = Modifier.padding(24.dp,24.dp,24.dp,16.dp)
            )
            Divider(
                modifier = Modifier.fillMaxWidth()
            )
            when(selectedOption){
                SelectedOption.Availiblity->{
                    CheckBoxOption(
                        name = "Currently Available",
                        checked = availablityOptions == AvailablityOptions.Available,
                        onCheckChange = {selectCurrentlyAvailable()}
                    )
                    CheckBoxOption(
                        name = "Sold Out",
                        checked = availablityOptions == AvailablityOptions.SoldOut,
                        onCheckChange = {selectSoldOut()}
                    )
                }
                SelectedOption.Sort ->{
                    CheckBoxOption(
                        name = "Recommended",
                        checked = sortOptions == SortOptions.Recommended,
                        onCheckChange = {selectRecommended()}
                    )
                    CheckBoxOption(
                        name = "Newly Arrived",
                        checked = sortOptions == SortOptions.NewlyArrived,
                        onCheckChange = {selectNewlyArrived()}
                    )
                    CheckBoxOption(
                        name = "Highest Rating",
                        checked = sortOptions == SortOptions.SellerRating,
                        onCheckChange = {selectSellerRating()}
                    )
                    CheckBoxOption(
                        name = "Lowest To Highest Price",
                        checked = sortOptions == SortOptions.PriceLoToHi,
                        onCheckChange = {selectPriceLoToHi()}
                    )
                    CheckBoxOption(
                        name = "Highest To Lowest Price",
                        checked = sortOptions == SortOptions.PriceHiToLo,
                        onCheckChange = {selectPriceHiToLo()}
                    )
                }
                SelectedOption.Condition ->{
                    CheckBoxOption(
                        name = "New With Tags",
                        checked = conditionOption[ConditionOption.NewWithTags] == true,
                        onCheckChange = { selectNewWithTags() }
                    )
                    CheckBoxOption(
                        name = "Like New",
                        checked = conditionOption[ConditionOption.LikeNew] == true,
                        onCheckChange = { selectLikeNew() }
                    )
                    CheckBoxOption(
                        name = "Good",
                        checked = conditionOption[ConditionOption.Good] == true,
                        onCheckChange = { selectGood() }
                    )
                    CheckBoxOption(
                        name = "Used",
                        checked = conditionOption[ConditionOption.Used] == true,
                        onCheckChange = { selectUsed() }
                    )
                }
                SelectedOption.Category -> {
                    if (category != CATEGORY.None){
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(18.dp,8.dp,24.dp,4.dp)
                                .clickable {
                                    selectBackCategory()
                                }
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.KeyboardArrowLeft,
                                contentDescription = "back button",
                            )
                            Spacer(Modifier.width(25.dp))
                            Text(
                                text = when (category) {
                                    CATEGORY.MEN -> "Mens Collection"
                                    CATEGORY.WOMEN -> "Womens Collection"
                                    CATEGORY.BABY_AND_KIDS -> "Baby and Kids"
                                    CATEGORY.BEAUTY_AND_CARE -> "Beauty and Care"
                                    CATEGORY.BOOKS -> "Books"
                                    CATEGORY.HOME_AND_KITCHEN -> "Home and Kitchen"
                                    CATEGORY.GADGETS -> "Gadgets"
                                    else -> ""
                                },
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 18.sp
                            )
                        }
                    }
                    when(category){
                        CATEGORY.None ->{
                            CategoryOption(
                                name = "Mens",
                                onClick = { selectMen() }
                            )
                            CategoryOption(
                                name = "Womens",
                                onClick = { selectWomen() }
                            )
                            CategoryOption(
                                name = "Baby and Kids",
                                onClick = { selectBabyAndKids() }
                            )
                            CategoryOption(
                                name = "Beauty and Care",
                                onClick = { selectBeautyAndCare() }
                            )
                            CategoryOption(
                                name = "Books",
                                onClick = { selectBooks() }
                            )
                            CategoryOption(
                                name = "Home and Kitchen",
                                onClick = { selectHomeAndKitchen() }
                            )
                            CategoryOption(
                                name = "Gadgets",
                                onClick = { selectGadgets() }
                            )
                        }
                        CATEGORY.MEN ->{
                            CheckBoxOption(
                                name = "T-Shirts and Shirts",
                                checked = men[MEN.T_SHIRTS_AND_SHIRTS] == true,
                                onCheckChange = { selectTshirtAndShirts() }
                            )
                            CheckBoxOption(
                                name = "Sweats and Hoodies",
                                checked = men[MEN.SWEATS_AND_HOODIES] == true,
                                onCheckChange = { selectSweatsAndHoodies() }
                            )
                            CheckBoxOption(
                                name = "Sweaters",
                                checked = men[MEN.SWEATERS] == true,
                                onCheckChange = { selectSweaters() }
                            )
                            CheckBoxOption(
                                name = "Jeans and Pants",
                                checked = men[MEN.JEANS_AND_PANTS] == true,
                                onCheckChange = { selectJeansAndPants() }
                            )
                            CheckBoxOption(
                                name = "Shorts",
                                checked = men[MEN.SHORTS] == true,
                                onCheckChange = { selectShorts() }
                            )
                            CheckBoxOption(
                                name = "Coats and Jackets",
                                checked = men[MEN.COATS_AND_JACKETS] == true,
                                onCheckChange = { selectCoatsAndJackets() }
                            )
                            CheckBoxOption(
                                name = "Suits and Blazers",
                                checked = men[MEN.SUITS_AND_BLAZERS] == true,
                                onCheckChange = { selectSuitsAndBlazers() }
                            )
                            CheckBoxOption(
                                name = "Ethnic Wear",
                                checked = men[MEN.ETHNIC_WEAR] == true,
                                onCheckChange = { selectEthnicWear() }
                            )
                            CheckBoxOption(
                                name = "Footwear",
                                checked = men[MEN.FOOTWEAR] == true,
                                onCheckChange = { selectFootwear() }
                            )
                            CheckBoxOption(
                                name = "Bags and Backpacks",
                                checked = men[MEN.BAGS_AND_BACKPACKS] == true,
                                onCheckChange = { selectBagsAndBackpacks() }
                            )
                            CheckBoxOption(
                                name = "Accessories",
                                checked = men[MEN.ACCESSORIES] == true,
                                onCheckChange = { selectAccessories() }
                            )
                            CheckBoxOption(
                                name = "Athletic Wear",
                                checked = men[MEN.ATHLETIC_WEAR] == true,
                                onCheckChange = { selectAthleticWear() }
                            )
                            CheckBoxOption(
                                name = "Other",
                                checked = men[MEN.OTHER] == true,
                                onCheckChange = { selectOther() }
                            )
                        }
                        CATEGORY.WOMEN ->{
                            CheckBoxOption(
                                name = "Ethnic",
                                checked = women[WOMEN.ETHNIC] == true,
                                onCheckChange = { selectWomenEthnic() }
                            )
                            CheckBoxOption(
                                name = "Western",
                                checked = women[WOMEN.WESTERN] == true,
                                onCheckChange = { selectWomenWestern() }
                            )
                            CheckBoxOption(
                                name = "Jewellery",
                                checked = women[WOMEN.JEWELLERY] == true,
                                onCheckChange = { selectWomenJewellery() }
                            )
                            CheckBoxOption(
                                name = "Accessories",
                                checked = women[WOMEN.ACCESSORIES] == true,
                                onCheckChange = { selectWomenAccessories() }
                            )
                            CheckBoxOption(
                                name = "Bags",
                                checked = women[WOMEN.BAGS] == true,
                                onCheckChange = { selectWomenBags() }
                            )
                            CheckBoxOption(
                                name = "Footwear",
                                checked = women[WOMEN.FOOTWEAR] == true,
                                onCheckChange = { selectWomenFootwear() }
                            )
                            CheckBoxOption(
                                name = "Inner Wear and Sleepwear",
                                checked = women[WOMEN.INNER_WEAR_AND_SLEEPWEAR] == true,
                                onCheckChange = { selectWomenInnerWearAndSleepwear() }
                            )
                        }
                        CATEGORY.BABY_AND_KIDS->{
                            CheckBoxOption(
                                name = "Boys Clothing",
                                checked = babyAndKids[BABY_AND_KIDS.BOYS_CLOTHING] == true,
                                onCheckChange = { selectBabyAndKidsBoysClothing() }
                            )
                            CheckBoxOption(
                                name = "Girls Clothing",
                                checked = babyAndKids[BABY_AND_KIDS.GIRLS_CLOTHING] == true,
                                onCheckChange = { selectBabyAndKidsGirlsClothing() }
                            )
                            CheckBoxOption(
                                name = "Boys Footwear",
                                checked = babyAndKids[BABY_AND_KIDS.BOYS_FOOTWEAR] == true,
                                onCheckChange = { selectBabyAndKidsBoysFootwear() }
                            )
                            CheckBoxOption(
                                name = "Girls Footwear",
                                checked = babyAndKids[BABY_AND_KIDS.GIRLS_FOOTWEAR] == true,
                                onCheckChange = { selectBabyAndKidsGirlsFootwear() }
                            )
                            CheckBoxOption(
                                name = "Bath and Skin Care",
                                checked = babyAndKids[BABY_AND_KIDS.BATH_AND_SKIN_CARE] == true,
                                onCheckChange = { selectBabyAndKidsBathAndSkinCare() }
                            )
                            CheckBoxOption(
                                name = "Accessories",
                                checked = babyAndKids[BABY_AND_KIDS.ACCESSORIES] == true,
                                onCheckChange = { selectBabyAndKidsAccessories() }
                            )
                            CheckBoxOption(
                                name = "Toys and Games",
                                checked = babyAndKids[BABY_AND_KIDS.TOYS_AND_GAMES] == true,
                                onCheckChange = { selectBabyAndKidsToysAndGames() }
                            )
                            CheckBoxOption(
                                name = "Other",
                                checked = babyAndKids[BABY_AND_KIDS.OTHER] == true,
                                onCheckChange = { selectBabyAndKidsOther() }
                            )
                        }
                        CATEGORY.BEAUTY_AND_CARE->{
                            CheckBoxOption(
                                name = "Skin Care",
                                checked = beautyAndCare[BEAUTY_AND_CARE.SKIN_CARE] == true,
                                onCheckChange = { selectBeautyAndCareSkinCare() }
                            )
                            CheckBoxOption(
                                name = "Hair Care",
                                checked = beautyAndCare[BEAUTY_AND_CARE.HAIR_CARE] == true,
                                onCheckChange = { selectBeautyAndCareHairCare() }
                            )
                            CheckBoxOption(
                                name = "Makeup and Nails",
                                checked = beautyAndCare[BEAUTY_AND_CARE.MAKEUP_AND_NAILS] == true,
                                onCheckChange = { selectBeautyAndCareMakeupAndNails() }
                            )
                            CheckBoxOption(
                                name = "Other",
                                checked = beautyAndCare[BEAUTY_AND_CARE.OTHER] == true,
                                onCheckChange = { selectBeautyAndCareOther() }
                            )
                        }
                        CATEGORY.BOOKS -> {
                            CheckBoxOption(
                                name = "Fiction",
                                checked = books[BOOKS.FICTION] == true,
                                onCheckChange = { selectBooksFiction() }
                            )
                            CheckBoxOption(
                                name = "Non-Fiction",
                                checked = books[BOOKS.NON_FICTION] == true,
                                onCheckChange = { selectBooksNonFiction() }
                            )
                            CheckBoxOption(
                                name = "Academic",
                                checked = books[BOOKS.ACADEMIC] == true,
                                onCheckChange = { selectBooksAcademic() }
                            )
                            CheckBoxOption(
                                name = "Children",
                                checked = books[BOOKS.CHILDREN] == true,
                                onCheckChange = { selectBooksChildren() }
                            )
                            CheckBoxOption(
                                name = "Comics and Graphic Novels",
                                checked = books[BOOKS.COMICS_AND_GRAPHIC_NOVELS] == true,
                                onCheckChange = { selectBooksComicsAndGraphicNovels() }
                            )
                            CheckBoxOption(
                                name = "Other",
                                checked = books[BOOKS.OTHER] == true,
                                onCheckChange = { selectBooksOther() }
                            )
                        }
                        CATEGORY.HOME_AND_KITCHEN -> {
                            CheckBoxOption(
                                name = "Furniture",
                                checked = homeAndKitchen[HOME_AND_KITCHEN.FURNITURE] == true,
                                onCheckChange = { selectHomeAndKitchenFurniture() }
                            )
                            CheckBoxOption(
                                name = "Decor",
                                checked = homeAndKitchen[HOME_AND_KITCHEN.DECOR] == true,
                                onCheckChange = { selectHomeAndKitchenDecor() }
                            )
                            CheckBoxOption(
                                name = "Kitchenware",
                                checked = homeAndKitchen[HOME_AND_KITCHEN.KITCHENWARE] == true,
                                onCheckChange = { selectHomeAndKitchenKitchenware() }
                            )
                            CheckBoxOption(
                                name = "Bedding",
                                checked = homeAndKitchen[HOME_AND_KITCHEN.BEDDING] == true,
                                onCheckChange = { selectHomeAndKitchenBedding() }
                            )
                            CheckBoxOption(
                                name = "Bath",
                                checked = homeAndKitchen[HOME_AND_KITCHEN.BATH] == true,
                                onCheckChange = { selectHomeAndKitchenBath() }
                            )
                            CheckBoxOption(
                                name = "Appliances",
                                checked = homeAndKitchen[HOME_AND_KITCHEN.APPLIANCES] == true,
                                onCheckChange = { selectHomeAndKitchenAppliances() }
                            )
                            CheckBoxOption(
                                name = "Other",
                                checked = homeAndKitchen[HOME_AND_KITCHEN.OTHER] == true,
                                onCheckChange = { selectHomeAndKitchenOther() }
                            )
                        }
                        CATEGORY.GADGETS -> {
                            CheckBoxOption(
                                name = "Mobile Phones",
                                checked = gadgets[GADGETS.MOBILE_PHONES] == true,
                                onCheckChange = { selectGadgetsMobilePhones() }
                            )
                            CheckBoxOption(
                                name = "Laptops",
                                checked = gadgets[GADGETS.LAPTOPS] == true,
                                onCheckChange = { selectGadgetsLaptops() }
                            )
                            CheckBoxOption(
                                name = "Tablets",
                                checked = gadgets[GADGETS.TABLETS] == true,
                                onCheckChange = { selectGadgetsTablets() }
                            )
                            CheckBoxOption(
                                name = "Cameras",
                                checked = gadgets[GADGETS.CAMERAS] == true,
                                onCheckChange = { selectGadgetsCameras() }
                            )
                            CheckBoxOption(
                                name = "Wearables",
                                checked = gadgets[GADGETS.WEARABLES] == true,
                                onCheckChange = { selectGadgetsWearables() }
                            )
                            CheckBoxOption(
                                name = "Accessories",
                                checked = gadgets[GADGETS.ACCESSORIES] == true,
                                onCheckChange = { selectGadgetsAccessories() }
                            )
                            CheckBoxOption(
                                name = "Other",
                                checked = gadgets[GADGETS.OTHER] == true,
                                onCheckChange = { selectGadgetsOther() }
                            )
                        }

                        else ->{}
                    }

                }

                else -> {}
            }
        }
}

@Composable
fun CheckBoxOption(
    name : String,
    checked : Boolean,
    onCheckChange : () -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp, 4.dp, 24.dp, 4.dp)
            .clickable { onCheckChange() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = name,
            fontSize = 16.sp
        )
        Checkbox(
            checked = checked,
            onCheckedChange = { onCheckChange() }
        )
    }
}

@Composable
fun CategoryOption(
    name : String,
    onClick : () -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp, 8.dp, 24.dp, 8.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = name,
            fontSize = 16.sp
        )
        Icon(
            imageVector = Icons.Rounded.KeyboardArrowRight,
            contentDescription = null,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBottomPopup() {
    SwapsyTheme {
        BottomPopup(
            selectedOption = SelectedOption.Category,
            sortOptions = SortOptions.Recommended,
            availablityOptions = AvailablityOptions.Available,
            conditionOption = ConditionOption.entries.associateWith { false },
            category = CATEGORY.MEN,
            men = MEN.entries.associateWith { false },
            women = WOMEN.entries.associateWith { false },
            babyAndKids = BABY_AND_KIDS.entries.associateWith { false },
            beautyAndCare = BEAUTY_AND_CARE.entries.associateWith { false },
            books = BOOKS.entries.associateWith { false },
            homeAndKitchen = HOME_AND_KITCHEN.entries.associateWith { false },
            gadgets = GADGETS.entries.associateWith { false },
            selectCurrentlyAvailable = {},
            selectSoldOut = {},
            selectRecommended = {},
            selectNewlyArrived = {},
            selectSellerRating = {},
            selectPriceHiToLo = {},
            selectPriceLoToHi = {},
            selectGood = {},
            selectUsed = {},
            selectLikeNew = {},
            selectNewWithTags = {},
            selectMen = {},
            selectWomen = {},
            selectBabyAndKids = {},
            selectBeautyAndCare = {},
            selectBooks = {},
            selectHomeAndKitchen = {},
            selectGadgets = {},
            selectBackCategory = {},

            // Specific Men category selection
            selectTshirtAndShirts = {},
            selectSweatsAndHoodies = {},
            selectSweaters = {},
            selectJeansAndPants = {},
            selectShorts = {},
            selectCoatsAndJackets = {},
            selectSuitsAndBlazers = {},
            selectEthnicWear = {},
            selectFootwear = {},
            selectBagsAndBackpacks = {},
            selectAccessories = {},
            selectAthleticWear = {},
            selectOther = {},

            // Specific Women category selection
            selectWomenAccessories = {},
            selectWomenBags = {},
            selectWomenFootwear = {},
            selectWomenJewellery = {},
            selectWomenEthnic = {},
            selectWomenWestern = {},
            selectWomenInnerWearAndSleepwear = {},

            // Specific Baby and Kids category selection
            selectBabyAndKidsOther = {},
            selectBabyAndKidsAccessories = {},
            selectBabyAndKidsBoysClothing = {},
            selectBabyAndKidsGirlsClothing = {},
            selectBabyAndKidsToysAndGames = {},
            selectBabyAndKidsBoysFootwear = {},
            selectBabyAndKidsGirlsFootwear = {},
            selectBabyAndKidsBathAndSkinCare = {},

            // Specific Beauty and Care category selection
            selectBeautyAndCareHairCare = {},
            selectBeautyAndCareSkinCare = {},
            selectBeautyAndCareMakeupAndNails = {},
            selectBeautyAndCareOther = {},

            // Specific Books category selection
            selectBooksNonFiction = {},
            selectBooksComicsAndGraphicNovels = {},
            selectBooksAcademic = {},
            selectBooksChildren = {},
            selectBooksOther = {},
            selectBooksFiction = {},

            // Specific Home and Kitchen category selection
            selectHomeAndKitchenDecor = {},
            selectHomeAndKitchenOther = {},
            selectHomeAndKitchenFurniture = {},
            selectHomeAndKitchenBedding = {},
            selectHomeAndKitchenAppliances = {},
            selectHomeAndKitchenKitchenware = {},
            selectHomeAndKitchenBath = {},

            // Specific Gadgets category selection
            selectGadgetsMobilePhones = {},
            selectGadgetsLaptops = {},
            selectGadgetsTablets = {},
            selectGadgetsCameras = {},
            selectGadgetsWearables = {},
            selectGadgetsAccessories = {},
            selectGadgetsOther = {}
        )
    }
}

