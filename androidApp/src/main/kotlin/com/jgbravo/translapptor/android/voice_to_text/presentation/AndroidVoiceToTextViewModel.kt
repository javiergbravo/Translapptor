package com.jgbravo.translapptor.android.voice_to_text.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jgbravo.translapptor.voice_to_text.domain.VoiceToTextParser
import com.jgbravo.translapptor.voice_to_text.presentation.VoiceToTextEvent
import com.jgbravo.translapptor.voice_to_text.presentation.VoiceToTextViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidVoiceToTextViewModel @Inject constructor(
    private val parser: VoiceToTextParser
) : ViewModel() {

    private val viewModel by lazy {
        VoiceToTextViewModel(
            coroutineScope = viewModelScope,
            parser = parser
        )
    }

    val state = viewModel.state

    fun onEvent(event: VoiceToTextEvent) {
        viewModel.onEvent(event)
    }
}