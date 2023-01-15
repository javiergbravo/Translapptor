package com.jgbravo.translapptor.translate.presentation

import com.jgbravo.translapptor.core.presentation.UiLanguage
import com.jgbravo.translapptor.core.util.Resource.Error
import com.jgbravo.translapptor.core.util.Resource.Success
import com.jgbravo.translapptor.core.util.toCommonStateFlow
import com.jgbravo.translapptor.translate.domain.TranslateException
import com.jgbravo.translapptor.translate.domain.history.HistoryDataSource
import com.jgbravo.translapptor.translate.domain.translate.Translate
import com.jgbravo.translapptor.translate.presentation.TranslateEvent.ChangeTranslationText
import com.jgbravo.translapptor.translate.presentation.TranslateEvent.ChooseFromLanguage
import com.jgbravo.translapptor.translate.presentation.TranslateEvent.ChooseToLanguage
import com.jgbravo.translapptor.translate.presentation.TranslateEvent.CloseTranslation
import com.jgbravo.translapptor.translate.presentation.TranslateEvent.EditTranslation
import com.jgbravo.translapptor.translate.presentation.TranslateEvent.OnErrorSeen
import com.jgbravo.translapptor.translate.presentation.TranslateEvent.OpenFromLanguageDropDown
import com.jgbravo.translapptor.translate.presentation.TranslateEvent.OpenToLanguageDropDown
import com.jgbravo.translapptor.translate.presentation.TranslateEvent.SelectHistoryItem
import com.jgbravo.translapptor.translate.presentation.TranslateEvent.StopChoosingLanguage
import com.jgbravo.translapptor.translate.presentation.TranslateEvent.SubmitVoiceResult
import com.jgbravo.translapptor.translate.presentation.TranslateEvent.SwapLanguages
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch

class TranslateViewModel(
    private val coroutineScope: CoroutineScope?,
    private val translate: Translate,
    private val historyDataSource: HistoryDataSource
) {

    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(TranslateState())
    val state = combine(
        _state,
        historyDataSource.getHistory()
    ) { state, history ->
        if (state.history != history) {
            state.copy(
                history = history.mapNotNull {
                    UiHistoryItem(
                        id = it.id ?: return@mapNotNull null,
                        fromText = it.fromText,
                        fromLanguage = UiLanguage.byCode(it.fromLanguageCode),
                        toText = it.toText,
                        toLanguage = UiLanguage.byCode(it.toLanguageCode)
                    )
                }
            )
        } else {
            state
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = TranslateState()
    ).toCommonStateFlow()

    private var translateJob: Job? = null

    fun onEvent(event: TranslateEvent) {
        when (event) {
            is ChangeTranslationText -> _state.update {
                it.copy(
                    fromText = event.text
                )
            }
            is ChooseFromLanguage -> _state.update {
                it.copy(
                    isChoosingFromLanguage = false,
                    fromLanguage = event.fromLanguage
                )
            }
            is ChooseToLanguage -> {
                val newState = _state.updateAndGet {
                    it.copy(
                        isChoosingToLanguage = false,
                        toLanguage = event.toLanguage
                    )
                }
                translate(newState)
            }
            CloseTranslation -> _state.update {
                it.copy(
                    isTranslating = false,
                    fromText = "",
                    toText = null,
                )
            }
            EditTranslation -> {
                if (state.value.toText == null) return
                _state.update {
                    it.copy(
                        toText = null,
                        isTranslating = false,
                    )
                }
            }
            OnErrorSeen -> _state.update {
                it.copy(
                    error = null
                )
            }
            OpenFromLanguageDropDown -> _state.update {
                it.copy(
                    isChoosingFromLanguage = true
                )
            }
            OpenToLanguageDropDown -> _state.update {
                it.copy(
                    isChoosingToLanguage = true
                )
            }
            //RecordAudio ->
            is SelectHistoryItem -> {
                translateJob?.cancel()
                _state.update {
                    it.copy(
                        fromText = event.item.fromText,
                        fromLanguage = event.item.fromLanguage,
                        toText = event.item.toText,
                        toLanguage = event.item.toLanguage,
                    )
                }
            }
            StopChoosingLanguage -> _state.update {
                it.copy(
                    isChoosingFromLanguage = false,
                    isChoosingToLanguage = false
                )
            }
            is SubmitVoiceResult -> _state.update {
                it.copy(
                    fromText = event.result ?: it.fromText,
                    isTranslating = if (!event.result.isNullOrBlank()) {
                        false
                    } else {
                        it.isTranslating
                    },
                    toText = if (!event.result.isNullOrBlank()) {
                        null
                    } else {
                        it.toText
                    }
                )
            }
            SwapLanguages -> _state.update {
                it.copy(
                    fromLanguage = it.toLanguage,
                    toLanguage = it.fromLanguage,
                    fromText = it.toText ?: "",
                    toText = if (it.toText == null) {
                        null
                    } else {
                        it.fromText
                    }
                )
            }
            TranslateEvent.Translate -> translate(state.value)
            else -> Unit
        }
    }

    private fun translate(state: TranslateState) {
        if (state.isTranslating || state.fromText.isBlank()) {
            return
        }
        translateJob = viewModelScope.launch {
            _state.update {
                it.copy(isTranslating = true)
            }
            val result = translate.execute(
                fromText = state.fromText,
                fromLanguage = state.fromLanguage.language,
                toLanguage = state.toLanguage.language
            )
            when (result) {
                is Success -> _state.update {
                    it.copy(
                        isTranslating = false,
                        toText = result.data
                    )
                }
                is Error -> _state.update {
                    it.copy(
                        isTranslating = false,
                        error = (result.throwable as? TranslateException)?.error
                    )
                }
            }
        }
    }
}