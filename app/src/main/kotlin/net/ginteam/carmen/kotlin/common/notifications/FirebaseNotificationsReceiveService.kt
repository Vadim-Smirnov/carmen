package net.ginteam.carmen.kotlin.common.notifications

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import android.net.Uri
import android.support.v7.app.NotificationCompat
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import net.ginteam.carmen.R

/**
 * Created by eugene_shcherbinock on 3/9/17.
 */

class FirebaseNotificationsReceiveService : FirebaseMessagingService() {

    private val mNotificationId: Int = 0

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        Log.d("FCMReceiver", "Receive notification: $remoteMessage")
        remoteMessage?.let { showNotification(remoteMessage) }
    }

    private fun showNotification(remoteMessage: RemoteMessage) {
        val defaultNotificationSound: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notification: Notification
                = NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle(remoteMessage.notification.title)
                .setContentText(remoteMessage.notification.body)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setSound(defaultNotificationSound)
                .build()

        val notificationManager: NotificationManager
                = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(mNotificationId, notification)
    }
}