package com.jgbravo.translapptor.translate.domain.translate

import com.jgbravo.translapptor.core.domain.language.Language

interface TranslateClient {

    suspend fun translate(
        fromText: String,
        fromLanguage: Language,
        toLanguage: Language
    ): String
}