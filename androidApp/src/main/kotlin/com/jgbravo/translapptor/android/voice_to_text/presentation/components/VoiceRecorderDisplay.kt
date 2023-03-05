package com.jgbravo.translapptor.android.voice_to_text.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jgbravo.translapptor.android.core.components.gradientSurface
import kotlin.math.PI
import kotlin.math.sin
import kotlin.random.Random

@Composable
fun VoiceRecorderDisplay(
    powerRatios: List<Float>,
    modifier: Modifier = Modifier
) {
    val primary = MaterialTheme.colors.primary
    Box(
        modifier = modifier
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(20.dp)
            )
            .clip(RoundedCornerShape(20.dp))
            .gradientSurface()
            .padding(
                horizontal = 32.dp,
                vertical = 8.dp
            )
            .drawBehind {
                val powerRationWidth = 3.dp.toPx()
                val powerRationCount = (size.width / (2 * powerRationWidth)).toInt()

                clipRect(
                    left = 0f,
                    top = 0f,
                    right = size.width,
                    bottom = size.height
                ) {
                    powerRatios
                        .takeLast(powerRationCount)
                        .reversed()
                        .forEachIndexed { index, ratio ->
                            val yTopStart = center.y - (size.height / 2f) * ratio
                            drawRoundRect(
                                color = primary,
                                topLeft = Offset(
                                    x = size.width - index * 2 * powerRationWidth,
                                    y = yTopStart
                                ),
                                size = Size(
                                    width = powerRationWidth,
                                    height = (center.y - yTopStart) * 2f
                                ),
                                cornerRadius = CornerRadius(100f)
                            )
                        }
                }
            }
    )
}

@Preview
@Composable
fun VoiceRecorderDisplayPreview() {
    Column {
        Row {
            VoiceRecorderDisplay(
                powerRatios = (0..50).map {
                    val percent = it / 100f
                    sin(percent * 2 * PI).toFloat()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Row {
            VoiceRecorderDisplay(
                powerRatios = (0..150).map {
                    Random.nextFloat()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            )
        }
    }
}