package com.example.mediaviewer.domain

import com.example.mediaviewer.domain.entity.Video

interface LocalVideoDataSource {
    suspend fun getVideos(): List<Video>
    suspend fun addVideos(videos: List<Video>)
}