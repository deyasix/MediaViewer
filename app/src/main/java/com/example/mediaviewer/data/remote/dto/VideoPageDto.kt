package com.example.mediaviewer.data.remote.dto

import com.google.gson.annotations.SerializedName

data class VideoPageDto(
    @SerializedName("page")
    val page: Int,
    @SerializedName("per_page")
    val perPage: Int,
    @SerializedName("total_results")
    val totalResult: Int,
    @SerializedName("videos")
    val videos: List<VideoDto>
)
