package com.jgbravo.translapptor.translate.presentation

import com.jgbravo.translapptor.core.domain.language.Language
import com.jgbravo.translapptor.core.presentation.UiLanguage
import com.jgbravo.translapptor.translate.domain.TranslateError

data class TranslateState(
    val fromText: String = "",
    val toText: String? = null,
    val isTranslating: Boolean = false,
    val fromLanguage: UiLanguage = UiLanguage.byCode(Language.ENGLISH.langCode),
    val toLanguage: UiLanguage = UiLanguage.byCode(Language.SPANISH.langCode),
    val isChoosingFromLanguage: Boolean = false,
    val isChoosingToLanguage: Boolean = false,
    val error: TranslateError? = null,
    val history: List<UiHistoryItem> = emptyList()
)