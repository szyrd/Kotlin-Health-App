package com.example.healthapp.ui.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.material3.*

@Composable
fun BottomNavigationBar() {
    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.primary, // Set the background color to blue
        contentColor = MaterialTheme.colorScheme.onPrimary // Set the content color for icons and text
    ) {
        BottomNavigationItem(
            selected = true,
            onClick = { /* Navigate to Home */ },
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            label = { Text("首页") }
        )
        BottomNavigationItem(
            selected = false,
            onClick = { /* Navigate to Data */ },
            icon = { Icon(Icons.Filled.BarChart, contentDescription = "Data") },
            label = { Text("数据") }
        )
        BottomNavigationItem(
            selected = false,
            onClick = { /* Navigate to Profile */ },
            icon = { Icon(Icons.Filled.Person, contentDescription = "Profile") },
            label = { Text("个人") }
        )
    }
}

