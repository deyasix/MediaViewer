package com.example.mediaviewer.data.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mediaviewer.data.local.db.DatabaseContract
import com.example.mediaviewer.domain.entity.Video

@Entity(DatabaseContract.Videos.TABLE_VIDEOS)
data class VideoDto(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val videoUrl: String,
    val imageUrl: String,
    val index: Int
) {
    fun toDomain(): Video {
        return Video(id, videoUrl, imageUrl)
    }
}