package com.example.proyecto.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OrderSuccessScreen(
    onContinueShopping: () -> Unit
) {
    val orderNumber = "NK-${(2000..2025).random()}-${(1000..9999).random()}"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorBg)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        // Green Check Circle
        Box(
            modifier = Modifier
                .size(80.dp)
                .border(3.dp, Color(0xFF4CAF50), CircleShape)
                .background(Color.White, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Success",
                tint = Color(0xFF4CAF50),
                modifier = Modifier.size(50.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Order description blocks (using full text instead of mock blanks for realism as requested)
        Text(
            text = "¡Tu pedido ha sido confirmado!",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = ColorBlackMockup
        )
        Text(
            text = "Gracias por tu compra.",
            color = Color.Gray,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Order Number Badge
        Card(
            modifier = Modifier.fillMaxWidth(0.8f),
            colors = CardDefaults.cardColors(containerColor = ColorBlackMockup),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "N° DE PEDIDO",
                    color = Color.Gray,
                    fontSize = 10.sp,
                    letterSpacing = 1.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "#$orderNumber",
                    color = ColorOrangeMockup,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        // Package icon and info
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Inventory,
                contentDescription = null,
                tint = Color(0xFF8B5A2B), // Brownish representing a box
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = "Tu paquete está siendo preparado.", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text(text = "Entrega estimada: 3-5 días hábiles", color = Color.Gray, fontSize = 12.sp)
            }
        }

        Divider(modifier = Modifier.padding(vertical = 24.dp).fillMaxWidth(), color = Color.LightGray)

        // Email icon and info
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = null,
                tint = Color(0xFF1E88E5), // Blueish representing mail
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = "Confirmación enviada a tu correo.", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Action Buttons
        Button(
            onClick = {}, // Functionality for "Ver Mi Pedido" can be empty/mock for now
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = ColorBlackMockup),
            shape = RoundedCornerShape(4.dp)
        ) {
            Text("VER MI PEDIDO", fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = onContinueShopping,
            modifier = Modifier.fillMaxWidth().height(50.dp),
            border = BorderStroke(1.dp, ColorBlackMockup),
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = ColorBlackMockup)
        ) {
            Text("SEGUIR COMPRANDO", fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
        }
        
        Spacer(modifier = Modifier.height(16.dp))
    }
}
