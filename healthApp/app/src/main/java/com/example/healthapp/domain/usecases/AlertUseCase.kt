package com.example.healthapp.domain.usecases

import com.example.healthapp.data.models.HealthIndicator

class AlertUseCase {
    fun isAlert(data: HealthIndicator): Boolean {
        return data.temperature > 37.5 ||
                data.heartRate < 60 || data.heartRate > 100 ||
                data.systolicBP > 140 || data.diastolicBP > 90
    }

    fun generateAlerts(data: HealthIndicator): List<String> {
        val alerts = mutableListOf<String>()
        if (data.temperature > 37.5) {
            alerts.add("体温异常警告\n目前您的体温已达到设备上限")
        }
        if (data.heartRate > 100) {
            alerts.add("心率异常警告\n目前您的心率已达到设备的心率上限")
        }
        if (data.oxygenLevel < 88) {
            alerts.add("血氧浓度异常警告\n目前您的血氧浓度低于设备的设定值")
        }
        if (data.systolicBP > 140) {
            alerts.add("血压异常警告\n目前您的高压值已超过正常范围")
        }
        return alerts
    }
}