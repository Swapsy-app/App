package com.example.freeupcopy.domain.model

import com.example.freeupcopy.domain.enums.SpecialOption

private const val DEFAULT = "Primary"

data class Category(
    val name: String,
    val subcategories: List<SubCategory> = emptyList()
) {


    companion object {
        val predefinedCategories = listOf(
            Category(
                name = "Women",
                subcategories = listOf(
                    SubCategory(
                        name = "Ethnic",
                        specialSubCatOption = listOf(
                            SpecialOption.FABRIC, SpecialOption.COLOUR, SpecialOption.OCCASION
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
                            SpecialOption.SIZE, SpecialOption.FABRIC, SpecialOption.COLOUR, SpecialOption.OCCASION
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
                            SpecialOption.COLOUR
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
                            SpecialOption.COLOUR
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
                            SpecialOption.SIZE, SpecialOption.FABRIC, SpecialOption.COLOUR
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
            Category(
                name = "Men",
                subcategories = listOf(
                    SubCategory(
                        name = DEFAULT,
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
            Category(
                "Baby & Kids", listOf(
                    SubCategory(
                        name = DEFAULT,
                        tertiaryCategories = listOf(
                            TertiaryCategory("Boys Clothing", specialOption = listOf(SpecialOption.SIZE, SpecialOption.FABRIC, SpecialOption.COLOUR)),
                            TertiaryCategory("Girls Clothing", specialOption = listOf(SpecialOption.SIZE, SpecialOption.FABRIC, SpecialOption.COLOUR)),
                            TertiaryCategory("Boys Footwear", specialOption = listOf(SpecialOption.SIZE)),
                            TertiaryCategory("Girls Footwear", specialOption = listOf(SpecialOption.SIZE)),
                            TertiaryCategory("Bath & Skin Care", specialOption = listOf(SpecialOption.EXPIRATION_DATE)),
                            TertiaryCategory("Accessories"),
                            TertiaryCategory("Toys & Games")
                        )
                    )
                )
            ),
            Category(
                name = "Beauty & Care",
                subcategories = listOf(
                    SubCategory(
                        name = "Skin Care",
                        specialSubCatOption = listOf(
                            SpecialOption.EXPIRATION_DATE
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
                        tertiaryCategories = listOf(
                            TertiaryCategory("Hair Oil"),
                            TertiaryCategory("Hair Serum"),
                            TertiaryCategory("Hair Gels & Masks"),
                            TertiaryCategory("Shampoo & Conditioner"),
                            TertiaryCategory("Hair Colour"),
                            TertiaryCategory("Hair Spray"),
                            TertiaryCategory("Combs & Hair Brushes"),
                            TertiaryCategory("Hair Appliances"),
                        )
                    ),
                    SubCategory(
                        name = "Make-Up & Nails",
                        tertiaryCategories = listOf(
                            TertiaryCategory("Foundation"),
                            TertiaryCategory("Compact"),
                            TertiaryCategory("Concealer"),
                            TertiaryCategory("Face Primer"),
                            TertiaryCategory("Blushes & Highlighter"),
                            TertiaryCategory("Eye Shadows"),
                            TertiaryCategory("Kajal & Eyeliner"),
                            TertiaryCategory("Eyebrow Pencil"),
                            TertiaryCategory("Mascara"),
                            TertiaryCategory("Lipstick"),
                            TertiaryCategory("Nail Polish"),
                            TertiaryCategory("Makeup Removers"),
                            TertiaryCategory("Tools & Accessories"),
                        )
                    ),
                    SubCategory(
                        name = "Bath & Body",
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
                        tertiaryCategories = listOf(
                            TertiaryCategory("Perfume"),
                            TertiaryCategory("Deodorant & Roll-Ons"),
                            TertiaryCategory("Body Mist")
                        )
                    ),
                    SubCategory(
                        name = "Men's Grooming",
                        tertiaryCategories = listOf(
                            TertiaryCategory("Beard Care"),
                            TertiaryCategory("Grooming Kits")
                        )
                    ),
                    SubCategory(
                        name = "Oral Care",
                        tertiaryCategories = listOf(
                            TertiaryCategory("Toothpaste & Brush"),
                            TertiaryCategory("Teeth Whitening"),
                            TertiaryCategory("Mouth Washes & Floss")
                        )
                    ),
                )
            ),
            Category(
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
            Category(
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
                        tertiaryCategories = listOf(
                            TertiaryCategory("Cooking Utensils"),
                            TertiaryCategory("Baking Utensils"),
                            TertiaryCategory("Kitchen Tools & Cutlery"),
                            TertiaryCategory("Glasses, Cups & Barware"),
                            TertiaryCategory("Containers & Tiffins"),
                            TertiaryCategory("Water Bottles"),
                            TertiaryCategory("Dinnerware")
                        )
                    )
                )
            ),
            Category(
                name = "Gadgets",
                subcategories = listOf(
                    SubCategory(
                        name = DEFAULT,
                        tertiaryCategories = listOf(
                            TertiaryCategory("Mobile Phones"),
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

data class CategoryUiModel(
    val category: String,
    val subCategory: String,
    val tertiaryCategory: String
)