package com.findmyphone.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.findmyphone.models.WearPaths
import com.google.android.gms.wearable.MessageClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FindingDeviceViewModel @Inject constructor(
    private val messageClient: MessageClient,
) : ViewModel() {

    fun onStopFindingDevice(nodeDeviceId: String) {
        viewModelScope.launch {
            messageClient.sendMessage(
                nodeDeviceId,
                WearPaths.STOP_ON_WATCH,
                byteArrayOf()
            )
        }
    }
}