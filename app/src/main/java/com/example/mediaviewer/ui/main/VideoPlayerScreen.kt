package com.example.mediaviewer.ui.main

import androidx.annotation.OptIn
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView


@OptIn(UnstableApi::class)
@Composable
fun VideoPlayerScreen(modifier: Modifier = Modifier, viewModel: VideoListViewModel) {
    val videos by remember {
        mutableStateOf(viewModel.getMediaItems())
    }

    val exoPlayer = ExoPlayer.Builder(LocalContext.current).build().apply {
        setMediaItems(videos, viewModel.indexOfCurrentVideo(), 0)
        addListener(getPlayerListener { viewModel.setCurrentVideo(it) })
        playWhenReady = true
        prepare()
    }

    AndroidView(
        factory = { context ->
            PlayerView(context).apply {
                player = exoPlayer
            }
        },
        modifier = modifier.fillMaxSize()
    )

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }
}

private fun getPlayerListener(onVideoChanged: (MediaItem) -> Unit): Player.Listener {
    return object : Player.Listener {
        override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
            super.onMediaItemTransition(mediaItem, reason)
            mediaItem?.let {
                onVideoChanged.invoke(it)
            }
        }
    }
}