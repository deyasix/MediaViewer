package com.example.mediaviewer.data.remote.dto

import com.example.mediaviewer.domain.entity.Video
import com.google.gson.annotations.SerializedName

data class VideoDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("url")
    val videoUrl: String,
    @SerializedName("image")
    val imageUrl: String,
    @SerializedName("video_files")
    val files: List<FileDto>
) {
    fun toDomain(): Video {
        return Video(id, files.first { it.quality != VideoQuality.UHD.key }.url, imageUrl)
    }
}
