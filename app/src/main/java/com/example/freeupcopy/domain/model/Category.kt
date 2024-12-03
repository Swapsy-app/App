package com.example.freeupcopy.domain.model

open class Category(
    val name: String,
    val subcategories: List<Category> = emptyList(),
    val imageId: Int? = null
) {

    companion object {
        val predefinedCategories = listOf(
            Category(
                name = "Women",
                subcategories = listOf(
                    Category("Ethnic", listOf(
                        Category("Kurtas"),
                        Category("Blouses"),
                        Category("Kurta Sets & Suits"),
                        Category("Sarees"),
                        Category("Dupattas"),
                        Category("Ethnic Skirts"),
                        Category("Lehenga Choli"),
                        Category("Ethnic Gowns"),
                        Category("Bridal Lehenga"),
                        Category("Palazzos & Salwars"),
                        Category("Dress Material")
                    )),
                    Category("Western", listOf(
                        Category("Dresses"),
                        Category("Tops & Tunics"),
                        Category("T-Shirts"),
                        Category("Jumpsuits & Co-ords"),
                        Category("Jeans & Trousers"),
                        Category("Sweaters & Sweatshirts"),
                        Category("Shorts & Skirts"),
                        Category("Jackets & Overcoats"),
                        Category("Blazers"),
                        Category("Active Wear")
                    )),
                    Category("Jewellery", listOf(
                        Category("Jewellery Sets"),
                        Category("Earrings & Studs"),
                        Category("Mangalsutras"),
                        Category("Bangles & Bracelets"),
                        Category("Necklaces & Chains"),
                        Category("Kamarbandh & Maangtika"),
                        Category("Rings"),
                        Category("Anklets & Nosepins")
                    )),
                    Category("Accessories", listOf(
                        Category("Sunglasses"),
                        Category("Watches"),
                        Category("Caps & Hats"),
                        Category("Hair Accessories"),
                        Category("Belts"),
                        Category("Scarfs & Stoles"),
                    )),
                    Category("Bags", listOf(
                        Category("Handbags"),
                        Category("Clutches"),
                        Category("Wallets"),
                        Category("Backpacks"),
                        Category("Slingbags")
                    )),
                    Category("Footwear", listOf(
                        Category("Flats & Sandals"),
                        Category("Heels & Wedges"),
                        Category("Boots"),
                        Category("Flipflops & Slippers"),
                        Category("Bellies & Ballerinas"),
                        Category("Sports Shoes"),
                        Category("Casual Shoes")
                    )),
                    Category("Innerwear & Sleepwear", listOf(
                        Category("Bra"),
                        Category("Briefs"),
                        Category("Camisoles & Slips"),
                        Category("Nightsuits & Pyjamas"),
                        Category("Maternity")
                    )),
                ),
            ),
            Category(
                name = "Men",
                subcategories = listOf(
                    Category("T-Shirts & Shirts"),
                    Category("Sweats & Hoodies"),
                    Category("Sweaters"),
                    Category("Jeans & Pants"),
                    Category("Shorts"),
                    Category("Coats & Jackets"),
                    Category("Suits & Blazers"),
                    Category("Ethnic Wear"),
                    Category("Footwear"),
                    Category("Bags & Backpacks"),
                    Category("Accessories"),
                    Category("Athletic Wear"),
                )
            ),
            Category("Baby & Kids", listOf(
                Category("Boys Clothing"),
                Category("Girls Clothing"),
                Category("Boys Footwear"),
                Category("Girls Footwear"),
                Category("Bath & Skin Care"),
                Category("Accessories"),
                Category("Toys & Games"),
            )),
            Category("Beauty & Care",
                listOf(
                    Category("Skin Care", listOf(
                        Category("Face Wash"),
                        Category("Face Toner"),
                        Category("Face Serum"),
                        Category("Masks & Peels"),
                        Category("Face Moisturiser"),
                        Category("Sunscreen"),
                        Category("Eye Care"),
                        Category("Night Care"),
                        Category("Skincare Kit"),
                    )),
                    Category("Hair Care", listOf(
                        Category("Hair Oil"),
                        Category("Hair Serum"),
                        Category("Hair Gels & Masks"),
                        Category("Shampoo & Conditioner"),
                        Category("Hair Colour"),
                        Category("Hair Spray"),
                        Category("Combs & Hair Brushes"),
                        Category("Hair Appliances"),
                    )),
                    Category("Make-Up & Nails", listOf(
                        Category("Foundation"),
                        Category("Compact"),
                        Category("Concealer"),
                        Category("Face Primer"),
                        Category("Blushes & Highlighter"),
                        Category("Eye Shadows"),
                        Category("Kajal & Eyeliner"),
                        Category("Eyebrow Pencil"),
                        Category("Mascara"),
                        Category("Lipstick"),
                        Category("Nail Polish"),
                        Category("Makeup Removers"),
                        Category("Tools & Accessories"),
                    )),
                    Category("Bath & Body", listOf(
                        Category("Body Washes & Scrubs"),
                        Category("Soaps"),
                        Category("Body Lotions"),
                        Category("Hair Removal"),
                        Category("Hand & Feet Cream"),
                        Category("Body Oil"),
                        Category("Intimate Hygiene"),
                        Category("Period Care")
                    )),
                    Category("Fragrances", listOf(
                        Category("Perfume"),
                        Category("Deodorant & Roll-Ons"),
                        Category("Body Mist")
                    )),
                    Category("Men's Grooming", listOf(
                        Category("Beard Care"),
                        Category("Grooming Kits")
                    )),
                    Category("Oral Care", listOf(
                        Category("Toothpaste & Brush"),
                        Category("Teeth Whitening"),
                        Category("Mouth Washes & Floss")
                    )),
            )),
            Category("Books", listOf(
                Category("Fiction"),
                Category("Textbooks"),
                Category("Children's Books"),
                Category("Indian Language Books")
            )),
            Category("Home & Kitchen", listOf(
                Category("Home Decor", listOf(
                    Category("Showpieces & Idols"),
                    Category("Wall Decor & Clocks"),
                    Category("Lamps & Lights"),
                    Category("Candles & Candle Holders"),
                    Category("Sewing & Craft"),
                    Category("Wallpapers & Stickers"),
                    Category("Pooja Needs"),
                    Category("Artwork"),
                    Category("Artificial Plants")
                )),
                Category("Kitchen & Dining", listOf(
                    Category("Cooking Utensils"),
                    Category("Baking Utensils"),
                    Category("Kitchen Tools & Cutlery"),
                    Category("Glasses, Cups & Barware"),
                    Category("Containers & Tiffins"),
                    Category("Water Bottles"),
                    Category("Dinnerware")
                )),
            )),
            Category("Gadgets", listOf(
                Category("Mobile Phones"),
                Category("Mobile Accessories"),
                Category("Tablets"),
                Category("Computers & Laptops"),
                Category("Cases & Covers"),
                Category("Drives & Storage"),
                Category("Headphones & Earphones"),
                Category("Camera & Photography"),
                Category("E-Books"),
                Category("Office Supplies & Stationery"),
                Category("Fitness Gadgets")
            ))
        )
    }
}
