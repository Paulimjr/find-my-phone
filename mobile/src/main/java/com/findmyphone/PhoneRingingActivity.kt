package com.findmyphone

import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Vibrator
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.findmyphone.common.timeLapse
import com.findmyphone.composables.PhoneRingingScreen
import com.findmyphone.services.WearMessageService.Companion.ARG_VIBRATE
import com.google.android.gms.wearable.CapabilityClient
import com.google.android.gms.wearable.CapabilityInfo
import com.google.android.gms.wearable.MessageClient
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.Wearable
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlin.system.exitProcess

class PhoneRingingActivity : ComponentActivity(),
    CapabilityClient.OnCapabilityChangedListener,
    MessageClient.OnMessageReceivedListener
{
    private var time = 0
    private var countDownTimer: CountDownTimer? = null
    private val messageClient by lazy { Wearable.getMessageClient(this) }
    private val capabilityClient by lazy { Wearable.getCapabilityClient(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val isVibrate = intent.getBooleanExtra(ARG_VIBRATE, false)
        if (isVibrate) startVibration()

        setContent {
            PhoneRingingScreen(
                onButtonClicked = ::stopVibration
            )
        }
    }

    override fun onResume() {
        super.onResume()
        messageClient.addListener(this)
        capabilityClient.addListener(
            this,
            Uri.parse("wear://"),
            CapabilityClient.FILTER_ALL
        )
    }

    override fun onPause() {
        super.onPause()
        messageClient.removeListener(this)
        capabilityClient.removeListener(this)
    }

    private fun startVibration() {
        lifecycleScope.launch {
            time = System.currentTimeMillis().toInt()
            countDownTimer = object : CountDownTimer(60000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    time = (millisUntilFinished / 1000).toInt()
                    for (index in timeLapse.indices) {
                        if (time == timeLapse[index]) {
                            (getSystemService(VIBRATOR_SERVICE) as Vibrator).vibrate(1000)
                        }
                    }
                }
                override fun onFinish() {}
            }.start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopVibration()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        stopVibration()
    }

    private fun stopVibration() {
        countDownTimer?.cancel()

        lifecycleScope.launch {
            try {
                val nodes = capabilityClient
                    .getCapability(WEAR_CAPABILITY, CapabilityClient.FILTER_ALL)
                    .await()
                    .nodes
                nodes.firstOrNull()?.let { node ->
                    async { messageClient.sendMessage(node.id, STOP_FIND_DEVICE_ON_WATCH, byteArrayOf()).await() }
                }?.await()

                finish()
            } catch (cancellationException: CancellationException) {
                finish()
            } catch (exception: Exception) {
                finish()
            }
        }
    }

    companion object {
        private const val WEAR_CAPABILITY = "wear_find_device"
        private const val STOP_FIND_DEVICE_ON_WATCH = "/stop"
        private const val STOP_FROM_WATCH = "/stop-on-watch"
    }

    override fun onCapabilityChanged(capability: CapabilityInfo) {
        Log.v("TAG", ">>>> $capability")
    }

    override fun onMessageReceived(messageEvent: MessageEvent) {
        lifecycleScope.launch {
            if (messageEvent.path == STOP_FROM_WATCH) {
                countDownTimer?.cancel()
                finish()
            }
        }
    }
}