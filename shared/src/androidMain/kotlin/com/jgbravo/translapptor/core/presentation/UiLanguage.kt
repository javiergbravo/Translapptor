package com.jgbravo.translapptor.core.presentation

import androidx.annotation.DrawableRes
import com.jgbravo.translapptor.R
import com.jgbravo.translapptor.core.domain.language.Language
import java.util.Locale

actual class UiLanguage(
    @DrawableRes val drawableRes: Int,
    actual val language: Language
) {

    fun toLocale(): Locale? {
        return when (language) {
            Language.ENGLISH -> Locale.ENGLISH
            Language.ARABIC -> Locale("ar", "AE")
            Language.AZERBAIJANI -> Locale("az", "AZ")
            Language.CHINESE -> Locale.CHINESE
            Language.CZECH -> Locale("cs", "CZ")
            Language.DANISH -> Locale("da", "DK")
            Language.DUTCH -> Locale("nl", "NL")
            Language.FINNISH -> Locale("fi", "FI")
            Language.FRENCH -> Locale.FRENCH
            Language.GERMAN -> Locale.GERMAN
            Language.GREEK -> Locale("el", "GR")
            Language.HEBREW -> Locale("he", "IL")
            Language.HINDI -> Locale("hi", "IN")
            Language.HUNGARIAN -> Locale("hu", "HU")
            Language.INDONESIAN -> Locale("id", "ID")
            Language.IRISH -> Locale("ga", "IE")
            Language.ITALIAN -> Locale.ITALIAN
            Language.JAPANESE -> Locale.JAPANESE
            Language.KOREAN -> Locale.KOREAN
            Language.PERSIAN -> Locale("fa", "IR")
            Language.POLISH -> Locale("pl", "PL")
            Language.PORTUGUESE -> Locale("pt", "PT")
            Language.RUSSIAN -> Locale("ru", "RU")
            Language.SLOVAK -> Locale("sk", "SK")
            Language.SWEDISH -> Locale("sv", "SE")
            Language.TURKISH -> Locale("tr", "TR")
            Language.UKRAINIAN -> Locale("uk", "UA")
            Language.SPANISH -> Locale("es", "ES")
        }
    }

    actual companion object {

        actual val allLanguages: List<UiLanguage>
            get() = Language.values().map { language ->
                UiLanguage(
                    language = language,
                    drawableRes = when (language) {
                        Language.ENGLISH -> R.drawable.ic_flag_english
                        Language.ARABIC -> R.drawable.ic_flag_arabic
                        Language.AZERBAIJANI -> R.drawable.ic_flag_azerbaijani
                        Language.CHINESE -> R.drawable.ic_flag_chinese
                        Language.CZECH -> R.drawable.ic_flag_czech
                        Language.DANISH -> R.drawable.ic_flag_danish
                        Language.DUTCH -> R.drawable.ic_flag_dutch
                        Language.FINNISH -> R.drawable.ic_flag_finnish
                        Language.FRENCH -> R.drawable.ic_flag_french
                        Language.GERMAN -> R.drawable.ic_flag_german
                        Language.GREEK -> R.drawable.ic_flag_greek
                        Language.HEBREW -> R.drawable.ic_flag_hebrew
                        Language.HINDI -> R.drawable.ic_flag_hindi
                        Language.HUNGARIAN -> R.drawable.ic_flag_hungarian
                        Language.INDONESIAN -> R.drawable.ic_flag_indonesian
                        Language.IRISH -> R.drawable.ic_flag_irish
                        Language.ITALIAN -> R.drawable.ic_flag_italian
                        Language.JAPANESE -> R.drawable.ic_flag_japanese
                        Language.KOREAN -> R.drawable.ic_flag_korean
                        Language.PERSIAN -> R.drawable.ic_flag_persian
                        Language.POLISH -> R.drawable.ic_flag_polish
                        Language.PORTUGUESE -> R.drawable.ic_flag_portuguese
                        Language.RUSSIAN -> R.drawable.ic_flag_russian
                        Language.SLOVAK -> R.drawable.ic_flag_slovak
                        Language.SPANISH -> R.drawable.ic_flag_spanish
                        Language.SWEDISH -> R.drawable.ic_flag_swedish
                        Language.TURKISH -> R.drawable.ic_flag_turkish
                        Language.UKRAINIAN -> R.drawable.ic_flag_ukrainian
                    }
                )
            }

        actual fun byCode(langCode: String): UiLanguage {
            return allLanguages.find { it.language.langCode == langCode }
                ?: throw IllegalArgumentException("Invalid or unsupported language code")
        }
    }
}