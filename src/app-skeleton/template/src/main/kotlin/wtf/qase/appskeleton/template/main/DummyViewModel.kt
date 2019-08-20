package wtf.qase.appskeleton.template.main

import androidx.lifecycle.ViewModel
import wtf.qase.appskeleton.template.repository.AppPreferences

class DummyViewModel(
    private val preferences: AppPreferences
) : ViewModel() {

    var count: Int = preferences.count

    fun increment(): Int {
        return set(count + 1)
    }

    fun reset(): Int {
        return set(0)
    }

    private fun set(value: Int): Int {
        count = value
        preferences.count = value
        return value
    }
}
