package com.findmyphone.datastorage

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * This class is responsible to save preferences to Watch locally
 */
class DataStoreUtil(
    context: Context,
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) {

    private val preferences: SharedPreferences = context.getSharedPreferences(WATCH_PREFERENCES, Context.MODE_PRIVATE)
    private val prefsEditor = preferences.edit()

    fun saveVibrate(value: Boolean) {
        coroutineScope.launch {
            prefsEditor.putBoolean(IS_VIBRATE_ENABLED, value).apply()
        }
    }

    fun getVibrate() = preferences.getBoolean(IS_VIBRATE_ENABLED, true)

    companion object {
        private const val WATCH_PREFERENCES = "watch_preferences"
        private const val IS_VIBRATE_ENABLED = "IS_VIBRATE_ENABLED"
    }
}