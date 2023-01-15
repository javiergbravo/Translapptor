package com.jgbravo.translapptor.core.presentation

import com.jgbravo.translapptor.core.domain.language.Language

actual class UiLanguage(
    actual val language: Language,
    val imageName: String
) {

    actual companion object {

        actual val allLanguages: List<UiLanguage>
            get() = Language.values().map { language ->
                UiLanguage(
                    language = language,
                    imageName = "ic_flag_${language.langName.lowercase()}"
                )
            }

        actual fun byCode(langCode: String): UiLanguage {
            return allLanguages.first { it.language.langCode == langCode }
        }
    }
}