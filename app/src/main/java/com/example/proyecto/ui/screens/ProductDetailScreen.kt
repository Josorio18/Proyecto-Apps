package com.example.proyecto.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.proyecto.model.Product
import com.example.proyecto.ui.components.PrimaryButton
import com.example.proyecto.ui.theme.Black
import com.example.proyecto.ui.theme.Gray100
import com.example.proyecto.ui.theme.Orange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    product: Product?,
    onBackClick: () -> Unit,
    onAddToCart: (Product, String, Int) -> Unit,
    onFavoriteToggle: (String, Boolean) -> Unit
) {
    if (product == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Producto no encontrado")
        }
        return
    }

    var selectedColor by remember { mutableStateOf(product.colors.first()) }
    var selectedSize by remember { mutableStateOf(product.sizes.first()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(product.name, fontSize = 18.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { onFavoriteToggle(product.id, product.isFavorite) }) {
                        Icon(
                            if (product.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint = if (product.isFavorite) Color.Red else Color.Black
                        )
                    }
                    IconButton(onClick = { /* navigate to cart */ }) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Cart")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Product Image Hero
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
                    .background(Gray100),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = product.imageUrl,
                    contentDescription = product.name,
                    modifier = Modifier.fillMaxSize().padding(32.dp),
                    contentScale = ContentScale.Fit
                )
            }

            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = product.brand.uppercase(),
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.Gray
                    )
                    if (product.isNew) {
                        Surface(color = Orange, shape = RoundedCornerShape(4.dp)) {
                            Text(
                                "NUEVO",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Text(
                    text = product.name,
                    style = MaterialTheme.typography.displayLarge,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "$${product.price}",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Orange,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Color Selection
                Text("COLOR", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
                LazyRow(
                    modifier = Modifier.padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(product.colors) { colorString ->
                        val color = try { Color(android.graphics.Color.parseColor(colorString)) } catch (e: Exception) { Color.Gray }
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(color)
                                .border(
                                    width = if (selectedColor == colorString) 3.dp else 1.dp,
                                    color = if (selectedColor == colorString) Orange else Color.LightGray,
                                    shape = CircleShape
                                )
                                .clickable { selectedColor = colorString }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Size Selection
                Text("TALLA (EU)", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
                LazyRow(
                    modifier = Modifier.padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(product.sizes) { size ->
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (selectedSize == size) Black else Color.White)
                                .border(1.dp, if (selectedSize == size) Black else Color.LightGray, RoundedCornerShape(8.dp))
                                .clickable { selectedSize = size },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = size.toString(),
                                color = if (selectedSize == size) Color.White else Color.Black,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = product.description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.DarkGray,
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(32.dp))

                PrimaryButton(
                    text = "AGREGAR AL CARRITO",
                    onClick = { onAddToCart(product, selectedColor, selectedSize) }
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                OutlinedButton(
                    onClick = { onFavoriteToggle(product.id, product.isFavorite) },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(4.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Black)
                ) {
                    Text("GUARDAR EN FAVORITOS", color = Black, fontWeight = FontWeight.Bold)
                }
                
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}
