package wtf.qase.appskeleton.example.service

import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import org.koin.core.KoinComponent
import org.koin.core.inject
import quanti.com.kotlinlog.Log
import wtf.qase.appskeleton.core.service.Subservice
import wtf.qase.appskeleton.example.repository.user.dto.api.UserApi
import java.util.concurrent.TimeUnit

class WatchdogSubservice : Subservice(), KoinComponent {

    private val userApi: UserApi by inject()

    override fun subscribe(): Disposable {
        return Observable
            .interval(5, TimeUnit.SECONDS)
            .doOnNext {
                Log.d("watchdog $it")
            }.concatMapSingle {
                userApi.getQuestions()
            }.subscribe({
                Log.d(it.toString())
            }, { error ->
                error.printStackTrace()
            })
    }
}
