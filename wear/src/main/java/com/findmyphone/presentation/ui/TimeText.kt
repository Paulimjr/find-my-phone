package com.findmyphone.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeSource
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.material.TimeTextDefaults

@Composable
fun TimeTextComposable(
    timeSource: TimeSource = TimeTextDefaults.timeSource(TimeTextDefaults.timeFormat()),
) {
    val timeText = timeSource.currentTime
    val leadingTextStyle = TimeTextDefaults.timeTextStyle(color = MaterialTheme.colors.primary)

    TimeText(
        startLinearContent = {
            Text(
                text = timeText,
                style = leadingTextStyle
            )
        }
    )
}

@Preview(device = Devices.WEAR_OS_SQUARE, showSystemUi = true)
@Composable
private fun PreviewTimeTextComposableSquare(
) {
    TimeTextComposable(timeSource = TimeTextDefaults.timeSource(TimeTextDefaults.timeFormat()))
}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
private fun PreviewTimeTextComposableRound(
) {
    TimeTextComposable(timeSource = TimeTextDefaults.timeSource(TimeTextDefaults.timeFormat()))
}