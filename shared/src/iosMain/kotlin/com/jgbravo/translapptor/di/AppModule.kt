package com.jgbravo.translapptor.di

import com.jgbravo.translapptor.database.TranslateDatabase
import com.jgbravo.translapptor.translate.data.history.SqlDelightHistoryDataSource
import com.jgbravo.translapptor.translate.data.local.DatabaseDriverFactory
import com.jgbravo.translapptor.translate.data.remote.HttpClientFactory
import com.jgbravo.translapptor.translate.data.translate.KtorTranslateClient
import com.jgbravo.translapptor.translate.domain.history.HistoryDataSource
import com.jgbravo.translapptor.translate.domain.translate.Translate
import com.jgbravo.translapptor.translate.domain.translate.TranslateClient

class AppModule {

    val historyDataSource: HistoryDataSource by lazy {
        SqlDelightHistoryDataSource(
            database = TranslateDatabase(
                driver = DatabaseDriverFactory().create()
            )
        )
    }

    private val translateClient: TranslateClient by lazy {
        KtorTranslateClient(
            httpClient = HttpClientFactory().create()
        )
    }

    val translateUseCase: Translate by lazy {
        Translate(
            client = translateClient,
            historyDataSource = historyDataSource
        )
    }
}