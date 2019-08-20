package wtf.qase.appskeleton.example

import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import quanti.com.kotlinlog.Log
import quanti.com.kotlinlog.android.AndroidLogger
import quanti.com.kotlinlog.base.LogLevel
import quanti.com.kotlinlog.base.LoggerBundle
import wtf.qase.appskeleton.core.BaseApp
import wtf.qase.appskeleton.example.di.appModule
import wtf.qase.appskeleton.example.di.dbModule
import wtf.qase.appskeleton.example.di.networkModule
import wtf.qase.appskeleton.example.repository.AppPreferences

class App : BaseApp() {

    private val preferences: AppPreferences by inject()

    override fun onCreate() {
        super.onCreate()

        // Use Crashlytics
        // TODO Fabric.with(this, Crashlytics())

        // Use custom logger
        Log.initialise(this)
        Log.addLogger(AndroidLogger(LoggerBundle(LogLevel.DEBUG)))
        Log.useUncheckedErrorHandler()

        if (GlobalContext.getOrNull() == null) {
            startKoin {
                androidContext(this@App)
                androidLogger()
                modules(listOf(appModule, dbModule, networkModule))
            }
        }

        preferences.init()
    }
}
