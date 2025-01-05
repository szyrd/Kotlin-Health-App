package com.example.healthapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.healthapp.ui.screens.MainScreen
import com.example.healthapp.ui.theme.HealthAppTheme
import com.example.healthapp.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HealthAppTheme {
                MainScreen(viewModel = MainViewModel())
            }
        }
    }
}
