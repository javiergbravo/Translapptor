package com.jgbravo.translapptor.android.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.jgbravo.translapptor.android.core.navigation.Destination.TranslateDestination
import com.jgbravo.translapptor.android.core.navigation.Destination.VoiceToTextDestination
import com.jgbravo.translapptor.android.core.navigation.components.DestinationNavHost
import com.jgbravo.translapptor.android.core.navigation.components.destinationComposable
import com.jgbravo.translapptor.android.translate.presentation.AndroidTranslateViewModel
import com.jgbravo.translapptor.android.translate.presentation.TranslateScreen
import com.jgbravo.translapptor.android.voice_to_text.presentation.AndroidVoiceToTextViewModel
import com.jgbravo.translapptor.android.voice_to_text.presentation.VoiceToTextScreen
import com.jgbravo.translapptor.core.domain.language.Language.ENGLISH
import com.jgbravo.translapptor.translate.presentation.TranslateEvent
import com.jgbravo.translapptor.voice_to_text.presentation.VoiceToTextEvent

@Composable
fun TranslapptorNavigator() {
    val navController = rememberNavController()

    DestinationNavHost(
        navController = navController,
        startDestination = TranslateDestination,
    ) {
        destinationComposable(
            destination = TranslateDestination
        ) { backStackEntry ->
            val viewModel = hiltViewModel<AndroidTranslateViewModel>()
            val state by viewModel.state.collectAsState()

            val voiceResult by backStackEntry.savedStateHandle
                .getStateFlow<String?>(VoiceToTextDestination.VOICE_RESULT, null)
                .collectAsState()
            LaunchedEffect(voiceResult) {
                viewModel.onEvent((TranslateEvent.SubmitVoiceResult(voiceResult)))
                backStackEntry.savedStateHandle[VoiceToTextDestination.VOICE_RESULT] = null
            }

            TranslateScreen(
                state = state,
                onEvent = { event ->
                    when (event) {
                        is TranslateEvent.RecordAudio -> {
                            navController.navigate(
                                VoiceToTextDestination(state.fromLanguage.language.langCode)
                            )
                        }
                        else -> viewModel.onEvent(event)
                    }
                }
            )
        }
        destinationComposable(
            destination = VoiceToTextDestination
        ) { backStackEntry ->
            val viewModel = hiltViewModel<AndroidVoiceToTextViewModel>()
            val state by viewModel.state.collectAsState()

            val languageCode = backStackEntry.arguments?.getString(VoiceToTextDestination.LANGUAGE_CODE)

            VoiceToTextScreen(
                state = state,
                languageCode = languageCode ?: ENGLISH.langCode,
                onResult = { spokenText ->
                    navController.previousBackStackEntry?.savedStateHandle?.set(
                        key = VoiceToTextDestination.VOICE_RESULT,
                        value = spokenText
                    )
                    navController.popBackStack()
                },
                onEvent = { event ->
                    when (event) {
                        VoiceToTextEvent.Close -> navController.popBackStack()
                        else -> viewModel.onEvent(event)
                    }
                }
            )
        }
    }
}