package com.jgbravo.translapptor.android.core.router

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jgbravo.translapptor.android.translate.presentation.AndroidTranslateViewModel
import com.jgbravo.translapptor.android.translate.presentation.TranslateScreen

@Composable
fun TranslateRoot() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = TranslapptorScreen.TranslateScreen.route
    ) {
        composable(route = TranslapptorScreen.TranslateScreen.route) {
            val viewModel = hiltViewModel<AndroidTranslateViewModel>()
            val state by viewModel.state.collectAsState()
            TranslateScreen(
                state = state,
                onEvent = viewModel::onEvent
            )
        }
    }
}