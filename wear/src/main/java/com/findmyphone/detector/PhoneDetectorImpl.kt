package com.findmyphone.detector

import com.findmyphone.models.DeviceInfo
import com.findmyphone.models.WearPaths
import com.google.android.gms.wearable.CapabilityClient
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PhoneDetectorImpl @Inject constructor(
    private val capabilityClient: CapabilityClient
) : PhoneDetector {

    override val phoneStatusFlow: Flow<DeviceInfo>
        get() = capabilityClient.getPhoneStatusFlow()

    private fun CapabilityClient.getPhoneStatusFlow(): Flow<DeviceInfo> {
        return callbackFlow {
            val initialNodes = try {
                getCapability(WearPaths.PHONE_APP_CAPABILITY, CapabilityClient.FILTER_ALL).await().nodes
            } catch (e: Exception) {
                trySend(DeviceInfo(nodeId = null, deviceName = null))
                return@callbackFlow
            }

            val initialPhoneStatus = if (initialNodes.isEmpty()) {
                DeviceInfo(nodeId = null, deviceName = null)
            } else {
                val device = initialNodes.firstOrNull()
                DeviceInfo(nodeId = device?.id, deviceName = device?.displayName)
            }

            trySend(initialPhoneStatus)
            val listener = CapabilityClient.OnCapabilityChangedListener {
                val phoneStatus = if (it.nodes.isEmpty()) {
                    DeviceInfo(nodeId = null, deviceName = null)
                } else {
                    val device = initialNodes.firstOrNull()
                    DeviceInfo(nodeId = device?.id, deviceName = device?.displayName)
                }
                trySend(phoneStatus)
            }
            addListener(listener, WearPaths.PHONE_APP_CAPABILITY)
            awaitClose { removeListener(listener) }
        }
    }
}