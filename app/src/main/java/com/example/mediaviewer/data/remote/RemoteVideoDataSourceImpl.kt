package com.example.mediaviewer.data.remote

import com.example.mediaviewer.domain.RemoteVideoDataSource
import com.example.mediaviewer.domain.entity.Video

class RemoteVideoDataSourceImpl(private val videoService: VideoService) : RemoteVideoDataSource {
    override suspend fun getVideos(): Result<List<Video>> {
        val response = videoService.getVideos()
        val body = response.body()
        return if (response.isSuccessful && body != null) {
            Result.success(body.videos.map { it.toDomain() })
        } else Result.failure(Throwable(response.message()))
    }
}