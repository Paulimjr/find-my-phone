package com.findmyphone.presentation.viewmodel.state

sealed class HomeViewState {
    object Loading : HomeViewState()
    object PhoneNoConnected : HomeViewState()
    data class PhoneConnected(val nodeDeviceId: String?) : HomeViewState()
}
