package com.jgbravo.translapptor.di

import com.jgbravo.translapptor.testing.data.local.FakeHistoryDataSource
import com.jgbravo.translapptor.testing.data.remote.FakeTranslateClient
import com.jgbravo.translapptor.testing.domain.FakeVoiceToTextParser
import com.jgbravo.translapptor.translate.domain.history.HistoryDataSource
import com.jgbravo.translapptor.translate.domain.translate.Translate
import com.jgbravo.translapptor.translate.domain.translate.TranslateClient
import com.jgbravo.translapptor.voice_to_text.domain.VoiceToTextParser

class TestAppModule : AppModule {

    override val historyDataSource: HistoryDataSource = FakeHistoryDataSource()
    override val translateClient: TranslateClient = FakeTranslateClient()
    override val translateUseCase: Translate = Translate(
        client = translateClient,
        historyDataSource = historyDataSource
    )
    override val voiceParser: VoiceToTextParser = FakeVoiceToTextParser()
}