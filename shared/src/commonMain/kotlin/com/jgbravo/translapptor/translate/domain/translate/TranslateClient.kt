package com.jgbravo.translapptor.translate.domain.translate

import com.jgbravo.translapptor.core.domain.language.Language

interface TranslateClient {

    suspend fun translate(
        textToTranslate: String,
        fromLanguage: Language,
        toLanguage: Language
    ): String
}