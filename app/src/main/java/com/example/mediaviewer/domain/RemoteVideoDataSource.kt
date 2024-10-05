package com.example.mediaviewer.domain

import com.example.mediaviewer.domain.entity.Video

interface RemoteVideoDataSource {
    suspend fun getVideos(): Result<List<Video>>
}