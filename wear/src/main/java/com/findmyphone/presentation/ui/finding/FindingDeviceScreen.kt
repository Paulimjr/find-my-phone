package com.findmyphone.presentation.ui.finding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Text
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.findmyphone.R
import com.findmyphone.presentation.viewmodel.FindingDeviceViewModel

@Composable
fun FindingDeviceScreen(
    viewModel: FindingDeviceViewModel = hiltViewModel(),
    navController: NavController,
    nodeDeviceId: String,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Button(
            onClick = {
                viewModel.onStopFindingDevice(nodeDeviceId)
                navController.popBackStack()
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Red,
                contentColor = Color.White,
            ),
            modifier = Modifier
                .align(Alignment.Center)
                .size(72.dp),
            shape = CircleShape,
        ) {
            Text(
                text = stringResource(id = R.string.stop),
                color = Color.White,
                textAlign = TextAlign.Center,
                fontSize = 16.sp
            )
        }
    }
}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
private fun FindingDeviceScreenRoundedPreview() {
    FindingDeviceScreen(
        navController = rememberSwipeDismissableNavController(),
        nodeDeviceId = ""
    )
}

@Preview(device = Devices.WEAR_OS_SQUARE, showSystemUi = true)
@Composable
private fun FindingDeviceScreenSquarePreview() {
    FindingDeviceScreen(
        navController = rememberSwipeDismissableNavController(),
        nodeDeviceId = ""
    )
}