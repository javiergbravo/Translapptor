package com.jgbravo.translapptor.android.core.router

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jgbravo.translapptor.android.translate.presentation.AndroidTranslateViewModel
import com.jgbravo.translapptor.android.translate.presentation.TranslateScreen
import com.jgbravo.translapptor.translate.presentation.TranslateEvent

@Composable
fun TranslateRoot() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = TranslapptorScreen.TranslateScreen.route
    ) {
        composable(
            route = TranslapptorScreen.TranslateScreen.route
        ) {
            val viewModel = hiltViewModel<AndroidTranslateViewModel>()
            val state by viewModel.state.collectAsState()
            TranslateScreen(
                state = state,
                onEvent = { event ->
                    when (event) {
                        is TranslateEvent.RecordAudio -> {
                            navController.navigate(
                                TranslapptorScreen.VoiceToTextScreen.route + "/${state.fromLanguage.language.langCode}"
                            )
                        }
                        else -> viewModel.onEvent(event)
                    }
                }
            )
        }
        composable(
            route = TranslapptorScreen.VoiceToTextScreen.route + "/{languageCode}",
            arguments = listOf(
                navArgument("languageCode") {
                    type = NavType.StringType
                    defaultValue = "en"
                }
            )
        ) {
            Text(text = "Voice to text with language = ${it.arguments?.getString("languageCode")}")
        }
    }
}