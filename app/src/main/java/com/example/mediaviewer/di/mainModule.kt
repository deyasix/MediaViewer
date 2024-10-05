package com.example.mediaviewer.di

import androidx.room.Room
import com.example.mediaviewer.BuildConfig
import com.example.mediaviewer.data.local.LocalVideoDataSourceImpl
import com.example.mediaviewer.data.local.db.AppDatabase
import com.example.mediaviewer.data.local.db.DatabaseContract
import com.example.mediaviewer.data.remote.VideoService
import com.example.mediaviewer.data.remote.RemoteVideoDataSourceImpl
import com.example.mediaviewer.domain.LocalVideoDataSource
import com.example.mediaviewer.domain.VideoRepository
import com.example.mediaviewer.ui.main.VideoListViewModel
import com.example.mediaviewer.domain.RemoteVideoDataSource
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val mainModule = module {
    // AppDatabase
    single<AppDatabase> {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java,
            DatabaseContract.DATABASE_NAME
        ).build()
    }
    single { get<AppDatabase>().getVideosDao() }

    // Network
    singleOf(::getOkHttpClient)
    singleOf(::getRetrofit)

    // Data
    single { get<Retrofit>().create(VideoService::class.java) }
    single<RemoteVideoDataSource> { RemoteVideoDataSourceImpl(get()) }
    single<LocalVideoDataSource> { LocalVideoDataSourceImpl(get()) }

    // Domain
    singleOf(::VideoRepository)

    // ViewModel
    viewModelOf(::VideoListViewModel)
}

private fun getRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl(BuildConfig.PEXELS_BASE_URL).client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()
}

private fun getOkHttpClient(): OkHttpClient {
    val loggingInterceptor = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.HEADERS)
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }
    return OkHttpClient.Builder().addInterceptor { chain ->
        val request = chain.request()
        chain.proceed(
            request.newBuilder().addHeader("Authorization", BuildConfig.PEXELS_API_KEY)
                .build()
        )
    }.addInterceptor(loggingInterceptor).build()
}