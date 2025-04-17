package com.example.pharmacyapp.presentation.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pharmacyapp.R
import com.example.pharmacyapp.data.model.UserDetails
import com.example.pharmacyapp.presentation.theme.LightBlue
import com.example.pharmacyapp.presentation.viewmodel.AuthViewModel
import com.example.pharmacyapp.utils.Constants

@Composable
fun SignUpScreen(navController: NavController) {

    val authViewModel: AuthViewModel = hiltViewModel()
    val mContext = LocalContext.current

    var fullName by remember { mutableStateOf("") }
    var mobileNo by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isFullNameError by remember { mutableStateOf(false) }
    var isMobileNoError by remember { mutableStateOf(false) }
    var isAddressError by remember { mutableStateOf(false) }
    var isDobError by remember { mutableStateOf(false) }
    var isEmailError by remember { mutableStateOf(false) }
    var isPasswordError by remember { mutableStateOf(false) }
    var isConfirmPasswordError by remember { mutableStateOf(false) }

    fun validate() {
        if (fullName.isEmpty()) {
            isFullNameError = true
        } else if (mobileNo.isEmpty() || (mobileNo.length != 10)) {
            isMobileNoError = true
            isFullNameError = false
            isEmailError = false
            isPasswordError = false
        } else if (address.isEmpty()) {
            isAddressError = true
            isMobileNoError = false
            isFullNameError = false
            isEmailError = false
            isPasswordError = false
        } else if (dob.isEmpty()) {
            isDobError = true
            isAddressError = false
            isMobileNoError = false
            isFullNameError = false
            isEmailError = false
            isPasswordError = false
        } else if (
            email.isEmpty() ||
            !email.contains("@") ||
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        ) {
            isFullNameError = false
            isEmailError = true
        } else if (password.isEmpty() || password.length < 6) {
            isFullNameError = false
            isPasswordError = true
        } else if (confirmPassword.isEmpty() || (password != confirmPassword)) {
            isFullNameError = false
            isConfirmPasswordError = true
        } else {
            authViewModel.signUpUser(
                context = mContext,
                email = email,
                password = password,
                userDetails = UserDetails(
                    img = "",
                    name = fullName,
                    mobileNumber = mobileNo,
                    address = address,
                    email = email,
                    dob = dob
                ),
                onSuccess = {
                    navController.navigate(Constants.BOTTOM_NAV) {
                        popUpTo(Constants.SIGNUP_SCREEN) {
                            inclusive = true
                        }
                    }
                })
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(Color.White)
                .imePadding()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(R.string.signup),
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = fullName,
                onValueChange = {
                    fullName = it
                },
                label = {
                    Text(stringResource(R.string.full_name))
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Name Icon"
                    )
                },
                supportingText = {
                    if (isFullNameError) {
                        if (fullName.isEmpty()) {
                            ErrorText(text = stringResource(R.string.required))
                        }
                    }
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = mobileNo,
                onValueChange = {
                    mobileNo = it
                },
                label = {
                    Text(stringResource(R.string.mobile_number))
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = "Phone Icon"
                    )
                },
                supportingText = {
                    if (isMobileNoError) {
                        if (mobileNo.isEmpty()) {
                            ErrorText(text = stringResource(R.string.required))
                        } else if (mobileNo.length != 10) {
                            ErrorText(text = stringResource(R.string.invalid_value))
                        }
                    }
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = address,
                onValueChange = {
                    address = it
                },
                label = {
                    Text(stringResource(R.string.address))
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Address Icon"
                    )
                },
                supportingText = {
                    if (isAddressError) {
                        if (address.isEmpty()) {
                            ErrorText(text = stringResource(R.string.required))
                        }
                    }
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = dob,
                onValueChange = {
                    dob = it
                },
                label = {
                    Text(stringResource(R.string.d_o_b))
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "DOB Icon"
                    )
                },
                supportingText = {
                    if (isDobError) {
                        if (dob.isEmpty()) {
                            ErrorText(text = stringResource(R.string.required))
                        }
                    }
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                },
                label = {
                    Text(stringResource(R.string.email))
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Email Icon"
                    )
                },
                supportingText = {
                    if (isEmailError) {
                        if (email.isEmpty()) {
                            ErrorText(text = stringResource(R.string.required))
                        } else if (!email.contains("@")) {
                            ErrorText(text = stringResource(R.string.invalid_email_format))
                        }
                    }
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                },
                label = {
                    Text(stringResource(R.string.password))
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Password Icon"
                    )
                },
                supportingText = {
                    if (isPasswordError) {
                        if (password.isEmpty()) {
                            ErrorText(text = stringResource(R.string.required))
                        } else if (password.length > 6) {
                            ErrorText(text = stringResource(R.string.password_must_be_at_least_6_characters))
                        } else {
                            ErrorText(text = stringResource(R.string.invalid_value))
                        }
                    }
                },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it
                },
                label = {
                    Text(stringResource(R.string.confirm_password))
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Password Icon"
                    )
                },
                supportingText = {
                    if (isConfirmPasswordError) {
                        if (confirmPassword.isEmpty()) {
                            ErrorText(text = stringResource(R.string.required))
                        } else {
                            ErrorText(text = stringResource(R.string.passwords_do_not_match))
                        }
                    }
                },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            ElevatedButton(
                onClick = {
                    validate()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = LightBlue),
            ) {
                Text(
                    text = stringResource(R.string.signup),
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.already_have_account),
                    color = Color.Black
                )
                Text(
                    text = stringResource(R.string.login),
                    fontSize = 18.sp,
                    color = Color.Green,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .clickable {
                            navController.navigate(Constants.LOGIN_SCREEN) {
                                popUpTo(Constants.SIGNUP_SCREEN) {
                                    inclusive = true
                                }
                            }
                        }
                )
            }
        }
    }
}

@Composable
fun ErrorText(text: String) {
    Text(
        text = text,
        color = Color.Red
    )
}
