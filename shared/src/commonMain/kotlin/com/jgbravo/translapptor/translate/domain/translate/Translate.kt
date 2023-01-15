package com.jgbravo.translapptor.translate.domain.translate

import com.jgbravo.translapptor.core.domain.language.Language
import com.jgbravo.translapptor.core.util.Resource
import com.jgbravo.translapptor.translate.domain.TranslateException
import com.jgbravo.translapptor.translate.domain.history.HistoryDataSource
import com.jgbravo.translapptor.translate.domain.history.models.HistoryItem

class Translate(
    private val client: TranslateClient,
    private val historyDataSource: HistoryDataSource
) {

    suspend fun execute(
        fromText: String,
        fromLanguage: Language,
        toLanguage: Language
    ): Resource<String> {
        return try {
            val translatedText = client.translate(
                fromText = fromText,
                fromLanguage = fromLanguage,
                toLanguage = toLanguage
            )
            historyDataSource.insertHistoryItem(
                HistoryItem(
                    id = null,
                    fromLanguageCode = fromLanguage.langCode,
                    fromText = fromText,
                    toLanguageCode = toLanguage.langCode,
                    toText = translatedText
                )
            )
            Resource.Success(translatedText)
        } catch (e: TranslateException) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }
}