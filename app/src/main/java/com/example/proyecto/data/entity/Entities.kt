package com.example.proyecto.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey val id: String,
    val name: String,
    val brand: String,
    val price: Double,
    val imageUrl: String,
    val description: String,
    val category: String, // Running, Jordan, School, etc.
    val colors: String, // Comma separated hex values
    val sizes: String, // Comma separated integers
    val isFavorite: Boolean = false,
    val isNew: Boolean = false
)

@Entity(tableName = "cart_items")
data class CartItemEntity(
    @PrimaryKey(autoGenerate = true) val cartId: Int = 0,
    val productId: String,
    val name: String,
    val price: Double,
    val imageUrl: String,
    val selectedColor: String,
    val selectedSize: Int,
    var quantity: Int = 1
)
