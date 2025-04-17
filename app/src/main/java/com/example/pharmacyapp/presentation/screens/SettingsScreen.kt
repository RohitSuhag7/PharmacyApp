package com.example.pharmacyapp.presentation.screens

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pharmacyapp.R
import com.example.pharmacyapp.presentation.theme.LightBlue
import com.example.pharmacyapp.presentation.viewmodel.AuthViewModel
import com.example.pharmacyapp.presentation.viewmodel.DataStorePreferenceViewModel
import com.example.pharmacyapp.utils.Constants
import com.example.pharmacyapp.utils.DropDownMenuEventHandler
import com.example.pharmacyapp.utils.LocalLanguageManager
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController) {

    val context = LocalContext.current

    // viewmodel
    val dataStorePreferenceViewModel: DataStorePreferenceViewModel = hiltViewModel()
    val isDarkTheme = dataStorePreferenceViewModel.theme.collectAsState()
    val selectedLanguage = dataStorePreferenceViewModel.language.collectAsState()

    // Auth ViewModel
    val authViewModel: AuthViewModel = hiltViewModel()

    val listOfLanguages = listOf(
        "en" to stringResource(R.string.english),
        "hi" to stringResource(R.string.hindi)
    )

    LaunchedEffect(Unit) {
        dataStorePreferenceViewModel.loadLanguage()
        dataStorePreferenceViewModel.loadTheme()
    }

    Scaffold(
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
                            text = "Settings Screen",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = LightBlue),
                navigationIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .clickable {
                                navController.popBackStack()
                            }
                    )
                }
            )
        }
    ) { innerPaddings ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 16.dp)
                .padding(innerPaddings),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.select_theme),
                fontWeight = FontWeight.Bold,
                color = if (isDarkTheme.value) Color.White else Color.Black
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.dark_theme),
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isDarkTheme.value) Color.White else Color.Black
                )

                Switch(
                    checked = isDarkTheme.value,
                    onCheckedChange = {
                        dataStorePreferenceViewModel.setTheme(it)
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.select_language),
                fontWeight = FontWeight.Bold,
                color = if (isDarkTheme.value) Color.White else Color.Black
            )

            DropDownMenuEventHandler(
                selectedValue = if (selectedLanguage.value == "en")
                    stringResource(R.string.english)
                else stringResource(
                    R.string.hindi
                ),
                options = listOfLanguages.map { it.second },
                onValueChangedEvent = { selectedLanguageName ->
                    val languageCode = listOfLanguages.first {
                        it.second == selectedLanguageName
                    }.first

                    dataStorePreferenceViewModel.setLanguage(languageCode)
                    LocalLanguageManager.updateLocalLanguage(context, languageCode)
                },
                getOptionLabel = {
                    it
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                ElevatedButton(
                    onClick = {
                        val pdfFilePath = File(context.filesDir, "android.pdf")

                        if (!pdfFilePath.exists()) {
                            copyFileFromAssets(context, "android.pdf", pdfFilePath)
                        }
                        navController.navigate(Constants.PDF_VIEWER_SCREEN + "?${Constants.PDF_FILE_PATH}=${pdfFilePath}")
                    }
                ) {
                    Text(
                        text = "PDF Viewer",
                        fontSize = 18.sp
                    )
                }

                ElevatedButton(
                    onClick = {
                        navController.navigate(Constants.EXOPLAYER_SCREEN)
                    }
                ) {
                    Text(
                        text = "ExoPlayer",
                        fontSize = 18.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                ElevatedButton(
                    onClick = {
                        authViewModel.signOutUser()
                        navController.navigate(Constants.LOGIN_SCREEN) {
                            popUpTo(Constants.MORE_SCREEN)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(horizontal = 16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = LightBlue),
                ) {
                    Text(
                        text = stringResource(R.string.signout),
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}

fun copyFileFromAssets(context: Context, assetName: String, destFile: File) {
    // Open the asset file
    val assetManager = context.assets
    var inputStream: InputStream? = null
    var outputStream: FileOutputStream? = null

    try {
        inputStream = assetManager.open(assetName)
        outputStream = FileOutputStream(destFile)

        // Buffer for reading the asset file
        val buffer = ByteArray(1024)
        var length: Int
        while (inputStream.read(buffer).also { length = it } > 0) {
            outputStream.write(buffer, 0, length)
        }
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        try {
            inputStream?.close()
            outputStream?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
