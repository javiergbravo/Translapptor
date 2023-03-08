package com.jgbravo.translaptor.translate.data.local

import com.jgbravo.translapptor.core.util.CommonFlow
import com.jgbravo.translapptor.core.util.toCommonFlow
import com.jgbravo.translapptor.translate.domain.history.HistoryDataSource
import com.jgbravo.translapptor.translate.domain.history.models.HistoryItem
import kotlinx.coroutines.flow.MutableStateFlow

class FakeHistoryDataSource : HistoryDataSource {

    private val _data = MutableStateFlow<List<HistoryItem>>(emptyList())

    override fun getHistory(): CommonFlow<List<HistoryItem>> {
        return _data.toCommonFlow()
    }

    override suspend fun insertHistoryItem(historyItem: HistoryItem) {
        _data.value += historyItem
    }
}