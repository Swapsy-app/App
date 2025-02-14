package com.example.freeupcopy.domain.enums

enum class Filter(val displayValue: String) {
    AVAILABILITY("Availability"),
    CONDITION("Condition"),
    SELLER_RATING("Seller Rating"),
    PRICE("Price"),
    CATEGORY("Category"),
    BRAND("Brand"),
    SIZE("Size"),

    FABRIC("Fabric"),
    COLOUR("Colour"),
    OCCASION("Occasion"),
    SHAPE("Shape"),
    LENGTH("Length"),
}

enum class AvailabilityOption(val displayValue: String) {
    AVAILABLE("Available"),
    SOLD_OUT("Sold Out")
}

enum class ConditionOption(val displayValue: String, val description: String) {
    WITH_TAG("New with Price Tag", "Completely unused, original price tag still attached, perfect condition"),
    ALMOST_NEW("Almost New", "Without price tag, never used or used a few times, minimal signs of wear, looks nearly new"),
    Nice("Nice", "Gently used, well-maintained, great for continued use"),
    USED("Used", "Clearly shows wear and tear with flaws")
}

enum class SellerRatingOption(val displayValue: String) {
    RATING_3_0("3.0"),
    RATING_3_5("4.5"),
    RATING_4_0("4.0"),
    RATING_4_5("4.5")
}

enum class SellerBadge(val displayValue: String) {
    STARTER("Starter"),
    ACHIEVER("Achiever"),
    CONQUEROR("Conqueror"),
    LEGEND("Legend"),
    TRENDSETTER("Trendsetter")
}

enum class FilterSpecialOption(
    val displayValue: String
) {
    NEW_ARRIVAL("New Arrival"),
    FREE_IN_COIN("Free in Coin"),
    NEW_WITH_TAGS("New with Tags"),
    SWAPGO_ASSURED("SwapGo Assured")
}

private const val DEFAULT = "Primary"

data class FilterCategoryUiModel(
    val name: String,
    val subcategories: List<FilterSubCategory> = emptyList()
) {
    companion object {
        val predefinedCategories = listOf(
            FilterCategoryUiModel(
                name = "Women",
                subcategories = listOf(
                    FilterSubCategory(
                        name = "Ethnic",
                        tertiaryCategories = listOf(
                            FilterTertiaryCategory("Sarees", specialFilters = listOf(Filter.FABRIC, Filter.COLOUR, Filter.OCCASION)),
                            FilterTertiaryCategory("Blouses", specialFilters = listOf(Filter.SIZE, Filter.FABRIC, Filter.COLOUR, Filter.OCCASION, Filter.SHAPE)),
                            FilterTertiaryCategory("Kurtas", specialFilters = listOf(Filter.SIZE, Filter.FABRIC, Filter.COLOUR, Filter.OCCASION, Filter.SHAPE)),
                            FilterTertiaryCategory("Kurta Sets & Suits", specialFilters = listOf(Filter.SIZE, Filter.FABRIC, Filter.COLOUR, Filter.OCCASION, Filter.SHAPE)),
                            FilterTertiaryCategory("Dupattas", specialFilters = listOf(Filter.FABRIC, Filter.COLOUR, Filter.OCCASION)),
                            FilterTertiaryCategory("Lehenga Choli", specialFilters = listOf(Filter.SIZE, Filter.FABRIC, Filter.COLOUR, Filter.OCCASION)),
                            FilterTertiaryCategory("Ethnic Skirts", specialFilters = listOf(Filter.SIZE, Filter.FABRIC, Filter.COLOUR, Filter.OCCASION)),
                            FilterTertiaryCategory("Bridal Lehenga", specialFilters = listOf(Filter.SIZE, Filter.FABRIC, Filter.COLOUR, Filter.OCCASION)),
                            FilterTertiaryCategory("Ethnic Gowns", specialFilters = listOf(Filter.SIZE, Filter.FABRIC, Filter.COLOUR, Filter.OCCASION)),
                            FilterTertiaryCategory("Palazzos & Salwars", specialFilters = listOf(Filter.SIZE, Filter.FABRIC, Filter.COLOUR, Filter.OCCASION)),
                            FilterTertiaryCategory("Dress Material")
                        )
                    ),
                    FilterSubCategory(
                        name = "Western",
                        tertiaryCategories = listOf(
                            FilterTertiaryCategory("Dresses", specialFilters = listOf(Filter.SHAPE, Filter.LENGTH, Filter.SIZE, Filter.FABRIC, Filter.COLOUR, Filter.OCCASION)),
                            FilterTertiaryCategory("Tops & Tunics", specialFilters = listOf(Filter.SHAPE, Filter.SIZE, Filter.FABRIC, Filter.COLOUR, Filter.OCCASION)),
                            FilterTertiaryCategory("T-Shirts", specialFilters = listOf(Filter.SHAPE, Filter.SIZE, Filter.FABRIC, Filter.COLOUR, Filter.OCCASION)),
                            FilterTertiaryCategory("Jumpsuits & Co-ords", specialFilters = listOf(Filter.SIZE, Filter.FABRIC, Filter.COLOUR, Filter.OCCASION, Filter.SHAPE)),
                            FilterTertiaryCategory("Jeans & Trousers", specialFilters = listOf(Filter.SIZE, Filter.FABRIC, Filter.COLOUR, Filter.OCCASION, Filter.SHAPE)),
                            FilterTertiaryCategory("Sweaters & Sweatshirts", specialFilters = listOf(Filter.SIZE, Filter.FABRIC, Filter.COLOUR, Filter.OCCASION)),
                            FilterTertiaryCategory("Shorts & Skirts", specialFilters = listOf(Filter.SIZE, Filter.FABRIC, Filter.COLOUR, Filter.OCCASION)),
                            FilterTertiaryCategory("Jackets & Overcoats", specialFilters = listOf(Filter.SIZE, Filter.FABRIC, Filter.COLOUR, Filter.OCCASION)),
                            FilterTertiaryCategory("Blazers", specialFilters = listOf(Filter.SIZE, Filter.FABRIC, Filter.COLOUR, Filter.OCCASION)),
                            FilterTertiaryCategory("Active Wear", specialFilters = listOf(Filter.SIZE, Filter.FABRIC, Filter.COLOUR, Filter.OCCASION)),
                        )
                    ),
                    FilterSubCategory(
                        name = "Jewellery",
                        tertiaryCategories = listOf(
                            FilterTertiaryCategory("Jewellery Sets"),
                            FilterTertiaryCategory("Earrings & Studs"),
                            FilterTertiaryCategory("Mangalsutras"),
                            FilterTertiaryCategory("Bangles & Bracelets"),
                            FilterTertiaryCategory("Necklaces & Chains"),
                            FilterTertiaryCategory("Kamarbandh & Maangtika"),
                            FilterTertiaryCategory("Rings"),
                            FilterTertiaryCategory("Anklets & Nosepins")
                        )
                    ),
                    FilterSubCategory(
                        name = "Accessories",
                        tertiaryCategories = listOf(
                            FilterTertiaryCategory("Sunglasses"),
                            FilterTertiaryCategory("Watches"),
                            FilterTertiaryCategory("Caps & Hats"),
                            FilterTertiaryCategory("Hair Accessories"),
                            FilterTertiaryCategory("Belts"),
                            FilterTertiaryCategory("Scarfs & Stoles"),
                        )
                    ),
                    FilterSubCategory(
                        name = "Bags",
                        tertiaryCategories = listOf(
                            FilterTertiaryCategory("Handbags", specialFilters = listOf(Filter.COLOUR)),
                            FilterTertiaryCategory("Clutches", specialFilters = listOf(Filter.COLOUR)),
                            FilterTertiaryCategory("Wallets", specialFilters = listOf(Filter.COLOUR)),
                            FilterTertiaryCategory("Backpacks", specialFilters = listOf(Filter.COLOUR)),
                            FilterTertiaryCategory("Slingbags", specialFilters = listOf(Filter.COLOUR))
                        )
                    ),
                    FilterSubCategory(
                        name = "Footwear",
                        tertiaryCategories = listOf(
                            FilterTertiaryCategory("Flats & Sandals", specialFilters = listOf(Filter.COLOUR)),
                            FilterTertiaryCategory("Heels & Wedges", specialFilters = listOf(Filter.COLOUR)),
                            FilterTertiaryCategory("Boots", specialFilters = listOf(Filter.COLOUR)),
                            FilterTertiaryCategory("Flipflops & Slippers", specialFilters = listOf(Filter.COLOUR)),
                            FilterTertiaryCategory("Bellies & Ballerinas", specialFilters = listOf(Filter.COLOUR)),
                            FilterTertiaryCategory("Sports Shoes", specialFilters = listOf(Filter.COLOUR)),
                            FilterTertiaryCategory("Casual Shoes", specialFilters = listOf(Filter.COLOUR))
                        )
                    ),
                    FilterSubCategory(
                        name = "Innerwear & Sleepwear",
                        tertiaryCategories = listOf(
                            FilterTertiaryCategory("Bra", specialFilters = listOf(Filter.SIZE, Filter.FABRIC, Filter.COLOUR)),
                            FilterTertiaryCategory("Briefs", specialFilters = listOf(Filter.SIZE, Filter.FABRIC, Filter.COLOUR)),
                            FilterTertiaryCategory("Camisoles & Slips", specialFilters = listOf(Filter.SIZE, Filter.FABRIC, Filter.COLOUR)),
                            FilterTertiaryCategory("Nightsuits & Pyjamas", specialFilters = listOf(Filter.SIZE, Filter.FABRIC, Filter.COLOUR)),
                            FilterTertiaryCategory("Maternity", specialFilters = listOf(Filter.SIZE, Filter.FABRIC, Filter.COLOUR))
                        )
                    )
                ),
            ),
            FilterCategoryUiModel(
                name = "Men",
                subcategories = listOf(
                    FilterSubCategory(
                        name = DEFAULT,
                        tertiaryCategories = listOf(
                            FilterTertiaryCategory("T-Shirts & Shirts", specialFilters = listOf(Filter.SIZE, Filter.FABRIC, Filter.OCCASION, Filter.COLOUR)),
                            FilterTertiaryCategory("Sweats & Hoodies", specialFilters = listOf(Filter.SIZE, Filter.FABRIC, Filter.OCCASION, Filter.COLOUR)),
                            FilterTertiaryCategory("Sweaters", specialFilters = listOf(Filter.SIZE, Filter.FABRIC, Filter.OCCASION, Filter.COLOUR)),
                            FilterTertiaryCategory("Jeans & Pants", specialFilters = listOf(Filter.SIZE, Filter.FABRIC, Filter.OCCASION, Filter.COLOUR)),
                            FilterTertiaryCategory("Shorts", specialFilters = listOf(Filter.SIZE, Filter.FABRIC, Filter.OCCASION, Filter.COLOUR)),
                            FilterTertiaryCategory("Coats & Jackets", specialFilters = listOf(Filter.SIZE, Filter.FABRIC, Filter.OCCASION, Filter.COLOUR)),
                            FilterTertiaryCategory("Suits & Blazers", specialFilters = listOf(Filter.SIZE, Filter.FABRIC, Filter.OCCASION, Filter.COLOUR)),
                            FilterTertiaryCategory("EthnicWear", specialFilters = listOf(Filter.SIZE, Filter.FABRIC, Filter.OCCASION, Filter.COLOUR)),
                            FilterTertiaryCategory("Footwear", specialFilters = listOf(Filter.SIZE, Filter.COLOUR)),
                            FilterTertiaryCategory("Bags & Backpacks", specialFilters = listOf(Filter.COLOUR)),
                            FilterTertiaryCategory("Accessories"),
                            FilterTertiaryCategory("Athletic Wear", specialFilters = listOf(Filter.SIZE, Filter.FABRIC, Filter.COLOUR)),
                        )
                    )
                )
            ),
            FilterCategoryUiModel(
                "Baby & Kids", listOf(
                    FilterSubCategory(
                        name = DEFAULT,
                        tertiaryCategories = listOf(
                            FilterTertiaryCategory("Boys Clothing", specialFilters = listOf(Filter.SIZE, Filter.FABRIC, Filter.COLOUR)),
                            FilterTertiaryCategory("Girls Clothing", specialFilters = listOf(Filter.SIZE, Filter.FABRIC, Filter.COLOUR)),
                            FilterTertiaryCategory("Boys Footwear", specialFilters = listOf(Filter.SIZE)),
                            FilterTertiaryCategory("Girls Footwear", specialFilters = listOf(Filter.SIZE)),
                            FilterTertiaryCategory("Bath & Skin Care"),
                            FilterTertiaryCategory("Accessories"),
                            FilterTertiaryCategory("Toys & Games")
                        )
                    )
                )
            ),
            FilterCategoryUiModel(
                name = "Beauty & Care",
                subcategories = listOf(
                    FilterSubCategory(
                        name = "Skin Care",
                        tertiaryCategories = listOf(
                            FilterTertiaryCategory("Face Wash"),
                            FilterTertiaryCategory("Face Toner"),
                            FilterTertiaryCategory("Face Serum"),
                            FilterTertiaryCategory("Masks & Peels"),
                            FilterTertiaryCategory("Face Moisturiser"),
                            FilterTertiaryCategory("Sunscreen"),
                            FilterTertiaryCategory("Eye Care"),
                            FilterTertiaryCategory("Night Care"),
                            FilterTertiaryCategory("Skincare Kit"),
                        )
                    ),
                    FilterSubCategory(
                        name = "Hair Care",
                        tertiaryCategories = listOf(
                            FilterTertiaryCategory("Hair Oil"),
                            FilterTertiaryCategory("Hair Serum"),
                            FilterTertiaryCategory("Hair Gels & Masks"),
                            FilterTertiaryCategory("Shampoo & Conditioner"),
                            FilterTertiaryCategory("Hair Colour"),
                            FilterTertiaryCategory("Hair Spray"),
                            FilterTertiaryCategory("Combs & Hair Brushes"),
                            FilterTertiaryCategory("Hair Appliances"),
                        )
                    ),
                    FilterSubCategory(
                        name = "Make-Up & Nails",
                        tertiaryCategories = listOf(
                            FilterTertiaryCategory("Foundation"),
                            FilterTertiaryCategory("Compact"),
                            FilterTertiaryCategory("Concealer"),
                            FilterTertiaryCategory("Face Primer"),
                            FilterTertiaryCategory("Blushes & Highlighter"),
                            FilterTertiaryCategory("Eye Shadows"),
                            FilterTertiaryCategory("Kajal & Eyeliner"),
                            FilterTertiaryCategory("Eyebrow Pencil"),
                            FilterTertiaryCategory("Mascara"),
                            FilterTertiaryCategory("Lipstick", specialFilters = listOf(Filter.COLOUR)),
                            FilterTertiaryCategory("Nail Polish", specialFilters = listOf(Filter.COLOUR)),
                            FilterTertiaryCategory("Makeup Removers"),
                            FilterTertiaryCategory("Tools & Accessories"),
                        )
                    ),
                    FilterSubCategory(
                        name = "Bath & Body",
                        tertiaryCategories = listOf(
                            FilterTertiaryCategory("Body Washes & Scrubs"),
                            FilterTertiaryCategory("Soaps"),
                            FilterTertiaryCategory("Body Lotions"),
                            FilterTertiaryCategory("Hair Removal"),
                            FilterTertiaryCategory("Hand & Feet Cream"),
                            FilterTertiaryCategory("Body Oil"),
                            FilterTertiaryCategory("Intimate Hygiene"),
                            FilterTertiaryCategory("Period Care")
                        )
                    ),
                    FilterSubCategory(
                        name = "Fragrances",
                        tertiaryCategories = listOf(
                            FilterTertiaryCategory("Perfume"),
                            FilterTertiaryCategory("Deodorant & Roll-Ons"),
                            FilterTertiaryCategory("Body Mist")
                        )
                    ),
                    FilterSubCategory(
                        name = "Men's Grooming",
                        tertiaryCategories = listOf(
                            FilterTertiaryCategory("Beard Care"),
                            FilterTertiaryCategory("Grooming Kits")
                        )
                    ),
                    FilterSubCategory(
                        name = "Oral Care",
                        tertiaryCategories = listOf(
                            FilterTertiaryCategory("Toothpaste & Brush"),
                            FilterTertiaryCategory("Teeth Whitening"),
                            FilterTertiaryCategory("Mouth Washes & Floss")
                        )
                    ),
                )
            ),
            FilterCategoryUiModel(
                name = "Books",
                subcategories = listOf(
                    FilterSubCategory(
                        name = DEFAULT,
                        tertiaryCategories = listOf(
                            FilterTertiaryCategory("Fiction"),
                            FilterTertiaryCategory("Textbooks"),
                            FilterTertiaryCategory("Children's Books"),
                            FilterTertiaryCategory("Indian Language Books"),
                        )
                    )
                )
            ),
            FilterCategoryUiModel(
                name = "Home & Kitchen",
                subcategories = listOf(
                    FilterSubCategory(
                        name = "Home Decor",
                        tertiaryCategories = listOf(
                            FilterTertiaryCategory("Showpieces & Idols"),
                            FilterTertiaryCategory("Wall Decor & Clocks"),
                            FilterTertiaryCategory("Lamps & Lights"),
                            FilterTertiaryCategory("Candles & Candle Holders"),
                            FilterTertiaryCategory("Sewing & Craft"),
                            FilterTertiaryCategory("Wallpapers & Stickers"),
                            FilterTertiaryCategory("Pooja Needs"),
                            FilterTertiaryCategory("Artwork"),
                            FilterTertiaryCategory("Artificial Plants")
                        )
                    ),
                    FilterSubCategory(
                        name = "Kitchen & Dining",
                        tertiaryCategories = listOf(
                            FilterTertiaryCategory("Cooking Utensils"),
                            FilterTertiaryCategory("Baking Utensils"),
                            FilterTertiaryCategory("Kitchen Tools & Cutlery"),
                            FilterTertiaryCategory("Glasses, Cups & Barware"),
                            FilterTertiaryCategory("Containers & Tiffins"),
                            FilterTertiaryCategory("Water Bottles"),
                            FilterTertiaryCategory("Dinnerware")
                        )
                    ),
                    FilterSubCategory(
                        name = "Bedding & Furnishing",
                        tertiaryCategories = listOf(
                            FilterTertiaryCategory("Bedsheets & Curtains", specialFilters = listOf(Filter.COLOUR)),
                            FilterTertiaryCategory("Pillow Covers & Cushion Covers", specialFilters = listOf(Filter.COLOUR)),
                        )
                    ),
                    FilterSubCategory(
                        name = "Bath & Storage",
                        tertiaryCategories = listOf(
                            FilterTertiaryCategory("Organisers & Storage"),
                            FilterTertiaryCategory("Bathroom Accessories"),
                        )
                    ),
                    FilterSubCategory(
                        name = "Pet Supplies",
                        tertiaryCategories = listOf(
                            FilterTertiaryCategory("Pet Accessories")
                        )
                    ),
                )
            ),
            FilterCategoryUiModel(
                name = "Gadgets",
                subcategories = listOf(
                    FilterSubCategory(
                        name = DEFAULT,
                        tertiaryCategories = listOf(
                            FilterTertiaryCategory("Mobile Phones"),
                            FilterTertiaryCategory("Mobile Accessories"),
                            FilterTertiaryCategory("Tablets"),
                            FilterTertiaryCategory("Computers & Laptops"),
                            FilterTertiaryCategory("Cases & Covers"),
                            FilterTertiaryCategory("Drives & Storage"),
                            FilterTertiaryCategory("Headphones & Earphones"),
                            FilterTertiaryCategory("Camera & Photography"),
                            FilterTertiaryCategory("E-Books"),
                            FilterTertiaryCategory("Office Supplies & Stationery"),
                            FilterTertiaryCategory("Fitness Gadgets")
                        )
                    )
                )
            )
        )
    }
}

data class FilterSubCategory(
    val name: String,
    val tertiaryCategories: List<FilterTertiaryCategory>
)

data class FilterTertiaryCategory(
    val name: String,
    val imageUrl: Int? = null,
    val specialFilters: List<Filter> = emptyList()
)
