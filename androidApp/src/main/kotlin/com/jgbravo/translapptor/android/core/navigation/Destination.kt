package com.jgbravo.translapptor.android.core.navigation

sealed class Destination(protected val route: String, vararg params: String) {

    val fullRoute: String = if (params.isEmpty()) {
        route
    } else {
        val builder = StringBuilder(route)
        params.forEach { param -> builder.append("/{$param}") }
        builder.toString()
    }

    sealed class NoArgumentDestination(route: String) : Destination(route) {
        operator fun invoke(): String = route
    }

    object TranslateDestination : NoArgumentDestination("translate")

    object VoiceToTextDestination : Destination("voice_to_text", "languageCode") {

        const val LANGUAGE_CODE = "languageCode"

        operator fun invoke(languageCode: String): String {
            return fullRoute.replace("{$LANGUAGE_CODE}", languageCode)
        }
    }
}