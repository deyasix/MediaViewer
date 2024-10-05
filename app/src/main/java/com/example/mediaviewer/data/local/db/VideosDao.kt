package com.example.mediaviewer.data.local.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mediaviewer.data.local.db.DatabaseContract.Videos.TABLE_VIDEOS
import com.example.mediaviewer.data.local.dto.VideoDto

@Dao
interface VideosDao {

    @Query("SELECT * FROM $TABLE_VIDEOS ORDER BY `index`")
    fun getVideos(): List<VideoDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertVideos(videos: List<VideoDto>)

    @Query("DELETE FROM $TABLE_VIDEOS")
    fun deleteAllVideos()

}