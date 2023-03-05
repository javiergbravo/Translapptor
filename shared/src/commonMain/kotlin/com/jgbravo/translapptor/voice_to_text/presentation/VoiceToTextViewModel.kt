package com.jgbravo.translapptor.voice_to_text.presentation

import com.jgbravo.translapptor.core.util.toCommonStateFlow
import com.jgbravo.translapptor.voice_to_text.domain.VoiceToTextParser
import com.jgbravo.translapptor.voice_to_text.presentation.VoiceToTextEvent.PermissionResult
import com.jgbravo.translapptor.voice_to_text.presentation.VoiceToTextEvent.Reset
import com.jgbravo.translapptor.voice_to_text.presentation.VoiceToTextEvent.ToggleRecording
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class VoiceToTextViewModel(
    coroutineScope: CoroutineScope? = null,
    private val parser: VoiceToTextParser
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(VoiceToTextState())
    val state = _state.combine(parser.state) { state, voiceResult ->
        state.copy(
            spokenText = voiceResult.result,
            recordError = voiceResult.error,
            displayState = when {
                voiceResult.error != null -> DisplayState.ERROR
                voiceResult.result.isNotBlank() && !voiceResult.isSpeaking -> DisplayState.DISPLAYING_RESULT
                voiceResult.isSpeaking -> DisplayState.SPEAKING
                else -> DisplayState.WAITING_TO_TALK
            }
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = VoiceToTextState()
    ).toCommonStateFlow()

    init {
        viewModelScope.launch {
            while (true) {
                if (state.value.displayState == DisplayState.SPEAKING) {
                    _state.update {
                        it.copy(
                            powerRatios = it.powerRatios + parser.state.value.powerRatio
                        )
                    }
                }
                delay(50L)
            }
        }
    }

    fun onEvent(event: VoiceToTextEvent) {
        when (event) {
            is PermissionResult -> _state.update {
                it.copy(
                    canRecord = true
                )
            }
            Reset -> {
                parser.reset()
                _state.update { VoiceToTextState() }
            }
            is ToggleRecording -> toggleRecording(event.languageCode)
            else -> Unit
        }
    }

    private fun toggleRecording(languageCode: String) {
        parser.cancel()
        if (state.value.displayState == DisplayState.SPEAKING) {
            parser.stopListening()
        } else {
            parser.startListening(languageCode)
        }
    }
}