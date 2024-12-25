package com.example.freeupcopy.domain.model

import com.example.freeupcopy.domain.enums.SpecialOption

private const val DEFAULT = "Primary"

data class CategoryUiModel(
    val name: String,
    val subcategories: List<SubCategory> = emptyList()
) {
    companion object {
        val predefinedCategories = listOf(
            CategoryUiModel(
                name = "Women",
                subcategories = listOf(
                    SubCategory(
                        name = "Ethnic",
                        specialSubCatOption = listOf(
                            SpecialOption.BRAND, SpecialOption.FABRIC, SpecialOption.COLOUR, SpecialOption.OCCASION
                        ),
                        tertiaryCategories = listOf(
                            TertiaryCategory("Sarees"),
                            TertiaryCategory("Blouses", specialOption = listOf(SpecialOption.SIZE, SpecialOption.SHAPE)),
                            TertiaryCategory("Kurtas", specialOption = listOf(SpecialOption.SIZE, SpecialOption.SHAPE)),
                            TertiaryCategory("Kurta Sets & Suits", specialOption = listOf(SpecialOption.SIZE, SpecialOption.SHAPE)),
                            TertiaryCategory("Dupattas"),
                            TertiaryCategory("Lehenga Choli", specialOption = listOf(SpecialOption.SIZE)),
                            TertiaryCategory("Ethnic Skirts", specialOption = listOf(SpecialOption.SIZE)),
                            TertiaryCategory("Bridal Lehenga", specialOption = listOf(SpecialOption.SIZE)),
                            TertiaryCategory("Ethnic Gowns", specialOption = listOf(SpecialOption.SIZE)),
                            TertiaryCategory("Palazzos & Salwars", specialOption = listOf(SpecialOption.SIZE)),
                            TertiaryCategory("Dress Material")
                        )
                    ),
                    SubCategory(
                        name = "Western",
                        specialSubCatOption = listOf(
                            SpecialOption.BRAND, SpecialOption.SIZE, SpecialOption.FABRIC, SpecialOption.COLOUR, SpecialOption.OCCASION
                        ),
                        tertiaryCategories = listOf(
                            TertiaryCategory("Dresses", specialOption = listOf(SpecialOption.SHAPE, SpecialOption.LENGTH)),
                            TertiaryCategory("Tops & Tunics", specialOption = listOf(SpecialOption.SHAPE)),
                            TertiaryCategory("T-Shirts", specialOption = listOf(SpecialOption.SHAPE)),
                            TertiaryCategory("Jumpsuits & Co-ords", specialOption = listOf(SpecialOption.SHAPE)),
                            TertiaryCategory("Jeans & Trousers", specialOption = listOf(SpecialOption.SHAPE)),
                            TertiaryCategory("Sweaters & Sweatshirts"),
                            TertiaryCategory("Shorts & Skirts"),
                            TertiaryCategory("Jackets & Overcoats"),
                            TertiaryCategory("Blazers"),
                            TertiaryCategory("Active Wear"),
                        )
                    ),
                    SubCategory(
                        name = "Jewellery",
                        tertiaryCategories = listOf(
                            TertiaryCategory("Jewellery Sets"),
                            TertiaryCategory("Earrings & Studs"),
                            TertiaryCategory("Mangalsutras"),
                            TertiaryCategory("Bangles & Bracelets"),
                            TertiaryCategory("Necklaces & Chains"),
                            TertiaryCategory("Kamarbandh & Maangtika"),
                            TertiaryCategory("Rings"),
                            TertiaryCategory("Anklets & Nosepins")
                        )
                    ),
                    SubCategory(
                        name = "Accessories",
                        specialSubCatOption = listOf(
                            SpecialOption.BRAND
                        ),
                        tertiaryCategories = listOf(
                            TertiaryCategory("Sunglasses"),
                            TertiaryCategory("Watches"),
                            TertiaryCategory("Caps & Hats"),
                            TertiaryCategory("Hair Accessories"),
                            TertiaryCategory("Belts"),
                            TertiaryCategory("Scarfs & Stoles"),
                        )
                    ),
                    SubCategory(
                        name = "Bags",
                        specialSubCatOption = listOf(
                            SpecialOption.BRAND, SpecialOption.COLOUR
                        ),
                        tertiaryCategories = listOf(
                            TertiaryCategory("Handbags"),
                            TertiaryCategory("Clutches"),
                            TertiaryCategory("Wallets"),
                            TertiaryCategory("Backpacks"),
                            TertiaryCategory("Slingbags")
                        )
                    ),
                    SubCategory(
                        name = "Footwear",
                        specialSubCatOption = listOf(
                            SpecialOption.BRAND, SpecialOption.COLOUR
                        ),
                        tertiaryCategories = listOf(
                            TertiaryCategory("Flats & Sandals"),
                            TertiaryCategory("Heels & Wedges"),
                            TertiaryCategory("Boots"),
                            TertiaryCategory("Flipflops & Slippers"),
                            TertiaryCategory("Bellies & Ballerinas"),
                            TertiaryCategory("Sports Shoes"),
                            TertiaryCategory("Casual Shoes")
                        )
                    ),
                    SubCategory(
                        name = "Innerwear & Sleepwear",
                        specialSubCatOption = listOf(
                            SpecialOption.BRAND, SpecialOption.SIZE, SpecialOption.FABRIC, SpecialOption.COLOUR
                        ),
                        tertiaryCategories = listOf(
                            TertiaryCategory("Bra"),
                            TertiaryCategory("Briefs"),
                            TertiaryCategory("Camisoles & Slips"),
                            TertiaryCategory("Nightsuits & Pyjamas"),
                            TertiaryCategory("Maternity")
                        )
                    )
                ),
            ),
            CategoryUiModel(
                name = "Men",
                subcategories = listOf(
                    SubCategory(
                        name = DEFAULT,
                        specialSubCatOption = listOf(
                            SpecialOption.BRAND
                        ),
                        tertiaryCategories = listOf(
                            TertiaryCategory("T-Shirts & Shirts", specialOption = listOf(SpecialOption.SIZE, SpecialOption.FABRIC, SpecialOption.OCCASION, SpecialOption.COLOUR)),
                            TertiaryCategory("Sweats & Hoodies", specialOption = listOf(SpecialOption.SIZE, SpecialOption.FABRIC, SpecialOption.OCCASION, SpecialOption.COLOUR)),
                            TertiaryCategory("Sweaters", specialOption = listOf(SpecialOption.SIZE, SpecialOption.FABRIC, SpecialOption.OCCASION, SpecialOption.COLOUR)),
                            TertiaryCategory("Jeans & Pants", specialOption = listOf(SpecialOption.SIZE, SpecialOption.FABRIC, SpecialOption.OCCASION, SpecialOption.COLOUR)),
                            TertiaryCategory("Shorts", specialOption = listOf(SpecialOption.SIZE, SpecialOption.FABRIC, SpecialOption.OCCASION, SpecialOption.COLOUR)),
                            TertiaryCategory("Coats & Jackets", specialOption = listOf(SpecialOption.SIZE, SpecialOption.FABRIC, SpecialOption.OCCASION, SpecialOption.COLOUR)),
                            TertiaryCategory("Suits & Blazers", specialOption = listOf(SpecialOption.SIZE, SpecialOption.FABRIC, SpecialOption.OCCASION, SpecialOption.COLOUR)),
                            TertiaryCategory("EthnicWear", specialOption = listOf(SpecialOption.SIZE, SpecialOption.FABRIC, SpecialOption.OCCASION, SpecialOption.COLOUR)),
                            TertiaryCategory("Footwear", specialOption = listOf(SpecialOption.SIZE, SpecialOption.COLOUR)),
                            TertiaryCategory("Bags & Backpacks", specialOption = listOf(SpecialOption.COLOUR)),
                            TertiaryCategory("Accessories"),
                            TertiaryCategory("Athletic Wear", specialOption = listOf(SpecialOption.SIZE, SpecialOption.FABRIC, SpecialOption.COLOUR)),
                        )
                    )
                )
            ),
            CategoryUiModel(
                "Baby & Kids", listOf(
                    SubCategory(
                        name = DEFAULT,
                        tertiaryCategories = listOf(
                            TertiaryCategory("Boys Clothing", specialOption = listOf(SpecialOption.BRAND, SpecialOption.SIZE, SpecialOption.FABRIC, SpecialOption.COLOUR)),
                            TertiaryCategory("Girls Clothing", specialOption = listOf(SpecialOption.BRAND, SpecialOption.SIZE, SpecialOption.FABRIC, SpecialOption.COLOUR)),
                            TertiaryCategory("Boys Footwear", specialOption = listOf(SpecialOption.BRAND, SpecialOption.SIZE)),
                            TertiaryCategory("Girls Footwear", specialOption = listOf(SpecialOption.BRAND, SpecialOption.SIZE)),
                            TertiaryCategory("Bath & Skin Care", specialOption = listOf(SpecialOption.BRAND, SpecialOption.EXPIRATION_DATE)),
                            TertiaryCategory("Accessories"),
                            TertiaryCategory("Toys & Games")
                        )
                    )
                )
            ),
            CategoryUiModel(
                name = "Beauty & Care",
                subcategories = listOf(
                    SubCategory(
                        name = "Skin Care",
                        specialSubCatOption = listOf(
                            SpecialOption.BRAND, SpecialOption.EXPIRATION_DATE
                        ),
                        tertiaryCategories = listOf(
                            TertiaryCategory("Face Wash"),
                            TertiaryCategory("Face Toner"),
                            TertiaryCategory("Face Serum"),
                            TertiaryCategory("Masks & Peels"),
                            TertiaryCategory("Face Moisturiser"),
                            TertiaryCategory("Sunscreen"),
                            TertiaryCategory("Eye Care"),
                            TertiaryCategory("Night Care"),
                            TertiaryCategory("Skincare Kit"),
                        )
                    ),
                    SubCategory(
                        name = "Hair Care",
                        specialSubCatOption = listOf(
                            SpecialOption.BRAND
                        ),
                        tertiaryCategories = listOf(
                            TertiaryCategory("Hair Oil", specialOption = listOf(SpecialOption.EXPIRATION_DATE)),
                            TertiaryCategory("Hair Serum", specialOption = listOf(SpecialOption.EXPIRATION_DATE)),
                            TertiaryCategory("Hair Gels & Masks", specialOption = listOf(SpecialOption.EXPIRATION_DATE)),
                            TertiaryCategory("Shampoo & Conditioner", specialOption = listOf(SpecialOption.EXPIRATION_DATE)),
                            TertiaryCategory("Hair Colour", specialOption = listOf(SpecialOption.EXPIRATION_DATE)),
                            TertiaryCategory("Hair Spray", specialOption = listOf(SpecialOption.EXPIRATION_DATE)),
                            TertiaryCategory("Combs & Hair Brushes"),
                            TertiaryCategory("Hair Appliances"),
                        )
                    ),
                    SubCategory(
                        name = "Make-Up & Nails",
                        specialSubCatOption = listOf(
                            SpecialOption.BRAND
                        ),
                        tertiaryCategories = listOf(
                            TertiaryCategory("Foundation", specialOption = listOf(SpecialOption.EXPIRATION_DATE)),
                            TertiaryCategory("Compact", specialOption = listOf(SpecialOption.EXPIRATION_DATE)),
                            TertiaryCategory("Concealer", specialOption = listOf(SpecialOption.EXPIRATION_DATE)),
                            TertiaryCategory("Face Primer", specialOption = listOf(SpecialOption.EXPIRATION_DATE)),
                            TertiaryCategory("Blushes & Highlighter", specialOption = listOf(SpecialOption.EXPIRATION_DATE)),
                            TertiaryCategory("Eye Shadows", specialOption = listOf(SpecialOption.EXPIRATION_DATE)),
                            TertiaryCategory("Kajal & Eyeliner", specialOption = listOf(SpecialOption.EXPIRATION_DATE)),
                            TertiaryCategory("Eyebrow Pencil", specialOption = listOf(SpecialOption.EXPIRATION_DATE)),
                            TertiaryCategory("Mascara", specialOption = listOf(SpecialOption.EXPIRATION_DATE)),
                            TertiaryCategory("Lipstick", specialOption = listOf(SpecialOption.COLOUR)),
                            TertiaryCategory("Nail Polish", specialOption = listOf(SpecialOption.COLOUR)),
                            TertiaryCategory("Makeup Removers", specialOption = listOf(SpecialOption.EXPIRATION_DATE)),
                            TertiaryCategory("Tools & Accessories"),
                        )
                    ),
                    SubCategory(
                        name = "Bath & Body",
                        specialSubCatOption = listOf(
                            SpecialOption.BRAND, SpecialOption.EXPIRATION_DATE
                        ),
                        tertiaryCategories = listOf(
                            TertiaryCategory("Body Washes & Scrubs"),
                            TertiaryCategory("Soaps"),
                            TertiaryCategory("Body Lotions"),
                            TertiaryCategory("Hair Removal"),
                            TertiaryCategory("Hand & Feet Cream"),
                            TertiaryCategory("Body Oil"),
                            TertiaryCategory("Intimate Hygiene"),
                            TertiaryCategory("Period Care")
                        )
                    ),
                    SubCategory(
                        name = "Fragrances",
                        specialSubCatOption = listOf(
                            SpecialOption.BRAND, SpecialOption.EXPIRATION_DATE
                        ),
                        tertiaryCategories = listOf(
                            TertiaryCategory("Perfume"),
                            TertiaryCategory("Deodorant & Roll-Ons"),
                            TertiaryCategory("Body Mist")
                        )
                    ),
                    SubCategory(
                        name = "Men's Grooming",
                        specialSubCatOption = listOf(
                            SpecialOption.BRAND, SpecialOption.EXPIRATION_DATE
                        ),
                        tertiaryCategories = listOf(
                            TertiaryCategory("Beard Care"),
                            TertiaryCategory("Grooming Kits")
                        )
                    ),
                    SubCategory(
                        name = "Oral Care",
                        specialSubCatOption = listOf(
                            SpecialOption.BRAND, SpecialOption.EXPIRATION_DATE
                        ),
                        tertiaryCategories = listOf(
                            TertiaryCategory("Toothpaste & Brush"),
                            TertiaryCategory("Teeth Whitening"),
                            TertiaryCategory("Mouth Washes & Floss")
                        )
                    ),
                )
            ),
            CategoryUiModel(
                name = "Books",
                subcategories = listOf(
                    SubCategory(
                        name = DEFAULT,
                        tertiaryCategories = listOf(
                            TertiaryCategory("Fiction"),
                            TertiaryCategory("Textbooks"),
                            TertiaryCategory("Children's Books"),
                            TertiaryCategory("Indian Language Books"),
                        )
                    )
                )
            ),
            CategoryUiModel(
                name = "Home & Kitchen",
                subcategories = listOf(
                    SubCategory(
                        name = "Home Decor",
                        tertiaryCategories = listOf(
                            TertiaryCategory("Showpieces & Idols"),
                            TertiaryCategory("Wall Decor & Clocks"),
                            TertiaryCategory("Lamps & Lights"),
                            TertiaryCategory("Candles & Candle Holders"),
                            TertiaryCategory("Sewing & Craft"),
                            TertiaryCategory("Wallpapers & Stickers"),
                            TertiaryCategory("Pooja Needs"),
                            TertiaryCategory("Artwork"),
                            TertiaryCategory("Artificial Plants")
                        )
                    ),
                    SubCategory(
                        name = "Kitchen & Dining",
                        specialSubCatOption = listOf(
                            SpecialOption.BRAND
                        ),
                        tertiaryCategories = listOf(
                            TertiaryCategory("Cooking Utensils"),
                            TertiaryCategory("Baking Utensils"),
                            TertiaryCategory("Kitchen Tools & Cutlery"),
                            TertiaryCategory("Glasses, Cups & Barware"),
                            TertiaryCategory("Containers & Tiffins"),
                            TertiaryCategory("Water Bottles"),
                            TertiaryCategory("Dinnerware")
                        )
                    ),
                    SubCategory(
                        name = "Bedding & Furnishing",
                        specialSubCatOption = listOf(
                            SpecialOption.BRAND, SpecialOption.COLOUR
                        ),
                        tertiaryCategories = listOf(
                            TertiaryCategory("Bedsheets & Curtains"),
                            TertiaryCategory("Pillow Covers & Cushion Covers"),
                        )
                    ),
                    SubCategory(
                        name = "Bath & Storage",
                        specialSubCatOption = listOf(
                            SpecialOption.BRAND
                        ),
                        tertiaryCategories = listOf(
                            TertiaryCategory("Organisers & Storage"),
                            TertiaryCategory("Bathroom Accessories"),
                        )
                    ),
                    SubCategory(
                        name = "Pet Supplies",
                        tertiaryCategories = listOf(
                            TertiaryCategory("Pet Accessories")
                        )
                    ),
                )
            ),
            CategoryUiModel(
                name = "Gadgets",
                subcategories = listOf(
                    SubCategory(
                        name = DEFAULT,
                        tertiaryCategories = listOf(
                            TertiaryCategory("Mobile Phones", specialOption = listOf(SpecialOption.BRAND, SpecialOption.MODEL_NUMBER, SpecialOption.INCLUDES, SpecialOption.STORAGE_CAPACITY, SpecialOption.RAM, SpecialOption.BATTERY_CAPACITY, SpecialOption.MOBILE_NETWORK, SpecialOption.SCREEN_SIZE, SpecialOption.SIM_TYPE, SpecialOption.WARRANTY)),
                            TertiaryCategory("Mobile Accessories"),
                            TertiaryCategory("Tablets"),
                            TertiaryCategory("Computers & Laptops"),
                            TertiaryCategory("Cases & Covers"),
                            TertiaryCategory("Drives & Storage"),
                            TertiaryCategory("Headphones & Earphones"),
                            TertiaryCategory("Camera & Photography"),
                            TertiaryCategory("E-Books"),
                            TertiaryCategory("Office Supplies & Stationery"),
                            TertiaryCategory("Fitness Gadgets")
                        )
                    )
                )
            )
        )
    }
}

data class SubCategory(
    val name: String,
    val tertiaryCategories: List<TertiaryCategory>,
    val specialSubCatOption: List<SpecialOption> = emptyList()
)

data class TertiaryCategory(
    val name: String,
    val imageUrl: Int? = null,
    val specialOption: List<SpecialOption> = emptyList()
)