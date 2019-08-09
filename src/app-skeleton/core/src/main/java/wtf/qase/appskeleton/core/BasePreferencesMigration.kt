package wtf.qase.appskeleton.core

import android.content.SharedPreferences

class BasePreferencesMigration(val function: (SharedPreferences) -> Unit) {
    fun migrate(settings: SharedPreferences) {
        function(settings)
    }
}
