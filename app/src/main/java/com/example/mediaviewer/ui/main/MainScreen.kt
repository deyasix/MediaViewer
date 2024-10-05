package com.example.mediaviewer.ui.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mediaviewer.ui.main.navigation.NavRoutes
import org.koin.androidx.compose.navigation.koinNavViewModel

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = NavRoutes.VIDEO_LIST.route
        ) {

            composable(route = NavRoutes.VIDEO_LIST.route) {
                val backStackEntry =
                    remember(it) { navController.getBackStackEntry(NavRoutes.VIDEO_LIST.route) }
                VideoListScreen(
                    viewModel = koinNavViewModel(viewModelStoreOwner = backStackEntry),
                    onNavigateToVideo = {
                        navController.navigate(NavRoutes.VIDEO_DETAILS.route)
                    })
            }
            composable(route = NavRoutes.VIDEO_DETAILS.route) {
                val backStackEntry =
                    remember(it) { navController.getBackStackEntry(NavRoutes.VIDEO_LIST.route) }
                VideoPlayerScreen(viewModel = koinNavViewModel(viewModelStoreOwner = backStackEntry))
            }
        }

    }
}