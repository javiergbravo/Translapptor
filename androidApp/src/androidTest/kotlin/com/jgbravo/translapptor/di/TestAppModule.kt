package com.jgbravo.translapptor.di

import com.jgbravo.translapptor.translate.data.local.FakeHistoryDataSource
import com.jgbravo.translapptor.translate.data.remote.FakeTranslateClient
import com.jgbravo.translapptor.translate.domain.history.HistoryDataSource
import com.jgbravo.translapptor.translate.domain.translate.Translate
import com.jgbravo.translapptor.translate.domain.translate.TranslateClient
import com.jgbravo.translapptor.voice_to_text.domain.FakeVoiceToTextParser
import com.jgbravo.translapptor.voice_to_text.domain.VoiceToTextParser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Singleton
    fun provideFakeTranslateClient(): TranslateClient = FakeTranslateClient()

    @Provides
    @Singleton
    fun provideFakeHistoryDataSource(): HistoryDataSource = FakeHistoryDataSource()

    @Provides
    @Singleton
    fun provideTranslateUseCase(
        client: TranslateClient,
        dataSource: HistoryDataSource
    ): Translate {
        return Translate(
            client = client,
            historyDataSource = dataSource
        )
    }

    @Provides
    @Singleton
    fun provideFakeVoiceToTextParser(): VoiceToTextParser = FakeVoiceToTextParser()
}