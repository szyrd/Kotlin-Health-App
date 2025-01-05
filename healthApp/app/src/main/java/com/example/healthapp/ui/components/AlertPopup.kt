package com.example.healthapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay


@Composable
fun AlertPopup(alertMessage: String, onDismiss: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize() // This ensures the Box fills the available space
    ) {
        Card(
            modifier = Modifier
                .align(Alignment.TopCenter) // Proper alignment for the Card
                .padding(horizontal = 16.dp, vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFDE0E0E)),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = alertMessage.split("\n")[0], // Title (e.g., "体温异常警告")
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = alertMessage.split("\n")[1], // Detailed message
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.White)
                )
            }
        }
    }

    // Automatically dismiss the alert after 2 seconds
    LaunchedEffect(alertMessage) {
        delay(2000)
        onDismiss()
    }
}






