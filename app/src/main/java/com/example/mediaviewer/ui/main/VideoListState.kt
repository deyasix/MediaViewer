package com.example.mediaviewer.ui.main

import com.example.mediaviewer.domain.entity.Video

sealed class VideoListState {
    data object Loading : VideoListState()
    data class Error(val message: String) : VideoListState()
    data class Success(val data: List<Video>) : VideoListState()
}