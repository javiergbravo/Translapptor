package com.jgbravo.translapptor.android.translate.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.jgbravo.translapptor.R.drawable
import com.jgbravo.translapptor.core.domain.language.Language
import com.jgbravo.translapptor.core.presentation.UiLanguage

@Composable
fun SmallLanguageIcon(
    language: UiLanguage,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = language.drawableRes,
        contentDescription = language.language.langName,
        modifier = modifier.size(25.dp)
    )
}

@Preview
@Composable
fun SmallLanguageIconPreview() {
    SmallLanguageIcon(
        language = UiLanguage(drawable.ic_flag_english, Language.ENGLISH)
    )
}