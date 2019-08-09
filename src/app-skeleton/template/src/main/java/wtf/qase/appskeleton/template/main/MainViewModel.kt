package wtf.qase.appskeleton.template.main

import androidx.lifecycle.ViewModel
import wtf.qase.appskeleton.template.repo.AppPreferences

class MainViewModel(
    private val preferences: AppPreferences
) : ViewModel() {

    val prefs: String by lazy {
        preferences.toString()
    }
}
