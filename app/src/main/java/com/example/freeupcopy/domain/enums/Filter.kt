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
    SORT("Sort"),
}

enum class AvailabilityOption(val displayValue: String, val filterName: String) {
    AVAILABLE("Available", "available"),
    SOLD_OUT("Sold Out", "sold")
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
                            FilterTertiaryCategory(parentCategory = "Women", "Sarees", specialFilters = listOf(Filter.FABRIC, Filter.COLOUR, Filter.OCCASION)),
                            FilterTertiaryCategory(parentCategory = "Women","Blouses", specialFilters = listOf(Filter.SIZE, Filter.FABRIC, Filter.COLOUR, Filter.OCCASION, Filter.SHAPE)),
                            FilterTertiaryCategory(parentCategory = "Women","Kurtas", specialFilters = listOf(Filter.SIZE, Filter.FABRIC, Filter.COLOUR, Filter.OCCASION, Filter.SHAPE)),
                            FilterTertiaryCategory(parentCategory = "Women","Kurta Sets & Suits", specialFilters = listOf(Filter.SIZE, Filter.FABRIC, Filter.COLOUR, Filter.OCCASION, Filter.SHAPE)),
                            FilterTertiaryCategory(parentCategory = "Women","Dupattas", specialFilters = listOf(Filter.FABRIC, Filter.COLOUR, Filter.OCCASION)),
                            FilterTertiaryCategory(parentCategory = "Women","Lehenga Choli", specialFilters = listOf(Filter.SIZE, Filter.FABRIC, Filter.COLOUR, Filter.OCCASION)),
                            FilterTertiaryCategory(parentCategory = "Women","Ethnic Skirts", specialFilters = listOf(Filter.SIZE, Filter.FABRIC, Filter.COLOUR, Filter.OCCASION)),
                            FilterTertiaryCategory(parentCategory = "Women","Bridal Lehenga", specialFilters = listOf(Filter.SIZE, Filter.FABRIC, Filter.COLOUR, Filter.OCCASION)),
                            FilterTertiaryCategory(parentCategory = "Women","Ethnic Gowns", specialFilters = listOf(Filter.SIZE, Filter.FABRIC, Filter.COLOUR, Filter.OCCASION)),
                            FilterTertiaryCategory(parentCategory = "Women","Palazzos & Salwars", specialFilters = listOf(Filter.SIZE, Filter.FABRIC, Filter.COLOUR, Filter.OCCASION)),
                            FilterTertiaryCategory(parentCategory = "Women","Dress Material")
                        )
                    ),
                    FilterSubCategory(
                        name = "Western",
                        tertiaryCategories = listOf(
                            FilterTertiaryCategory(parentCategory = "Women","Dresses", specialFilters = listOf(Filter.SHAPE, Filter.LENGTH, Filter.SIZE, Filter.FABRIC, Filter.COLOUR, Filter.OCCASION)),
                            FilterTertiaryCategory(parentCategory = "Women","Tops & Tunics", specialFilters = listOf(Filter.SHAPE, Filter.SIZE, Filter.FABRIC, Filter.COLOUR, Filter.OCCASION)),
                            FilterTertiaryCategory(parentCategory = "Women","T-Shirts", specialFilters = listOf(Filter.SHAPE, Filter.SIZE, Filter.FABRIC, Filter.COLOUR, Filter.OCCASION)),
                            FilterTertiaryCategory(parentCategory = "Women","Jumpsuits & Co-ords", specialFilters = listOf(Filter.SIZE, Filter.FABRIC, Filter.COLOUR, Filter.OCCASION, Filter.SHAPE)),
                            FilterTertiaryCategory(parentCategory = "Women","Jeans & Trousers", specialFilters = listOf(Filter.SIZE, Filter.FABRIC, Filter.COLOUR, Filter.OCCASION, Filter.SHAPE)),
                            FilterTertiaryCategory(parentCategory = "Women","Sweaters & Sweatshirts", specialFilters = listOf(Filter.SIZE, Filter.FABRIC, Filter.COLOUR, Filter.OCCASION)),
                            FilterTertiaryCategory(parentCategory = "Women","Shorts & Skirts", specialFilters = listOf(Filter.SIZE, Filter.FABRIC, Filter.COLOUR, Filter.OCCASION)),
                            FilterTertiaryCategory(parentCategory = "Women","Jackets & Overcoats", specialFilters = listOf(Filter.SIZE, Filter.FABRIC, Filter.COLOUR, Filter.OCCASION)),
                            FilterTertiaryCategory(parentCategory = "Women","Blazers", specialFilters = listOf(Filter.SIZE, Filter.FABRIC, Filter.COLOUR, Filter.OCCASION)),
                            FilterTertiaryCategory(parentCategory = "Women","Active Wear", specialFilters = listOf(Filter.SIZE, Filter.FABRIC, Filter.COLOUR, Filter.OCCASION)),
                        )
                    ),
                    FilterSubCategory(
                        name = "Jewellery",
                        tertiaryCategories = listOf(
                            FilterTertiaryCategory(parentCategory = "Women","Jewellery Sets"),
                            FilterTertiaryCategory(parentCategory = "Women","Earrings & Studs"),
                            FilterTertiaryCategory(parentCategory = "Women","Mangalsutras"),
                            FilterTertiaryCategory(parentCategory = "Women","Bangles & Bracelets"),
                            FilterTertiaryCategory(parentCategory = "Women","Necklaces & Chains"),
                            FilterTertiaryCategory(parentCategory = "Women","Kamarbandh & Maangtika"),
                            FilterTertiaryCategory(parentCategory = "Women","Rings"),
                            FilterTertiaryCategory(parentCategory = "Women","Anklets & Nosepins")
                        )
                    ),
                    FilterSubCategory(
                        name = "Accessories",
                        tertiaryCategories = listOf(
                            FilterTertiaryCategory(parentCategory = "Women","Sunglasses"),
                            FilterTertiaryCategory(parentCategory = "Women","Watches"),
                            FilterTertiaryCategory(parentCategory = "Women","Caps & Hats"),
                            FilterTertiaryCategory(parentCategory = "Women","Hair Accessories"),
                            FilterTertiaryCategory(parentCategory = "Women","Belts"),
                            FilterTertiaryCategory(parentCategory = "Women","Scarfs & Stoles"),
                        )
                    ),
                    FilterSubCategory(
                        name = "Bags",
                        tertiaryCategories = listOf(
                            FilterTertiaryCategory(parentCategory = "Women","Handbags", specialFilters = listOf(Filter.COLOUR)),
                            FilterTertiaryCategory(parentCategory = "Women","Clutches", specialFilters = listOf(Filter.COLOUR)),
                            FilterTertiaryCategory(parentCategory = "Women","Wallets", specialFilters = listOf(Filter.COLOUR)),
                            FilterTertiaryCategory(parentCategory = "Women","Backpacks", specialFilters = listOf(Filter.COLOUR)),
                            FilterTertiaryCategory(parentCategory = "Women","Slingbags", specialFilters = listOf(Filter.COLOUR))
                        )
                    ),
                    FilterSubCategory(
                        name = "Footwear",
                        tertiaryCategories = listOf(
                            FilterTertiaryCategory(parentCategory = "Women","Flats & Sandals", specialFilters = listOf(Filter.COLOUR)),
                            FilterTertiaryCategory(parentCategory = "Women","Heels & Wedges", specialFilters = listOf(Filter.COLOUR)),
                            FilterTertiaryCategory(parentCategory = "Women","Boots", specialFilters = listOf(Filter.COLOUR)),
                            FilterTertiaryCategory(parentCategory = "Women","Flipflops & Slippers", specialFilters = listOf(Filter.COLOUR)),
                            FilterTertiaryCategory(parentCategory = "Women","Bellies & Ballerinas", specialFilters = listOf(Filter.COLOUR)),
                            FilterTertiaryCategory(parentCategory = "Women","Sports Shoes", specialFilters = listOf(Filter.COLOUR)),
                            FilterTertiaryCategory(parentCategory = "Women","Casual Shoes", specialFilters = listOf(Filter.COLOUR))
                        )
                    ),
                    FilterSubCategory(
                        name = "Innerwear & Sleepwear",
                        tertiaryCategories = listOf(
                            FilterTertiaryCategory(parentCategory = "Women","Bra", specialFilters = listOf(Filter.SIZE, Filter.FABRIC, Filter.COLOUR)),
                            FilterTertiaryCategory(parentCategory = "Women","Briefs", specialFilters = listOf(Filter.SIZE, Filter.FABRIC, Filter.COLOUR)),
                            FilterTertiaryCategory(parentCategory = "Women","Camisoles & Slips", specialFilters = listOf(Filter.SIZE, Filter.FABRIC, Filter.COLOUR)),
                            FilterTertiaryCategory(parentCategory = "Women","Nightsuits & Pyjamas", specialFilters = listOf(Filter.SIZE, Filter.FABRIC, Filter.COLOUR)),
                            FilterTertiaryCategory(parentCategory = "Women","Maternity", specialFilters = listOf(Filter.SIZE, Filter.FABRIC, Filter.COLOUR))
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
                            FilterTertiaryCategory(parentCategory = "Men","T-Shirts & Shirts", specialFilters = listOf(Filter.SIZE, Filter.FABRIC, Filter.OCCASION, Filter.COLOUR)),
                            FilterTertiaryCategory(parentCategory = "Men","Sweats & Hoodies", specialFilters = listOf(Filter.SIZE, Filter.FABRIC, Filter.OCCASION, Filter.COLOUR)),
                            FilterTertiaryCategory(parentCategory = "Men","Sweaters", specialFilters = listOf(Filter.SIZE, Filter.FABRIC, Filter.OCCASION, Filter.COLOUR)),
                            FilterTertiaryCategory(parentCategory = "Men","Jeans & Pants", specialFilters = listOf(Filter.SIZE, Filter.FABRIC, Filter.OCCASION, Filter.COLOUR)),
                            FilterTertiaryCategory(parentCategory = "Men","Shorts", specialFilters = listOf(Filter.SIZE, Filter.FABRIC, Filter.OCCASION, Filter.COLOUR)),
                            FilterTertiaryCategory(parentCategory = "Men","Coats & Jackets", specialFilters = listOf(Filter.SIZE, Filter.FABRIC, Filter.OCCASION, Filter.COLOUR)),
                            FilterTertiaryCategory(parentCategory = "Men","Suits & Blazers", specialFilters = listOf(Filter.SIZE, Filter.FABRIC, Filter.OCCASION, Filter.COLOUR)),
                            FilterTertiaryCategory(parentCategory = "Men","EthnicWear", specialFilters = listOf(Filter.SIZE, Filter.FABRIC, Filter.OCCASION, Filter.COLOUR)),
                            FilterTertiaryCategory(parentCategory = "Men","Footwear", specialFilters = listOf(Filter.SIZE, Filter.COLOUR)),
                            FilterTertiaryCategory(parentCategory = "Men","Bags & Backpacks", specialFilters = listOf(Filter.COLOUR)),
                            FilterTertiaryCategory(parentCategory = "Men","Accessories"),
                            FilterTertiaryCategory(parentCategory = "Men","Athletic Wear", specialFilters = listOf(Filter.SIZE, Filter.FABRIC, Filter.COLOUR)),
                        )
                    )
                )
            ),
            FilterCategoryUiModel(
                "Baby & Kids", listOf(
                    FilterSubCategory(
                        name = DEFAULT,
                        tertiaryCategories = listOf(
                            FilterTertiaryCategory(parentCategory = "Baby & Kids","Boys Clothing", specialFilters = listOf(Filter.SIZE, Filter.FABRIC, Filter.COLOUR)),
                            FilterTertiaryCategory(parentCategory = "Baby & Kids","Girls Clothing", specialFilters = listOf(Filter.SIZE, Filter.FABRIC, Filter.COLOUR)),
                            FilterTertiaryCategory(parentCategory = "Baby & Kids","Boys Footwear", specialFilters = listOf(Filter.SIZE)),
                            FilterTertiaryCategory(parentCategory = "Baby & Kids","Girls Footwear", specialFilters = listOf(Filter.SIZE)),
                            FilterTertiaryCategory(parentCategory = "Baby & Kids","Bath & Skin Care"),
                            FilterTertiaryCategory(parentCategory = "Baby & Kids","Accessories"),
                            FilterTertiaryCategory(parentCategory = "Baby & Kids","Toys & Games")
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
                            FilterTertiaryCategory(parentCategory = "Beauty & Care","Face Wash"),
                            FilterTertiaryCategory(parentCategory = "Beauty & Care","Face Toner"),
                            FilterTertiaryCategory(parentCategory = "Beauty & Care","Face Serum"),
                            FilterTertiaryCategory(parentCategory = "Beauty & Care","Masks & Peels"),
                            FilterTertiaryCategory(parentCategory = "Beauty & Care","Face Moisturiser"),
                            FilterTertiaryCategory(parentCategory = "Beauty & Care","Sunscreen"),
                            FilterTertiaryCategory(parentCategory = "Beauty & Care","Eye Care"),
                            FilterTertiaryCategory(parentCategory = "Beauty & Care","Night Care"),
                            FilterTertiaryCategory(parentCategory = "Beauty & Care","Skincare Kit"),
                        )
                    ),
                    FilterSubCategory(
                        name = "Hair Care",
                        tertiaryCategories = listOf(
                            FilterTertiaryCategory(parentCategory = "Beauty & Care","Hair Oil"),
                            FilterTertiaryCategory(parentCategory = "Beauty & Care","Hair Serum"),
                            FilterTertiaryCategory(parentCategory = "Beauty & Care","Hair Gels & Masks"),
                            FilterTertiaryCategory(parentCategory = "Beauty & Care","Shampoo & Conditioner"),
                            FilterTertiaryCategory(parentCategory = "Beauty & Care","Hair Colour"),
                            FilterTertiaryCategory(parentCategory = "Beauty & Care","Hair Spray"),
                            FilterTertiaryCategory(parentCategory = "Beauty & Care","Combs & Hair Brushes"),
                            FilterTertiaryCategory(parentCategory = "Beauty & Care","Hair Appliances"),
                        )
                    ),
                    FilterSubCategory(
                        name = "Make-Up & Nails",
                        tertiaryCategories = listOf(
                            FilterTertiaryCategory(parentCategory = "Beauty & Care","Foundation"),
                            FilterTertiaryCategory(parentCategory = "Beauty & Care","Compact"),
                            FilterTertiaryCategory(parentCategory = "Beauty & Care","Concealer"),
                            FilterTertiaryCategory(parentCategory = "Beauty & Care","Face Primer"),
                            FilterTertiaryCategory(parentCategory = "Beauty & Care","Blushes & Highlighter"),
                            FilterTertiaryCategory(parentCategory = "Beauty & Care","Eye Shadows"),
                            FilterTertiaryCategory(parentCategory = "Beauty & Care","Kajal & Eyeliner"),
                            FilterTertiaryCategory(parentCategory = "Beauty & Care","Eyebrow Pencil"),
                            FilterTertiaryCategory(parentCategory = "Beauty & Care","Mascara"),
                            FilterTertiaryCategory(parentCategory = "Beauty & Care","Lipstick", specialFilters = listOf(Filter.COLOUR)),
                            FilterTertiaryCategory(parentCategory = "Beauty & Care","Nail Polish", specialFilters = listOf(Filter.COLOUR)),
                            FilterTertiaryCategory(parentCategory = "Beauty & Care","Makeup Removers"),
                            FilterTertiaryCategory(parentCategory = "Beauty & Care","Tools & Accessories"),
                        )
                    ),
                    FilterSubCategory(
                        name = "Bath & Body",
                        tertiaryCategories = listOf(
                            FilterTertiaryCategory(parentCategory = "Beauty & Care","Body Washes & Scrubs"),
                            FilterTertiaryCategory(parentCategory = "Beauty & Care","Soaps"),
                            FilterTertiaryCategory(parentCategory = "Beauty & Care","Body Lotions"),
                            FilterTertiaryCategory(parentCategory = "Beauty & Care","Hair Removal"),
                            FilterTertiaryCategory(parentCategory = "Beauty & Care","Hand & Feet Cream"),
                            FilterTertiaryCategory(parentCategory = "Beauty & Care","Body Oil"),
                            FilterTertiaryCategory(parentCategory = "Beauty & Care","Intimate Hygiene"),
                            FilterTertiaryCategory(parentCategory = "Beauty & Care","Period Care")
                        )
                    ),
                    FilterSubCategory(
                        name = "Fragrances",
                        tertiaryCategories = listOf(
                            FilterTertiaryCategory(parentCategory = "Fragrances","Perfume"),
                            FilterTertiaryCategory(parentCategory = "Fragrances","Deodorant & Roll-Ons"),
                            FilterTertiaryCategory(parentCategory = "Fragrances","Body Mist")
                        )
                    ),
                    FilterSubCategory(
                        name = "Men's Grooming",
                        tertiaryCategories = listOf(
                            FilterTertiaryCategory(parentCategory = "Fragrances","Beard Care"),
                            FilterTertiaryCategory(parentCategory = "Fragrances","Grooming Kits")
                        )
                    ),
                    FilterSubCategory(
                        name = "Oral Care",
                        tertiaryCategories = listOf(
                            FilterTertiaryCategory(parentCategory = "Fragrances","Toothpaste & Brush"),
                            FilterTertiaryCategory(parentCategory = "Fragrances","Teeth Whitening"),
                            FilterTertiaryCategory(parentCategory = "Fragrances","Mouth Washes & Floss")
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
                            FilterTertiaryCategory(parentCategory = "Books","Fiction"),
                            FilterTertiaryCategory(parentCategory = "Books","Textbooks"),
                            FilterTertiaryCategory(parentCategory = "Books","Children's Books"),
                            FilterTertiaryCategory(parentCategory = "Books","Indian Language Books"),
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
                            FilterTertiaryCategory(parentCategory = "Home & Kitchen","Showpieces & Idols"),
                            FilterTertiaryCategory(parentCategory = "Home & Kitchen","Wall Decor & Clocks"),
                            FilterTertiaryCategory(parentCategory = "Home & Kitchen","Lamps & Lights"),
                            FilterTertiaryCategory(parentCategory = "Home & Kitchen","Candles & Candle Holders"),
                            FilterTertiaryCategory(parentCategory = "Home & Kitchen","Sewing & Craft"),
                            FilterTertiaryCategory(parentCategory = "Home & Kitchen","Wallpapers & Stickers"),
                            FilterTertiaryCategory(parentCategory = "Home & Kitchen","Pooja Needs"),
                            FilterTertiaryCategory(parentCategory = "Home & Kitchen","Artwork"),
                            FilterTertiaryCategory(parentCategory = "Home & Kitchen","Artificial Plants")
                        )
                    ),
                    FilterSubCategory(
                        name = "Kitchen & Dining",
                        tertiaryCategories = listOf(
                            FilterTertiaryCategory(parentCategory = "Home & Kitchen","Cooking Utensils"),
                            FilterTertiaryCategory(parentCategory = "Home & Kitchen","Baking Utensils"),
                            FilterTertiaryCategory(parentCategory = "Home & Kitchen","Kitchen Tools & Cutlery"),
                            FilterTertiaryCategory(parentCategory = "Home & Kitchen","Glasses, Cups & Barware"),
                            FilterTertiaryCategory(parentCategory = "Home & Kitchen","Containers & Tiffins"),
                            FilterTertiaryCategory(parentCategory = "Home & Kitchen","Water Bottles"),
                            FilterTertiaryCategory(parentCategory = "Home & Kitchen","Dinnerware")
                        )
                    ),
                    FilterSubCategory(
                        name = "Bedding & Furnishing",
                        tertiaryCategories = listOf(
                            FilterTertiaryCategory(parentCategory = "Home & Kitchen","Bedsheets & Curtains", specialFilters = listOf(Filter.COLOUR)),
                            FilterTertiaryCategory(parentCategory = "Home & Kitchen","Pillow Covers & Cushion Covers", specialFilters = listOf(Filter.COLOUR)),
                        )
                    ),
                    FilterSubCategory(
                        name = "Bath & Storage",
                        tertiaryCategories = listOf(
                            FilterTertiaryCategory(parentCategory = "Home & Kitchen","Organisers & Storage"),
                            FilterTertiaryCategory(parentCategory = "Home & Kitchen","Bathroom Accessories"),
                        )
                    ),
                    FilterSubCategory(
                        name = "Pet Supplies",
                        tertiaryCategories = listOf(
                            FilterTertiaryCategory(parentCategory = "Home & Kitchen","Pet Accessories")
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
                            FilterTertiaryCategory(parentCategory = "Gadgets","Mobile Phones"),
                            FilterTertiaryCategory(parentCategory = "Gadgets","Mobile Accessories"),
                            FilterTertiaryCategory(parentCategory = "Gadgets","Tablets"),
                            FilterTertiaryCategory(parentCategory = "Gadgets","Computers & Laptops"),
                            FilterTertiaryCategory(parentCategory = "Gadgets","Cases & Covers"),
                            FilterTertiaryCategory(parentCategory = "Gadgets","Drives & Storage"),
                            FilterTertiaryCategory(parentCategory = "Gadgets","Headphones & Earphones"),
                            FilterTertiaryCategory(parentCategory = "Gadgets","Camera & Photography"),
                            FilterTertiaryCategory(parentCategory = "Gadgets","E-Books"),
                            FilterTertiaryCategory(parentCategory = "Gadgets","Office Supplies & Stationery"),
                            FilterTertiaryCategory(parentCategory = "Gadgets","Fitness Gadgets")
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
    val parentCategory: String,  // e.g., "Women" or "Men"
    val name: String,
    val imageUrl: Int? = null,
    val specialFilters: List<Filter> = emptyList()
) {
    val uniqueKey: String
        get() = "${parentCategory.lowercase()}_$name".replace(" ", "_")
}
