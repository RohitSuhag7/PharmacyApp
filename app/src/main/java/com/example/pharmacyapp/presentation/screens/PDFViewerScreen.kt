package com.example.pharmacyapp.presentation.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pharmacyapp.presentation.theme.LightBlue
import com.example.pharmacyapp.presentation.viewmodel.DataStorePreferenceViewModel
import com.example.pharmacyapp.utils.PDFRendererHelper
import kotlinx.coroutines.delay
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PDFViewerScreen(navController: NavController, pdfFile: File) {

    // ViewModel
    val dataStorePreferenceViewModel: DataStorePreferenceViewModel = hiltViewModel()
    val pageIndex = dataStorePreferenceViewModel.pdfPageIndex.collectAsState()

    val rendererHelper = remember { PDFRendererHelper(pdfFile) }
    var currentPageIndex by remember { mutableIntStateOf(pageIndex.value) }
    Log.d("PageCount", "View: $currentPageIndex")
    val pageCount = rendererHelper.getPageCount()

//    var isLoading = pageIndex.value == 0

    LaunchedEffect(pageIndex.value) {
        if (pageIndex.value != 0) {
            currentPageIndex = pageIndex.value
        } else {
            dataStorePreferenceViewModel.loadPDFPageCount()
        }
    }

    // Simple navigation buttons for paging
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
                            text = "PDF Viewer",
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
//                                dataStorePreferenceViewModel.savePDFPageCount(currentPageIndex)
                                navController.popBackStack()
                            }
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val currentPageBitmap = rendererHelper.getPageBitmap(currentPageIndex)
            if (currentPageBitmap != null) {
                Image(
                    bitmap = currentPageBitmap.asImageBitmap(),
                    contentDescription = "PDF Page $currentPageIndex",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(500.dp)
                        .aspectRatio(
                            currentPageBitmap.width.toFloat() / currentPageBitmap.height.toFloat(),
                            matchHeightConstraintsFirst = true
                        ),
                    contentScale = ContentScale.FillWidth
                )
            }

            Spacer(modifier = Modifier.height(50.dp))

            Row(
                modifier = Modifier
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(
                    onClick = {
                        if (currentPageIndex > 0) currentPageIndex -= 1
                        dataStorePreferenceViewModel.savePDFPageCount(currentPageIndex)
                    },
                    enabled = currentPageIndex > 0
                ) {
                    Text("Previous")
                }

                Spacer(modifier = Modifier.width(50.dp))

                Button(
                    onClick = {
                        if (currentPageIndex < pageCount - 1) currentPageIndex += 1
                        dataStorePreferenceViewModel.savePDFPageCount(currentPageIndex)
                    },
                    enabled = currentPageIndex < pageCount - 1
                ) {
                    Text("Next")
                }
            }
        }

        // Cleanup
        LaunchedEffect(key1 = currentPageIndex) {
            rendererHelper.getPageBitmap(currentPageIndex)
        }

        // Close resources when the composable is disposed
        DisposableEffect(Unit) {
            onDispose {
                rendererHelper.close()
            }
        }
    }
}
