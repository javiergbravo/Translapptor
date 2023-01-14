package com.jgbravo.translapptor.translate.data.history

import com.jgbravo.translapptor.core.util.CommonFlow
import com.jgbravo.translapptor.core.util.toCommonFlow
import com.jgbravo.translapptor.database.TranslateDatabase
import com.jgbravo.translapptor.translate.domain.history.HistoryDataSource
import com.jgbravo.translapptor.translate.domain.history.models.HistoryItem
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock

class SqlDelightHistoryDataSource(
    database: TranslateDatabase
) : HistoryDataSource {

    private val queries = database.translateQueries

    override fun getHistory(): CommonFlow<List<HistoryItem>> {
        return queries
            .getHistory()
            .asFlow()
            .mapToList()
            .map { history ->
                history.map { it.toHistoryItem() }
            }
            .toCommonFlow()
    }

    override suspend fun insertHistoryItem(item: HistoryItem) {
        queries.insertHistoryEntity(
            id = item.id,
            fromLanguageCode = item.fromLanguageCode,
            fromText = item.fromText,
            toLanguageCode = item.toLanguageCode,
            toText = item.toText,
            timeStamp = Clock.System.now().toEpochMilliseconds()
        )
    }
}