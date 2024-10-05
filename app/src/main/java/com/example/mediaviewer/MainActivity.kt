package com.example.mediaviewer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.mediaviewer.ui.main.MainScreen
import com.example.mediaviewer.ui.theme.MediaViewerTheme
import org.koin.compose.KoinContext


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KoinContext {
                MediaViewerTheme {
                    MainScreen()
                }
            }
        }
    }
}