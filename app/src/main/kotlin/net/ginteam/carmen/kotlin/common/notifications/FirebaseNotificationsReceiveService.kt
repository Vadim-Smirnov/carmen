package net.ginteam.carmen.kotlin.common.notifications

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.support.v7.app.NotificationCompat
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import net.ginteam.carmen.R
import net.ginteam.carmen.kotlin.model.NotificationModel
import net.ginteam.carmen.kotlin.view.activity.SplashActivity


/**
 * Created by eugene_shcherbinock on 3/9/17.
 */

class FirebaseNotificationsReceiveService : FirebaseMessagingService() {

    companion object {
        private const val NOTIFICATION_ID: Int = 0
        private const val NOTIFICATION_RECEIVE_REQUEST_CODE = 125

        private const val TITLE_PARAMETER = "title"
        private const val TEXT_PARAMETER = "text"
        private const val DAYS_PARAMETER = "days"
        private const val MESSAGE_ID = "messageId"

        const val NOTIFICATION_ARGUMENT = "notification_argument"
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        Log.d("FirebaseService", "Receive notification: ${remoteMessage?.data}")
        remoteMessage?.let { showNotification(remoteMessage) }
    }

    private fun showNotification(remoteMessage: RemoteMessage) {
        val notificationModel: NotificationModel
                = getNotificationModelFromMessageData(remoteMessage.data)

        val intent = Intent(this, SplashActivity::class.java)
        intent.putExtra(NOTIFICATION_ARGUMENT, notificationModel)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(
                this, NOTIFICATION_RECEIVE_REQUEST_CODE,
                intent, PendingIntent.FLAG_ONE_SHOT)

        val defaultNotificationSound: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notification: Notification
                = NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setContentTitle(notificationModel.title)
                .setContentText(notificationModel.text)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setSound(defaultNotificationSound)
                .build()

        val notificationManager: NotificationManager
                = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    private fun getNotificationModelFromMessageData(jsonData: MutableMap<String, String>): NotificationModel {
        return NotificationModel(
                jsonData[TITLE_PARAMETER] as String,
                jsonData[TEXT_PARAMETER] as String,
                jsonData[DAYS_PARAMETER] as String,
                jsonData[MESSAGE_ID] as String)
    }
}