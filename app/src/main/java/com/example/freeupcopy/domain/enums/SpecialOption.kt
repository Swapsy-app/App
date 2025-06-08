package com.example.freeupcopy.domain.enums

import com.example.freeupcopy.ui.presentation.sell_screen.SellUiState

enum class SpecialOption(
    val initialLabel: String,
    val valueLabel: String,
    val valueSelector: (SellUiState) -> String?
) {


    FABRIC("Choose Fabric", "Fabric", { it.fabric }),
    COLOUR("Choose Colour", "Colour", { it.color }),
    OCCASION("Choose Occasion", "Occasion", { it.occasion }),
    BRAND("Choose a Brand", "Brand", { it.brand }),

    MODEL_NUMBER("Enter Model Number", "Model Number", { it.modelNumber }),
    INCLUDES("Specify Includes", "Includes", { it.includes }),
    STORAGE_CAPACITY("Enter Storage Capacity", "Storage Capacity", { it.storageCapacity }),
    RAM("Enter RAM", "RAM", { it.ram }),
    BATTERY_CAPACITY("Enter Battery Capacity", "Battery Capacity", { it.batteryCapacity }),
    MOBILE_NETWORK("Select Mobile Network", "Mobile Network", { it.mobileNetwork }),
    SCREEN_SIZE("Enter Screen Size", "Screen Size", { it.screenSize }),
    SIM_TYPE("Select SIM Type", "SIM Type", { it.simType }),
    WARRANTY("Enter Warranty Details", "Warranty", { it.warranty }),

    SIZE("Enter Size", "Size", { "it.size" }),
    SHAPE("Enter Shape", "Shape", { it.shape }),
    LENGTH("Enter Length", "Length", { it.length }),
    EXPIRATION_DATE("Set Expiration Date", "Expiration Date", { it.expirationDate });

//    COLOUR,
//    OCCASION,
//    BRAND,
//
//    MODEL_NUMBER,
//    INCLUDES,
//    STORAGE_CAPACITY,
//    RAM,
//    BATTERY_CAPACITY,
//    MOBILE_NETWORK,
//    SCREEN_SIZE,
//    SIM_TYPE,
//    WARRANTY,
//
//    SIZE,
//    SHAPE,
//    LENGTH,
//    EXPIRATION_DATE
}