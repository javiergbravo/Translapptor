package com.jgbravo.translapptor.translate.data.history

import com.jgbravo.translapptor.translate.domain.history.models.HistoryItem
import database.HistoryEntity

fun HistoryEntity.toHistoryItem(): HistoryItem = HistoryItem(
    id = id,
    fromLanguageCode = fromLanguageCode,
    fromText = fromText,
    toLanguageCode = toLanguageCode,
    toText = toText
)