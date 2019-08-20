package wtf.qase.appskeleton.template.repo

import android.content.Context
import wtf.qase.appskeleton.core.BasePreferences
import wtf.qase.appskeleton.core.BasePreferencesMigration
import java.util.TreeMap

class AppPreferences(context: Context) : BasePreferences(context, VERSION, MIGRATIONS) {

    companion object {
        const val VERSION = 1

        val MIGRATIONS = TreeMap<Int, BasePreferencesMigration>()

        private const val COUNT = "count"
    }

    override fun init() {
    }

    var count: Int
        get() = settings.getInt(COUNT, 0)
        set(l) = settings.edit().putInt(COUNT, l).apply()
}
