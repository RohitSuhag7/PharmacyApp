package com.example.pharmacyapp.utils

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.pharmacyapp.presentation.theme.LightBlue

@Composable
fun CustomChart(
    barValue: List<Float>,
    xAxisScale: List<String>,
    totalAmount: Int
) {
    val context = LocalContext.current

    val barGraphHeight by remember { mutableStateOf(200.dp) }
    val barGraphWidth by remember { mutableStateOf(20.dp) }

    val scaleYAxisWidth by remember { mutableStateOf(50.dp) }
    val scaleLineWidth by remember { mutableStateOf(2.dp) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.elevatedCardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(barGraphHeight)
                .padding(top = 16.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Start
        ) {
            // scale Y-Axis
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(scaleYAxisWidth),
                contentAlignment = Alignment.BottomCenter
            ) {
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(text = totalAmount.toString())
                    Spacer(modifier = Modifier.fillMaxHeight())
                }

                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(text = (totalAmount / 2).toString())
                    Spacer(modifier = Modifier.fillMaxHeight(0.5f))
                }
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(text = "0")
                    Spacer(modifier = Modifier.fillMaxHeight(0.0f))
                }
            }

            // Y-Axis Line
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(scaleLineWidth)
                    .background(LightBlue)
            )

            // Graph
            barValue.forEach {
                Box(
                    modifier = Modifier
                        .padding(
                            start = barGraphWidth,
                            bottom = 5.dp
                        )
                        .width(barGraphWidth)
                        .fillMaxHeight(it)
                        .background(LightBlue)
                        .clickable {
                            Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
                        }
                )
            }
        }

        // X-Axis Line
        Box(
            modifier = Modifier
                .padding(
                    start = scaleYAxisWidth + scaleLineWidth,
                    end = 16.dp
                )
                .fillMaxWidth()
                .height(scaleLineWidth)
                .background(LightBlue)
        )

        // Scale X-Axis
        Row(
            modifier = Modifier
                .padding(
                    start = scaleYAxisWidth + barGraphWidth + scaleLineWidth,
                    bottom = 16.dp
                )
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(barGraphWidth)
        ) {
            xAxisScale.forEach {
                Text(
                    modifier = Modifier.width(barGraphWidth),
                    text = it,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
