package com.example.mediaviewer.data.remote.dto

import com.google.gson.annotations.SerializedName

data class FileDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("link")
    val url: String,
    @SerializedName("quality")
    val quality: String
)
