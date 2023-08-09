package com.findmyphone.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.findmyphone.datastorage.DataStoreUtil
import com.findmyphone.presentation.theme.FindMyPhoneTheme
import com.findmyphone.presentation.ui.Destinations
import com.findmyphone.presentation.ui.HomeScreen
import com.findmyphone.presentation.ui.SettingsScreen
import com.findmyphone.presentation.ui.SplashScreen
import com.findmyphone.presentation.ui.finding.FindingDeviceScreen
import com.findmyphone.presentation.viewmodel.MainViewModel
import com.google.android.gms.wearable.MessageClient
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel by viewModels<MainViewModel>()

    @Inject
    lateinit var messageClient: MessageClient

    @Inject
    lateinit var dataStorageUtil: DataStoreUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FindMyDeviceMain(dataStorageUtil)
        }

        mainViewModel.closeScreenAction.observe(this) { isClose ->
            if(isClose) onBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()
        messageClient.addListener(mainViewModel)
    }

    override fun onPause() {
        super.onPause()
        messageClient.removeListener(mainViewModel)
    }
}

@Composable
private fun FindMyDeviceMain(
    dataStoreUtil: DataStoreUtil
) {
    FindMyPhoneTheme {
        val navController = rememberSwipeDismissableNavController()
        SwipeDismissableNavHost(
            navController = navController,
            startDestination = Destinations.Splash.route,
        ) {
            composable(route = Destinations.Splash.route) {
                SplashScreen(navController = navController)
            }
            composable(route = Destinations.Home.route) {
                HomeScreen(navController = navController)
            }
            composable(route = Destinations.Settings.route) {
                SettingsScreen(dataStoreUtil = dataStoreUtil)
            }
            composable(route = Destinations.FindingDevice.route) { backStackEntry ->
                FindingDeviceScreen(
                    navController = navController,
                    nodeDeviceId = backStackEntry.arguments?.getString("deviceId").toString()
                )
            }
        }
    }
}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
private fun DefaultRoundedPreview() {
    SplashScreen()
}

@Preview(device = Devices.WEAR_OS_SQUARE, showSystemUi = true)
@Composable
private fun DefaultSquarePreview() {
    SplashScreen()
}