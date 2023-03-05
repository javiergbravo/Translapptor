package com.jgbravo.translapptor.voice_to_text.domain

import com.jgbravo.translapptor.core.util.CommonStateFlow

interface VoiceToTextParser {

    val state: CommonStateFlow<VoiceToTextParserState>

    fun startListening(languageCode: String)

    fun stopListening()

    fun cancel()

    fun reset()
}