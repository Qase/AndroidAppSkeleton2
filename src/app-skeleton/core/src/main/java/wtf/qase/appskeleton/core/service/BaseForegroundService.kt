package wtf.qase.appskeleton.core.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat

abstract class BaseForegroundService : Service() {

    companion object {
        const val ARG_NOTIFICATION_ID = "notificationId"
        const val ARG_CHANNEL_ID = "channelId"
        const val ARG_ICON = "icon"
        const val ARG_TITLE = "title"
        const val ARG_DESC = "desc"
        const val ARG_ACTIVITY_CLASS = "activityClass"

        fun start(context: Context, intent: Intent) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent)
            } else {
                context.startService(intent)
            }
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        val notificationId = intent.getIntExtra(ARG_NOTIFICATION_ID, 1)
        val channelId = intent.getStringExtra(ARG_CHANNEL_ID)
        val iconResId = intent.getIntExtra(ARG_ICON, 0)
        val title = intent.getStringExtra(ARG_TITLE)
        val desc = intent.getStringExtra(ARG_DESC)
        val activityClass = Class.forName(intent.getStringExtra(ARG_ACTIVITY_CLASS))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, title, NotificationManager.IMPORTANCE_LOW)
            channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
            channel.setShowBadge(false)
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(iconResId)
            .setContentTitle(title)
            .setContentText(desc)
            .setContentIntent(PendingIntent.getActivity(
                this,
                0,
                Intent(this, activityClass),
                PendingIntent.FLAG_UPDATE_CURRENT
            ))

        // Ignore NPE on older OS versions. See
        // https://stackoverflow.com/questions/43123466/java-lang-nullpointerexception-attempt-to-invoke-interface-method-java-util-it
        val notification = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            try {
                builder.build()
            } catch (e: NullPointerException) {
                return START_STICKY
            }
        } else {
            builder.build()
        }

        startForeground(notificationId, notification)
        return START_STICKY
    }
}
