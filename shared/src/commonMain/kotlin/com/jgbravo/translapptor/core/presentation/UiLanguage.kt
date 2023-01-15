package com.jgbravo.translapptor.core.presentation

import com.jgbravo.translapptor.core.domain.language.Language

expect class UiLanguage {

    val language: Language

    companion object {

        val allLanguages: List<UiLanguage>

        fun byCode(langCode: String): UiLanguage
    }
}