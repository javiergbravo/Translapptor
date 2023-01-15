package com.jgbravo.translapptor.translate.presentation

import com.jgbravo.translapptor.core.presentation.UiLanguage

data class UiHistoryItem(
    val id: Long?,
    val fromLanguage: UiLanguage,
    val fromText: String,
    val toLanguage: UiLanguage,
    val toText: String
)