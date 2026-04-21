package com.example.proyecto.data

import com.example.proyecto.data.dao.CartDao
import com.example.proyecto.data.dao.ProductDao
import com.example.proyecto.data.entity.CartItemEntity
import com.example.proyecto.data.entity.ProductEntity
import com.example.proyecto.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AppRepository(
    private val productDao: ProductDao,
    private val cartDao: CartDao
) {
    val allProducts: Flow<List<Product>> = productDao.getAllProducts().map { entities ->
        entities.map { it.toDomain() }
    }

    val cartItems: Flow<List<CartItemEntity>> = cartDao.getCartItems()

    fun getProductsByCategory(category: String): Flow<List<Product>> = 
        productDao.getProductsByCategory(category).map { entities ->
            entities.map { it.toDomain() }
        }

    fun getFavoriteProducts(): Flow<List<Product>> = 
        productDao.getFavoriteProducts().map { entities ->
            entities.map { it.toDomain() }
        }

    suspend fun getProductById(id: String): Product? = 
        productDao.getProductById(id)?.toDomain()

    suspend fun updateFavoriteStatus(id: String, isFavorite: Boolean) {
        productDao.updateFavoriteStatus(id, isFavorite)
    }

    suspend fun addToCart(item: CartItemEntity) {
        cartDao.addToCart(item)
    }

    suspend fun updateCartItem(item: CartItemEntity) {
        cartDao.updateCartItem(item)
    }

    suspend fun removeFromCart(item: CartItemEntity) {
        cartDao.removeFromCart(item)
    }

    suspend fun clearCart() {
        cartDao.clearCart()
    }

    suspend fun seedDatabaseIfEmpty() {
        // Catálogo Final de 12 Productos (100% Locales para uso Offline)
        val initialProducts = listOf(
            ProductEntity(
                id = "1",
                name = "Nike Air Max 270",
                brand = "Nike",
                price = 160.0,
                imageUrl = "android.resource://com.example.proyecto/drawable/shoe_air_max_270",
                description = "Siente el aire bajo tus pies con la gran cámara de aire de los 270.",
                category = "Running",
                colors = "#000000,#00BCD4,#FF5722",
                sizes = "38,39,40,41,42,43,44",
                isNew = true
            ),
            ProductEntity(
                id = "2",
                name = "Nike Air Force 1 '07",
                brand = "Nike",
                price = 120.0,
                imageUrl = "android.resource://com.example.proyecto/drawable/shoe_air_force_1",
                description = "El mito sigue vivo con el estilo original de las Force 1.",
                category = "Casual",
                colors = "#FFFFFF,#000000",
                sizes = "36,37,38,39,40,41,42,43,44,45",
                isNew = false
            ),
            ProductEntity(
                id = "3",
                name = "Air Jordan 1 Mid",
                brand = "Jordan",
                price = 140.0,
                imageUrl = "android.resource://com.example.proyecto/drawable/shoe_jordan_1",
                description = "Lleva el legado de MJ a las calles con el estilo inconfundible de Jordan.",
                category = "Jordan",
                colors = "#FF0000,#000000,#FFFFFF",
                sizes = "40,41,42,43,44,45,46",
                isNew = true
            ),
            ProductEntity(
                id = "4",
                name = "Nike Dunk Low Retro",
                brand = "Nike",
                price = 110.0,
                imageUrl = "android.resource://com.example.proyecto/drawable/shoe_dunk_low",
                description = "Icono del baloncesto de los 80, ahora para tu estilo diario.",
                category = "Casual",
                colors = "#000000,#FFFFFF,#4CAF50",
                sizes = "39,40,41,42,43,44",
                isNew = true
            ),
            ProductEntity(
                id = "5",
                name = "Adidas Ultraboost Light",
                brand = "Adidas",
                price = 190.0,
                imageUrl = "android.resource://com.example.proyecto/drawable/shoe_ultraboost",
                description = "La energía épica de Ultraboost en un diseño ultraligero.",
                category = "Running",
                colors = "#000000,#FFFFFF,#FFEB3B",
                sizes = "38,39,40,41,42,43,44,45",
                isNew = false
            ),
            ProductEntity(
                id = "6",
                name = "Nike Blazer Mid '77",
                brand = "Nike",
                price = 100.0,
                imageUrl = "android.resource://com.example.proyecto/drawable/shoe_blazer_mid",
                description = "Estilo vintage para el futuro de la moda urbana.",
                category = "Casual",
                colors = "#FFFFFF,#FF5722,#2196F3",
                sizes = "37,38,39,40,41,42",
                isNew = false
            ),
            ProductEntity(
                id = "7",
                name = "Air Jordan 4 Retro",
                brand = "Jordan",
                price = 210.0,
                imageUrl = "android.resource://com.example.proyecto/drawable/shoe_jordan_4",
                description = "Un clásico atemporal con detalles premium y soporte lateral.",
                category = "Jordan",
                colors = "#808080,#FFFFFF,#000000",
                sizes = "40,41,42,43,44,45",
                isNew = true
            ),
            ProductEntity(
                id = "8",
                name = "Nike Pegasus 40",
                brand = "Nike",
                price = 130.0,
                imageUrl = "android.resource://com.example.proyecto/drawable/shoe_pegasus_40",
                description = "Amortiguación reactiva para tus carreras diarias más exigentes.",
                category = "Running",
                colors = "#FB8C00,#000000,#03A9F4",
                sizes = "38,39,40,41,42,43,44,45,46",
                isNew = true
            ),
            ProductEntity(
                id = "9",
                name = "Zapato Escolar Clásico",
                brand = "UrbanSteps",
                price = 85.0,
                imageUrl = "android.resource://com.example.proyecto/drawable/shoe_school_classic",
                description = "Zapato escolar de cuero negro de alta durabilidad, ideal para el uso diario en el colegio.",
                category = "Escolar",
                colors = "#000000",
                sizes = "36,37,38,39,40,41,42",
                isNew = true
            ),
            ProductEntity(
                id = "10",
                name = "Mocasín Escolar Confort",
                brand = "UrbanSteps",
                price = 90.0,
                imageUrl = "android.resource://com.example.proyecto/drawable/shoe_school_loafer",
                description = "Estilo y comodidad en un solo zapato. Cuero flexible y suela antideslizante.",
                category = "Escolar",
                colors = "#000000",
                sizes = "36,37,38,39,40,41,42",
                isNew = true
            ),
            ProductEntity(
                id = "11",
                name = "Botas Urban Coffee",
                brand = "UrbanSteps",
                price = 145.0,
                imageUrl = "android.resource://com.example.proyecto/drawable/shoe_casual_boots",
                description = "Botas de cuero estilo vintage, perfectas para climas fríos y senderismo urbano.",
                category = "Casual",
                colors = "#795548",
                sizes = "40,41,42,43,44,45",
                isNew = false
            ),
            ProductEntity(
                id = "12",
                name = "Football Pro Neon",
                brand = "UrbanSteps",
                price = 120.0,
                imageUrl = "android.resource://com.example.proyecto/drawable/shoe_football_pro",
                description = "Botines de fútbol de alto rendimiento con tracción superior para césped artificial.",
                category = "Deporte",
                colors = "#CCFF00,#000000",
                sizes = "38,39,40,41,42,43,44",
                isNew = true
            )
        )
        productDao.insertProducts(initialProducts)
    }

    private fun ProductEntity.toDomain(): Product {
        return Product(
            id = id,
            name = name,
            brand = brand,
            price = price,
            imageUrl = imageUrl,
            description = description,
            category = category,
            colors = colors.split(","),
            sizes = sizes.split(",").map { it.toInt() },
            isFavorite = isFavorite,
            isNew = isNew
        )
    }
}
