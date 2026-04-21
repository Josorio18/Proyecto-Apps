package com.example.proyecto.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyecto.ui.theme.Black
import com.example.proyecto.ui.theme.Orange
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onSplashFinished: () -> Unit) {
    LaunchedEffect(key1 = true) {
        delay(2500) // Simulated loading
        onSplashFinished()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Logo Bubble
        Box(
            modifier = Modifier
                .border(2.dp, Orange, RoundedCornerShape(50.dp))
                .padding(horizontal = 24.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "URBAN",
                color = Orange,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Brand Name
        Text(
            text = "URBANSTEPS",
            color = Color.White,
            style = MaterialTheme.typography.displayLarge,
            letterSpacing = 2.sp
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Loading State
        Text(
            text = "CARGANDO...",
            color = Color.Gray,
            letterSpacing = 4.sp,
            fontSize = 18.sp
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        LinearProgressIndicator(
            modifier = Modifier
                .width(150.dp)
                .height(4.dp),
            color = Orange,
            trackColor = Color.DarkGray
        )
    }
}
