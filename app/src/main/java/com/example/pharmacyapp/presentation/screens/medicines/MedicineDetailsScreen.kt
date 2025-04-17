package com.example.pharmacyapp.presentation.screens.medicines

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pharmacyapp.R
import com.example.pharmacyapp.data.model.Medicine
import com.example.pharmacyapp.data.model.OrderedMedicineDetails
import com.example.pharmacyapp.presentation.theme.LightBlue
import com.example.pharmacyapp.presentation.theme.LightGreen
import com.example.pharmacyapp.presentation.viewmodel.OrderedMedicineViewModel
import com.example.pharmacyapp.presentation.viewmodel.PharmacyViewModel
import com.example.pharmacyapp.utils.Constants
import com.example.pharmacyapp.utils.DropDownMenuEventHandler
import com.google.gson.Gson

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicineDetailsScreen(navController: NavController, medicineJsonString: String?) {

    val medicineData = Gson().fromJson(medicineJsonString, Medicine::class.java)
    val medicineId by remember { mutableStateOf(medicineData.id ?: "") }
    val medicineName by remember { mutableStateOf(medicineData.name ?: "") }
    val medicineCategory by remember { mutableStateOf(medicineData.category ?: "") }
    val medicineDesc by remember { mutableStateOf(medicineData.description ?: "") }
    val medicinePrice by remember { mutableStateOf(medicineData.price ?: "") }
    val medicineManufacturer by remember { mutableStateOf(medicineData.manufacturer ?: "") }

    val otherDetails = listOf(
        "Category: $medicineCategory",
        "Description: $medicineDesc",
        "Price: ₹$medicinePrice",
        "Manufacturer: $medicineManufacturer"
    )

    val pillsCount = remember { mutableIntStateOf(0) }

    // OrderedMedicineViewModel
    val orderedMedicineViewModel: OrderedMedicineViewModel = hiltViewModel()

    // PharmacyViewModel
    val pharmacyViewModel: PharmacyViewModel = hiltViewModel()
    val pharmacyDetails by pharmacyViewModel.pharmaciesData.collectAsState()

    val selectedPharmacy = remember { mutableStateOf<String?>(null) }

    val mContext = LocalContext.current
    val snackBarHostState = remember { SnackbarHostState() }
    val showSnackBar = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        pharmacyViewModel.fetchPharmacies()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                    ) {
                        Text(
                            text = stringResource(R.string.medicine_detail),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = LightBlue),
                modifier = Modifier.height(120.dp)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(paddingValues = paddingValues)
                .verticalScroll(state = rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = stringResource(R.string.medicine),
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                elevation = CardDefaults.elevatedCardElevation(4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                OutlinedTextField(
                    readOnly = true,
                    value = medicineName,
                    onValueChange = {},
                    textStyle = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White
                    ),
                    modifier = Modifier.padding(8.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = stringResource(R.string.quantity),
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
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
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IconButton(onClick = {
                        pillsCount.value += 1
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_add),
                            contentDescription = "increase quantity",
                            tint = LightGreen,
                            modifier = Modifier.size(50.dp)
                        )
                    }
                    Text(
                        text = stringResource(R.string.pills, pillsCount.intValue),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    IconButton(onClick = {
                        if (pillsCount.intValue != 0) pillsCount.value -= 1
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_remove),
                            contentDescription = "increase quantity",
                            tint = LightGreen,
                            modifier = Modifier.size(50.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = stringResource(R.string.other_details),
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            DropDownMenuEventHandler(
                selectedValue = stringResource(R.string.medicine_other_details),
                options = otherDetails,
                onValueChangedEvent = {},
                getOptionLabel = {
                    it
                }
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = stringResource(R.string.select_pharmacy_for_order),
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            DropDownMenuEventHandler(
                selectedValue = selectedPharmacy.value
                    ?: stringResource(R.string.select_a_pharmacy),
                options = pharmacyDetails.map {
                    it.name
                },
                onValueChangedEvent = {
                    selectedPharmacy.value = it
                },
                getOptionLabel = {
                    it
                }
            )

            Spacer(modifier = Modifier.height(64.dp))

            ElevatedButton(
                onClick = {
                    if (pillsCount.intValue != 0 && selectedPharmacy.value != null) {
                        orderedMedicineViewModel.insertOrderedMedicine(
                            OrderedMedicineDetails(
                                id = medicineId,
                                medicineName = medicineName,
                                medicinePillsCount = "${pillsCount.intValue}₹",
                                medicineCategory = medicineCategory,
                                medicineDescription = medicineDesc,
                                medicinePrice = "${medicinePrice}₹",
                                medicineManufacturer = medicineManufacturer,
                                selectedPharmacy = selectedPharmacy.value
                            )
                        )
                        navController.navigate(Constants.MEDICINES_LIST_SCREEN) {
                            popUpTo(Constants.MEDICINES_DETAILS_SCREEN) {
                                inclusive = true
                            }
                        }
                    } else {
                        showSnackBar.value = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = LightBlue),
            ) {
                Text(
                    text = stringResource(R.string.order_medicine),
                    fontSize = 18.sp
                )
            }

            // SnackbarHost for displaying the Snackbar
            SnackbarHost(
                hostState = snackBarHostState,
                modifier = Modifier.padding(bottom = 50.dp)
            )

            if (showSnackBar.value) {
                LaunchedEffect(snackBarHostState) {
                    snackBarHostState.showSnackbar(mContext.getString(R.string.please_select_or_enter_all_the_field))
                    showSnackBar.value = false
                }
            }

        }
    }
}
