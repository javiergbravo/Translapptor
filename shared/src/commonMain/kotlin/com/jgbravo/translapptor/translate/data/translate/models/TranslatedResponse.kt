package com.jgbravo.translapptor.translate.data.translate.models

import kotlinx.serialization.Serializable

@Serializable
data class TranslatedResponse(
    val translatedText: String
)
