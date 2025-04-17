package com.example.pharmacyapp.presentation.screens

import android.app.Activity
import android.app.PictureInPictureParams
import android.content.pm.ActivityInfo
import android.media.session.PlaybackState
import android.util.Rational
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import com.example.pharmacyapp.R
import com.example.pharmacyapp.presentation.MainActivity
import com.example.pharmacyapp.presentation.theme.LightBlue
import com.example.pharmacyapp.presentation.viewmodel.ExoPlayerViewModel
import com.example.pharmacyapp.utils.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExoPlayerScreen(navController: NavController) {
    val viewModel: ExoPlayerViewModel = hiltViewModel()

    val videoUri by remember { mutableStateOf(Constants.VIDEO_URL) }
    var isFullScreen by rememberSaveable { mutableStateOf(false) }

    val context = LocalContext.current
    val activity = context as Activity
    val mainActivity = context as MainActivity

    val exoPlayer = viewModel.exoPlayer.collectAsState()
    val isBuffering = viewModel.isBuffering

    var pipMode by rememberSaveable { mutableStateOf(false) }

    BackHandler {
        pipMode = true
    }

    fun enterPipMode(): PictureInPictureParams? {
        val aspectRatio = Rational(16, 9)
        val params = PictureInPictureParams
            .Builder()
            .setAspectRatio(aspectRatio)
            .build()
        return params
    }

    LaunchedEffect(pipMode) {
        if (pipMode) {
            enterPipMode()?.let { params ->
                mainActivity.enterPictureInPictureMode(params)
            }
        }
    }

    LaunchedEffect(videoUri) {
        viewModel.initializePlayer(context, videoUri)
    }

    LaunchedEffect(exoPlayer.value?.playWhenReady) {
        val state =
            if (exoPlayer.value?.playWhenReady == true) PlaybackState.STATE_PLAYING else PlaybackState.STATE_PAUSED
        val playbackState =
            PlaybackState.Builder().setState(state, exoPlayer.value?.currentPosition ?: 0L, 1f)
                .setActions(PlaybackState.ACTION_PLAY or PlaybackState.ACTION_PAUSE or PlaybackState.ACTION_SEEK_TO or PlaybackState.ACTION_FAST_FORWARD or PlaybackState.ACTION_REWIND or PlaybackState.ACTION_SKIP_TO_NEXT or PlaybackState.ACTION_SKIP_TO_PREVIOUS)
                .build()
        viewModel.mediaSession?.setPlaybackState(playbackState)
    }

    Scaffold(
        topBar = {
            if (!isFullScreen && !pipMode) {
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
                                text = "ExoPlayer",
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
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center
        ) {
            val videoModifier = if (isFullScreen) {
                Modifier
                    .fillMaxSize()
                    .aspectRatio(16f / 6.5f)
            } else {
                Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
            }
            Box(
                contentAlignment = Alignment.Center
            ) {
                // Embed PlayerView using AndroidView
                if (exoPlayer.value != null) {
                    AndroidView(
                        modifier = videoModifier,
                        factory = {
                            PlayerView(context).apply {
                                player = exoPlayer.value
                                useController = true
                            }
                        },
                        update = {
                            it.player = exoPlayer.value
                        }
                    )
                }

                // Show loading indicator while buffering
                if (isBuffering) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(48.dp)
                            .align(Alignment.Center),
                        color = Color.White
                    )
                }

                val iconModifier = if (isFullScreen) {
                    Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 30.dp, end = 15.dp)
                } else {
                    Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                }

                if (!pipMode) {
                    // Fullscreen Toggle Button
                    IconButton(
                        onClick = {
                            if (isFullScreen) {
                                isFullScreen = false
                                activity.requestedOrientation =
                                    ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
                            } else {
                                isFullScreen = true
                                activity.requestedOrientation =
                                    ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                            }
                        },
                        modifier = iconModifier
                            .background(Color.Black.copy(alpha = 0.7f), shape = CircleShape)
                    ) {
                        Icon(
                            painter = painterResource(
                                if (isFullScreen) R.drawable.ic_close_full
                                else R.drawable.ic_open_in_full
                            ),
                            contentDescription = "Toggle Fullscreen",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.releasePlayer()
        }
    }
}
