package com.findmyphone.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.findmyphone.models.WearPaths.STOP_FIND_DEVICE
import com.google.android.gms.wearable.MessageClient
import com.google.android.gms.wearable.MessageEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel(), MessageClient.OnMessageReceivedListener {

    private val _closeScreenAction = MutableLiveData(false)
    val closeScreenAction: LiveData<Boolean> = _closeScreenAction

    override fun onMessageReceived(messageEvent: MessageEvent) {
        viewModelScope.launch {
            if (messageEvent.path == STOP_FIND_DEVICE) {
                _closeScreenAction.value = true
            }
        }
    }

}