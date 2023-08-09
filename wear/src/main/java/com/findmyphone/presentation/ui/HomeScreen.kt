package com.findmyphone.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.findmyphone.presentation.ui.loading.LoadingScreen
import com.findmyphone.presentation.ui.phone.NoPhoneConnectedScreen
import com.findmyphone.presentation.ui.phone.PhoneConnectedScreen
import com.findmyphone.presentation.viewmodel.HomeViewModel
import com.findmyphone.presentation.viewmodel.state.HomeViewState
import kotlinx.coroutines.flow.StateFlow


@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    HomeContent(
        viewState = homeViewModel.homeViewState,
        onFindDeviceClicked = { deviceId ->
            homeViewModel.startFindDevice(deviceId.toString())
            navController.navigate("finding_screen/$deviceId")
        },
        onSettingsClicked = {
            navController.navigate(Destinations.Settings.route)
        }
    )
}

@Composable
fun HomeContent(
    viewState: StateFlow<HomeViewState>,
    onFindDeviceClicked: (String?) -> Unit,
    onSettingsClicked: () -> Unit,
) {
    when(val state = viewState.collectAsState().value) {
        is HomeViewState.Loading -> LoadingScreen()
        is HomeViewState.PhoneNoConnected -> NoPhoneConnectedScreen()
        is HomeViewState.PhoneConnected -> {
            PhoneConnectedScreen(
                onFindDeviceClicked = { onFindDeviceClicked.invoke(state.nodeDeviceId) },
                onSettingsClicked = onSettingsClicked
            )
        }
    }
}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
private fun PreviewHomeScreen(
) {
    HomeScreen(navController = rememberSwipeDismissableNavController())
}