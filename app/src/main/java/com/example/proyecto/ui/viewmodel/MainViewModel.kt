package com.example.proyecto.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto.data.AppDatabase
import com.example.proyecto.data.AppRepository
import com.example.proyecto.data.entity.CartItemEntity
import com.example.proyecto.model.Product
import com.example.proyecto.notification.NotificationManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: AppRepository
    private val notificationManager: NotificationManager

    init {
        val database = AppDatabase.getDatabase(application)
        repository = AppRepository(database.productDao(), database.cartDao())
        notificationManager = NotificationManager(application)
        
        viewModelScope.launch {
            repository.seedDatabaseIfEmpty()
        }
    }

    val products: StateFlow<List<Product>> = repository.allProducts
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val cartItems: StateFlow<List<CartItemEntity>> = repository.cartItems
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val favoriteProducts: StateFlow<List<Product>> = repository.getFavoriteProducts()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun toggleFavorite(productId: String, currentStatus: Boolean) {
        viewModelScope.launch {
            val newStatus = !currentStatus
            repository.updateFavoriteStatus(productId, newStatus)
            
            val product = products.value.find { it.id == productId }
            product?.let {
                notificationManager.showFavoriteNotification(it.name, newStatus)
            }
        }
    }

    fun addToCart(product: Product, color: String, size: Int) {
        viewModelScope.launch {
            val existingItem = cartItems.value.find { 
                it.productId == product.id && it.selectedColor == color && it.selectedSize == size 
            }
            if (existingItem != null) {
                repository.updateCartItem(existingItem.copy(quantity = existingItem.quantity + 1))
            } else {
                repository.addToCart(
                    CartItemEntity(
                        productId = product.id,
                        name = product.name,
                        price = product.price,
                        imageUrl = product.imageUrl,
                        selectedColor = color,
                        selectedSize = size
                    )
                )
                notificationManager.showAddToCartNotification(product.name)
            }
        }
    }

    fun removeFromCart(item: CartItemEntity) {
        viewModelScope.launch {
            repository.removeFromCart(item)
        }
    }

    fun updateCartQuantity(item: CartItemEntity, delta: Int) {
        viewModelScope.launch {
            val newQuantity = item.quantity + delta
            if (newQuantity > 0) {
                repository.updateCartItem(item.copy(quantity = newQuantity))
            } else {
                repository.removeFromCart(item)
            }
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            repository.clearCart()
        }
    }

    fun showPurchaseSuccessNotification(totalAmount: Double, itemCount: Int) {
        notificationManager.showPurchaseSuccessNotification(totalAmount, itemCount)
    }

    fun showWelcomeNotification() {
        notificationManager.showWelcomeNotification()
    }

    fun showSpecialOfferNotification(discount: Int, productName: String) {
        notificationManager.showSpecialOfferNotification(discount, productName)
    }

    fun showNewProductNotification(productName: String, category: String) {
        notificationManager.showNewProductNotification(productName, category)
    }

    fun showLowStockNotification(productName: String, remainingStock: Int) {
        notificationManager.showLowStockNotification(productName, remainingStock)
    }
}
