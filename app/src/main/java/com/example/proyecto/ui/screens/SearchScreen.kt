package com.example.proyecto.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyecto.model.Product
import com.example.proyecto.ui.components.ShoeCard
import com.example.proyecto.ui.components.UrbanStepsBottomBar
import com.example.proyecto.ui.theme.Black
import com.example.proyecto.ui.theme.Orange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    products: List<Product>,
    onProductClick: (String) -> Unit,
    onFavoriteToggle: (String, Boolean) -> Unit,
    onNavigate: (String) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var showFilters by remember { mutableStateOf(false) }
    
    // Filter states
    val categories = listOf("Todos", "Running", "Casual", "Jordan", "Escolar", "Deporte")
    var selectedCategory by remember { mutableStateOf("Todos") }
    var selectedSize by remember { mutableStateOf<Int?>(null) }
    var maxPrice by remember { mutableFloatStateOf(300f) }

    val filteredProducts = products.filter { product ->
        val matchesQuery = product.name.contains(searchQuery, ignoreCase = true) || 
                          product.brand.contains(searchQuery, ignoreCase = true)
        val matchesCategory = selectedCategory == "Todos" || product.category == selectedCategory
        val matchesSize = selectedSize == null || product.sizes.contains(selectedSize!!)
        val matchesPrice = product.price <= maxPrice

        matchesQuery && matchesCategory && matchesSize && matchesPrice
    }

    val sheetState = rememberModalBottomSheetState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mundo UrbanSteps", fontWeight = FontWeight.Black, fontSize = 24.sp) },
                actions = {
                    Box {
                        IconButton(onClick = { showFilters = true }) {
                            BadgedBox(badge = {
                                if (selectedCategory != "Todos" || selectedSize != null || maxPrice < 300f) {
                                    Badge(containerColor = Orange)
                                }
                            }) {
                                Icon(Icons.Default.FilterList, contentDescription = "Filter")
                            }
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Black, 
                    titleContentColor = Color.White, 
                    actionIconContentColor = Color.White
                )
            )
        },
        bottomBar = {
            UrbanStepsBottomBar(currentRoute = "search", onNavigate = onNavigate)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            // Animated Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("¿Qué estás buscando?") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Orange) },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(Icons.Default.Clear, contentDescription = "Clear")
                        }
                    }
                },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Orange,
                    unfocusedBorderColor = Color.LightGray,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                singleLine = true
            )

            // Current Applied Filters Display
            if (selectedCategory != "Todos" || selectedSize != null) {
                LazyRow(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (selectedCategory != "Todos") {
                        item { ActiveFilterChip(text = selectedCategory) { selectedCategory = "Todos" } }
                    }
                    if (selectedSize != null) {
                        item { ActiveFilterChip(text = "Talla $selectedSize") { selectedSize = null } }
                    }
                }
            }

            // Results Heading
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                val resultsText = if (searchQuery.isEmpty()) {
                    if (selectedCategory != "Todos") selectedCategory else "Todos los productos"
                } else {
                    "Resultados para '$searchQuery'"
                }
                
                Text(
                    text = resultsText,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${filteredProducts.size} items",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.Gray
                )
            }

            // Results Grid
            if (filteredProducts.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("No encontramos lo que buscas", style = MaterialTheme.typography.bodyLarge)
                        TextButton(onClick = {
                            searchQuery = ""
                            selectedCategory = "Todos"
                            selectedSize = null
                            maxPrice = 300f
                        }) {
                            Text("Limpiar todos los filtros", color = Orange)
                        }
                    }
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp),
                    contentPadding = PaddingValues(bottom = 80.dp)
                ) {
                    items(filteredProducts) { product ->
                        ShoeCard(
                            product = product,
                            onClick = { onProductClick(product.id) },
                            onFavoriteClick = { onFavoriteToggle(product.id, product.isFavorite) }
                        )
                    }
                }
            }
        }
    }

    // Modern Filtering Bottom Sheet
    if (showFilters) {
        ModalBottomSheet(
            onDismissRequest = { showFilters = false },
            sheetState = sheetState,
            containerColor = Color.White,
            dragHandle = { BottomSheetDefaults.DragHandle(color = Color.LightGray) }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 48.dp)
            ) {
                Text("Filtrar por", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Black)
                Spacer(modifier = Modifier.height(24.dp))

                // Category Section
                Text("CATEGORÍA", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold, color = Color.Gray)
                LazyRow(
                    modifier = Modifier.padding(vertical = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(categories) { category ->
                        FilterChip(
                            name = category,
                            isSelected = selectedCategory == category
                        ) {
                            selectedCategory = category
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Size Section
                Text("TALLA (EU)", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold, color = Color.Gray)
                val sizes = (38..46).toList()
                LazyRow(
                    modifier = Modifier.padding(vertical = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(sizes) { size ->
                        SizeFilterChip(
                            size = size,
                            isSelected = selectedSize == size
                        ) {
                            selectedSize = if (selectedSize == size) null else size
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Price Section
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("PRECIO MÁXIMO", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold, color = Color.Gray)
                    Text("$${maxPrice.toInt()}", fontWeight = FontWeight.Bold, color = Orange)
                }
                Slider(
                    value = maxPrice,
                    onValueChange = { maxPrice = it },
                    valueRange = 0f..300f,
                    colors = SliderDefaults.colors(
                        thumbColor = Black,
                        activeTrackColor = Orange,
                        inactiveTrackColor = Color.LightGray
                    )
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = { showFilters = false },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Black)
                ) {
                    Text("VER ${filteredProducts.size} RESULTADOS", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun FilterChip(name: String, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        color = if (isSelected) Black else Color(0xFFF5F5F5),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Text(
            text = name,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
            color = if (isSelected) Color.White else Black,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
    }
}

@Composable
fun SizeFilterChip(size: Int, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        color = if (isSelected) Black else Color.White,
        border = if (isSelected) null else androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.size(48.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = size.toString(),
                color = if (isSelected) Color.White else Black,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun ActiveFilterChip(text: String, onRemove: () -> Unit) {
    Surface(
        color = Orange.copy(alpha = 0.1f),
        shape = CircleShape,
        border = androidx.compose.foundation.BorderStroke(1.dp, Orange)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text, color = Orange, fontWeight = FontWeight.Bold, fontSize = 12.sp)
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                Icons.Default.Clear,
                contentDescription = null,
                modifier = Modifier.size(14.dp).clickable { onRemove() },
                tint = Orange
            )
        }
    }
}
