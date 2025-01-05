package com.example.healthapp.data.repository

import com.example.healthapp.data.models.HealthIndicator
import kotlin.random.Random

class HealthDataRepository {
    fun generateRandomData(): HealthIndicator {
        return HealthIndicator(
            temperature = Random.nextFloat() * (38.5f - 36.0f) + 36.0f,
            heartRate = Random.nextInt(50, 200),
            systolicBP = Random.nextInt(100, 160),
            diastolicBP = Random.nextInt(60, 100),
            oxygenLevel = Random.nextInt(90, 100)
        )
    }
}