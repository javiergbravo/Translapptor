package com.jgbravo.translapptor.di

import com.jgbravo.translapptor.translate.domain.history.HistoryDataSource
import com.jgbravo.translapptor.translate.domain.translate.Translate
import com.jgbravo.translapptor.translate.domain.translate.TranslateClient
import com.jgbravo.translapptor.voice_to_text.domain.VoiceToTextParser

interface AppModule {

    val historyDataSource: HistoryDataSource
    val translateClient: TranslateClient
    val translateUseCase: Translate
    val voiceParser: VoiceToTextParser
}