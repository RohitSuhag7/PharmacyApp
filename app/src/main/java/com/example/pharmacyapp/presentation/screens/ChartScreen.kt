package com.example.pharmacyapp.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pharmacyapp.R
import com.example.pharmacyapp.utils.CustomChart

@Composable
fun ChartScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CustomChart(
            barValue = listOf(0.5f, 0.6f, 0.2f, 0.7f, 0.8f, 0.10f, 0.4f),
            xAxisScale = listOf("A", "B", "C", "D", "E", "F", "G"),
            totalAmount = 100
        )

        CardWithTextField(
            valueText = stringResource(R.string.pharmacies_rating),
            trailingValueText = "4.7"
        )
        CardWithTextField(
            valueText = stringResource(R.string.doctors_rating),
            trailingValueText = "4.5"
        )
        CardWithTextField(
            valueText = stringResource(R.string.medicines_rating),
            trailingValueText = "4.6"
        )
    }
}

@Composable
fun CardWithTextField(
    valueText: String,
    trailingValueText: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.elevatedCardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        TextField(
            readOnly = true,
            value = valueText,
            onValueChange = {
                // Blank because of read only
            },
            trailingIcon = {
                Text(
                    text = trailingValueText,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            textStyle = androidx.compose.ui.text.TextStyle(
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}
