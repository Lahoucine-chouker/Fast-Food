package com.example.restv2

data class FoodItem(
    val image: Int,
    val price: String,
    val name: String,
    val category: String,
    var quantity: Int // Added quantity
)
