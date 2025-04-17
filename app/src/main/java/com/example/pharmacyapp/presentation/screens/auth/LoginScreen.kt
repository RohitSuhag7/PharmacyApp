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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.example.pharmacyapp.presentation.theme.LightBlue
import com.example.pharmacyapp.presentation.viewmodel.AuthViewModel
import com.example.pharmacyapp.utils.Constants

@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current

    val authViewModel: AuthViewModel = hiltViewModel()
    val loginUser by authViewModel.loginStateFlow.collectAsState()

    var email by remember { mutableStateOf("") } //context.getString(R.string.login_email)
    var password by remember { mutableStateOf("") } //context.getString(R.string.login_pass)
    var isChecked by remember { mutableStateOf(false) }
    var isEmailError by remember { mutableStateOf(false) }
    var isPasswordError by remember { mutableStateOf(false) }

    fun validate() {
        if (
            email.isEmpty() ||
            !email.contains("@") ||
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        ) {
            isEmailError = true
            isPasswordError = false
        } else if (password.isEmpty() || password.length < 6) {
            isPasswordError = true
            isEmailError = false
        } else {
            authViewModel.loginUser(email, password)
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
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.login),
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(32.dp))

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
                        Text(
                            text = "Enter valid or correct email",
                            color = Color.Red
                        )
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
                        Text(
                            text = "Enter valid or correct password",
                            color = Color.Red
                        )
                    }
                },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = {
                        isChecked = it
                    }
                )
                Text(text = stringResource(R.string.remember_me))
            }

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
                    text = stringResource(R.string.login),
                    fontSize = 18.sp
                )
            }

            // Navigate to Bottom Nav Screen
            if (loginUser != null) {
                navController.navigate(Constants.BOTTOM_NAV) {
                    popUpTo(Constants.LOGIN_SCREEN) {
                        inclusive = true
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.don_t_have_account),
                    color = Color.Black
                )
                Text(
                    text = stringResource(R.string.signup),
                    fontSize = 18.sp,
                    color = Color.Green,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .clickable {
                            navController.navigate(Constants.SIGNUP_SCREEN) {
                                popUpTo(Constants.LOGIN_SCREEN) {
                                    inclusive = true
                                }
                            }
                        }
                )
            }
        }
    }
}
