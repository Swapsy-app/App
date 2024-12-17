package com.example.freeupcopy.domain.model

//open class Category(
//    val name: String,
//    val subcategories: List<Category> = emptyList(),
//    val imageId: Int? = null
//) {
//
//    companion object {
//        val predefinedCategories = listOf(
//            Category(
//                name = "Women",
//                subcategories = listOf(
//                    Category("Ethnic", listOf(
//                        Category("Kurtas"),
//                        Category("Blouses"),
//                        Category("Kurta Sets & Suits"),
//                        Category("Sarees"),
//                        Category("Dupattas"),
//                        Category("Ethnic Skirts"),
//                        Category("Lehenga Choli"),
//                        Category("Ethnic Gowns"),
//                        Category("Bridal Lehenga"),
//                        Category("Palazzos & Salwars"),
//                        Category("Dress Material")
//                    )),
//                    Category("Western", listOf(
//                        Category("Dresses"),
//                        Category("Tops & Tunics"),
//                        Category("T-Shirts"),
//                        Category("Jumpsuits & Co-ords"),
//                        Category("Jeans & Trousers"),
//                        Category("Sweaters & Sweatshirts"),
//                        Category("Shorts & Skirts"),
//                        Category("Jackets & Overcoats"),
//                        Category("Blazers"),
//                        Category("Active Wear")
//                    )),
//                    Category("Jewellery", listOf(
//                        Category("Jewellery Sets"),
//                        Category("Earrings & Studs"),
//                        Category("Mangalsutras"),
//                        Category("Bangles & Bracelets"),
//                        Category("Necklaces & Chains"),
//                        Category("Kamarbandh & Maangtika"),
//                        Category("Rings"),
//                        Category("Anklets & Nosepins")
//                    )),
//                    Category("Accessories", listOf(
//                        Category("Sunglasses"),
//                        Category("Watches"),
//                        Category("Caps & Hats"),
//                        Category("Hair Accessories"),
//                        Category("Belts"),
//                        Category("Scarfs & Stoles"),
//                    )),
//                    Category("Bags", listOf(
//                        Category("Handbags"),
//                        Category("Clutches"),
//                        Category("Wallets"),
//                        Category("Backpacks"),
//                        Category("Slingbags")
//                    )),
//                    Category("Footwear", listOf(
//                        Category("Flats & Sandals"),
//                        Category("Heels & Wedges"),
//                        Category("Boots"),
//                        Category("Flipflops & Slippers"),
//                        Category("Bellies & Ballerinas"),
//                        Category("Sports Shoes"),
//                        Category("Casual Shoes")
//                    )),
//                    Category("Innerwear & Sleepwear", listOf(
//                        Category("Bra"),
//                        Category("Briefs"),
//                        Category("Camisoles & Slips"),
//                        Category("Nightsuits & Pyjamas"),
//                        Category("Maternity")
//                    )),
//                ),
//            ),
//            Category(
//                name = "Men",
//                subcategories = listOf(
//                    Category("T-Shirts & Shirts"),
//                    Category("Sweats & Hoodies"),
//                    Category("Sweaters"),
//                    Category("Jeans & Pants"),
//                    Category("Shorts"),
//                    Category("Coats & Jackets"),
//                    Category("Suits & Blazers"),
//                    Category("Ethnic Wear"),
//                    Category("Footwear"),
//                    Category("Bags & Backpacks"),
//                    Category("Accessories"),
//                    Category("Athletic Wear"),
//                )
//            ),
//            Category("Baby & Kids", listOf(
//                Category("Boys Clothing"),
//                Category("Girls Clothing"),
//                Category("Boys Footwear"),
//                Category("Girls Footwear"),
//                Category("Bath & Skin Care"),
//                Category("Accessories"),
//                Category("Toys & Games"),
//            )),
//            Category("Beauty & Care",
//                listOf(
//                    Category("Skin Care", listOf(
//                        Category("Face Wash"),
//                        Category("Face Toner"),
//                        Category("Face Serum"),
//                        Category("Masks & Peels"),
//                        Category("Face Moisturiser"),
//                        Category("Sunscreen"),
//                        Category("Eye Care"),
//                        Category("Night Care"),
//                        Category("Skincare Kit"),
//                    )),
//                    Category("Hair Care", listOf(
//                        Category("Hair Oil"),
//                        Category("Hair Serum"),
//                        Category("Hair Gels & Masks"),
//                        Category("Shampoo & Conditioner"),
//                        Category("Hair Colour"),
//                        Category("Hair Spray"),
//                        Category("Combs & Hair Brushes"),
//                        Category("Hair Appliances"),
//                    )),
//                    Category("Make-Up & Nails", listOf(
//                        Category("Foundation"),
//                        Category("Compact"),
//                        Category("Concealer"),
//                        Category("Face Primer"),
//                        Category("Blushes & Highlighter"),
//                        Category("Eye Shadows"),
//                        Category("Kajal & Eyeliner"),
//                        Category("Eyebrow Pencil"),
//                        Category("Mascara"),
//                        Category("Lipstick"),
//                        Category("Nail Polish"),
//                        Category("Makeup Removers"),
//                        Category("Tools & Accessories"),
//                    )),
//                    Category("Bath & Body", listOf(
//                        Category("Body Washes & Scrubs"),
//                        Category("Soaps"),
//                        Category("Body Lotions"),
//                        Category("Hair Removal"),
//                        Category("Hand & Feet Cream"),
//                        Category("Body Oil"),
//                        Category("Intimate Hygiene"),
//                        Category("Period Care")
//                    )),
//                    Category("Fragrances", listOf(
//                        Category("Perfume"),
//                        Category("Deodorant & Roll-Ons"),
//                        Category("Body Mist")
//                    )),
//                    Category("Men's Grooming", listOf(
//                        Category("Beard Care"),
//                        Category("Grooming Kits")
//                    )),
//                    Category("Oral Care", listOf(
//                        Category("Toothpaste & Brush"),
//                        Category("Teeth Whitening"),
//                        Category("Mouth Washes & Floss")
//                    )),
//            )),
//            Category("Books", listOf(
//                Category("Fiction"),
//                Category("Textbooks"),
//                Category("Children's Books"),
//                Category("Indian Language Books")
//            )),
//            Category("Home & Kitchen", listOf(
//                Category("Home Decor", listOf(
//                    Category("Showpieces & Idols"),
//                    Category("Wall Decor & Clocks"),
//                    Category("Lamps & Lights"),
//                    Category("Candles & Candle Holders"),
//                    Category("Sewing & Craft"),
//                    Category("Wallpapers & Stickers"),
//                    Category("Pooja Needs"),
//                    Category("Artwork"),
//                    Category("Artificial Plants")
//                )),
//                Category("Kitchen & Dining", listOf(
//                    Category("Cooking Utensils"),
//                    Category("Baking Utensils"),
//                    Category("Kitchen Tools & Cutlery"),
//                    Category("Glasses, Cups & Barware"),
//                    Category("Containers & Tiffins"),
//                    Category("Water Bottles"),
//                    Category("Dinnerware")
//                )),
//            )),
//            Category("Gadgets", listOf(
//                Category("Mobile Phones"),
//                Category("Mobile Accessories"),
//                Category("Tablets"),
//                Category("Computers & Laptops"),
//                Category("Cases & Covers"),
//                Category("Drives & Storage"),
//                Category("Headphones & Earphones"),
//                Category("Camera & Photography"),
//                Category("E-Books"),
//                Category("Office Supplies & Stationery"),
//                Category("Fitness Gadgets")
//            ))
//        )
//    }
//}

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
                        tertiaryCategories = listOf(
                            TertiaryCategory("Kurtas"),
                            TertiaryCategory("Blouses"),
                            TertiaryCategory("Kurta Sets & Suits"),
                            TertiaryCategory("Sarees"),
                            TertiaryCategory("Dupattas"),
                            TertiaryCategory("Ethnic Skirts"),
                            TertiaryCategory("Lehenga Choli"),
                            TertiaryCategory("Ethnic Gowns"),
                            TertiaryCategory("Bridal Lehenga"),
                            TertiaryCategory("Palazzos & Salwars"),
                            TertiaryCategory("Dress Material")
                        )
                    ),
                    SubCategory(
                        name = "Western",
                        tertiaryCategories = listOf(
                            TertiaryCategory("Dresses"),
                            TertiaryCategory("Tops & Tunics"),
                            TertiaryCategory("T-Shirts"),
                            TertiaryCategory("Jumpsuits & Co-ords"),
                            TertiaryCategory("Jeans & Trousers"),
                            TertiaryCategory("Sweaters & Sweatshirts"),
                            TertiaryCategory("Shorts & Skirts"),
                            TertiaryCategory("Jackets & Overcoats"),
                            TertiaryCategory("Blazers"),
                            TertiaryCategory("Active Wear")
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
                        name = "T-Shirts & Shirts",
                        tertiaryCategories = listOf(
                            TertiaryCategory("T-Shirts"),
                            TertiaryCategory("Shirts"),
                            TertiaryCategory("Polos"),
                            TertiaryCategory("Henleys"),
                            TertiaryCategory("Sweatshirts"),
                            TertiaryCategory("Hoodies"),
                            TertiaryCategory("Sweaters"),
                            TertiaryCategory("Jackets"),
                            TertiaryCategory("Blazers"),
                            TertiaryCategory("Suits")
                        )
                    ),
                    SubCategory(
                        name = "Jeans & Pants",
                        tertiaryCategories = listOf(
                            TertiaryCategory("Jeans"),
                            TertiaryCategory("Trousers"),
                            TertiaryCategory("Chinos"),
                            TertiaryCategory("Joggers"),
                            TertiaryCategory("Track Pants"),
                            TertiaryCategory("Shorts"),
                            TertiaryCategory("3/4ths"),
                            TertiaryCategory("Cargos"),
                            TertiaryCategory("Pyjamas")
                        )
                    ),
                    SubCategory(
                        name = "Footwear",
                        tertiaryCategories = listOf(
                            TertiaryCategory("Casual Shoes"),
                            TertiaryCategory("Formal Shoes"),
                            TertiaryCategory("Sneakers"),
                            TertiaryCategory("Loafers"),
                            TertiaryCategory("Boots"),
                            TertiaryCategory("Sandals & Floaters"),
                            TertiaryCategory("Flip Flops"),
                            TertiaryCategory("Slippers"),
                            TertiaryCategory("Sports Shoes"),
                            TertiaryCategory("Running Shoes")
                        )
                    ),
                    SubCategory(
                        name = "Accessories",
                        tertiaryCategories = listOf(
                            TertiaryCategory("Watches"),
                            TertiaryCategory("Sunglasses"),
                            TertiaryCategory("Belts"),
                            TertiaryCategory("Wallets"),
                            TertiaryCategory("Caps & Hats"),
                            TertiaryCategory("Ties, Cufflinks & Pocket Squares"),
                            TertiaryCategory("Scarves"),
                            TertiaryCategory("Mufflers"),
                            TertiaryCategory("Phone Cases"),
                            TertiaryCategory("Rings & Wristwear")
                        )
                    ),
                    SubCategory(
                        name = "Bags & Backpacks",
                        tertiaryCategories = listOf(
                            TertiaryCategory("Backpacks"),
                            TertiaryCategory("Laptop Bags"),
                            TertiaryCategory("Messenger Bags"),
                            TertiaryCategory("Duffle Bags"),
                            TertiaryCategory("Trolley")
                        )
                    )
                )
            ),
            Category(
                "Baby & Kids", listOf(
                    SubCategory(
                        name = "Boys Clothing",
                        tertiaryCategories = listOf(
                            TertiaryCategory("T-Shirts"),
                            TertiaryCategory("Shirts"),
                            TertiaryCategory("Shorts"),
                            TertiaryCategory("Jeans"),
                            TertiaryCategory("Trousers"),
                            TertiaryCategory("Track Pants"),
                            TertiaryCategory("Jackets"),
                            TertiaryCategory("Sweaters"),
                            TertiaryCategory("Sweatshirts"),
                            TertiaryCategory("Hoodies")
                        )
                    ),
                    SubCategory(
                        name = "Girls Clothing",
                        tertiaryCategories = listOf(
                            TertiaryCategory("Tops"),
                            TertiaryCategory("Dresses"),
                            TertiaryCategory("Skirts"),
                            TertiaryCategory("Shorts"),
                            TertiaryCategory("Jeans"),
                            TertiaryCategory("Trousers"),
                            TertiaryCategory("Leggings"),
                            TertiaryCategory("Jackets"),
                            TertiaryCategory("Sweaters"),
                            TertiaryCategory("Sweatshirts")
                        )
                    )
                )
            ),
            Category(
                name = "Books",
                subcategories = listOf(
                    SubCategory(
                        name = "Primary",
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
                        name = "Primary",
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
    val tertiaryCategories: List<TertiaryCategory>
)

data class TertiaryCategory(
    val name: String,
    val imageId: Int? = null
)

data class CategoryUiModel(
    val category: String,
    val subCategory: String,
    val tertiaryCategory: String
)