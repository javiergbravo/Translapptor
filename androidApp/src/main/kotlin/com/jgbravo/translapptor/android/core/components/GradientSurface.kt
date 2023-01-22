package com.jgbravo.translapptor.android.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

fun Modifier.gradientSurface(): Modifier = composed {
    if (isSystemInDarkTheme()) {
        this.background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFF23262E),
                    Color(0xFF212329)
                )
            )
        )
    } else {
        this.background(MaterialTheme.colors.surface)
    }
}