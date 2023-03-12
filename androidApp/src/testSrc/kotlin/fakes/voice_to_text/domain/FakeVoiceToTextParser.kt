package fakes.voice_to_text.domain

import com.jgbravo.translapptor.core.util.CommonStateFlow
import com.jgbravo.translapptor.core.util.toCommonStateFlow
import com.jgbravo.translapptor.voice_to_text.domain.VoiceToTextParser
import com.jgbravo.translapptor.voice_to_text.domain.VoiceToTextParserState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class FakeVoiceToTextParser : VoiceToTextParser {

    private val _state = MutableStateFlow(VoiceToTextParserState())
    var voiceResult = "test result"

    override val state: CommonStateFlow<VoiceToTextParserState>
        get() = _state.toCommonStateFlow()

    override fun startListening(languageCode: String) {
        _state.update {
            it.copy(
                result = "",
                isSpeaking = true
            )
        }
    }

    override fun stopListening() {
        _state.update {
            it.copy(
                result = voiceResult,
                isSpeaking = false
            )
        }
    }

    override fun cancel() = Unit

    override fun reset() {
        _state.value = VoiceToTextParserState()
    }
}