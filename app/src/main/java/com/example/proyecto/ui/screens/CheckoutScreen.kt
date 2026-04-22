package com.example.proyecto.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyecto.data.entity.CartItemEntity

val ColorBg = Color(0xFFF3F3ED)
val ColorSurface = Color(0xFFEBEBE5)
val ColorOrangeMockup = Color(0xFFFF5202)
val ColorBlackMockup = Color(0xFF1E1E1E)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    cartItems: List<CartItemEntity>,
    onBackClick: () -> Unit,
    onPaymentSuccess: () -> Unit
) {
    var currentStep by remember { mutableStateOf(1) }
    
    // Address fields
    var fullName by remember { mutableStateOf("Juan Pérez") }
    var address by remember { mutableStateOf("Calle 123 #45-67") }
    var city by remember { mutableStateOf("Bogotá, Colombia") }

    // Payment fields
    var cardNumber by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }

    val subtotal = cartItems.sumOf { it.price * it.quantity }
    val isFormValid = if (currentStep == 1) {
        fullName.isNotBlank() && address.isNotBlank() && city.isNotBlank()
    } else {
        cardNumber.length >= 15 && expiryDate.length >= 4 && cvv.length >= 3
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text("Finalizar Compra", fontWeight = FontWeight.Medium, fontSize = 18.sp, modifier = Modifier.padding(end = 48.dp)) 
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        if (currentStep == 2) currentStep = 1 else onBackClick()
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = ColorBlackMockup, 
                    titleContentColor = Color.White, 
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(ColorBg)
        ) {
            // STEPPER
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp, horizontal = 32.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StepItem("1", "DIRECCIÓN", isActive = currentStep >= 1)
                Divider(modifier = Modifier.weight(1f).padding(horizontal = 8.dp), color = Color.LightGray)
                StepItem("2", "PAGO", isActive = currentStep >= 2)
                Divider(modifier = Modifier.weight(1f).padding(horizontal = 8.dp), color = Color.LightGray)
                StepItem("3", "CONFIRMAR", isActive = currentStep == 3)
            }

            Divider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))

            Spacer(modifier = Modifier.height(24.dp))

            if (currentStep == 1) {
                // STEP 1: DIRECCIÓN
                Column(modifier = Modifier.padding(horizontal = 16.dp).weight(1f)) {
                    Text(
                        text = "DIRECCIÓN DE ENVÍO", 
                        fontSize = 12.sp, 
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray,
                        letterSpacing = 1.sp,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    // Selected Address Card
                    OutlinedCard(
                        modifier = Modifier.fillMaxWidth(),
                        border = androidx.compose.foundation.BorderStroke(1.dp, ColorOrangeMockup),
                        colors = CardDefaults.outlinedCardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Icon(Icons.Default.Place, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = fullName, fontWeight = FontWeight.Bold, color = ColorBlackMockup)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(text = address, color = Color.Gray, fontSize = 14.sp)
                                Text(text = "$city - Envío a Domicilio", color = Color.Gray, fontSize = 14.sp)
                            }
                            Icon(Icons.Default.CheckCircle, contentDescription = "Selected", tint = ColorOrangeMockup)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Add Address Button
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(8.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null, tint = Color.Black)
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(text = "AGREGAR DIRECCIÓN", fontWeight = FontWeight.Bold, fontSize = 12.sp, letterSpacing = 1.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Text(
                        text = "RESUMEN DEL PEDIDO", 
                        fontSize = 12.sp, 
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray,
                        letterSpacing = 1.sp,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    // Summary Card
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = ColorSurface),
                        shape = RoundedCornerShape(8.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Artículos", color = Color.Gray)
                                Text("$${String.format("%.2f", subtotal)}", fontWeight = FontWeight.Bold)
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Envío estándar", color = Color.Gray)
                                Text("GRATIS", color = Color(0xFF4CAF50), fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }

                // Bottom Button Step 1
                Box(modifier = Modifier.padding(16.dp)) {
                    Button(
                        onClick = { currentStep = 2 },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = ColorOrangeMockup),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text("CONTINUAR → PAGO", fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                    }
                }
            } else if (currentStep == 2) {
                // STEP 2: PAGO
                Column(modifier = Modifier.padding(horizontal = 16.dp).weight(1f)) {
                    Text(
                        text = "MÉTODO DE PAGO", 
                        fontSize = 12.sp, 
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray,
                        letterSpacing = 1.sp,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            OutlinedTextField(
                                value = cardNumber,
                                onValueChange = { if (it.length <= 16) cardNumber = it.filter { c -> c.isDigit() } },
                                label = { Text("Número de Tarjeta") },
                                modifier = Modifier.fillMaxWidth(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                trailingIcon = { Icon(Icons.Default.CreditCard, contentDescription = null, tint = Color.Gray) }
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                OutlinedTextField(
                                    value = expiryDate,
                                    onValueChange = { if (it.length <= 5) expiryDate = it },
                                    label = { Text("MM/YY") },
                                    modifier = Modifier.weight(1f),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                                )
                                OutlinedTextField(
                                    value = cvv,
                                    onValueChange = { if (it.length <= 4) cvv = it.filter { c -> c.isDigit() } },
                                    label = { Text("CVV") },
                                    modifier = Modifier.weight(1f),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                                )
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Final Total
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = ColorBlackMockup),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text("Total a pagar", color = Color.White, fontSize = 16.sp)
                            Text("$${String.format("%.2f", subtotal)}", color = ColorOrangeMockup, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        }
                    }
                }

                // Bottom Button Step 2
                Box(modifier = Modifier.padding(16.dp)) {
                    Button(
                        onClick = onPaymentSuccess,
                        enabled = isFormValid,
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = ColorOrangeMockup),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text("FINALIZAR PEDIDO", fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun StepItem(number: String, label: String, isActive: Boolean) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .background(if (isActive) ColorBlackMockup else Color.LightGray, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(text = number, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label, 
            fontSize = 10.sp, 
            fontWeight = FontWeight.Bold,
            color = if (isActive) ColorBlackMockup else Color.Gray
        )
    }
}
