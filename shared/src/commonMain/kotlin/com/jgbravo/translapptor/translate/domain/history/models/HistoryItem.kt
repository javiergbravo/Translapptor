package com.jgbravo.translapptor.translate.domain.history.models

data class HistoryItem(
    val id: Long?,
    val fromLanguageCode: String,
    val fromText: String,
    val toLanguageCode: String,
    val toText: String
)
