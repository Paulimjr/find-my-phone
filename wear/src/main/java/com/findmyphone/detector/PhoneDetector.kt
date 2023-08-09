package com.findmyphone.detector

import com.findmyphone.models.DeviceInfo
import kotlinx.coroutines.flow.Flow

interface PhoneDetector {
    val phoneStatusFlow: Flow<DeviceInfo>
}