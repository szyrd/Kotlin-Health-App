package com.example.healthapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.healthapp.ui.components.ActionButtons
import com.example.healthapp.ui.components.AlertPopup
import com.example.healthapp.ui.components.BottomNavigationBar
import com.example.healthapp.ui.components.HeaderSection
import com.example.healthapp.ui.components.HealthIndicatorsSection
import com.example.healthapp.viewmodel.MainViewModel

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val uiState = viewModel.uiState.collectAsState()

    Scaffold(
        bottomBar = { BottomNavigationBar() }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                HeaderSection()
                Spacer(modifier = Modifier.height(16.dp))
                ActionButtons()
                Spacer(modifier = Modifier.height(16.dp))
                HealthIndicatorsSection(
                    data = uiState.value.data,
                    isAlert = uiState.value.alertQueue.isNotEmpty()
                )
            }

            // Display the first alert in the queue
            if (uiState.value.alertQueue.isNotEmpty()) {
                AlertPopup(
                    alertMessage = uiState.value.alertQueue.first(), // Show the first alert
                    onDismiss = { viewModel.removeFirstAlert() } // Remove the alert after dismissal
                )
            }
        }
    }
}







