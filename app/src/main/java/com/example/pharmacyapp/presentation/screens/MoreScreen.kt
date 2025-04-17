package com.example.pharmacyapp.presentation.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.pharmacyapp.R
import com.example.pharmacyapp.presentation.screens.auth.FirebaseUiState
import com.example.pharmacyapp.presentation.theme.LightBlue
import com.example.pharmacyapp.presentation.viewmodel.AuthViewModel
import com.example.pharmacyapp.utils.Constants
import com.example.pharmacyapp.utils.CustomDialogWithTextField

@Composable
fun MoreScreen(navController: NavController) {

    val viewModel: AuthViewModel = hiltViewModel()

    val userDetails by viewModel.userDetails.collectAsState()

    val showEditDialog = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getUserDetails()
    }

    var cardItems = listOf<CardItem>()

    val editingItem = remember { mutableStateOf<CardItem?>(null) }
    val updatedDescription = remember { mutableStateOf("") }

    var selectedImageUri by rememberSaveable { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) {
        selectedImageUri = it
    }

    when (val data = userDetails) {
        is FirebaseUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is FirebaseUiState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = data.message,
                    color = Color.Red)
            }
        }

        is FirebaseUiState.Success -> {
            val userData = data.data

            // Fetch image from FireStore to show to user
            val imageUrl = userData.img
            if (imageUrl != "" && selectedImageUri == null) {
                selectedImageUri = Uri.parse(imageUrl)
            }

            cardItems = listOf(
                CardItem(
                    Icons.Filled.Person,
                    stringResource(R.string.full_name),
                    userData.name ?: stringResource(R.string.jai_singh)
                ),
                CardItem(
                    Icons.Filled.Phone,
                    stringResource(R.string.mobile_number),
                    userData.mobileNumber ?: "+91-8761234567"
                ),
                CardItem(
                    Icons.Filled.Email,
                    stringResource(R.string.email),
                    userData.email ?: stringResource(R.string.login_email)
                ),
                CardItem(
                    Icons.Filled.LocationOn,
                    stringResource(R.string.address),
                    userData.address ?: "Dobh, Rohtak, Haryana"
                ),
                CardItem(
                    Icons.Filled.DateRange,
                    stringResource(R.string.d_o_b),
                    userData.dob ?: "08-03-1999"
                ),
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(180.dp)
                .padding(8.dp)
                .border(
                    width = 2.dp,
                    color = LightBlue,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            if (selectedImageUri != null) {
                AsyncImage(
                    model = selectedImageUri,
                    contentDescription = null,
                    modifier = Modifier
                        .size(180.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    painter = painterResource(R.drawable.profile_img_back),
                    contentDescription = null,
                    modifier = Modifier
                        .size(180.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }

            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit",
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp)
                    .size(32.dp)
                    .background(Color.White, shape = CircleShape)
                    .border(1.dp, Color.Gray, shape = CircleShape)
                    .padding(4.dp)
                    .clickable {
                        launcher.launch(
                            PickVisualMediaRequest(mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }
            )
        }
        if (selectedImageUri != null) {
            viewModel.updateUserDetails(mapOf("img" to selectedImageUri!!.path!!))
        }
        cardItems.forEach { item ->
            ProfileDetailsCardView(
                imageVector = item.imageVector,
                title = item.title,
                desc = item.description,
                onClickEdit = {
                    editingItem.value = item
                    updatedDescription.value = item.description
                    showEditDialog.value = true
                }
            )
        }

        if (showEditDialog.value) {
            editingItem.value?.let { item ->
                CustomDialogWithTextField(
                    onDismissRequest = {
                        showEditDialog.value = false
                    },
                    onConfirmClick = {
                        val fieldMap = mapOf(
                            "Full Name" to "name",
                            "Mobile Number" to "mobileNumber",
                            "Email" to "email",
                            "Address" to "address",
                            "Date of Birth" to "dob"
                        )

                        val fieldName = fieldMap[item.title] ?: return@CustomDialogWithTextField
                        viewModel.updateUserDetails(mapOf(fieldName to updatedDescription.value))
                        showEditDialog.value = false
                    },
                    textValue = updatedDescription.value,
                    onTextValueChange = {
                        updatedDescription.value = it
                    },
                    labelValue = item.title
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = stringResource(R.string.other_details),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Card(
            modifier = Modifier
                .padding(
                    horizontal = 22.dp,
                    vertical = 12.dp
                )
                .clickable {
                    navController.navigate(Constants.SETTINGS_SCREEN)
                },
            colors = CardDefaults.cardColors(Color.White)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.settings),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = Color.Black
                )
            }
        }
    }
}

@Composable
fun ProfileDetailsCardView(
    imageVector: ImageVector,
    title: String,
    desc: String,
    onClickEdit: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 16.dp,
                horizontal = 30.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            imageVector = imageVector,
            contentDescription = null,
            modifier = Modifier
                .size(32.dp)
                .clip(RectangleShape),
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(color = LightBlue)
        )

        Column(
            modifier = Modifier
                .padding(horizontal = 12.dp)
        ) {
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

//            TextField(
//                value = desc,
//                onValueChange = {},
//                textStyle = TextStyle(color = Color.Black),
//                singleLine = true,
//                colors = TextFieldDefaults.colors(
//                    focusedContainerColor = Color.Transparent,
//                    unfocusedContainerColor = Color.Transparent,
//                    focusedIndicatorColor = Color.Transparent,
//                    unfocusedIndicatorColor = Color.Transparent
//                )
//            )

            Text(
                text = desc,
                color = Color.Black,
                modifier = Modifier
                    .padding(start = 4.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = "edit data",
            modifier = Modifier
                .clickable {
                    onClickEdit()
                }
        )
    }
}

data class CardItem(
    val imageVector: ImageVector,
    val title: String,
    val description: String
)
