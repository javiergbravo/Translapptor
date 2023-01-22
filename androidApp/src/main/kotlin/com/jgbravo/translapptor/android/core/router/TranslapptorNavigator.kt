package com.jgbravo.translapptor.android.core.router

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.jgbravo.translapptor.android.core.router.Destination.TranslateDestination
import com.jgbravo.translapptor.android.core.router.Destination.VoiceToTextDestination
import com.jgbravo.translapptor.android.core.router.components.DestinationNavHost
import com.jgbravo.translapptor.android.core.router.components.destinationComposable
import com.jgbravo.translapptor.android.translate.presentation.AndroidTranslateViewModel
import com.jgbravo.translapptor.android.translate.presentation.TranslateScreen
import com.jgbravo.translapptor.android.voiceSpeech.presentation.VoiceSpeechScreen
import com.jgbravo.translapptor.core.domain.language.Language.ENGLISH
import com.jgbravo.translapptor.translate.presentation.TranslateEvent.RecordAudio

@Composable
fun TranslapptorNavigator() {
    val navController = rememberNavController()

    DestinationNavHost(
        navController = navController,
        startDestination = TranslateDestination,
    ) {
        destinationComposable(
            destination = TranslateDestination
        ) {
            val viewModel = hiltViewModel<AndroidTranslateViewModel>()
            val state by viewModel.state.collectAsState()
            TranslateScreen(
                state = state,
                onEvent = { event ->
                    when (event) {
                        is RecordAudio -> {
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
        ) {
            val languageCode = it.arguments?.getString(VoiceToTextDestination.LANGUAGE_CODE)
            VoiceSpeechScreen(languageCode = languageCode ?: ENGLISH.langCode)
        }
    }
}