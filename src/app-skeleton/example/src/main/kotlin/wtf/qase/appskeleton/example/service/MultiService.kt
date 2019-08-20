package wtf.qase.appskeleton.example.service

import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import org.koin.android.ext.android.inject
import quanti.com.kotlinlog.Log
import wtf.qase.appskeleton.core.service.BaseForegroundService
import wtf.qase.appskeleton.core.service.Subservice
import wtf.qase.appskeleton.example.MainActivity

class MultiService : BaseForegroundService() {

    companion object {
        fun start(context: Context) {
            Log.d("-- start($context) --")
            start(context, Intent(context, MultiService::class.java)
                .putExtra(ARG_NOTIFICATION_ID, 1)
                .putExtra(ARG_CHANNEL_ID, "Example")
                .putExtra(ARG_ICON, android.R.drawable.ic_lock_lock)
                .putExtra(ARG_TITLE, "Title")
                .putExtra(ARG_DESC, "Description")
                .putExtra(ARG_ACTIVITY_CLASS, MainActivity::class.java.canonicalName)
            )
        }
    }

    inner class LocalBinder: Binder()

    private val binder: IBinder = LocalBinder()
    private val watchdogSubservice: WatchdogSubservice by inject()

    private lateinit var subservices: List<Subservice>

    override fun onBind(intent: Intent): IBinder? {
        return binder
    }

    override fun onCreate() {
        super.onCreate()
        subservices = listOf(
            watchdogSubservice
        )
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        subservices.forEach { it.start() }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        subservices.forEach { it.stop() }
        super.onDestroy()
    }
}