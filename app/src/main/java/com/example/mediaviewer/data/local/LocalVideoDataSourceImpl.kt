package com.example.mediaviewer.data.local

import com.example.mediaviewer.data.local.db.VideosDao
import com.example.mediaviewer.data.local.dto.VideoDto
import com.example.mediaviewer.domain.LocalVideoDataSource
import com.example.mediaviewer.domain.entity.Video

class LocalVideoDataSourceImpl(private val videosDao: VideosDao) : LocalVideoDataSource {
    override suspend fun getVideos(): List<Video> {
        return videosDao.getVideos().map { it.toDomain() }
    }

    override suspend fun addVideos(videos: List<Video>) {
        videosDao.deleteAllVideos()
        videosDao.insertVideos(videos.mapIndexed { index, video ->
            VideoDto(
                video.id,
                video.videoUrl,
                video.imageUrl,
                index
            )
        })
    }

}