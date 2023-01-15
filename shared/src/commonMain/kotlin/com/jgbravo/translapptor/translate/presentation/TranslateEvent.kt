package com.jgbravo.translapptor.translate.presentation

import com.jgbravo.translapptor.core.presentation.UiLanguage

sealed class TranslateEvent {

    data class ChooseFromLanguage(val fromLanguage: UiLanguage) : TranslateEvent()

    data class ChooseToLanguage(val toLanguage: UiLanguage) : TranslateEvent()

    object StopChoosingLanguage : TranslateEvent()

    object SwapLanguages : TranslateEvent()

    data class ChangeTranslationText(val text: String) : TranslateEvent()

    object Translate : TranslateEvent()

    object OpenFromLanguageDropDown : TranslateEvent()

    object OpenToLanguageDropDown : TranslateEvent()

    object CloseTranslation : TranslateEvent()

    data class SelectHistoryItem(val item: UiHistoryItem) : TranslateEvent()

    object EditTranslation : TranslateEvent()

    data class SubmitVoiceResult(val result: String?) : TranslateEvent()

    object OnErrorSeen : TranslateEvent()

    object RecordAudio : TranslateEvent()
}