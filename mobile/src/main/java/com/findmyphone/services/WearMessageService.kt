package com.findmyphone.services

import android.content.Intent
import com.findmyphone.PhoneRingingActivity
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.WearableListenerService


class WearMessageService : WearableListenerService() {

    override fun onMessageReceived(messageEvent: MessageEvent) {
        super.onMessageReceived(messageEvent)
        val isVibrateEnabled = String(messageEvent.data).toBoolean()

        when(messageEvent.path) {
            FIND_DEVICE_PATH -> {
                val intentRingDevice = Intent(this, PhoneRingingActivity::class.java)
                intentRingDevice.putExtra(ARG_VIBRATE, isVibrateEnabled)
                intentRingDevice.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intentRingDevice)
            }
        }
    }

    companion object {
        private const val FIND_DEVICE_PATH = "/find-mobile-device"
        const val ARG_VIBRATE = "ARG_VIBRATE"
    }
}