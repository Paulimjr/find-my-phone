package com.findmyphone.presentation.ui.phone

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.Vignette
import androidx.wear.compose.material.VignettePosition
import androidx.wear.compose.material.rememberScalingLazyListState
import com.findmyphone.R
import com.findmyphone.presentation.ui.TimeTextComposable

@Composable
fun NoPhoneConnectedScreen(
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier
) {
    val scalingLazyListState = rememberScalingLazyListState()
    Scaffold(
        vignette = { Vignette(vignettePosition = VignettePosition.TopAndBottom) },
        positionIndicator = { PositionIndicator(scalingLazyListState = scalingLazyListState) },
        timeText = { TimeTextComposable() }
    ) {
        ScalingLazyColumn(
            state = scalingLazyListState,
            contentPadding = PaddingValues(
                horizontal = 8.dp,
                vertical = 22.dp
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Image(
                    painter = painterResource(id = R.drawable.no_phone_connected),
                    alignment = Alignment.Center,
                    modifier = iconModifier.size(24.dp),
                    contentDescription = "no phone connected!",
                )
            }
            item {
                Text(
                    modifier = modifier.padding(top = 8.dp, start = 12.dp, end = 12.dp),
                    text = stringResource(id = R.string.error_connect_device),
                    color = Color.White,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
private fun NoPhoneConnectedScreenRoundedPreview() {
    NoPhoneConnectedScreen()
}

@Preview(device = Devices.WEAR_OS_SQUARE, showSystemUi = true)
@Composable
private fun NoPhoneConnectedScreenSquarePreview() {
    NoPhoneConnectedScreen()
}