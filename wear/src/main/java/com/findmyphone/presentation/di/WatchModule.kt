package com.findmyphone.presentation.di

import android.content.Context
import com.findmyphone.datastorage.DataStoreUtil
import com.findmyphone.detector.PhoneDetector
import com.findmyphone.detector.PhoneDetectorImpl
import com.google.android.gms.wearable.CapabilityClient
import com.google.android.gms.wearable.DataClient
import com.google.android.gms.wearable.MessageClient
import com.google.android.gms.wearable.NodeClient
import com.google.android.gms.wearable.Wearable
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class WatchModule {

    @Binds
    abstract fun bindPhoneDetector(phoneDetector: PhoneDetectorImpl): PhoneDetector

    companion object {

        @Provides
        fun providesCapabilityClient(@ApplicationContext context: Context) : CapabilityClient {
            return Wearable.getCapabilityClient(context)
        }

        @Provides
        fun providesDataClient(@ApplicationContext context: Context) : DataClient {
            return Wearable.getDataClient(context)
        }

        @Provides
        fun providesNodeClient(@ApplicationContext context: Context) : NodeClient {
            return Wearable.getNodeClient(context)
        }

        @Provides
        fun providesMessageClient(@ApplicationContext context: Context) : MessageClient {
            return Wearable.getMessageClient(context)
        }

        @Provides
        fun providesDataStoreUtil(@ApplicationContext context: Context) : DataStoreUtil {
            return DataStoreUtil(context)
        }

    }
}