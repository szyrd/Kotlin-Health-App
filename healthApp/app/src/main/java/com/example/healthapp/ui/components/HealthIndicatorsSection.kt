package com.example.healthapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.healthapp.data.models.HealthIndicator

@Composable
fun HealthIndicatorsSection(data: HealthIndicator, isAlert: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Title
        Text(
            text = "各项指标",
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // First Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            IndicatorCard(
                title = "我的体温",
                value = AnnotatedString("${data.temperature}°C"),
                status = if (data.temperature > 37.5) "异常：偏高" else "正常",
                isAlert = data.temperature > 37.5,
                modifier = Modifier.weight(1f)
            )
            IndicatorCard(
                title = "实时心率",
                value = AnnotatedString("${data.heartRate} bpm"),
                status = if (data.heartRate < 60 || data.heartRate > 100) "异常：偏高" else "正常",
                isAlert = data.heartRate < 60 || data.heartRate > 100,
                modifier = Modifier.weight(1f)
            )
        }

        // Second Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            IndicatorCard(
                title = "血氧浓度",
                value = AnnotatedString("${data.oxygenLevel} SPO₂"),
                status = "正常",
                isAlert = false,
                modifier = Modifier.weight(1f)
            )
            IndicatorCard(
                title = "我的血压",
                value = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontSize = 12.sp)) { append("高压值: ") }
                    append("${data.systolicBP} mmHg\n")
                    withStyle(style = SpanStyle(fontSize = 12.sp)) { append("低压值: ") }
                    append("${data.diastolicBP} mmHg")
                },
                status = when {
                    data.systolicBP > 140 && data.diastolicBP > 90 -> "异常：高压值偏高\n异常：低压值偏高"
                    data.systolicBP > 140 -> "异常：高压值偏高"
                    data.diastolicBP > 90 -> "异常：低压值偏高"
                    else -> "正常"
                },
                isAlert = data.systolicBP > 140 || data.diastolicBP > 90,
                modifier = Modifier.weight(1f)
            )
        }
    }
}








