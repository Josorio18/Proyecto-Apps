package com.example.proyecto.model

data class Product(
    val id: String,
    val name: String,
    val brand: String,
    val price: Double,
    val imageUrl: String,
    val description: String,
    val category: String,
    val colors: List<String>,
    val sizes: List<Int>,
    val isFavorite: Boolean = false,
    val isNew: Boolean = false
)
