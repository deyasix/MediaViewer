package com.example.mediaviewer.ui.main

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ShapeDefaults

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mediaviewer.domain.entity.Video

@Composable
fun VideoListScreen(
    modifier: Modifier = Modifier,
    viewModel: VideoListViewModel,
    onNavigateToVideo: () -> Unit
) {
    Column(modifier.background(Color.White)) {
        Box(Modifier.fillMaxWidth()) {
            IconButton(
                onClick = viewModel::updateVideos,
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Icon(Icons.Outlined.Refresh, "Update Button", tint = Color.Black)
            }
        }
        Box(Modifier.fillMaxSize()) {
            val screenState by viewModel.state.collectAsState()
            when (screenState) {
                is VideoListState.Success -> {
                    val videos = (screenState as VideoListState.Success).data
                    VideoList(videos = videos) {
                        viewModel.setCurrentVideo(it)
                        onNavigateToVideo()
                    }
                }

                is VideoListState.Loading -> {
                    LoadingScreen(Modifier.align(Alignment.Center))
                }

                is VideoListState.Error -> {
                    val errorMessage = (screenState as VideoListState.Error).message
                    Toast.makeText(LocalContext.current, errorMessage, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}

@Composable
fun VideoList(modifier: Modifier = Modifier, videos: List<Video>, onVideoClick: (Video) -> Unit) {
    LazyVerticalStaggeredGrid(StaggeredGridCells.Adaptive(180.dp), modifier = modifier) {
        items(videos) {
            Video(Modifier, it.imageUrl) {
                onVideoClick(it)
            }
        }
    }
}

@Composable
private fun Video(
    modifier: Modifier = Modifier,
    imageUrl: String,
    onVideoClick: () -> Unit
) {
    Box(
        modifier
            .padding(8.dp)
            .border(1.dp, Color.Black)
            .fillMaxSize()
    ) {
        AsyncImage(imageUrl, "")
        PlayButton(modifier.matchParentSize(), onClick = onVideoClick)
    }
}

@Composable
private fun PlayButton(modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    IconButton(onClick, modifier.fillMaxSize()) {
        Icon(
            Icons.Outlined.PlayArrow,
            "Play Video Button",
            modifier = Modifier
                .size(32.dp)
                .background(Color.White, ShapeDefaults.Large),
            tint = Color.Black
        )
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    CircularProgressIndicator(modifier)
}