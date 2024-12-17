package com.example.freeupcopy.domain.model

data class FilterSection(
    val name : String,
    val onCLick : () -> Unit,
    val isSelected : Boolean,
    val options : List<FilterOption>
)

data class  FilterOption(
    val name : String,
    val onCLick: () -> Unit,
    val isSelected: Boolean
)