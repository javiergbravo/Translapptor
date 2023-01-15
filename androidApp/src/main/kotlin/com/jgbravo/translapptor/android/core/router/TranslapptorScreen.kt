package com.jgbravo.translapptor.android.core.router

sealed class TranslapptorScreen(val route: String) {

    object TranslateScreen : TranslapptorScreen("translate")
    object VoiceToTextScreen : TranslapptorScreen("voice_to_text")
}