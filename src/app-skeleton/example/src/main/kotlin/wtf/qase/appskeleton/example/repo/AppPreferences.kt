package wtf.qase.appskeleton.example.repo

import android.content.Context
import wtf.qase.appskeleton.core.BasePreferences
import wtf.qase.appskeleton.core.BasePreferencesMigration
import java.util.TreeMap

class AppPreferences(context: Context) : BasePreferences(context, VERSION, MIGRATIONS) {

    companion object {
        const val VERSION = 2

        val MIGRATIONS = TreeMap<Int, BasePreferencesMigration>().apply {

            // Initial migration
            this[1] = BasePreferencesMigration { settings ->
                settings
                    .edit()
                    .putBoolean("first_start", true)
                    .putLong("number", 1234)
                    .apply()
            }

            // Next migration
            this[2] = BasePreferencesMigration { settings ->
                val number = settings.getLong("number", 0)
                settings.edit()
                    .remove("number")
                    .putInt("number", number.toInt())
                    .apply()
            }
        }

        private const val FIRST_START = "first_start"
        private const val NUMBER = "number"
    }

    override fun init() {
    }

    var firstStart: Boolean
        get() = settings.getBoolean(FIRST_START, false)
        set(b) = settings.edit().putBoolean(FIRST_START, b).apply()

    // For initial version only
    /*var number: Long
        get() = settings.getLong(NUMBER, 0)
        set(l) = settings.edit().putLong(NUMBER, l).apply()*/

    var number: Int
        get() = settings.getInt(NUMBER, 0)
        set(l) = settings.edit().putInt(NUMBER, l).apply()
}