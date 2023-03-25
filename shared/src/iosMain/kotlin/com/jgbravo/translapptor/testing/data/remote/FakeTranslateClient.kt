package com.jgbravo.translapptor.testing.data.remote

import com.jgbravo.translapptor.core.domain.language.Language
import com.jgbravo.translapptor.translate.domain.translate.TranslateClient

class FakeTranslateClient : TranslateClient {

    var translatedText = "test translation"

    override suspend fun translate(fromText: String, fromLanguage: Language, toLanguage: Language): String {
        return translatedText
    }
}