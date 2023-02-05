package com.jgbravo.translapptor.android.translate.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jgbravo.translapptor.R.drawable
import com.jgbravo.translapptor.android.core.components.gradientSurface
import com.jgbravo.translapptor.android.core.theme.LightBlue
import com.jgbravo.translapptor.core.domain.language.Language
import com.jgbravo.translapptor.core.presentation.UiLanguage
import com.jgbravo.translapptor.translate.presentation.UiHistoryItem

@Composable
fun TranslateHistoryItem(
    item: UiHistoryItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .shadow(
                elevation = 5.dp,
                shape = RoundedCornerShape(20.dp)
            )
            .clip(RoundedCornerShape(20.dp))
            .gradientSurface()
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SmallLanguageIcon(language = item.fromLanguage)
            Spacer(modifier = Modifier.padding(16.dp))
            Text(
                text = item.fromText,
                color = LightBlue,
                style = MaterialTheme.typography.body2
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SmallLanguageIcon(language = item.toLanguage)
            Spacer(modifier = Modifier.padding(16.dp))
            Text(
                text = item.toText,
                color = MaterialTheme.colors.onSurface,
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Preview
@Composable
fun TranslateHistoryItemPreview() {
    TranslateHistoryItem(
        item = UiHistoryItem(
            id = 1,
            fromLanguage = UiLanguage(drawable.ic_flag_english, Language.ENGLISH),
            fromText = "Hello",
            toLanguage = UiLanguage(drawable.ic_flag_spanish, Language.SPANISH),
            toText = "Hola"
        ),
        onClick = {},
    )
}