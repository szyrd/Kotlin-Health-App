package com.example.healthapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthapp.data.models.HealthIndicator
import com.example.healthapp.data.repository.HealthDataRepository
import com.example.healthapp.domain.usecases.AlertUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: HealthDataRepository = HealthDataRepository(),
    private val alertUseCase: AlertUseCase = AlertUseCase()
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    private val alertQueue = mutableListOf<String>() // Internal queue for alerts

    init {
        startDataUpdates()
    }

    private fun startDataUpdates() {
        viewModelScope.launch {
            while (true) {
                val newData = repository.generateRandomData()
                val newAlerts = mutableListOf<String>()

                if (newData.temperature > 37.5) {
                    newAlerts.add("体温异常警告\n目前您的体温已达到设备上限")
                }
                if (newData.heartRate > 100) {
                    newAlerts.add("心率异常警告\n目前您的心率已达到设备的心率上限")
                }
                if (newData.oxygenLevel < 88) {
                    newAlerts.add("血氧浓度异常警告\n目前您的血氧浓度低于设备的设定值")
                }
                if (newData.systolicBP > 140) {
                    newAlerts.add("血压异常警告\n目前您的高压值已超过正常范围")
                }

                _uiState.value = UiState(
                    data = newData,
                    isAlert = newAlerts.isNotEmpty(),
                    alertQueue = newAlerts
                )
                delay(5000)
            }
        }
    }


    /**
     * Processes the health data, checks for alerts, and updates the UI state.
     */
    private fun processHealthData(data: HealthIndicator) {
        val newAlerts = alertUseCase.generateAlerts(data)

        // Add new alerts to the queue
        alertQueue.addAll(newAlerts)

        // Update the UI state with new data and check if there are any alerts
        _uiState.value = _uiState.value.copy(
            data = data,
            isAlert = alertQueue.isNotEmpty() // True if the queue is not empty
        )

        // Automatically remove the first alert after 2 seconds
        if (alertQueue.isNotEmpty()) {
            viewModelScope.launch {
                delay(2000) // Wait for 2 seconds
                removeFirstAlert()
            }
        }
    }

    /**
     * Removes the first alert from the queue and updates the UI state.
     */
    fun removeFirstAlert() {
        val currentQueue = _uiState.value.alertQueue
        if (currentQueue.isNotEmpty()) {
            _uiState.value = _uiState.value.copy(
                alertQueue = currentQueue.drop(1) // Remove the first alert
            )
        }
    }


    data class UiState(
        val data: HealthIndicator = HealthIndicator(37.0f, 70, 115, 70, 95),
        val isAlert: Boolean = false,
        val alertQueue: List<String> = emptyList()
    )
}
