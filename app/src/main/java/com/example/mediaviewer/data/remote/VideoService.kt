package com.example.mediaviewer.data.remote

import com.example.mediaviewer.data.remote.dto.VideoPageDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface VideoService {

    @GET("videos/popular/")
    suspend fun getVideos(@Query("per_page") perPage: Int = 25): Response<VideoPageDto>
}