package com.example.mediaviewer.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mediaviewer.data.local.dto.VideoDto

@Database(
    entities = [VideoDto::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getVideosDao(): VideosDao
}