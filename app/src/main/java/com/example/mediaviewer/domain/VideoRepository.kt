package com.example.mediaviewer.domain

import com.example.mediaviewer.domain.entity.Video

class VideoRepository(
    private val remoteVideoDataSource: RemoteVideoDataSource,
    private val localVideoDataSource: LocalVideoDataSource
) {

    suspend fun getVideos(): Result<List<Video>> {
        val cachedVideos = localVideoDataSource.getVideos()
        return if (cachedVideos.isEmpty()) updateVideos()
        else Result.success(cachedVideos)
    }

    suspend fun updateVideos(): Result<List<Video>> {
        return remoteVideoDataSource.getVideos().onSuccess {
            localVideoDataSource.addVideos(it)
        }
    }
}