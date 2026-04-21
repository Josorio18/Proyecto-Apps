package com.example.proyecto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyecto.ui.navigation.Screen
import com.example.proyecto.ui.screens.*
import com.example.proyecto.ui.theme.ProyectoTheme
import com.example.proyecto.ui.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProyectoTheme {
                val navController = rememberNavController()
                val products by viewModel.products.collectAsState()
                val cartItems by viewModel.cartItems.collectAsState()
                val favoriteProducts by viewModel.favoriteProducts.collectAsState()

                NavHost(
                    navController = navController,
                    startDestination = Screen.Splash.route
                ) {
                    composable(Screen.Splash.route) {
                        SplashScreen(onSplashFinished = {
                            navController.navigate(Screen.Home.route) {
                                popUpTo(Screen.Splash.route) { inclusive = true }
                            }
                        })
                    }

                    composable(Screen.Home.route) {
                        HomeScreen(
                            products = products,
                            onProductClick = { productId ->
                                navController.navigate(Screen.Detail.createRoute(productId))
                            },
                            onFavoriteToggle = { id, status -> viewModel.toggleFavorite(id, status) },
                            onCartClick = { navController.navigate(Screen.Cart.route) },
                            onSearchClick = { navController.navigate("search") },
                            onNavigate = { route ->
                                navController.navigate(route) {
                                    popUpTo(Screen.Home.route) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }

                    composable("search") { // Added explicitly for clarity
                        SearchScreen(
                            products = products,
                            onProductClick = { productId ->
                                navController.navigate(Screen.Detail.createRoute(productId))
                            },
                            onFavoriteToggle = { id, status -> viewModel.toggleFavorite(id, status) },
                            onNavigate = { route -> navController.navigate(route) }
                        )
                    }

                    composable(Screen.Detail.route) { backStackEntry ->
                        val productId = backStackEntry.arguments?.getString("productId")
                        val product = products.find { it.id == productId }
                        ProductDetailScreen(
                            product = product,
                            onBackClick = { navController.popBackStack() },
                            onAddToCart = { p, color, size ->
                                viewModel.addToCart(p, color, size)
                                navController.navigate(Screen.Cart.route)
                            },
                            onFavoriteToggle = { id, status -> viewModel.toggleFavorite(id, status) }
                        )
                    }

                    composable(Screen.Cart.route) {
                        CartScreen(
                            cartItems = cartItems,
                            onBackClick = { navController.popBackStack() },
                            onQuantityChange = { item, delta -> viewModel.updateCartQuantity(item, delta) },
                            onRemoveItem = { item -> viewModel.removeFromCart(item) },
                            onCheckout = { viewModel.clearCart() }
                        )
                    }

                    composable(Screen.Favorites.route) {
                        FavoritesScreen(
                            favoriteProducts = favoriteProducts,
                            onProductClick = { productId ->
                                navController.navigate(Screen.Detail.createRoute(productId))
                            },
                            onFavoriteToggle = { id, status -> viewModel.toggleFavorite(id, status) },
                            onNavigate = { route -> navController.navigate(route) }
                        )
                    }

                    composable(Screen.Profile.route) {
                        ProfileScreen(
                            onNavigate = { route -> navController.navigate(route) }
                        )
                    }
                }
            }
        }
    }
}