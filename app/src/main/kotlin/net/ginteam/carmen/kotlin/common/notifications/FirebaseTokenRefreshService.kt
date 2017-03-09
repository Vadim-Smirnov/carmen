package net.ginteam.carmen.kotlin.common.notifications

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService

/**
 * Created by eugene_shcherbinock on 3/9/17.
 */

class FirebaseTokenRefreshService : FirebaseInstanceIdService() {

    override fun onTokenRefresh() {
        FirebaseInstanceId.getInstance().token?.let {
            Log.d("FirebaseToken", "Token refreshed: $it")
            sendTokenToServer(it)
        }
    }

    private fun sendTokenToServer(token: String) {

    }
}