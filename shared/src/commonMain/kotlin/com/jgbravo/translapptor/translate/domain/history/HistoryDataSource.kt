package com.jgbravo.translapptor.translate.domain.history

import com.jgbravo.translapptor.core.util.CommonFlow
import com.jgbravo.translapptor.translate.domain.history.models.HistoryItem

interface HistoryDataSource {

    fun getHistory(): CommonFlow<List<HistoryItem>>

    suspend fun insertHistoryItem(item: HistoryItem)
}