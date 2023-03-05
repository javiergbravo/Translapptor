package com.jgbravo.translapptor.android.voice_to_text.presentation

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun VoiceSpeechScreen(
    languageCode: String
) {
    Text(text = "Voice to text with language = $languageCode")
}