package com.example.pharmacyapp.utils

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pharmacyapp.R
import com.example.pharmacyapp.presentation.theme.LightBlue

@Composable
fun MySearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onKeyboardSearchClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.elevatedCardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = {
                onQueryChange(it)
            },
            textStyle = TextStyle(
                fontSize = 20.sp
            ),
            placeholder = {
                Text(
                    text = stringResource(R.string.type_here),
                    color = Color.Gray
                )
            }, leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "search icon",
                    tint = LightBlue
                )
            },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onKeyboardSearchClick()
                }
            )
        )
    }
}
