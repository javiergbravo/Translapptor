package com.jgbravo.translapptor.android.translate.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jgbravo.translapptor.R.drawable
import com.jgbravo.translapptor.android.core.theme.LightBlue
import com.jgbravo.translapptor.core.domain.language.Language
import com.jgbravo.translapptor.core.presentation.UiLanguage

@Composable
fun LanguageDisplay(
    language: UiLanguage,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        SmallLanguageIcon(language = language)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = language.language.langName,
            color = LightBlue
        )
    }
}

@Preview
@Composable
fun LanguageDisplayPreview() {
    LanguageDisplay(
        language = UiLanguage(drawable.ic_flag_english, Language.ENGLISH)
    )
}