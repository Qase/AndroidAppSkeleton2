package wtf.qase.appskeleton.example.main.settings

import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import wtf.qase.appskeleton.example.repo.AppPreferences
import wtf.qase.appskeleton.example.repo.api.UserApi
import wtf.qase.appskeleton.example.repo.dto.LoginRequest

class SettingsViewModel(
    private val preferences: AppPreferences,
    private val userApi: UserApi
) : ViewModel() {

    val prefs: String by lazy {
        "${preferences.firstStart}, ${preferences.number} as ${preferences.number::class.java}"
    }

    fun login(username: String, password: String) = userApi
        .login(LoginRequest(username, password))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    fun logout() = userApi
        .logout()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}
