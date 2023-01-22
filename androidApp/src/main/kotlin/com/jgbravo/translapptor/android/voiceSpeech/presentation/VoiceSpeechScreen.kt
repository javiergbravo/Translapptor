package com.jgbravo.translapptor.android.voiceSpeech.presentation

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun VoiceSpeechScreen(
    languageCode: String
) {
    Text(text = "Voice to text with language = $languageCode")
}