package net.ginteam.carmen.kotlin.common.notifications

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService
import net.ginteam.carmen.kotlin.provider.AuthProvider
import net.ginteam.carmen.kotlin.provider.AuthenticationProvider
import net.ginteam.carmen.utils.DeviceUtils

/**
 * Created by eugene_shcherbinock on 3/9/17.
 */

class FirebaseTokenRefreshService : FirebaseInstanceIdService() {

    override fun onTokenRefresh() {
        FirebaseInstanceId.getInstance().token?.let {
            Log.d("FirebaseService", "Token refreshed: $it")
            sendTokenToServer(it)
        }
    }

    private fun sendTokenToServer(token: String) {
        val authenticationProvider: AuthProvider = AuthenticationProvider
        authenticationProvider
                .deviceRegister(DeviceUtils.getDeviceId(), token, DeviceUtils.getDeviceType())
                .subscribe()
    }
}