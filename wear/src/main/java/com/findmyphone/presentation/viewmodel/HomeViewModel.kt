package com.findmyphone.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.findmyphone.datastorage.DataStoreUtil
import com.findmyphone.detector.PhoneDetector
import com.findmyphone.models.DeviceInfo
import com.findmyphone.models.WearPaths
import com.findmyphone.presentation.viewmodel.state.HomeViewState
import com.google.android.gms.wearable.MessageClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val phoneDetector: PhoneDetector,
    private val messageClient: MessageClient,
    private val dataStoreUtil: DataStoreUtil,
) : ViewModel() {

    private val _homeViewState = MutableStateFlow<HomeViewState>(HomeViewState.Loading)
    val homeViewState: StateFlow<HomeViewState> = _homeViewState

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                phoneDetector.phoneStatusFlow
                    .distinctUntilChanged()
                    .collect { mapStateDeviceInfo(it) }
            }
        }
    }

    private fun mapStateDeviceInfo(deviceInfo: DeviceInfo) {
        viewModelScope.launch {
            if (deviceInfo.nodeId != null && deviceInfo.deviceName != null) {
                _homeViewState.value = HomeViewState.PhoneConnected(deviceInfo.nodeId)
            } else {
                _homeViewState.value = HomeViewState.PhoneNoConnected
            }
        }
    }

    fun startFindDevice(deviceId: String) {
        viewModelScope.launch {

            val isVibrateEnabled = dataStoreUtil.getVibrate().toString()

            messageClient.sendMessage(
                deviceId,
                WearPaths.FIND_DEVICE_PATH,
                isVibrateEnabled.toByteArray()
            )
        }
    }
}