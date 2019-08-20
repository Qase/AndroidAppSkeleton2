package wtf.qase.appskeleton.core

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import java.util.TreeMap

abstract class BasePreferences(context: Context, version: Int, migrations: TreeMap<Int, BasePreferencesMigration>) {

    companion object {
        private const val SETTINGS_VERSION = "settings.version"
    }

    protected val settings: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    init {
        val prevVersion = settings.getInt(SETTINGS_VERSION, 0)
        if (prevVersion < version) {
            Log.d("core.preferences", "upgrade preferences: from $prevVersion to $version")

            migrations
                .entries
                .filter { it.key in (prevVersion + 1)..version }
                .forEach { entry ->
                    Log.d("core.preferences", "applying migration for version ${entry.key}")
                    entry.value.migrate(settings)
                }

            settings
                .edit()
                .putInt(SETTINGS_VERSION, version)
                .apply()
        }
    }

    abstract fun init()
}
