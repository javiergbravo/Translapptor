package com.jgbravo.translapptor.android.di

import com.jgbravo.translapptor.database.TranslateDatabase
import com.jgbravo.translapptor.translate.data.history.SqlDelightHistoryDataSource
import com.jgbravo.translapptor.translate.domain.history.HistoryDataSource
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideTranslateDatabase(
        sqlDriver: SqlDriver
    ): TranslateDatabase {
        return TranslateDatabase(driver = sqlDriver)
    }

    @Provides
    @Singleton
    fun provideHistoryDataSource(
        translateDatabase: TranslateDatabase
    ): HistoryDataSource {
        return SqlDelightHistoryDataSource(database = translateDatabase)
    }
}