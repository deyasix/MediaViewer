package com.example.mediaviewer.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import com.example.mediaviewer.domain.VideoRepository
import com.example.mediaviewer.domain.entity.Video
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class VideoListViewModel(private val videoRepository: VideoRepository) : ViewModel() {

    private val _state: MutableStateFlow<VideoListState> = MutableStateFlow(VideoListState.Loading)
    val state = _state.asStateFlow()

    private val _currentVideo = MutableStateFlow<Video?>(null)

    init {
        getVideos()
    }

    private fun getVideos() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.emit(VideoListState.Loading)
            videoRepository.getVideos().onSuccess {
                _state.emit(VideoListState.Success(it))
            }.onFailure {
                _state.emit(VideoListState.Error(it.message ?: "Something went wrong"))
            }
        }
    }

    fun updateVideos() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.emit(VideoListState.Loading)
            videoRepository.updateVideos().onSuccess {
                _state.emit(VideoListState.Success(it))
            }.onFailure {
                _state.emit(VideoListState.Error(it.message ?: "Something went wrong"))
            }
        }
    }

    fun setCurrentVideo(video: Video) {
        viewModelScope.launch {
            _currentVideo.emit(video)
        }
    }

    fun setCurrentVideo(mediaItem: MediaItem) {
        viewModelScope.launch {
            val video = getVideosOrEmptyList().find { it.id.toString() == mediaItem.mediaId }
            _currentVideo.emit(video)
        }
    }

    fun getMediaItems(): List<MediaItem> {
        return getVideosOrEmptyList().map {
            MediaItem.Builder().setMediaId(it.id.toString()).setUri(it.videoUrl).build()
        }
    }

    fun indexOfCurrentVideo(): Int = getVideosOrEmptyList().indexOf(_currentVideo.value)

    private fun getVideosOrEmptyList(): List<Video> {
        return (state.value as? VideoListState.Success)?.data ?: emptyList()
    }

}