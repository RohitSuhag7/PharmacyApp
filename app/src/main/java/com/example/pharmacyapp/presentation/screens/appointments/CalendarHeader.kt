package com.example.pharmacyapp.presentation.screens.appointments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pharmacyapp.presentation.theme.LightBlue

@Composable
fun CalendarHeader(
    headerText: String,
    onPreviousMonthClick: () -> Unit,
    onNextMonthClick: () -> Unit,
    isPreviousDisabled: Boolean,
) {
    Row(
        modifier = Modifier
            .padding(bottom = 16.dp)
            .height(80.dp)
            .fillMaxWidth()
            .background(color = LightBlue),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onPreviousMonthClick, enabled = isPreviousDisabled.not()) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "Previous",
                tint = if (isPreviousDisabled) Color.Gray else Color.White,
                modifier = Modifier.size(50.dp)
            )
        }

        Text(text = headerText, fontSize = 25.sp, color = Color.White)

        IconButton(onClick = onNextMonthClick) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Next",
                tint = Color.White,
                modifier = Modifier.size(50.dp)
            )
        }
    }
}
