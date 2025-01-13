package com.example.freeupcopy.ui.presentation.wish_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.freeupcopy.R
import com.example.freeupcopy.ui.presentation.product_card.ProductCard
import com.example.freeupcopy.ui.presentation.product_listing.ProductListingUiState
import com.example.freeupcopy.ui.presentation.product_listing.componants.SelectedOptionsRow
import com.example.freeupcopy.ui.presentation.wish_list.componants.BottomPopup
import com.example.freeupcopy.ui.presentation.wish_list.componants.SelectedOptionsRowWishList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WishListScreen(
    modifier: Modifier = Modifier,
    viewModel: WishListViewModel = viewModel()
) {
    val state = viewModel.state.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Spacer(modifier = Modifier.size(32.dp))
                            Text(
                                text = "WishList",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = "close"
                        )
                    }
                }
            )
        },
    ) { paddingValues ->
        Column(
            Modifier
                .padding(paddingValues)
                .fillMaxWidth()
        ){
            SelectedOptionsRowWishList(
                isOptionSelected = {viewModel.isOptionSelected(it)},
                onOptionClicked = {viewModel.onOptionClicked(it)},
                openBottomPopUp = {viewModel.onOpenBottomPopup()}
            )
            Spacer(Modifier.height(8.dp))
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2), // 2 items per row
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                items(10) { index ->
                    ProductCard(
                        containerColor = MaterialTheme.colorScheme.background,
                        companyName = listOf(
                            "Adidas", "Nike", "Puma", null, "Under Armour",
                            "Levi's", "Calvin Klein", "Tommy Hilfiger", "Lacoste", null
                        )[index],
                        productName = listOf(
                            "Adidas Bomber Jacket", "Nike Air Max", "Puma Suede", "Reebok Classic", "Under Armour Hoodie",
                            "Levi's Jeans", "Calvin Klein T-shirt", "Tommy Hilfiger Polo", "Lacoste L.12.12", "Ralph Lauren Shirt"
                        )[index],
                        size = listOf(
                            "40 inches", "L", "M", "XL", "L",
                            "32 inches", "S", "M", "XL", "L"
                        )[index],
                        productThumbnail = painterResource(id = R.drawable.bomber_jacket), // Assuming R.drawable.bomber_jacket is a placeholder
                        priceOffered = listOf(
                            null, "499", "399", null, "799",
                            "1999", null, "1499", "1299", null
                        )[index],
                        coinsOffered = listOf(
                            null, "1000", null, "599", null,
                            null, "999", null, null, "1799"
                        )[index],
                        specialOffer = listOf(
                            listOf("4000", "2000"), listOf("300","800"), null, null, null,
                            null, null, listOf("1000", "500"), null, null
                        )[index],
                        priceOriginal = listOf(
                            "3500", "2999", "1999", "1499", "1999",
                            "3999", "1999", "2999", "2499", "3499"
                        )[index],
                        badge = listOf(
                            "Trusted", "Sale", "New", null, "Limited Edition",
                            "Classic", "Trendy", "Luxury", "Iconic", null
                        )[index],
                        isLiked = true,
                        onLikeClick = {},
                        priorityPriceType = null, // Set priorityPriceType to null for all products
                        isClothes = listOf(
                            true, false, true, false, true,
                            true, true, true, false, true
                        )[index]
                    )
                }
            }
        }
    }
    if (state.value.isBottomPopupVisible){
        ModalBottomSheet(onDismissRequest = { viewModel.onCloseBottomPopup() }){
            BottomPopup(
                selectedOption = state.value.selectedOption,
                sortOptions = state.value.sortOptions,
                availablityOptions = state.value.AvailablityOption,
                conditionOption = state.value.conditionOption,
                category = state.value.category,
                men = state.value.menCategory,
                women = state.value.womenCategory,
                babyAndKids = state.value.babyAndKidsCategory,
                beautyAndCare = state.value.beautyAndCareCategory,
                books = state.value.booksCategory,
                homeAndKitchen = state.value.homeAndKitchenCategory,
                gadgets = state.value.gadgetsCategory,

                // Availability selection
                selectCurrentlyAvailable = { viewModel.selectCurrentlyAvailable() },
                selectSoldOut = { viewModel.selectSoldOut() },

                // Sorting selection
                selectRecommended = { viewModel.selectRecommended() },
                selectNewlyArrived = { viewModel.selectNewlyArrived() },
                selectSellerRating = { viewModel.selectSellerRating() },
                selectPriceHiToLo = { viewModel.selectPriceHiToLo() },
                selectPriceLoToHi = { viewModel.selectPriceLoToHi() },

                // Condition selection
                selectGood = { viewModel.selectGood() },
                selectUsed = { viewModel.selectUsed() },
                selectLikeNew = { viewModel.selectLikeNew() },
                selectNewWithTags = { viewModel.selectNewWithTags() },

                // Category selection
                selectMen = { viewModel.selectMen() },
                selectWomen = { viewModel.selectWomen() },
                selectBabyAndKids = { viewModel.selectBabyAndKids() },
                selectBeautyAndCare = { viewModel.selectBeautyAndCare() },
                selectBooks = { viewModel.selectBooks() },
                selectHomeAndKitchen = { viewModel.selectHomeAndKitchen() },
                selectGadgets = { viewModel.selectGadgets() },

                // Specific Men category selection
                selectTshirtAndShirts = { viewModel.selectTshirtAndShirts() },
                selectSweatsAndHoodies = { viewModel.selectSweatsAndHoodies() },
                selectSweaters = { viewModel.selectSweaters() },
                selectJeansAndPants = { viewModel.selectJeansAndPants() },
                selectShorts = { viewModel.selectShorts() },
                selectCoatsAndJackets = { viewModel.selectCoatsAndJackets() },
                selectSuitsAndBlazers = { viewModel.selectSuitsAndBlazers() },
                selectEthnicWear = { viewModel.selectEthnicWear() },
                selectFootwear = { viewModel.selectFootwear() },
                selectBagsAndBackpacks = { viewModel.selectBagsAndBackpacks() },
                selectAccessories = { viewModel.selectAccessories() },
                selectAthleticWear = { viewModel.selectAthleticWear() },
                selectOther = { viewModel.selectOther() },

                // Specific Women category selection
                selectWomenAccessories = { viewModel.selectWomenAccessories() },
                selectWomenBags = { viewModel.selectWomenBags() },
                selectWomenFootwear = { viewModel.selectWomenFootwear() },
                selectWomenJewellery = { viewModel.selectWomenJewellery() },
                selectWomenEthnic = { viewModel.selectWomenEthnic() },
                selectWomenWestern = { viewModel.selectWomenWestern() },
                selectWomenInnerWearAndSleepwear = { viewModel.selectWomenInnerWearAndSleepwear() },

                // Specific Baby and Kids category selection
                selectBabyAndKidsOther = { viewModel.selectBabyAndKidsOther() },
                selectBabyAndKidsAccessories = { viewModel.selectBabyAndKidsAccessories() },
                selectBabyAndKidsBoysClothing = { viewModel.selectBabyAndKidsBoysClothing() },
                selectBabyAndKidsGirlsClothing = { viewModel.selectBabyAndKidsGirlsClothing() },
                selectBabyAndKidsToysAndGames = { viewModel.selectBabyAndKidsToysAndGames() },
                selectBabyAndKidsBoysFootwear = { viewModel.selectBabyAndKidsBoysFootwear() },
                selectBabyAndKidsGirlsFootwear = { viewModel.selectBabyAndKidsGirlsFootwear() },
                selectBabyAndKidsBathAndSkinCare = { viewModel.selectBabyAndKidsBathAndSkinCare() },

                // Specific Beauty and Care category selection
                selectBeautyAndCareHairCare = { viewModel.selectBeautyAndCareHairCare() },
                selectBeautyAndCareSkinCare = { viewModel.selectBeautyAndCareSkinCare() },
                selectBeautyAndCareMakeupAndNails = { viewModel.selectBeautyAndCareMakeupAndNails() },
                selectBeautyAndCareOther = { viewModel.selectBeautyAndCareOther() },

                // Specific Books category selection
                selectBooksNonFiction = { viewModel.selectBooksNonFiction() },
                selectBooksComicsAndGraphicNovels = { viewModel.selectBooksComicsAndGraphicNovels() },
                selectBooksAcademic = { viewModel.selectBooksAcademic() },
                selectBooksChildren = { viewModel.selectBooksChildren() },
                selectBooksOther = { viewModel.selectBooksOther() },
                selectBooksFiction = { viewModel.selectBooksFiction() },

                // Specific Home and Kitchen category selection
                selectHomeAndKitchenDecor = { viewModel.selectHomeAndKitchenDecor() },
                selectHomeAndKitchenOther = { viewModel.selectHomeAndKitchenOther() },
                selectHomeAndKitchenFurniture = { viewModel.selectHomeAndKitchenFurniture() },
                selectHomeAndKitchenBedding = { viewModel.selectHomeAndKitchenBedding() },
                selectHomeAndKitchenAppliances = { viewModel.selectHomeAndKitchenAppliances() },
                selectHomeAndKitchenKitchenware = { viewModel.selectHomeAndKitchenKitchenware() },
                selectHomeAndKitchenBath = { viewModel.selectHomeAndKitchenBath() },

                // Specific Gadgets category selection
                selectGadgetsMobilePhones = { viewModel.selectGadgetsMobilePhones() },
                selectGadgetsLaptops = { viewModel.selectGadgetsLaptops() },
                selectGadgetsTablets = { viewModel.selectGadgetsTablets() },
                selectGadgetsCameras = { viewModel.selectGadgetsCameras() },
                selectGadgetsWearables = { viewModel.selectGadgetsWearables() },
                selectGadgetsAccessories = { viewModel.selectGadgetsAccessories() },
                selectGadgetsOther = { viewModel.selectGadgetsOther() },

                // Back category selection
                selectBackCategory = { viewModel.selectBackCategory() }
            )

        }
    }
}


@Preview(showBackground = true) @Composable
fun PreviewWishList(){
    WishListScreen()
}