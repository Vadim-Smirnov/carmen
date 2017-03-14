package net.ginteam.carmen.kotlin.model

import java.io.Serializable

/**
 * Created by eugene_shcherbinock on 3/13/17.
 */

data class NotificationModel(val title: String,
                             val text: String,
                             val days: String,
                             val messageId: String): Serializable