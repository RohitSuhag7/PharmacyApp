package com.example.pharmacyapp.presentation.viewmodel

import android.content.Context
import android.media.MediaMetadata
import android.media.session.MediaSession
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ExoPlayerViewModel @Inject constructor() : ViewModel() {

    private var _exoPlayer = MutableStateFlow<ExoPlayer?>(null)
    val exoPlayer: StateFlow<ExoPlayer?> get() = _exoPlayer

    private var playbackPosition by mutableLongStateOf(0L)

    private var playWhenReady by mutableStateOf(true)

    var isBuffering by mutableStateOf(true)
        private set

    var mediaSession: MediaSession? = null

    fun initializePlayer(context: Context, videoUri: String) {
        if (_exoPlayer.value == null) {
            _exoPlayer.value = ExoPlayer.Builder(context).build().apply {
                setMediaItem(MediaItem.fromUri(videoUri))

                prepare()

                seekTo(playbackPosition)

                playWhenReady = this@ExoPlayerViewModel.playWhenReady

                addListener(object : Player.Listener {
                    override fun onPlaybackStateChanged(state: Int) {
                        isBuffering = (state == Player.STATE_BUFFERING)
                    }
                })
            }

            mediaSession = MediaSession(context, _exoPlayer.value.toString()).apply {
                isActive = true
                setCallback(object : MediaSession.Callback() {
                    override fun onPlay() {
                        _exoPlayer.value?.play()
                    }

                    override fun onPause() {
                        _exoPlayer.value?.pause()
                    }

                    override fun onSkipToNext() {
                        _exoPlayer.value?.seekToNext()
                    }

                    override fun onSkipToPrevious() {
                        _exoPlayer.value?.seekToPrevious()
                    }

                    override fun onSeekTo(pos: Long) {
                        _exoPlayer.value?.seekTo(pos)
                    }
                })

                // Set up media metadata
                val mediaMetadata = MediaMetadata.Builder()
                    .putString(MediaMetadata.METADATA_KEY_TITLE, "ExoPlayer Video")
                    .putLong(MediaMetadata.METADATA_KEY_DURATION, _exoPlayer.value?.duration ?: 0L)
                    .build()

                setMetadata(mediaMetadata)
            }
        }
    }

    fun releasePlayer() {
        _exoPlayer.value?.run {
            playbackPosition = currentPosition

            playWhenReady = this.playWhenReady

            release()
        }
        mediaSession?.release()
        _exoPlayer.value = null
    }

    override fun onCleared() {
        super.onCleared()
        releasePlayer()
    }
}
