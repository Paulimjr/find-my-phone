package com.findmyphone.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.Switch
import androidx.wear.compose.material.SwitchDefaults
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.ToggleChip
import androidx.wear.compose.material.ToggleChipDefaults
import androidx.wear.compose.material.rememberScalingLazyListState
import com.findmyphone.R
import com.findmyphone.datastorage.DataStoreUtil

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    dataStoreUtil: DataStoreUtil
) {
    val scalingLazyListState = rememberScalingLazyListState()
    ScalingLazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        state = scalingLazyListState,
        contentPadding = PaddingValues(
            top = 2.dp,
            bottom = 16.dp
        ),
    ) {
        item {
            Text(text = "Settings", textAlign = TextAlign.Center, color = Color.White)
        }
        item {
            ToggleEnableSound(dataStoreUtil)
        }
    }
}

@Composable
private fun ToggleEnableSound(
    dataStoreUtil: DataStoreUtil
) {
    var checked by remember { mutableStateOf(dataStoreUtil.getVibrate()) }

    ToggleChip(
        label = {
            Text(text = stringResource(id = R.string.vibrate))
        },
        secondaryLabel = {
            Text(
                text = stringResource(id = R.string.title_vibrate),
                overflow = TextOverflow.Ellipsis,
            )
        },
        checked = checked,
        colors = ToggleChipDefaults.toggleChipColors(
            uncheckedContentColor = Color.White,
            checkedStartBackgroundColor = colorResource(id = R.color.color_app),
            checkedEndBackgroundColor = colorResource(id = R.color.color_app),
            checkedContentColor = Color.White
        ),
        toggleControl = {
            Switch(
                checked = checked,
                enabled = true,
                colors = SwitchDefaults.colors(
                    checkedTrackColor = Color.LightGray,
                    checkedThumbColor = Color.White,
                ),
                modifier = Modifier.semantics {
                    this.contentDescription = if (checked) "On" else "Off"
                },
            )
        },
        onCheckedChange = {
                dataStoreUtil.saveVibrate(it)
                checked = it },
        appIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_music_note),
                contentDescription = stringResource(id = R.string.vibrate_text),
                modifier = Modifier
                    .size(24.dp)
                    .wrapContentSize(align = Alignment.Center),
            )
        },
        enabled = true,
    )
}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
private fun PreviewLookingScreen(
) {
    //SettingsScreen(da)
}

@Preview(device = Devices.WEAR_OS_SQUARE, showSystemUi = true)
@Composable
private fun PreviewLookingScreenSquare(
) {
    // SettingsScreen()
}