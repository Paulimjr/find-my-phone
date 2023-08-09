package com.findmyphone.presentation.ui.phone

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.CompactButton
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.findmyphone.R

@Composable
fun PhoneConnectedScreen(
    modifier: Modifier = Modifier,
    onFindDeviceClicked: () -> Unit,
    onSettingsClicked: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Chip(
            onClick = onFindDeviceClicked,
            modifier = Modifier.align(Alignment.Center),
            colors = ChipDefaults.chipColors(
                backgroundColor = MaterialTheme.colors.surface,
                contentColor = Color.White
            ),
            label = {
                Text(text = stringResource(id = R.string.tap_to_find))
            },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_phone_ring),
                    contentDescription = "find device icon",
                    modifier = Modifier
                        .size(ChipDefaults.LargeIconSize)
                        .wrapContentSize(align = Alignment.Center)
                )
            }
        )

        CompactButton(
            onClick = onSettingsClicked,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = R.color.color_app),
                contentColor = Color.White
            ),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 4.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_settings),
                contentDescription = "icon settings",
                modifier = Modifier
                    .size(ButtonDefaults.DefaultIconSize)
                    .wrapContentSize(align = Alignment.Center),
            )
        }
    }
}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
private fun PhoneConnectedScreenRoundedPreview() {
    PhoneConnectedScreen(
        onFindDeviceClicked = {},
        onSettingsClicked = {}
    )
}

@Preview(device = Devices.WEAR_OS_SQUARE, showSystemUi = true)
@Composable
private fun PhoneConnectedScreenSquarePreview() {
    PhoneConnectedScreen(
        onFindDeviceClicked = {},
        onSettingsClicked = {}
    )
}