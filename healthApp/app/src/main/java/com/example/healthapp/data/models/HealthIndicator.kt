package com.example.healthapp.data.models

data class HealthIndicator(
    val temperature: Float,
    val heartRate: Int,
    val systolicBP: Int,
    val diastolicBP: Int,
    val oxygenLevel: Int
)