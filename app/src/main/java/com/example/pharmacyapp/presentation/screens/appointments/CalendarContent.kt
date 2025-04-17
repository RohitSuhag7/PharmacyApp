package com.example.pharmacyapp.presentation.screens.appointments

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.pharmacyapp.presentation.theme.LightBlue
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarContent(
    days: List<LocalDate?>,
    selectedDate: MutableState<LocalDate>,
    today: LocalDate,
    listOfWeeks: List<String>
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier
                .padding(8.dp)
                .height(20.dp)
        ) {
            items(listOfWeeks.size) { week ->
                Text(
                    text = listOfWeeks[week],
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                )
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier
                .padding(8.dp)
                .height(200.dp)
        ) {
            items(days.size) { day ->
                Box(modifier = Modifier
                    .size(40.dp)
                    .clickable(enabled = days[day]?.isAfter(today) == true) {
                        days[day]?.let {
                            selectedDate.value = it
                        }
                    }
                    .background(
                        if (selectedDate.value == days[day]) LightBlue else Color.Transparent,
                        CircleShape
                    ),
                    contentAlignment = Alignment.Center
                )
                {
                    Text(
                        text = if (days[day] != null) days[day]?.dayOfMonth.toString() else "",
                        color = if (selectedDate.value == days[day]) Color.White else Color.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
            }
        }
    }
}
